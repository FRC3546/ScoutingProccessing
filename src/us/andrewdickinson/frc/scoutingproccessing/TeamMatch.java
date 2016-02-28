package us.andrewdickinson.frc.scoutingproccessing;


import java.awt.*;
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

    public TeamMatch(Builder b) {
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

    public int getTeam_number() {
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
        }

        public Builder auto_staged_defense(Defense auto_staged_defense) {
            this.auto_staged_defense = auto_staged_defense;
            return this;
        }

        public Builder auto_defense_action(AutonomousCrossAction auto_defense_action) {
            this.auto_defense_action = auto_defense_action;
            return this;
        }

        public Builder auto_attempted_score_goal(Goal auto_attempted_score_goal) {
            this.auto_attempted_score_goal = auto_attempted_score_goal;
            return this;
        }

        public Builder auto_score_successful(boolean auto_score_successful) {
            this.auto_score_successful = auto_score_successful;
            return this;
        }

        public Builder auto_robot_crossed_midline(boolean auto_robot_crossed_midline) {
            this.auto_robot_crossed_midline = auto_robot_crossed_midline;
            return this;
        }

        public Builder teleop_defense_success(HashMap<Defense, CrossAction> teleop_defense_success) {
            this.teleop_defense_success = teleop_defense_success;
            return this;
        }

        public Builder teleop_balls_scored_low(int teleop_balls_scored_low) {
            this.teleop_balls_scored_low = teleop_balls_scored_low;
            return this;
        }

        public Builder teleop_balls_scored_high(int teleop_balls_scored_high) {
            this.teleop_balls_scored_high = teleop_balls_scored_high;
            return this;
        }

        public Builder teleop_endgame_action(EndgameAction teleop_endgame_action) {
            this.teleop_endgame_action = teleop_endgame_action;
            return this;
        }

        public Builder teleop_defense_quality(Quality teleop_defense_quality) {
            this.teleop_defense_quality = teleop_defense_quality;
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
