package us.andrewdickinson.frc.scoutingproccessing;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.sun.deploy.util.StringUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Andrew on 3/1/16.
 */
public class PDFGenerator {
    private TeamReport teamReport;
    private Document document;
    private PdfWriter writer;
    private PdfPTable table;

    Font bold = new Font(Font.FontFamily.TIMES_ROMAN, 18.0f, Font.BOLD);
    Font reg = new Font(Font.FontFamily.TIMES_ROMAN, 18.0f);
    Font small = new Font(Font.FontFamily.TIMES_ROMAN, 11.0f);
    Font smallItal = new Font(Font.FontFamily.TIMES_ROMAN, 11.0f, Font.ITALIC);
    Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 11.0f, Font.BOLD);
    Font ital = new Font(Font.FontFamily.TIMES_ROMAN, 18.0f, Font.ITALIC);

    public PDFGenerator(String path, TeamReport teamReport) throws IOException, DocumentException {
        this.document = new Document();
        writer = PdfWriter.getInstance(document, new FileOutputStream(path));
        document.open();

        this.teamReport = teamReport;
    }

    public void addHeader() throws DocumentException, IOException {
        int team_number = teamReport.getTeamNumber();
        int rank = 24; //TODO: Automate
        int matches = teamReport.getMatchesPlayed();

        addPhrase(Integer.toString(team_number), bold);
        addPhrase(" Rank #" + rank, reg);
        addPhrase(" (" + matches + " matches played)", ital);

        addTextLine("Pit Scout: " + teamReport.getPitScout(), small);
        addTextLine(teamReport.getDriveTrain() + " ("
                + teamReport.getGroundClearance() + "\" ground clearance)", small);

        ArrayList<Integer> midLineCrossings = teamReport.getMatchesWhereRobotCrossedMidline();
        if (midLineCrossings.size() == 0){
            addTextLine(ReportStrings.neverCrossedMidline, smallItal);
        } else {
            addTextLine("Crossed midline in matches: " + midLineCrossings, small);
        }

        Image img = Image.getInstance("img.jpg");
        img.scaleToFit(150, 150);
        img.setAbsolutePosition(400, 740);

        PdfContentByte canvas = writer.getDirectContentUnder();
        canvas.addImage(img);
    }

    public void startTable() throws DocumentException {
        table = new PdfPTable(new float[]{2.9f,1.26f,3.906f});

        table.setWidthPercentage(100);
        table.setSpacingBefore(0f);
        table.setSpacingAfter(0f);

        addRow(new String[]{"Task","Claim","Data"});
    }

    public void finishTable() throws DocumentException {
        document.add(table);
    }

    public void addFooter() throws DocumentException{
        addTextLine("Comments: " + StringUtils.join(teamReport.getComments(), "; "), small);
        addTextLine("Match Scouts: " + StringUtils.join(teamReport.getMatchScouts(), "; "), small);
    }

    private void addPhrase(String text, Font f) throws DocumentException{
        document.add(new Phrase(text, f));
    }

    private void addTextLine(String text, Font f) throws DocumentException{
        document.add(new Phrase("\n" + text, f));
    }

    public void addRow(ReportRow reportRow){
        if (reportRow.boldReport()){
            addBoldRow(reportRow.getRowEntries());
        } else if (reportRow.highlightReport()) {
            addRowHighlightedData(reportRow.getRowEntries());
        } else {
            addRow(reportRow.getRowEntries());
        }
    }

    private void addRowHighlightedData(String[] text){
        for (int i = 0; i < 2; i++){
            addCell(text[i]);
        }
        addHighlightedCell(text[2]);
    }

    private void addBoldRow(String[] text){
        for (int i = 0; i < 3; i++){
            addBoldCell(text[i]);
        }
    }

    private void addRow(String[] text){
        for (int i = 0; i < 3; i++){
            addCell(text[i]);
        }
    }

    private void addCell(String text){
        addCell(text, small);
    }

    private void addHighlightedCell(String text){
        Chunk ch = new Chunk(text, small);
        ch.setBackground(BaseColor.YELLOW);
        PdfPCell c = new PdfPCell(new Phrase(ch));
        addCell(c);
    }

    private void addBoldCell(String text){
        addCell(text, smallBold);
    }

    private void addCell(String text, Font f){
        PdfPCell c = new PdfPCell(new Phrase(text, f));
        addCell(c);
    }

    private void addCell(PdfPCell c){
        c.setPadding(7);
        table.addCell(c);
    }

    public void export() throws DocumentException{
        document.close();
    }
}
