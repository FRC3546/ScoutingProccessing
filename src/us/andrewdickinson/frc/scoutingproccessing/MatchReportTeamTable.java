package us.andrewdickinson.frc.scoutingproccessing;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

/**
 * Created by Andrew on 3/6/16.
 */
public class MatchReportTeamTable {
    PdfPTable pdfPTable;

    Font small = new Font(Font.FontFamily.TIMES_ROMAN, 8.0f);
    Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 8.0f, Font.BOLD);

    public MatchReportTeamTable(PdfPTable table){
        pdfPTable = table;
    }

    public PdfPTable exportTable(){
        return pdfPTable;
    }

    public void addTask(String taskDescription, boolean claim){
        addRow(new ReportRow(taskDescription, claim, "N/A"));
    }

    public void addTask(String taskDescription, boolean claim, String data){
        addRow(new ReportRow(taskDescription, claim, data));
    }

    public void addTask(String taskDescription, CrossCapability cross_claim, String data){
        addRow(new ReportRow(taskDescription, cross_claim != CrossCapability.NotAble, cross_claim.name(), data, true));
    }

    public void addRow(ReportRow reportRow){
        if (reportRow.boldReport()){
            addBoldRow(reportRow.getRowEntries(), reportRow.isAboutADefense());
        } else if (reportRow.highlightReport()) {
            addRowHighlightedData(reportRow.getRowEntries(), reportRow.isAboutADefense());
        } else {
            addRow(reportRow.getRowEntries(), reportRow.isAboutADefense());
        }
    }

    private void addRowHighlightedData(String[] text, boolean aboutADefense){
        for (int i = 0; i < 2; i++){
            addCell(text[i], aboutADefense);
        }
        addHighlightedCell(text[2], aboutADefense);
    }

    private void addRowHighlightedData(String[] text){
        addRowHighlightedData(text, false);
    }

    private void addBoldRow(String[] text){
        addBoldRow(text, false);
    }

    private void addBoldRow(String[] text, boolean aboutDefense){
        for (int i = 0; i < 3; i++){
            addBoldCell(text[i], aboutDefense);
        }
    }

    private void addRow(String[] text, boolean aboutADefense){
        for (int i = 0; i < 3; i++){
            addCell(text[i], aboutADefense);
        }
    }

    public void addRow(String[] text){
        addRow(text, false);
    }

    private void addCell(String text, boolean aboutADefense){
        addCell(text, small, aboutADefense);
    }

    private void addCell(String text){
        addCell(text, small, false);
    }

    private void addHighlightedCell(String text, boolean aboutADefense){
        String shortened = DataDefinitions.getInstance().getMatchReportReplacements().replaceInString(text);
        Chunk ch = new Chunk(shortened, small);
        ch.setBackground(BaseColor.GRAY);
        PdfPCell c = new PdfPCell(new Phrase(ch));
        addCell(c, aboutADefense);
    }

    private void addHighlightedCell(String text){
        addHighlightedCell(text, false);
    }

    private void addBoldCell(String text, boolean aboutADefense){
        addCell(text, smallBold, aboutADefense);
    }

    private void addBoldCell(String text){
        addBoldCell(text, false);
    }

    private void addCell(String text, Font f, boolean aboutADefense){
        String shortened = DataDefinitions.getInstance().getMatchReportReplacements().replaceInString(text);
        PdfPCell c = new PdfPCell(new Phrase(shortened, f));
        addCell(c, aboutADefense);
    }

    private void addCell(String text, Font f){
        addCell(text, f, false);
    }

    private void addCell(PdfPCell c, boolean aboutADefense){
        c.setPadding(4);
        if (aboutADefense) c.setGrayFill(.85f);
        pdfPTable.addCell(c);
    }

    private void addCell(PdfPCell c){
        addCell(c, false);
    }
}
