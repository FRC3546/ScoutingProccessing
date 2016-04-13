package us.andrewdickinson.frc.scoutingproccessing;

import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Created by Andrew on 2/28/16.
 */
public class TeamReport {
    private Team team;

    public TeamReport(Team team) {
        if (team == null) throw new IllegalArgumentException("Team is null");
        this.team = team;
    }

    public void generatePDF(String path) throws IOException, DocumentException {
        ReportGenerator rg = new ReportGenerator(this, path);
        rg.generate();
    }

    public int getTeamNumber(){
        return team.getTeamNumber();
    }

    public String getDriveTrain(){
        return team.getDrivetrain();
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

    public HashMap<Defense, CrossCapability> getClaimTeleopDefenseCrossAbility(){
        return team.getClaimTeleopDefenseCrossAbility();
    }
    public boolean getClaimTeleopBoulderLowGoal(){
        return team.getClaimTeleopBoulderLowgoal();
    }

    public boolean getClaimTeleopBoulderHighGoal(){
        return team.getClaimTeleopBoulderHighgoal();
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
                    teamMatch.wasAutoScoreSuccessful()){
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
                    teamMatch.wasAutoScoreSuccessful()){
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

    public HashMap<Defense, CrossCapability> getTeleopDefenseClaimsMap(){
        return team.getClaimTeleopDefenseCrossAbility();
    }

    public SuccessTable<Defense, CrossAction> getTeleopDefenseSuccessTable(){
        ArrayList<HashMap<Defense, CrossAction>> match_results = new ArrayList<>();
        for (TeamMatch teamMatch : team.getTeamMatches()){
            match_results.add(teamMatch.getTeleopDefenseSuccess());
        }
        SuccessTable<Defense, CrossAction> st = new SuccessTable<>(match_results, Defense.class, CrossAction.class);
        st.removeRating(CrossAction.NotAttempted);
        return st;
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

    public String getPitScout(){
        return team.getPitScoutName();
    }

    public ArrayList<ScoringSuccess> getScoringSuccess(){
        return team.getTeamMatches().stream()
                .filter(match -> match.getScoringQuality() != null)
                .map(TeamMatch::getScoringQuality)
                .collect(Collectors.toCollection(ArrayList<ScoringSuccess>::new));

    }

    public ArrayList<DrivingCoordination> getDrivingCoordination(){
        return team.getTeamMatches().stream()
                .map(TeamMatch::getDrivingCoordination)
                .collect(Collectors.toCollection(ArrayList<DrivingCoordination>::new));

    }

    public ArrayList<Quality> getDefensePlayedQuality(){
        return team.getTeamMatches().stream()
                .filter(match -> match.getTeleopDefenseQuality() != null)
                .map(TeamMatch::getTeleopDefenseQuality)
                .collect(Collectors.toCollection(ArrayList<Quality>::new));

    }

    public ArrayList<String> getMatchScouts(){
        ArrayList<String> scouts = new ArrayList<>();
        for (TeamMatch teamMatch : team.getTeamMatches()){
            scouts.add(teamMatch.getScoutName());
        }

        return scouts;
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

    //Summary Functions

    public String getReachedDefenseSummary(){
        ArrayList<Defense> defensesReached = new ArrayList<>();
        team.getTeamMatches().stream()
                .filter(teamMatch ->
                        teamMatch.getAutoDefenseAction() == AutonomousCrossAction.Cross ||
                                teamMatch.getAutoDefenseAction() == AutonomousCrossAction.Reach)
                .forEach(teamMatch -> defensesReached.add(teamMatch.getAutoStagedDefense()));

        return ReportStrings.getPerformanceSummary(defensesReached);
    }

    public String getAutoLowBarSummary(){
        ArrayList<AutonomousCrossAction> lowBarPreformance = new ArrayList<>();
        team.getTeamMatches().stream()
                .filter(teamMatch -> teamMatch.getAutoStagedDefense() == Defense.Low_Bar)
                .forEach(teamMatch -> lowBarPreformance.add(teamMatch.getAutoDefenseAction()));

        return ReportStrings.getPerformanceSummary(lowBarPreformance);
    }

    public String getAutoCrossedDefenseSummary(){
        ArrayList<Defense> defensesCrossed = new ArrayList<>();
        team.getTeamMatches().stream()
                .filter(teamMatch ->
                        teamMatch.getAutoDefenseAction() == AutonomousCrossAction.Cross)
                .forEach(teamMatch -> defensesCrossed.add(teamMatch.getAutoStagedDefense()));

        return ReportStrings.getPerformanceSummary(defensesCrossed);
    }

    public String getAutoScoreLowSummary(){
        long low_goals_in_auto = team.getTeamMatches().stream()
                .filter(teamMatch -> teamMatch.getAutoAttemptedScoreGoal() == Goal.Low)
                .filter(TeamMatch::wasAutoScoreSuccessful)
                .count();

        long attempted = team.getTeamMatches().stream()
                .filter(teamMatch -> teamMatch.getAutoAttemptedScoreGoal() == Goal.Low)
                .count();

        if (low_goals_in_auto == 0){
            if (attempted == 0){
                return ReportStrings.neverDemonstrated;
            } else {
                return ReportStrings.neverDemonstrated + ". Attempted " + attempted + " time(s)";
            }
        } else {
            return "Made: " + low_goals_in_auto + ". Attempted: " + attempted;
        }
    }

    public String getAutoScoreHighSummary(){
        long high_goals_in_auto = team.getTeamMatches().stream()
                .filter(teamMatch -> teamMatch.getAutoAttemptedScoreGoal() == Goal.High)
                .filter(TeamMatch::wasAutoScoreSuccessful)
                .count();

        long attempted = team.getTeamMatches().stream()
                .filter(teamMatch -> teamMatch.getAutoAttemptedScoreGoal() == Goal.High)
                .count();

        if (high_goals_in_auto == 0){
            if (attempted == 0){
                return ReportStrings.neverDemonstrated;
            } else {
                return ReportStrings.neverDemonstrated + ". Attempted " + attempted + " time(s)";
            }
        } else {
            return "Made: " + high_goals_in_auto + ". Attempted: " + attempted;
        }
    }

    public String getTeleopScoreLowSummary(){
       int goals = getTotalLowGoal();
        if (goals == 0) return ReportStrings.neverDemonstrated;
        return goals + " total (" + getAverageLowGoal() + " per match). " +
                ReportStrings.getPerformanceSummary(getScoringSuccess());
    }

    public String getTeleopScoreHighSummary(){
       int goals = getTotalHighGoal();
        if (goals == 0) return ReportStrings.neverDemonstrated;
        return goals + " total (" + getAverageHighGoal() + " per match). " +
                ReportStrings.getPerformanceSummary(getScoringSuccess());
    }

    public String getChallengeSummary(){
        int challenges = (int) team.getTeamMatches().stream()
                .filter(match -> match.getTeleopEndgameAction() == EndgameAction.Challenge).count();

        if (challenges == 0){
            return ReportStrings.neverDemonstrated;
        } else {
            DecimalFormat formatter = new DecimalFormat("#.######", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
            formatter.setRoundingMode( RoundingMode.DOWN );

            return challenges + " time(s). (" + formatter.format(((double) challenges / (double) getMatchesPlayed()) * 100) + "% of the time)";
        }
    }

    public String getScaleSummary(){
        int scales = (int) team.getTeamMatches().stream()
                .filter(match -> match.getTeleopEndgameAction() == EndgameAction.Scale).count();
        int attempts = (int) team.getTeamMatches().stream()
                .filter(match -> match.getTeleopEndgameAction() == EndgameAction.AttemptedScale ||
                        match.getTeleopEndgameAction() == EndgameAction.Scale).count();

        if (scales == 0){
            return ReportStrings.neverDemonstrated;
        } else {
            DecimalFormat formatter = new DecimalFormat("#.######", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
            formatter.setRoundingMode( RoundingMode.DOWN );

            return scales + " time(s); " + attempts + " attempt(s) (" + formatter.format(((double) scales / (double) attempts) * 100) + "% success)";
        }
    }

    public String getDriverCoordinationSummary(){
        return ReportStrings.getPerformanceSummary(getDrivingCoordination());
    }

    public String getDefensePlayedSummary(){
        return ReportStrings.getPerformanceSummary(getDefensePlayedQuality());
    }
}
