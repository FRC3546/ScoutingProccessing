package us.andrewdickinson.frc.scoutingproccessing;

/**
 * Created by Andrew on 3/1/16.
 */
public class ReportRow {
    private String taskDescription;
    private boolean claimed;
    private String claim_text;
    private boolean aboutADefense;
    private String dataReport;

    public ReportRow(String taskDescription, boolean claimed, String dataReport) {
        this(taskDescription, claimed, "Yes", dataReport, false);
    }

    public ReportRow(String taskDescription, boolean claimed, String claim_text, String dataReport, boolean aboutADefense) {
        this.taskDescription = taskDescription;
        this.claimed = claimed;
        this.claim_text = claim_text;
        this.dataReport = dataReport;
        this.aboutADefense = aboutADefense;
    }

    public String[] getRowEntries(){
        if (claimed || (!claim_text.equals("Yes") && !aboutADefense)) {
            return new String[]{taskDescription, claim_text, dataReport};
        } else {
            if (dataReport.contains(ReportStrings.neverDemonstrated) && !claimed) {
                return new String[]{taskDescription, "No", "N/A"};
            } else {
                return new String[]{taskDescription, "No", dataReport};
            }
        }
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public String getClaimText() {
        return claim_text;
    }

    public boolean isClaimed() {
        return claimed;
    }

    public String getDataReport() {
        return dataReport;
    }

    public boolean highlightReport(){
        return dataReport.contains(ReportStrings.neverDemonstrated) && claimed;
    }

    public boolean boldReport(){
        return !claimed && aboutADefense;
    }

    public boolean isAboutADefense() {
        return aboutADefense;
    }
}
