package us.andrewdickinson.frc.scoutingproccessing;

import java.util.HashMap;

/**
 * Created by Andrew on 2/28/16.
 */
public class DataDefinitions {
    private static DataDefinitions instance;
    private HeaderManagement pitScoutingHeaders = new HeaderManagement();
    private HeaderManagement matchScoutingHeaders = new HeaderManagement();
    private HashMap<String, CrossCapability> crossCapabilityHeaderDefinition = new HashMap<>();

    private static void setup(){
        if (instance != null) throw new IllegalArgumentException();
        instance = new DataDefinitions();

        instance.pitScoutingHeaders.addHeader("team_number", "Team Number");
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
        instance.pitScoutingHeaders.addHeader("comments", "Comments (Optional)");

        instance.matchScoutingHeaders.addHeader("matchnum", "Match Number");
        instance.matchScoutingHeaders.addHeader("alliance", "Which Alliance?");

        instance.matchScoutingHeaders.addHeader("team1_"+"auto_staged_defense", "Staged In Front of Defense", 0);
        instance.matchScoutingHeaders.addHeader("team1_"+"auto_defense_action", "Action (with respect to the above defense)", 0);
        instance.matchScoutingHeaders.addHeader("team1_"+"auto_robot_crossed_midline", "Robot Crossed line to opposite side of the field - Incurring a foul (Should be Rare)", 0);
        instance.matchScoutingHeaders.addHeader("team1_"+"auto_score", "Scoring", 0);
        instance.matchScoutingHeaders.addHeader("team1_"+"lowbar_success", "Defenses [Low Bar]", 0);
        instance.matchScoutingHeaders.addHeader("team1_"+"chevaldefrise_success", "Defenses [Cheval De Frise]", 0);
        instance.matchScoutingHeaders.addHeader("team1_"+"portcullis_success", "Defenses [Portcullis]", 0);
        instance.matchScoutingHeaders.addHeader("team1_"+"ramparts_success", "Defenses [Ramparts]", 0);
        instance.matchScoutingHeaders.addHeader("team1_"+"moat_success", "Defenses [Moat]", 0);
        instance.matchScoutingHeaders.addHeader("team1_"+"sallyport_success", "Defenses [Sally Port]", 0);
        instance.matchScoutingHeaders.addHeader("team1_"+"drawbridge_success", "Defenses [Drawbridge]", 0);
        instance.matchScoutingHeaders.addHeader("team1_"+"roughterrain_success", "Defenses [Rough Terrain]", 0);
        instance.matchScoutingHeaders.addHeader("team1_"+"rockwall_success", "Defenses [Rock Wall]", 0);
        instance.matchScoutingHeaders.addHeader("team1_"+"teleop_balls_scored_low", "Balls Scored (Low)", 0);
        instance.matchScoutingHeaders.addHeader("team1_"+"teleop_balls_scored_high", "Balls Scored (High)", 0);
        instance.matchScoutingHeaders.addHeader("team1_"+"teleop_endgame_action", "Endgame", 0);
        instance.matchScoutingHeaders.addHeader("team1_"+"teleop_defense_quality", "Defense", 0);
        instance.matchScoutingHeaders.addHeader("team1_"+"comment", "Comments (Optional)", 0);

        instance.matchScoutingHeaders.addHeader("team2_"+"auto_staged_defense", "Staged In Front of Defense", 1);
        instance.matchScoutingHeaders.addHeader("team2_"+"auto_defense_action", "Action (with respect to the above defense)", 1);
        instance.matchScoutingHeaders.addHeader("team2_"+"auto_robot_crossed_midline", "Robot Crossed line to opposite side of the field - Incurring a foul (Should be Rare)", 1);
        instance.matchScoutingHeaders.addHeader("team2_"+"auto_score", "Scoring", 1);
        instance.matchScoutingHeaders.addHeader("team2_"+"lowbar_success", "Defenses [Low Bar]", 1);
        instance.matchScoutingHeaders.addHeader("team2_"+"chevaldefrise_success", "Defenses [Cheval De Frise]", 1);
        instance.matchScoutingHeaders.addHeader("team2_"+"portcullis_success", "Defenses [Portcullis]", 1);
        instance.matchScoutingHeaders.addHeader("team2_"+"ramparts_success", "Defenses [Ramparts]", 1);
        instance.matchScoutingHeaders.addHeader("team2_"+"moat_success", "Defenses [Moat]", 1);
        instance.matchScoutingHeaders.addHeader("team2_"+"sallyport_success", "Defenses [Sally Port]", 1);
        instance.matchScoutingHeaders.addHeader("team2_"+"drawbridge_success", "Defenses [Drawbridge]", 1);
        instance.matchScoutingHeaders.addHeader("team2_"+"roughterrain_success", "Defenses [Rough Terrain]", 1);
        instance.matchScoutingHeaders.addHeader("team2_"+"rockwall_success", "Defenses [Rock Wall]", 1);
        instance.matchScoutingHeaders.addHeader("team2_"+"teleop_balls_scored_low", "Balls Scored (Low)", 1);
        instance.matchScoutingHeaders.addHeader("team2_"+"teleop_balls_scored_high", "Balls Scored (High)", 1);
        instance.matchScoutingHeaders.addHeader("team2_"+"teleop_endgame_action", "Endgame", 1);
        instance.matchScoutingHeaders.addHeader("team2_"+"teleop_defense_quality", "Defense", 1);
        instance.matchScoutingHeaders.addHeader("team2_"+"comment", "Comments (Optional)", 1);

        instance.matchScoutingHeaders.addHeader("team3_"+"auto_staged_defense", "Staged In Front of Defense", 2);
        instance.matchScoutingHeaders.addHeader("team3_"+"auto_defense_action", "Action (with respect to the above defense)", 2);
        instance.matchScoutingHeaders.addHeader("team3_"+"auto_robot_crossed_midline", "Robot Crossed line to opposite side of the field - Incurring a foul (Should be Rare)", 2);
        instance.matchScoutingHeaders.addHeader("team3_"+"auto_score", "Scoring", 2);
        instance.matchScoutingHeaders.addHeader("team3_"+"lowbar_success", "Defenses [Low Bar]", 2);
        instance.matchScoutingHeaders.addHeader("team3_"+"chevaldefrise_success", "Defenses [Cheval De Frise]", 2);
        instance.matchScoutingHeaders.addHeader("team3_"+"portcullis_success", "Defenses [Portcullis]", 2);
        instance.matchScoutingHeaders.addHeader("team3_"+"ramparts_success", "Defenses [Ramparts]", 2);
        instance.matchScoutingHeaders.addHeader("team3_"+"moat_success", "Defenses [Moat]", 2);
        instance.matchScoutingHeaders.addHeader("team3_"+"sallyport_success", "Defenses [Sally Port]", 2);
        instance.matchScoutingHeaders.addHeader("team3_"+"drawbridge_success", "Defenses [Drawbridge]", 2);
        instance.matchScoutingHeaders.addHeader("team3_"+"roughterrain_success", "Defenses [Rough Terrain]", 2);
        instance.matchScoutingHeaders.addHeader("team3_"+"rockwall_success", "Defenses [Rock Wall]", 2);
        instance.matchScoutingHeaders.addHeader("team3_"+"teleop_balls_scored_low", "Balls Scored (Low)", 2);
        instance.matchScoutingHeaders.addHeader("team3_"+"teleop_balls_scored_high", "Balls Scored (High)", 2);
        instance.matchScoutingHeaders.addHeader("team3_"+"teleop_endgame_action", "Endgame", 2);
        instance.matchScoutingHeaders.addHeader("team3_"+"teleop_defense_quality", "Defense", 2);
        instance.matchScoutingHeaders.addHeader("team3_"+"comment", "Comments (Optional)", 2);

        instance.crossCapabilityHeaderDefinition.put("Cannot Cross Without Assistance", CrossCapability.NotAble);
        instance.crossCapabilityHeaderDefinition.put("Crosses Slowly", CrossCapability.CrossSlow);
        instance.crossCapabilityHeaderDefinition.put("Crosses", CrossCapability.Cross);
        instance.crossCapabilityHeaderDefinition.put("Crosses Swiftly", CrossCapability.CrossSwiftly);
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
}
