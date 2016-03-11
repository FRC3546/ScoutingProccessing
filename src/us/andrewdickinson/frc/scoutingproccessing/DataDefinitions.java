package us.andrewdickinson.frc.scoutingproccessing;

import java.util.HashMap;

/**
 * Created by Andrew on 2/28/16.
 */
public class DataDefinitions {
    private static DataDefinitions instance;
    private HeaderManagement pitScoutingHeaders = new HeaderManagement();
    private HeaderManagement matchScoutingHeaders = new HeaderManagement();
    private ReplacementTable matchReportReplacements = new ReplacementTable();
    private HashMap<String, CrossCapability> crossCapabilityHeaderDefinition = new HashMap<>();
    private HashMap<String, CrossAction> crossActionHeaderDefinition = new HashMap<>();
    private HashMap<String, Alliance> allianceDefinition = new HashMap<>();
    private HashMap<String, Defense> defenseDefinition = new HashMap<>();
    private HashMap<String, Quality> qualityDefinition = new HashMap<>();
    private HashMap<String, ScoringSuccess> scoringSuccessDefinition = new HashMap<>();
    private HashMap<String, DrivingCoordination> coordinationDefinition = new HashMap<>();
    private HashMap<String, DriverExperience> driverExperienceDefinition = new HashMap<>();
    private HashMap<String, EndgameAction> endgameActionDefinition = new HashMap<>();
    private HashMap<String, AutonomousCrossAction> autoCrossDefinition = new HashMap<>();

