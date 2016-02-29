package us.andrewdickinson.frc.scoutingproccessing;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Andrew on 2/28/16.
 */
public class MatchReport {
    ArrayList<TeamReport> redTeams;
    ArrayList<TeamReport> blueTeams;

    public MatchReport(HashMap<Integer, Team> allTeams, int match){
        TBACommunication tbaCommunication = TBACommunication.getInstance();
        int[] redTeams = tbaCommunication.getRedTeams(match);
        int[] blueTeams = tbaCommunication.getBlueTeams(match);

        for (int teamNum : redTeams){
            this.redTeams.add(new TeamReport(allTeams.get(teamNum)));
        }

        for (int teamNum : blueTeams){
            this.blueTeams.add(new TeamReport(allTeams.get(teamNum)));
        }
    }
}
