package us.andrewdickinson.frc.scoutingproccessing;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by Andrew on 2/28/16.
 */
public class MatchReport {
    int match;
    ArrayList<TeamReport> redTeams;
    ArrayList<TeamReport> blueTeams;

    public MatchReport(ArrayList<TeamReport> redTeams, ArrayList<TeamReport> blueTeams){
        this.redTeams = redTeams;
        this.blueTeams = blueTeams;
    }

    public MatchReport(TreeMap<Integer, Team> allTeams, int match){
        this.match = match;
        TBACommunication tbaCommunication = TBACommunication.getInstance();
        if (!tbaCommunication.scheduleGenerated()) throw new IllegalStateException("Match schedule not generated yet. Query is meaningless");
        int[] redTeams = tbaCommunication.getRedTeams(match);
        int[] blueTeams = tbaCommunication.getBlueTeams(match);

        this.redTeams = new ArrayList<>();
        this.blueTeams = new ArrayList<>();

        for (int teamNum : redTeams){
            if (allTeams.get(teamNum) != null) this.redTeams.add(new TeamReport(allTeams.get(teamNum)));
        }

        for (int teamNum : blueTeams){
            if (allTeams.get(teamNum) != null) this.blueTeams.add(new TeamReport(allTeams.get(teamNum)));
        }
    }

    public MatchReport(TreeMap<Integer, Team> allTeams, int alliance, boolean isElimAlliance){
        if (!isElimAlliance) throw new IllegalArgumentException();
        this.match = 0;

        TBACommunication tbaCommunication = TBACommunication.getInstance();
        int[] teams = tbaCommunication.getAllianceTeams(alliance);

        this.redTeams = new ArrayList<>();
        this.blueTeams = new ArrayList<>();

        if (teams.length == 3) {
            for (int teamNum : teams){
                if (allTeams.get(teamNum) != null) this.redTeams.add(new TeamReport(allTeams.get(teamNum)));
            }
        } else if (teams.length == 4) {
            for (int i = 0; i < 3; i++){
                if (allTeams.get(teams[i]) != null) this.redTeams.add(new TeamReport(allTeams.get(teams[i])));
            }

            if (allTeams.get(3) != null) this.blueTeams.add(new TeamReport(allTeams.get(teams[3])));
        } else {
            throw new IllegalArgumentException();
        }
    }

    public int getMatch() {
        return match;
    }

    public void generateMatchPDF(String path) throws  IOException, DocumentException {
        String outputPath = path + match + ".pdf";
        String directory = path;

        if (path.contains(".pdf")){
            outputPath = path;

            directory = new File(path).getAbsoluteFile().getParentFile().getAbsolutePath();
        }
        MatchPDFGenerator generatorRed = new MatchPDFGenerator(directory + match + "_red.pdf", this, Alliance.Red);
        for (TeamReport teamReport : redTeams){
            generatorRed.addTeam(teamReport);
        }

        generatorRed.addLogo();
        generatorRed.export();

        MatchPDFGenerator generatorBlue = new MatchPDFGenerator(directory + match + "_blue.pdf", this, Alliance.Blue);
        for (TeamReport teamReport : blueTeams){
            generatorBlue.addTeam(teamReport);
        }

        generatorBlue.addLogo();
        generatorBlue.export();

        Document document = new Document();
        PdfCopy copy = new PdfCopy(document, new FileOutputStream(outputPath));
        document.open();

        PdfReader red = new PdfReader(directory + match + "_red.pdf");
        copy.addDocument(red);
        red.close();

        PdfReader blue = new PdfReader(directory + match + "_blue.pdf");
        copy.addDocument(blue);
        blue.close();

        new File(directory + match + "_red.pdf").delete();
        new File(directory + match + "_blue.pdf").delete();

        document.close();
    }

    public void generateMatchTeamsPDF(String path) throws IOException, DocumentException{
        String outputPath = path + match + ".pdf";
        String directory = path;

        if (path.contains(".pdf")){
            outputPath = path;

            directory = new File(path).getAbsoluteFile().getParentFile().getAbsolutePath();
        }

        for (TeamReport teamReport : redTeams) {
            teamReport.generatePDF(directory);
        }

        for (TeamReport teamReport : blueTeams) {
            teamReport.generatePDF(directory);
        }

        List<Integer> bothAlliances = IntStream.concat(redTeams.stream().mapToInt(TeamReport::getTeamNumber),
                blueTeams.stream().mapToInt(TeamReport::getTeamNumber)).boxed().collect(Collectors.toList());

        ReportGenerator.mergeTeams(directory, outputPath, new ArrayList<>(bothAlliances));
    }
}