    private static void setup(){
        if (instance != null) throw new IllegalArgumentException();
        instance = new DataDefinitions();

        instance.pitScoutingHeaders.addHeader("team_number", "Team Number");
        instance.pitScoutingHeaders.addHeader("scout_name", "Who scouted this robot?");
        instance.pitScoutingHeaders.addHeader("drivetrain", "Drivetrain");
        instance.pitScoutingHeaders.addHeader("ground_clearance", "Ground Clearance (inches)");
        instance.pitScoutingHeaders.addHeader("autonomous_claims", "Autonomous Mode. Can they...");
        instance.pitScoutingHeaders.addHeader("lowbar_claim", "Defense Crossing Ability (Teleop) [Low Bar]");
        instance.pitScoutingHeaders.addHeader("chevaldefrise_claim", "Defense Crossing Ability (Teleop) [Cheval De Frise]");
        instance.pitScoutingHeaders.addHeader("portcullis_claim", "Defense Crossing Ability (Teleop) [Portcullis]");
        instance.pitScoutingHeaders.addHeader("ramparts_claim", "Defense Crossing Ability (Teleop) [Ramparts]");
        instance.pitScoutingHeaders.addHeader("moat_claim", "Defense Crossing Ability (Teleop) [Moat]");
        instance.pitScoutingHeaders.addHeader("sallyport_claim", "Defense Crossing Ability (Teleop) [Sally Port]");
        instance.pitScoutingHeaders.addHeader("drawbridge_claim", "Defense Crossing Ability (Teleop) [Drawbridge]");
        instance.pitScoutingHeaders.addHeader("roughterrain_claim", "Defense Crossing Ability (Teleop) [Rough Terrain]");
        instance.pitScoutingHeaders.addHeader("rockwall_claim", "Defense Crossing Ability (Teleop) [Rock Wall]");
        instance.pitScoutingHeaders.addHeader("boulder_claims", "Boulder Manipulation. Can they...");
        instance.pitScoutingHeaders.addHeader("endgame_claims", "Endgame. Can they...");
        instance.pitScoutingHeaders.addHeader("rotated_drivers", "Do you rotate drivers over the course of the day?");
        instance.pitScoutingHeaders.addHeader("driver_experience", "How long have your drivers been driving?");
        instance.pitScoutingHeaders.addHeader("comments", "Comments (Optional)");

        instance.matchScoutingHeaders.addHeader("matchnum", "Match Number (i.e. \"3\" or \"27\")");
        instance.matchScoutingHeaders.addHeader("team_number", "Team Number");
        instance.matchScoutingHeaders.addHeader("scout_name", "Who scouted this robot?");
        instance.matchScoutingHeaders.addHeader("auto_staged_defense", "Staged In Front of Defense");
        instance.matchScoutingHeaders.addHeader("auto_defense_action", "Action (with respect to the above defense)");
        instance.matchScoutingHeaders.addHeader("auto_robot_crossed_midline", "Robot Crossed line to opposite side of the field - Incurring a foul (Should be Rare)");
        instance.matchScoutingHeaders.addHeader("auto_score", "Scoring");
        instance.matchScoutingHeaders.addHeader("lowbar_success", "Defenses [Low Bar]");
        instance.matchScoutingHeaders.addHeader("chevaldefrise_success", "Defenses [Cheval De Frise]");
        instance.matchScoutingHeaders.addHeader("portcullis_success", "Defenses [Portcullis]");
        instance.matchScoutingHeaders.addHeader("ramparts_success", "Defenses [Ramparts]");
        instance.matchScoutingHeaders.addHeader("moat_success", "Defenses [Moat]");
        instance.matchScoutingHeaders.addHeader("sallyport_success", "Defenses [Sally Port]");
        instance.matchScoutingHeaders.addHeader("drawbridge_success", "Defenses [Drawbridge]");
        instance.matchScoutingHeaders.addHeader("roughterrain_success", "Defenses [Rough Terrain]");
        instance.matchScoutingHeaders.addHeader("rockwall_success", "Defenses [Rock Wall]");
        instance.matchScoutingHeaders.addHeader("teleop_balls_scored_low", "Balls Scored (Low)");
        instance.matchScoutingHeaders.addHeader("teleop_balls_scored_high", "Balls Scored (High)");
        instance.matchScoutingHeaders.addHeader("teleop_scoring_success", "Scoring success (high and low goals combined). The robot...");
        instance.matchScoutingHeaders.addHeader("teleop_endgame_action", "Endgame");
        instance.matchScoutingHeaders.addHeader("teleop_defense_quality", "Defense");
        instance.matchScoutingHeaders.addHeader("teleop_driving_coordination", "Driving Coordination");
        instance.matchScoutingHeaders.addHeader("comment", "Comments (Optional)");
        

        instance.crossCapabilityHeaderDefinition.put("Cannot Cross Without Assistance", CrossCapability.NotAble);
        instance.crossCapabilityHeaderDefinition.put("Crosses Slowly", CrossCapability.CrossSlow);
        instance.crossCapabilityHeaderDefinition.put("Crosses", CrossCapability.Cross);
        instance.crossCapabilityHeaderDefinition.put("Crosses Swiftly", CrossCapability.CrossSwiftly);

        instance.crossActionHeaderDefinition.put("Did Not Attempt", CrossAction.NotAttempted);
        instance.crossActionHeaderDefinition.put("Failed to Cross", CrossAction.CrossFailed);
        instance.crossActionHeaderDefinition.put("Crossed Poorly", CrossAction.CrossedPoorly);
        instance.crossActionHeaderDefinition.put("Crossed Well", CrossAction.CrossedWell);


        instance.allianceDefinition.put("Red", Alliance.Red);
        instance.allianceDefinition.put("Blue", Alliance.Blue);

        instance.defenseDefinition.put("Low Bar", Defense.Low_Bar);
        instance.defenseDefinition.put("Cheval De Frise", Defense.Cheval_De_Frise);
        instance.defenseDefinition.put("Portcullis", Defense.Portcullis);
        instance.defenseDefinition.put("Ramparts", Defense.Ramparts);
        instance.defenseDefinition.put("Moat", Defense.Moat);
        instance.defenseDefinition.put("Sally Port", Defense.Sally_Port);
        instance.defenseDefinition.put("Drawbridge", Defense.Drawbridge);
        instance.defenseDefinition.put("Rough Terrain", Defense.Rough_Terrain);
        instance.defenseDefinition.put("Rock Wall", Defense.Rock_Wall);
        instance.defenseDefinition.put("None", null);

        instance.qualityDefinition.put("The robot played defense poorly", Quality.Poor);
        instance.qualityDefinition.put("The robot played defense well", Quality.Well);
        instance.qualityDefinition.put("The robot did not play defense", null);

        instance.endgameActionDefinition.put("Nothing", EndgameAction.Nothing);
        instance.endgameActionDefinition.put("Drove on to Batter (\"Challenge\")", EndgameAction.Challenge);
        instance.endgameActionDefinition.put("Attempted to Climb", EndgameAction.AttemptedScale);
        instance.endgameActionDefinition.put("Successfully climbed (\"Scale\")", EndgameAction.Scale);

        instance.autoCrossDefinition.put("Did nothing related to the defense", AutonomousCrossAction.Nothing);
        instance.autoCrossDefinition.put("Reached the defense", AutonomousCrossAction.Reach);
        instance.autoCrossDefinition.put("Crossed the defense", AutonomousCrossAction.Cross);

        instance.scoringSuccessDefinition.put("Didn't try to score boulders", null);
        instance.scoringSuccessDefinition.put("Often Missed", ScoringSuccess.OftenMissed);
        instance.scoringSuccessDefinition.put("Missed Sometimes", ScoringSuccess.MissedSometimes);
        instance.scoringSuccessDefinition.put("Hardly Missed", ScoringSuccess.HardlyMissed);
        instance.scoringSuccessDefinition.put("Never Missed", ScoringSuccess.NeverMissed);

        instance.coordinationDefinition.put("The robot moved smoothly, executing clearly planned maneuvers", DrivingCoordination.Smooth);
        instance.coordinationDefinition.put("The robot moved in a regular way, executing a plan", DrivingCoordination.Regular);
        instance.coordinationDefinition.put("The robot moved in an erratic way, its moves seemed kinda planned", DrivingCoordination.Erratic);
        instance.coordinationDefinition.put("The robot stumbled across the field, with no clear plan", DrivingCoordination.Stumbling);
        instance.coordinationDefinition.put("The robot was incapacitated in one way or another and didn't move much", DrivingCoordination.Incapacitated);

        instance.driverExperienceDefinition.put(DriverClaims.firstYear, DriverExperience.First_Year);
        instance.driverExperienceDefinition.put(DriverClaims.secondYear, DriverExperience.Second_Year);
        instance.driverExperienceDefinition.put(DriverClaims.thirdYear, DriverExperience.Third_Year);
        instance.driverExperienceDefinition.put(DriverClaims.fourthYear, DriverExperience.Fourth_Year);

        instance.matchReportReplacements.addReplacement("Low Bar", "LB");
        instance.matchReportReplacements.addReplacement("Cheval De Frise", "CdF");
        instance.matchReportReplacements.addReplacement("Portcullis", "PC");
        instance.matchReportReplacements.addReplacement("Ramparts", "Rmpp");
        instance.matchReportReplacements.addReplacement("Moat", "Moat");
        instance.matchReportReplacements.addReplacement("Sally Port", "SP");
        instance.matchReportReplacements.addReplacement("Drawbridge", "Drwb");
        instance.matchReportReplacements.addReplacement("Rough Terrain", "RT");
        instance.matchReportReplacements.addReplacement("Rock Wall", "RckW");

        instance.matchReportReplacements.addReplacement("Can't Cross", "Can't");
        instance.matchReportReplacements.addReplacement("CrossSlowly", "Slow");
        instance.matchReportReplacements.addReplacement("CrossSwiftly", "Swift");
        instance.matchReportReplacements.addReplacement("Crosses", "Cross");

        instance.matchReportReplacements.addReplacement("CrossedWell", "Well");
        instance.matchReportReplacements.addReplacement("CrossedPoorly", "Poor");

        instance.matchReportReplacements.addReplacement("Smooth", "Smth");
        instance.matchReportReplacements.addReplacement("Regular", "Reg");
        instance.matchReportReplacements.addReplacement("Erratic", "Erra");
        instance.matchReportReplacements.addReplacement("Stumbling", "Stmbl");
        instance.matchReportReplacements.addReplacement("Incapacitated", "Incap");

        instance.matchReportReplacements.addReplacement("MissedOften", "OftenMiss");
        instance.matchReportReplacements.addReplacement("HardlyMissed", "HardlyMiss");
        instance.matchReportReplacements.addReplacement("NeverMissed", "NeverMiss");
        instance.matchReportReplacements.addReplacement("MissedSometimes", "Missed");

        instance.matchReportReplacements.addReplacement("total ", "");
        instance.matchReportReplacements.addReplacement(" per match", "");
        instance.matchReportReplacements.addReplacement(" time(s)", "");
        instance.matchReportReplacements.addReplacement(" of the time", "");
        instance.matchReportReplacements.addReplacement(" success", "");

        instance.matchReportReplacements.addReplacement("Never Demonstrated", "ND");
    }

