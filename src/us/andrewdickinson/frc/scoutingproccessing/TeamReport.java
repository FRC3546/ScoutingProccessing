package us.andrewdickinson.frc.scoutingproccessing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by Andrew on 2/28/16.
 */
public class TeamReport {
    private Team team;

    public TeamReport(Team team) {
        this.team = team;
    }

    public int getTeamNumber(){
        return team.getTeamNumber();
    }

    public String getDriveTrain(){
        return team.getDrivetrain();
    }

    public Double getGroundClearance(){
        return team.getGroundClearance();
    }

    public boolean getClaimAutoCanScoreHigh(){
        return team.getClaimAutoScoreHigh();
    }

    public boolean getClaimAutoCanScoreLow(){
        return team.getClaimAutoScoreLow();
    }

    public boolean getClaimAutoCanCrossLowBar(){
        return team.getClaimAutoCrossLowBar();
    }

    public boolean getClaimAutoCanCrossOtherDefenses(){
        return team.getClaimAutoCrossOtherDefense();
    }

    public boolean getClaimAutoCanReachDefense(){
        return team.getClaimAutoReachDefense();
    }

    public HashMap<Defense, CrossCapability> getClaimTeleopDefenseCrossAbility(){
        return team.getClaimTeleopDefenseCrossAbility();
    }

    public boolean getClaimTeleopBoulderHerd(){
        return team.getClaimTeleopBoulderHerd();
    }

    public boolean getClaimTeleopBoulderPickup(){
        return team.getClaimTeleopBoulderPickup();
    }

    public boolean getClaimTeleopBoulderLowGoal(){
        return team.getClaimTeleopBoulderLowgoal();
    }

    public boolean getClaimTeleopBoulderHighGoal(){
        return team.getClaimTeleopBoulderHighgoal();
    }

    public boolean getClaimTeleopEndgameCanChallenge(){
        return team.getClaimTeleopChallenge();
    }

    public boolean getClaimTeleopEndgameCanScale(){
        return team.getClaimTeleopScale();
    }

    public int getMatchesPlayed(){
        return team.getTeamMatches().size();
    }

    public int getNumberOfMidlineCrossings(){
        return getMatchesWhereRobotCrossedMidline().size();
    }

    public ArrayList<Integer> getMatchesWhereRobotCrossedMidline(){
        ArrayList<Integer> crossings = new ArrayList<>();
        for (TeamMatch teamMatch : team.getTeamMatches()){
            if (teamMatch.inAutoRobotCrossedMidline()) {
                crossings.add(teamMatch.getMatchNumber());
            }
        }

        return crossings;
    }

    public double getAverageHighGoal(){
        return (double) getTotalHighGoal() / (double) getMatchesPlayed();
    }

    public int getTotalHighGoal(){
        int total = 0;
        for(TeamMatch teamMatch : team.getTeamMatches()){
            total += teamMatch.getTeleopBallsScoredHigh();
        }

        return total;
    }

    public int getTotalAutonHighGoal(){
        int total = 0;
        for(TeamMatch teamMatch : team.getTeamMatches()){
            if (teamMatch.getAutoAttemptedScoreGoal() == Goal.High &&
                    teamMatch.isAutoScoreSuccessful()){
                total += 1;
            }
        }

        return total;
    }

    public int getTotalAutonHighGoalAttempts(){
        int total = 0;
        for(TeamMatch teamMatch : team.getTeamMatches()){
            if (teamMatch.getAutoAttemptedScoreGoal() == Goal.High ){
                total += 1;
            }
        }

        return total;
    }

    public double getTotalAutonHighGoalAccuracy(){
        return (double) getTotalAutonHighGoal() / (double) getTotalAutonHighGoalAttempts();
    }

    public int getTotalAutonLowGoal(){
        int total = 0;
        for(TeamMatch teamMatch : team.getTeamMatches()){
            if (teamMatch.getAutoAttemptedScoreGoal() == Goal.Low &&
                    teamMatch.isAutoScoreSuccessful()){
                total += 1;
            }
        }

        return total;
    }

    public int getTotalAutonLowGoalAttempts(){
        int total = 0;
        for(TeamMatch teamMatch : team.getTeamMatches()){
            if (teamMatch.getAutoAttemptedScoreGoal() == Goal.Low ){
                total += 1;
            }
        }

        return total;
    }

    public double getTotalAutonLowGoalAccuracy(){
        return (double) getTotalAutonLowGoal() / (double) getTotalAutonLowGoalAttempts();
    }

    public double getAverageLowGoal(){
        return (double) getTotalLowGoal() / (double) getMatchesPlayed();
    }

    public int getTotalLowGoal(){
        int total = 0;
        for(TeamMatch teamMatch : team.getTeamMatches()){
            total += teamMatch.getTeleopBallsScoredLow();
        }

        return total;
    }

    public SuccessTable<Defense, CrossAction> getTeleopDefenseSuccessTable(){
        ArrayList<HashMap<Defense, CrossAction>> match_results = new ArrayList<>();
        for (TeamMatch teamMatch : team.getTeamMatches()){
            match_results.add(teamMatch.getTeleopDefenseSuccess());
        }
        return new SuccessTable<>(match_results, Defense.class, CrossAction.class);
    }

    public SuccessTable<Defense, AutonomousCrossAction> getAutonomousDefenseSuccessTable(){
        ArrayList<HashMap<Defense, AutonomousCrossAction>> match_results = new ArrayList<>();
        for (TeamMatch teamMatch : team.getTeamMatches()){
            HashMap<Defense, AutonomousCrossAction> match_result = new HashMap<>();
            match_result.put(teamMatch.getAutoStagedDefense(), teamMatch.getAutoDefenseAction());
            match_results.add(match_result);
        }
        return new SuccessTable<>(match_results, Defense.class, AutonomousCrossAction.class);
    }

    public HashMap<EndgameAction, Integer> getEndgamePreformance(){
        HashMap<EndgameAction, Integer> endgamePerformance = new HashMap<>();
        //Fill with zeros
        Arrays.asList(EndgameAction.values()).forEach((endgameAction -> endgamePerformance.put(endgameAction, 0)));

        //Increment the appropriate value for each match
        team.getTeamMatches().forEach(teamMatch ->
                endgamePerformance.put(teamMatch.getTeleopEndgameAction(),
                        endgamePerformance.get(teamMatch.getTeleopEndgameAction()) + 1));

        return endgamePerformance;
    }

    public HashMap<Quality, Integer> getDefensePreformance(){
        HashMap<Quality, Integer> defensePerformance = new HashMap<>();
        //Fill with zeros
        Arrays.asList(Quality.values()).forEach((quality -> defensePerformance.put(quality, 0)));
        defensePerformance.put(null, 0);

        //Increment the appropriate value for each match
        team.getTeamMatches().forEach(teamMatch ->
                defensePerformance.put(teamMatch.getTeleopDefenseQuality(),
                        defensePerformance.get(teamMatch.getTeleopDefenseQuality()) + 1));

        return defensePerformance;
    }

    public ArrayList<String> getComments(){
        ArrayList<String> comments = new ArrayList<>();
        for (TeamMatch teamMatch : team.getTeamMatches()){
            if (!teamMatch.getComment().equals("")) {
                comments.add(teamMatch.getComment());
            }
        }

        if (!team.getComment().equals("")) comments.add(team.getComment());

        return comments;
    }

}
