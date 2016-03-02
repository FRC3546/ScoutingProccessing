package us.andrewdickinson.frc.scoutingproccessing;

import com.itextpdf.text.DocumentException;

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

    public void generateMatchPDF() throws IOException, DocumentException{
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