    public class SheetNames {
        public static final String pitScouting = "Pit Scouting";
        public static final String matchScouting = "Match Scouting";
    }

    public class AutoClaims {
        public static final String low_bar = "Cross the low bar?";
        public static final String other_defense = "Cross any other defenses?";
        public static final String reach_defense = "Reach a defense?";
        public static final String score_high = "Score Low?";
        public static final String score_low = "Score High?";
    }

    public class AutoScoreWords {
        public static final String scored = "Scored";
        public static final String missed = "Missed";
        public static final String low_goal = "Low";
        public static final String high_goal = "High";
        public static final String no_attempt = "Did not attempt to score";
        public static final String didCrossMidlineWord = "Yes";
    }

    public class BoulderClaims {
        public static final String herd_ball = "Herd ball on ground?";
        public static final String pickup_ball = "Pick up from ground?";
        public static final String low_goal = "Score in low goal?";
        public static final String high_goal = "Score in high goal?";
    }

    public class EndgameClaims {
        public static final String challenge = "Challenge?";
        public static final String scale = "Scale?";
    }

    public class DriverClaims {
        public static final String doesRotateDrivers = "Yes";
        public static final String doesntRotateDrivers = "No";
        public static final String firstYear = "This is their first year";
        public static final String secondYear = "This is their second year";
        public static final String thirdYear = "This is their third year";
        public static final String fourthYear = "This is their fourth year";
    }

