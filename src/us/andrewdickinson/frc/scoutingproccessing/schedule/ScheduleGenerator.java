package us.andrewdickinson.frc.scoutingproccessing.schedule;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import us.andrewdickinson.frc.scoutingproccessing.DataDefinitions;
import us.andrewdickinson.frc.scoutingproccessing.Driver;
import us.andrewdickinson.frc.scoutingproccessing.TBACommunication;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.List;

/**
 * Created by Andrew on 3/8/16.
 */
public class ScheduleGenerator {
    private TBACommunication tba;
    private ScoutingSchedule scoutingSchedule;

    public static final boolean RANDOM_ANEALING_ENABLED = true;

    public ScheduleGenerator(int scouts_per_match){
        tba = TBACommunication.getInstance();
        scoutingSchedule = new ScoutingSchedule(tba, scouts_per_match);
    }

    public ScheduleGenerator(String savedPath) throws IOException, ClassNotFoundException {
        tba = TBACommunication.getInstance();
        FileInputStream fis = new FileInputStream(savedPath);
        ObjectInputStream in = new ObjectInputStream(fis);
        scoutingSchedule = (ScoutingSchedule) in.readObject();
        in.close();
        fis.close();
    }

    public void generate(double max_score_allowed){
        int j = 1;
        while (scoutingSchedule.getScore() > max_score_allowed){
            System.out.println("Generating schedule... " + "(Round " + j + ")");
            for (int i = 0; i < 1000; i++) {
                runRound();
            }
            j++;
        }
    }

    private void runRound(){
        double original_score = scoutingSchedule.getScore();

        double average_frequency = scoutingSchedule.getAverageMatchesPerTeam();

        List<Integer> teamsToTryReorganizing = scoutingSchedule.getTeamsOverAverage();
        for (int team : teamsToTryReorganizing){
            for (int i = 1; i < scoutingSchedule.numberOfMatches() + 1; i++){
                if (Arrays.stream(scoutingSchedule.getMatch(i)).anyMatch(t -> t == team)) {
                    Integer last_replacement = null;
                    int[] replacements = scoutingSchedule.getTeamReplacementOptions(i);
                    for (int replacement : replacements){
                        if (last_replacement == null) {
                            last_replacement = replacement;
                            scoutingSchedule.replaceTeamInMatch(i, team, replacement);
                        } else {
                            scoutingSchedule.replaceTeamInMatch(i, last_replacement, replacement);
                        }

                        double loop_score = scoutingSchedule.getScore();
                        if (loop_score < original_score
                                || randomMapDeltaToKeep(loop_score - original_score)){
                                    original_score = loop_score;
                        } else {
                            //Undo the change we just made
                            last_replacement = null;
                            scoutingSchedule.replaceTeamInMatch(i, replacement, team);
                        }
                    }
                }
            }
        }
    }

    public void export(String path) throws DocumentException, IOException{
        FileOutputStream fileOutputStream = new FileOutputStream(path);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(scoutingSchedule);
        objectOutputStream.close();
        fileOutputStream.close();
    }

    public void createPDF(String path) throws DocumentException, IOException{
        Document pdfDocument = new Document();
        PdfWriter writer = PdfWriter.getInstance(pdfDocument, new FileOutputStream(path));
        writer.setMargins(0, 0, 0, 0);
        pdfDocument.open();

        Font reg = new Font(Font.FontFamily.TIMES_ROMAN, 18.0f);
        pdfDocument.add(new Phrase("Scouting Schedule - " + DataDefinitions.TBAConnection.eventCode, reg));

        PdfPTable table = scoutingSchedule.getPDFTable();
        table.setWidthPercentage(100);
        pdfDocument.add(table);

        pdfDocument.close();
    }

    public boolean randomMapDeltaToKeep(double delta){
        double probability = Math.pow(Math.E, (-100 * Math.abs(delta) - 1));
        if (RANDOM_ANEALING_ENABLED){
            return Math.random() < probability;
        } else {
            return false;
        }
    }

    public ScoutingSchedule getScoutingSchedule() {
        return scoutingSchedule;
    }
}
