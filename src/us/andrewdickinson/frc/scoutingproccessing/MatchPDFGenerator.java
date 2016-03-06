package us.andrewdickinson.frc.scoutingproccessing;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.sun.deploy.util.StringUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by Andrew on 3/6/16.
 */
public class MatchPDFGenerator {
    private MatchReport matchReport;
    private Document document;
    private PdfWriter writer;
    private PdfPTable teamsTable;

    Font small = new Font(Font.FontFamily.TIMES_ROMAN, 11.0f);
    Font smallItal = new Font(Font.FontFamily.TIMES_ROMAN, 11.0f, Font.ITALIC);
    Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 11.0f, Font.BOLD);

    public MatchPDFGenerator(String path, MatchReport matchReport, Alliance alliance) throws IOException, DocumentException {
        this.document = new Document(PageSize.LETTER_LANDSCAPE.rotate());
        writer = PdfWriter.getInstance(document, new FileOutputStream(path));
        writer.setMargins(0,0,0,0);
        document.open();

        document.add(new Phrase("Match " + matchReport.getMatch() + " - " + alliance + " Alliance", smallBold));

        teamsTable = new PdfPTable(3);

        teamsTable.setWidthPercentage(100);
        teamsTable.setSpacingBefore(10f);
        teamsTable.setSpacingAfter(0f);

        this.matchReport = matchReport;
    }

    public void addLogo() throws IOException, DocumentException{
        Image img = Image.getInstance("img.jpg");
        img.scaleToFit(110, 110);
        img.setAbsolutePosition(625, 535);
//        img.scaleToFit(600, 500);
//        img.setAbsolutePosition(125, 100);

        PdfContentByte canvas = writer.getDirectContentUnder();
//        PdfGState state = new PdfGState();
//        state.setFillOpacity(.7f);
//        canvas.saveState();
//        canvas.setGState(state);
        canvas.addImage(img);
//        canvas.restoreState();
    }

    public void addTeam(TeamReport teamReport) throws DocumentException, IOException {
        MatchReportTeamTable teamTable = startIndividualTeamTable();

        SuccessTable<Defense, CrossAction> successTable;
        HashMap<Defense, CrossCapability> claimsMap;

        successTable = teamReport.getTeleopDefenseSuccessTable();
        claimsMap = teamReport.getTeleopDefenseClaimsMap();

        Arrays.asList(Defense.class.getEnumConstants())
                .forEach(defense ->
                        teamTable.addTask("Teleop " + defense.name().replace("_", " "),
                                claimsMap.get(defense),
                                ReportStrings.getPerformanceSummary(successTable.getFrequencyMap(defense))));

        teamTable.addTask("Auto Reach", teamReport.getClaimAutoCanReachDefense(), teamReport.getReachedDefenseSummary());
        teamTable.addTask("Auto Low Bar", teamReport.getClaimAutoCanCrossLowBar(), teamReport.getAutoLowBarSummary());
        teamTable.addTask("Auto Other D", teamReport.getClaimAutoCanCrossOtherDefenses(), teamReport.getAutoCrossedDefenseSummary());
        teamTable.addTask("Auto Low", teamReport.getClaimAutoCanScoreLow(), teamReport.getAutoScoreLowSummary());
        teamTable.addTask("Auto High", teamReport.getClaimAutoCanScoreHigh(), teamReport.getAutoScoreHighSummary());

        teamTable.addTask("Teleop Low", teamReport.getClaimTeleopBoulderLowGoal(), teamReport.getTeleopScoreLowSummary());
        teamTable.addTask("Teleop High", teamReport.getClaimTeleopBoulderHighGoal(), teamReport.getTeleopScoreHighSummary());

        teamTable.addTask("Herd Boulder", teamReport.getClaimTeleopBoulderHerd());
        teamTable.addTask("Pick Up Boulder", teamReport.getClaimTeleopBoulderPickup());

        teamTable.addTask("Challenge", teamReport.getClaimTeleopEndgameCanChallenge(), teamReport.getChallengeSummary());
        teamTable.addTask("Scale", teamReport.getClaimTeleopEndgameCanScale(), teamReport.getScaleSummary());

        teamTable.addTask("Rot. Drivers", teamReport.getClaimRotatesDrivers());
        teamTable.addRow(new ReportRow(
                "Driver XP",
                true,
                teamReport.getClaimDriverExperience().name().replace("_", " "),
                teamReport.getDriverCoordinationSummary(),
                false
        ));

        teamTable.addRow(new ReportRow(
                "Defense?",
                false,
                "N/A",
                teamReport.getDefensePlayedSummary(),
                false
        ));

        addIndividualTeamTable(teamTable, teamReport);
    }

    private MatchReportTeamTable startIndividualTeamTable() throws DocumentException {
        PdfPTable table = new PdfPTable(new float[]{.865f,.635f,1.677f});

        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(0f);

        MatchReportTeamTable teamTable = new MatchReportTeamTable(table);
        teamTable.addRow(new String[]{"Task", "Claim", "Data"});

        return teamTable;
    }

    private void addIndividualTeamTable(MatchReportTeamTable table, TeamReport teamReport) throws DocumentException, IOException {
        PdfPCell cell = new PdfPCell();
        cell.addElement(getTeamHeader(teamReport));
        cell.addElement(table.exportTable());
        cell.addElement(new Phrase("Comments: " + StringUtils.join(teamReport.getComments(), "; "), small));
        cell.setPadding(4);
        cell.setBorderColor(BaseColor.WHITE);

        teamsTable.addCell(cell);
    }

    private Phrase getTeamHeader(TeamReport teamReport) throws DocumentException, IOException {
        int team_number = teamReport.getTeamNumber();
        int rank = TBACommunication.getInstance().getRank(team_number);
        int matches = teamReport.getMatchesPlayed();

        Phrase phrase = new Phrase();
        phrase.add(new Chunk(Integer.toString(team_number), smallBold));

        phrase.add(new Chunk(" (" + rank + ")", small));
        phrase.add(new Chunk(" (" + matches + ")", small));
        phrase.add(new Chunk(" - ", small));

        phrase.add(new Chunk(teamReport.getDriveTrain() + " ("
                + teamReport.getGroundClearance() + "\")\n", small));

        ArrayList<Integer> midLineCrossings = teamReport.getMatchesWhereRobotCrossedMidline();
        if (midLineCrossings.size() == 0){
            phrase.add(new Chunk(ReportStrings.neverCrossedMidline, smallItal));
        } else {
            phrase.add(new Chunk("Crossed In: " + midLineCrossings, small));
        }

        return phrase;
    }

    public void export() throws DocumentException{
        PdfPCell cell = new PdfPCell(new Phrase("No Data", small));
        cell.setPadding(4);
        cell.setBorderColor(BaseColor.WHITE);

        int needed_fill;
        if (teamsTable.size() > 0){
            needed_fill = 3 - teamsTable.getRow(0).getCells().length;
        } else {
            needed_fill = 3;
        }

        for (int i = 0; i < needed_fill; i++){
            teamsTable.addCell(cell);
        }
        document.add(teamsTable);
        document.close();
    }
}
