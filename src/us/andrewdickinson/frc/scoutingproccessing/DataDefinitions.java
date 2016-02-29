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
    private HashMap<String, Alliance> allianceDefinition = new HashMap<>();

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

        for (int i = 1; i <= 3; i++) {
            instance.matchScoutingHeaders.addHeader("team" + i + "auto_staged_defense", "Staged In Front of Defense", i - 1);
            instance.matchScoutingHeaders.addHeader("team" + i + "auto_defense_action", "Action (with respect to the above defense)", i - 1);
            instance.matchScoutingHeaders.addHeader("team" + i + "auto_robot_crossed_midline", "Robot Crossed line to opposite side of the field - Incurring a foul (Should be Rare)", i - 1);
            instance.matchScoutingHeaders.addHeader("team" + i + "auto_score", "Scoring", i - 1);
            instance.matchScoutingHeaders.addHeader("team" + i + "lowbar_success", "Defenses [Low Bar]", i - 1);
            instance.matchScoutingHeaders.addHeader("team" + i + "chevaldefrise_success", "Defenses [Cheval De Frise]", i - 1);
            instance.matchScoutingHeaders.addHeader("team" + i + "portcullis_success", "Defenses [Portcullis]", i - 1);
            instance.matchScoutingHeaders.addHeader("team" + i + "ramparts_success", "Defenses [Ramparts]", i - 1);
            instance.matchScoutingHeaders.addHeader("team" + i + "moat_success", "Defenses [Moat]", i - 1);
            instance.matchScoutingHeaders.addHeader("team" + i + "sallyport_success", "Defenses [Sally Port]", i - 1);
            instance.matchScoutingHeaders.addHeader("team" + i + "drawbridge_success", "Defenses [Drawbridge]", i - 1);
            instance.matchScoutingHeaders.addHeader("team" + i + "roughterrain_success", "Defenses [Rough Terrain]", i - 1);
            instance.matchScoutingHeaders.addHeader("team" + i + "rockwall_success", "Defenses [Rock Wall]", i - 1);
            instance.matchScoutingHeaders.addHeader("team" + i + "teleop_balls_scored_low", "Balls Scored (Low)", i - 1);
            instance.matchScoutingHeaders.addHeader("team" + i + "teleop_balls_scored_high", "Balls Scored (High)", i - 1);
            instance.matchScoutingHeaders.addHeader("team" + i + "teleop_endgame_action", "Endgame", i - 1);
            instance.matchScoutingHeaders.addHeader("team" + i + "teleop_defense_quality", "Defense", i - 1);
            instance.matchScoutingHeaders.addHeader("team" + i + "comment", "Comments (Optional)", i - 1);
        }

        instance.crossCapabilityHeaderDefinition.put("Cannot Cross Without Assistance", CrossCapability.NotAble);
        instance.crossCapabilityHeaderDefinition.put("Crosses Slowly", CrossCapability.CrossSlow);
        instance.crossCapabilityHeaderDefinition.put("Crosses", CrossCapability.Cross);
        instance.crossCapabilityHeaderDefinition.put("Crosses Swiftly", CrossCapability.CrossSwiftly);

        instance.allianceDefinition.put("Red", Alliance.Red);
        instance.allianceDefinition.put("Blue", Alliance.Blue);
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

    public class TBAConnection {
        public static final String eventCodeDelimiter = "<CODE>";
        public static final String matches_url =
                "http://www.thebluealliance.com/api/v2/event/" + eventCodeDelimiter + "/matches";
        public static final String tbaAppIdHeaderName = "X-TBA-App-Id";
        public static final String tbaAppId = "frc3546:scouting2016:v0.1";
        public static final String eventCode = "2016scmb";

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

    public HashMap<String, Alliance> getAllianceDefinition() {
        return allianceDefinition;
    }
}
