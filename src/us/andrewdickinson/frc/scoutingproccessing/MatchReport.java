package us.andrewdickinson.frc.scoutingproccessing;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;

import java.io.FileOutputStream;
import java.io.IOException;
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

    public MatchReport(HashMap<Integer, Team> allTeams, int match){
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

    public int getMatch() {
        return match;
    }

    public void generateMatchPDF() throws  IOException, DocumentException {
        MatchPDFGenerator generatorRed = new MatchPDFGenerator("reports/matchsum/" + match + "_red.pdf", this, Alliance.Red);
        for (TeamReport teamReport : redTeams){
            generatorRed.addTeam(teamReport);
        }

        generatorRed.addLogo();
        generatorRed.export();

        MatchPDFGenerator generatorBlue = new MatchPDFGenerator("reports/matchsum/" + match + "_blue.pdf", this, Alliance.Blue);
        for (TeamReport teamReport : blueTeams){
            generatorBlue.addTeam(teamReport);
        }

        generatorBlue.addLogo();
        generatorBlue.export();

        Document document = new Document();
        PdfCopy copy = new PdfCopy(document, new FileOutputStream("reports/matchsum/" + match + ".pdf"));
        document.open();

        PdfReader red = new PdfReader("reports/matchsum/" + match + "_red.pdf");
        copy.addDocument(red);
        red.close();

        PdfReader blue = new PdfReader("reports/matchsum/" + match + "_blue.pdf");
        copy.addDocument(blue);
        blue.close();

        document.close();
    }

    public void generateMatchTeamsPDF() throws IOException, DocumentException{
        for (TeamReport teamReport : redTeams) {
            teamReport.generatePDF("reports/team/");
        }

        for (TeamReport teamReport : blueTeams) {
            teamReport.generatePDF("reports/team/");
        }

        List<Integer> bothAlliances = IntStream.concat(redTeams.stream().mapToInt(TeamReport::getTeamNumber),
                blueTeams.stream().mapToInt(TeamReport::getTeamNumber)).boxed().collect(Collectors.toList());

        ReportGenerator.mergeTeams("reports/team/", "reports/match/" + match + ".pdf", new ArrayList<>(bothAlliances));
    }
}
