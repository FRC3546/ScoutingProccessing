package us.andrewdickinson.frc.scoutingproccessing;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by Andrew on 2/28/16.
 */
public class TeamMatch {
    private int team_number;
    private int match_number;

    // Autonomous
    /* The defense that the robot was staged in front of for auto. null = none */
    private Defense auto_staged_defense;
    /* An enum representing the robot's success at crossing the above defense */
    private AutonomousCrossAction auto_defense_action;
    /* The goal the team attempted to score in auto this match. null = none */
    private Goal auto_attempted_score_goal;
    /* Indicates if the attempted score was successful */
    private boolean auto_score_successful;
    /* Indicates that the robot crossed the no-no line */
    private boolean auto_robot_crossed_midline;

    //Teleop
    /* Represents the robot's success at each defense */
    private HashMap<Defense, CrossAction> teleop_defense_success;
    /* The number of balls the robot scored in the low goal */
    private int teleop_balls_scored_low;
    /* The number of balls the robot scored in the high goal */
    private int teleop_balls_scored_high;
    /* Represents the action taken by the robot in the endgame */
    private EndgameAction teleop_endgame_action;
    /* Represents the robot's quality of playing defense. null = did not play defense */
    private Quality teleop_defense_quality;

    //Commentary
    private String comment;

    private TeamMatch(Builder b) {
        this.team_number = b.team_number;
        this.match_number = b.match_number;
        this.auto_staged_defense = b.auto_staged_defense;
        this.auto_defense_action = b.auto_defense_action;
        this.auto_attempted_score_goal = b.auto_attempted_score_goal;
        this.auto_score_successful = b.auto_score_successful;
        this.auto_robot_crossed_midline = b.auto_robot_crossed_midline;
        this.teleop_defense_success = b.teleop_defense_success;
        this.teleop_balls_scored_low = b.teleop_balls_scored_low;
        this.teleop_balls_scored_high = b.teleop_balls_scored_high;
        this.teleop_endgame_action = b.teleop_endgame_action;
        this.teleop_defense_quality = b.teleop_defense_quality;
        this.comment = b.comment;
    }

    public int getTeamNumber() {
        return team_number;
    }

    public int getMatch_number() {
        return match_number;
    }

    public Defense getAuto_staged_defense() {
        return auto_staged_defense;
    }

    public AutonomousCrossAction getAuto_defense_action() {
        return auto_defense_action;
    }

    public Goal getAuto_attempted_score_goal() {
        return auto_attempted_score_goal;
    }

    public boolean isAuto_score_successful() {
        return auto_score_successful;
    }

    public boolean isAuto_robot_crossed_midline() {
        return auto_robot_crossed_midline;
    }

    public Map<Defense, CrossAction> getTeleop_defense_success() {
        return teleop_defense_success;
    }

    public int getTeleop_balls_scored_low() {
        return teleop_balls_scored_low;
    }

    public int getTeleop_balls_scored_high() {
        return teleop_balls_scored_high;
    }

    public EndgameAction getTeleop_endgame_action() {
        return teleop_endgame_action;
    }

    public Quality getTeleop_defense_quality() {
        return teleop_defense_quality;
    }

    public String getComment() {
        return comment;
    }

    public static class Builder {
        private Integer team_number;
        private Integer match_number;
        private Defense auto_staged_defense;
        private AutonomousCrossAction auto_defense_action;
        private Goal auto_attempted_score_goal;
        private Boolean auto_score_successful;
        private Boolean auto_robot_crossed_midline;
        private HashMap<Defense, CrossAction> teleop_defense_success;
        private Integer teleop_balls_scored_low;
        private Integer teleop_balls_scored_high;
        private EndgameAction teleop_endgame_action;
        private Quality teleop_defense_quality;
        private String comment;

        public Builder(int team_number, int match_number) {
            this.team_number = team_number;
            this.match_number = match_number;
            teleop_defense_success = new HashMap<>();
        }

        public Builder auto_staged_defense(String auto_staged_defense) {
            this.auto_staged_defense = DataDefinitions.getInstance()
                    .getDefenseDefinition().get(auto_staged_defense);
            return this;
        }

        public Builder auto_defense_action(String auto_defense_action) {
            this.auto_defense_action = DataDefinitions.getInstance()
                    .getAutoCrossDefinition().get(auto_defense_action);
            return this;
        }

        public Builder auto_attempted_score(String auto_attempted_score) {
            if (auto_attempted_score.contains(DataDefinitions.AutoScoreWords.no_attempt)) {
                auto_attempted_score_goal = null;
            } else if (auto_attempted_score.contains(DataDefinitions.AutoScoreWords.low_goal)){
                auto_attempted_score_goal = Goal.Low;
            } else if (auto_attempted_score.contains(DataDefinitions.AutoScoreWords.high_goal)){
                auto_attempted_score_goal = Goal.High;
            }

            auto_score_successful = auto_attempted_score.contains(DataDefinitions.AutoScoreWords.scored);
            return this;
        }