    public class TBAConnection {
        public static final String eventCodeDelimiter = "<CODE>";
        public static final String matches_url =
                "http://www.thebluealliance.com/api/v2/event/" + eventCodeDelimiter + "/matches";
        public static final String rankings_url =
                "http://www.thebluealliance.com/api/v2/event/" + eventCodeDelimiter + "/rankings";
        public static final String teams_url =
                "http://www.thebluealliance.com/api/v2/event/" + eventCodeDelimiter + "/teams";
        public static final String tbaAppIdHeaderName = "X-TBA-App-Id";
        public static final String tbaAppId = "frc3546:scouting2016:v1.1";
//        public static final String eventCode = "2016scmb";
        public static final String eventCode = "2016misjo";

    }

    public static DataDefinitions getInstance(){
        if (instance == null) setup();
        return instance;
    }

    public HeaderManagement getPitScoutingHeaders(){
        return pitScoutingHeaders;
    }

    public HeaderManagement getMatchScoutingHeaders(){
        return matchScoutingHeaders;
    }

    public HashMap<String, CrossCapability> getCrossCapabilityHeaderDefinition() {
        return crossCapabilityHeaderDefinition;
    }

    public HashMap<String, CrossAction> getCrossActionHeaderDefinition() {
        return crossActionHeaderDefinition;
    }

    public HashMap<String, Alliance> getAllianceDefinition() {
        return allianceDefinition;
    }

    public HashMap<String, Defense> getDefenseDefinition() {
        return defenseDefinition;
    }

    public HashMap<String, Quality> getQualityDefinition() {
        return qualityDefinition;
    }

    public HashMap<String, ScoringSuccess> getScoringSuccessDefinition() {
        return scoringSuccessDefinition;
    }

    public HashMap<String, DrivingCoordination> getCoordinationDefinition() {
        return coordinationDefinition;
    }

    public HashMap<String, DriverExperience> getDriverExperienceDefinition() {
        return driverExperienceDefinition;
    }

    public HashMap<String, EndgameAction> getEndgameActionDefinition() {
        return endgameActionDefinition;
    }

    public HashMap<String, AutonomousCrossAction> getAutoCrossDefinition() {
        return autoCrossDefinition;
    }

    public ReplacementTable getMatchReportReplacements() {
        return matchReportReplacements;
    }
}
