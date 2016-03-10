package us.andrewdickinson.frc.scoutingproccessing.schedule;

import us.andrewdickinson.frc.scoutingproccessing.TBACommunication;

import java.util.*;

/**
 * Created by Andrew on 3/8/16.
 */
public class ScheduleGenerator {
    private TBACommunication tba;
    private ScoutingSchedule scoutingSchedule;

    public static final boolean RANDOM_ANEALING_ENABLED = true;

    public ScheduleGenerator(){
        tba = TBACommunication.getInstance();
        scoutingSchedule = new ScoutingSchedule(tba);
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