        public Builder auto_robot_crossed_midline(String auto_robot_crossed_midline) {
            this.auto_robot_crossed_midline = auto_robot_crossed_midline
                    .contains(DataDefinitions.AutoScoreWords.didCrossMidlineWord);
            return this;
        }

        public Builder teleop_lowbar_cross_success(String teleop_cross_success) {
            teleop_defense_success.put(Defense.Low_Bar,
                    DataDefinitions.getInstance()
                            .getCrossActionHeaderDefinition()
                            .get(teleop_cross_success)
            );
            return this;
        }

        public Builder teleop_chevaldefrise_cross_success(String teleop_cross_success) {
            teleop_defense_success.put(Defense.Cheval_De_Frise,
                    DataDefinitions.getInstance()
                            .getCrossActionHeaderDefinition()
                            .get(teleop_cross_success)
            );
            return this;
        }

        public Builder teleop_portcullis_cross_success(String teleop_cross_success) {
            teleop_defense_success.put(Defense.Portcullis,
                    DataDefinitions.getInstance()
                            .getCrossActionHeaderDefinition()
                            .get(teleop_cross_success)
            );
            return this;
        }

        public Builder teleop_ramparts_cross_success(String teleop_cross_success) {
            teleop_defense_success.put(Defense.Ramparts,
                    DataDefinitions.getInstance()
                            .getCrossActionHeaderDefinition()
                            .get(teleop_cross_success)
            );
            return this;
        }

        public Builder teleop_moat_cross_success(String teleop_cross_success) {
            teleop_defense_success.put(Defense.Moat,
                    DataDefinitions.getInstance()
                            .getCrossActionHeaderDefinition()
                            .get(teleop_cross_success)
            );
            return this;
        }

        public Builder teleop_sallyport_cross_success(String teleop_cross_success) {
            teleop_defense_success.put(Defense.Sally_Port,
                    DataDefinitions.getInstance()
                            .getCrossActionHeaderDefinition()
                            .get(teleop_cross_success)
            );
            return this;
        }

        public Builder teleop_drawbridge_cross_success(String teleop_cross_success) {
            teleop_defense_success.put(Defense.Drawbridge,
                    DataDefinitions.getInstance()
                            .getCrossActionHeaderDefinition()
                            .get(teleop_cross_success)
            );
            return this;
        }

        public Builder teleop_roughterrain_cross_success(String teleop_cross_success) {
            teleop_defense_success.put(Defense.Rough_Terrain,
                    DataDefinitions.getInstance()
                            .getCrossActionHeaderDefinition()
                            .get(teleop_cross_success)
            );
            return this;
        }

        public Builder teleop_rockwall_cross_success(String teleop_cross_success) {
            teleop_defense_success.put(Defense.Rock_Wall,
                    DataDefinitions.getInstance()
                            .getCrossActionHeaderDefinition()
                            .get(teleop_cross_success)
            );
            return this;
        }


        public Builder teleop_balls_scored_low(String teleop_balls_scored_low) {
            this.teleop_balls_scored_low = Double.valueOf(teleop_balls_scored_low).intValue();
            return this;
        }

        public Builder teleop_balls_scored_high(String teleop_balls_scored_high) {
            this.teleop_balls_scored_high = Double.valueOf(teleop_balls_scored_high).intValue();
            return this;
        }

        public Builder teleop_endgame_action(String teleop_endgame_action) {
            this.teleop_endgame_action = DataDefinitions.getInstance()
                    .getEndgameActionDefinition().get(teleop_endgame_action);
            return this;
        }

        public Builder teleop_defense_quality(String teleop_defense_quality) {
            this.teleop_defense_quality = DataDefinitions.getInstance()
                    .getQualityDefinition().get(teleop_defense_quality);
            return this;
        }

        public Builder comment(String comment) {
            this.comment = comment;
            return this;
        }

        public TeamMatch build(){
            //Those that are allowed to be null are removed here
            boolean complete = this.team_number != null &&
                this.match_number != null &&
                this.auto_defense_action != null &&
                this.auto_score_successful != null &&
                this.auto_robot_crossed_midline != null &&
                this.teleop_defense_success != null &&
                this.teleop_balls_scored_low != null &&
                this.teleop_balls_scored_high != null &&
                this.teleop_endgame_action != null &&
                this.comment != null;

            if (complete){
                return new TeamMatch(this);
            } else {
                throw new IllegalStateException("The builder has not been completed");
            }

        }
    }
}
