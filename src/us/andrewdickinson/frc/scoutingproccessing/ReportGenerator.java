package us.andrewdickinson.frc.scoutingproccessing;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Created by Andrew on 3/1/16.
 */
public class ReportGenerator {
    private PDFGenerator pdfGenerator;
    private TeamReport teamReport;
    private String path;

    public ReportGenerator(TeamReport tr, String path) throws DocumentException, IOException{
        this.teamReport = tr;
        this.path = path + teamReport.getTeamNumber() + ".pdf";
        pdfGenerator = new PDFGenerator(this.path, tr);
        pdfGenerator.addHeader();
    }

    public void addTask(String taskDescription, boolean claim){
        pdfGenerator.addRow(new ReportRow(taskDescription, claim, "N/A"));
    }

    public void addTask(String taskDescription, boolean claim, String data){
        pdfGenerator.addRow(new ReportRow(taskDescription, claim, data));
    }

    public void addTask(String taskDescription, CrossCapability cross_claim, String data){
        pdfGenerator.addRow(new ReportRow(taskDescription, cross_claim != CrossCapability.NotAble, cross_claim.name(), data, true));
    }

    public void generate() throws IOException, DocumentException{
        pdfGenerator.startTable();
        addTask("Reach Defense (Auto)", teamReport.getClaimAutoCanReachDefense(), teamReport.getReachedDefenseSummary());
        addTask("Low Bar (Auto)", teamReport.getClaimAutoCanCrossLowBar(), teamReport.getAutoLowBarSummary());
        addTask("Cross Other Defenses (Auto)", teamReport.getClaimAutoCanCrossOtherDefenses(), teamReport.getAutoCrossedDefenseSummary());
        addTask("Score Low (Auto)", teamReport.getClaimAutoCanScoreLow(), teamReport.getAutoScoreLowSummary());
        addTask("Score High (Auto)", teamReport.getClaimAutoCanScoreHigh(), teamReport.getAutoScoreHighSummary());

        SuccessTable<Defense, CrossAction> successTable;
        HashMap<Defense, CrossCapability> claimsMap;

        successTable = teamReport.getTeleopDefenseSuccessTable();
        claimsMap = teamReport.getTeleopDefenseClaimsMap();

        Arrays.asList(Defense.class.getEnumConstants())
                .forEach(defense ->
                        addTask(defense.name().replace("_", " ") + " (Teleop)",
                                claimsMap.get(defense),
                                ReportStrings.getPerformanceSummary(successTable.getFrequencyMap(defense))));

        addTask("Can Score Low? (Teleop)", teamReport.getClaimTeleopBoulderLowGoal(), teamReport.getTeleopScoreLowSummary());
        addTask("Can Score High? (Teleop)", teamReport.getClaimTeleopBoulderHighGoal(), teamReport.getTeleopScoreHighSummary());

        addTask("Can Herd Boulder on Ground?", teamReport.getClaimTeleopBoulderHerd());
        addTask("Can Pick Up Boulder from Ground?", teamReport.getClaimTeleopBoulderPickup());

        addTask("Can Challenge?", teamReport.getClaimTeleopEndgameCanChallenge(), teamReport.getChallengeSummary());
        addTask("Can Scale?", teamReport.getClaimTeleopEndgameCanScale(), teamReport.getScaleSummary());

        addTask("Rotates Drivers?", teamReport.getClaimRotatesDrivers());
        pdfGenerator.addRow(new ReportRow(
                "Driver Experience",
                true,
                teamReport.getClaimDriverExperience().name().replace("_", " "),
                teamReport.getDriverCoordinationSummary(),
                false
        ));

        pdfGenerator.addRow(new ReportRow(
                "Plays Defense Well?",
                false,
                "N/A",
                teamReport.getDefensePlayedSummary(),
                false
        ));

        pdfGenerator.finishTable();
        pdfGenerator.addFooter();
        pdfGenerator.export();
    }

    public static void mergeAndSortTeams(String path, String out, ArrayList<Integer> teams) throws IOException, DocumentException{
        teams.sort(Integer::compare);
        mergeTeams(path, out, teams);
    }

    public static void mergeTeams(String path, String out, ArrayList<Integer> teams) throws IOException, DocumentException {
        Document document = new Document();
        PdfCopy copy = new PdfCopy(document, new FileOutputStream(out));
        document.open();
        for (Integer team : teams){
            PdfReader this_page = new PdfReader(path + team + ".pdf");
            copy.addDocument(this_page);
            this_page.close();
        }

        document.close();
    }
}
