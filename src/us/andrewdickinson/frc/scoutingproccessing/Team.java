package us.andrewdickinson.frc.scoutingproccessing;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Andrew on 2/28/16.
 */
public class Team {
    private int team_number;
    private ArrayList<TeamMatch> match_data;

    //Pit Scouting info
    private String drivetrain;
    private double ground_clearance;
    private String comment;

    private boolean claim_auto_score_high;
    private boolean claim_auto_score_low;
    private boolean claim_auto_cross_low_bar;
    private boolean claim_auto_cross_other_defense;
    private boolean claim_auto_reach_defense;

    private HashMap<Defense, CrossCapability> claim_teleop_defense_cross_ability;
    private boolean claim_teleop_boulder_herd;
    private boolean claim_teleop_boulder_pickup;
    private boolean claim_teleop_boulder_lowgoal;
    private boolean claim_teleop_boulder_highgoal;

    private boolean claim_teleop_challenge;
    private boolean claim_teleop_scale;

    private Team(Builder b) {
        this.team_number = b.team_number;
        this.drivetrain = b.drivetrain;
        this.ground_clearance = b.ground_clearance;
        this.comment = b.comment;
        this.claim_auto_score_high = b.claim_auto_score_high;
        this.claim_auto_score_low = b.claim_auto_score_low;
        this.claim_auto_cross_low_bar = b.claim_auto_cross_low_bar;
        this.claim_auto_cross_other_defense = b.claim_auto_cross_other_defense;
        this.claim_auto_reach_defense = b.claim_auto_reach_defense;
        this.claim_teleop_defense_cross_ability = b.claim_teleop_defense_cross_ability;
        this.claim_teleop_boulder_herd = b.claim_teleop_boulder_herd;
        this.claim_teleop_boulder_pickup = b.claim_teleop_boulder_pickup;
        this.claim_teleop_boulder_lowgoal = b.claim_teleop_boulder_lowgoal;
        this.claim_teleop_boulder_highgoal = b.claim_teleop_boulder_highgoal;
        this.claim_teleop_challenge = b.claim_teleop_challenge;
        this.claim_teleop_scale = b.claim_teleop_scale;
    }

    public void addTeamMatch(TeamMatch teamMatch){
        match_data.add(teamMatch);
    }

    public int getTeamNumber() {
        return team_number;
    }

    public ArrayList<TeamMatch> getTeamMatches() {
        return match_data;
    }

    public String getDrivetrain() {
        return drivetrain;
    }

    public double getGround_clearance() {
        return ground_clearance;
    }

    public String getComment() {
        return comment;
    }

    public boolean isClaim_auto_score_high() {
        return claim_auto_score_high;
    }

    public boolean isClaim_auto_score_low() {
        return claim_auto_score_low;
    }

    public boolean isClaim_auto_cross_low_bar() {
        return claim_auto_cross_low_bar;
    }

    public boolean isClaim_auto_cross_other_defense() {
        return claim_auto_cross_other_defense;
    }

    public boolean isClaim_auto_reach_defense() {
        return claim_auto_reach_defense;
    }

    public HashMap<Defense, CrossCapability> getClaim_teleop_defense_cross_ability() {
        return claim_teleop_defense_cross_ability;
    }

    public boolean isClaim_teleop_boulder_herd() {
        return claim_teleop_boulder_herd;
    }

    public boolean isClaim_teleop_boulder_pickup() {
        return claim_teleop_boulder_pickup;
    }

    public boolean isClaim_teleop_boulder_lowgoal() {
        return claim_teleop_boulder_lowgoal;
    }

    public boolean isClaim_teleop_boulder_highgoal() {
        return claim_teleop_boulder_highgoal;
    }

    public boolean isClaim_teleop_challenge() {
        return claim_teleop_challenge;
    }

    public boolean isClaim_teleop_scale() {
        return claim_teleop_scale;
    }

    public static class Builder {
        private int team_number;
        private ArrayList<TeamMatch> match_data;
        private String drivetrain;
        private Double ground_clearance;
        private String comment;
        private Boolean claim_auto_score_high;
        private Boolean claim_auto_score_low;
        private Boolean claim_auto_cross_low_bar;
        private Boolean claim_auto_cross_other_defense;
        private Boolean claim_auto_reach_defense;
        private HashMap<Defense, CrossCapability> claim_teleop_defense_cross_ability;
        private Boolean claim_teleop_boulder_herd;
        private Boolean claim_teleop_boulder_pickup;
        private Boolean claim_teleop_boulder_lowgoal;
        private Boolean claim_teleop_boulder_highgoal;
        private Boolean claim_teleop_challenge;
        private Boolean claim_teleop_scale;

        public Builder(int team_number) {
            this.team_number = team_number;
            claim_teleop_defense_cross_ability = new HashMap<>();
        }

        public Builder(String team_number) {
            this(Double.valueOf(team_number).intValue());
        }

        public Builder match_data(ArrayList<TeamMatch> match_data) {
            this.match_data = match_data;
            return this;
        }

        public Builder drivetrain(String drivetrain) {
            this.drivetrain = drivetrain;
            return this;
        }

        public Builder ground_clearance(double ground_clearance) {
            this.ground_clearance = ground_clearance;
            return this;
        }

        public Builder ground_clearance(String ground_clearance) {
            return ground_clearance(Double.valueOf(ground_clearance));
        }

        public Builder comment(String comment) {
            this.comment = comment;
            return this;
        }

        public Builder auto_claims(String auto_claims){
            claim_auto_cross_low_bar = auto_claims.contains(DataDefinitions.AutoClaims.low_bar);
            claim_auto_cross_other_defense = auto_claims.contains(DataDefinitions.AutoClaims.other_defense);
            claim_auto_reach_defense = auto_claims.contains(DataDefinitions.AutoClaims.reach_defense);
            claim_auto_score_high = auto_claims.contains(DataDefinitions.AutoClaims.score_high);
            claim_auto_score_low = auto_claims.contains(DataDefinitions.AutoClaims.score_low);
            return this;
        }

        public Builder claim_teleop_lowbar_cross_ability(String claim_teleop_cross_ability) {
            claim_teleop_defense_cross_ability.put(Defense.Low_Bar,
                    DataDefinitions.getInstance()
                            .getCrossCapabilityHeaderDefinition()
                            .get(claim_teleop_cross_ability)
            );
            return this;
        }

        public Builder claim_teleop_chevaldefrise_cross_ability(String claim_teleop_cross_ability) {
            claim_teleop_defense_cross_ability.put(Defense.Cheval_De_Frise,
                    DataDefinitions.getInstance()
                            .getCrossCapabilityHeaderDefinition()
                            .get(claim_teleop_cross_ability)
            );
            return this;
        }

        public Builder claim_teleop_portcullis_cross_ability(String claim_teleop_cross_ability) {
            claim_teleop_defense_cross_ability.put(Defense.Portcullis,
                    DataDefinitions.getInstance()
                            .getCrossCapabilityHeaderDefinition()
                            .get(claim_teleop_cross_ability)
            );
            return this;
        }

        public Builder claim_teleop_ramparts_cross_ability(String claim_teleop_cross_ability) {
            claim_teleop_defense_cross_ability.put(Defense.Ramparts,
                    DataDefinitions.getInstance()
                            .getCrossCapabilityHeaderDefinition()
                            .get(claim_teleop_cross_ability)
            );
            return this;
        }

        public Builder claim_teleop_moat_cross_ability(String claim_teleop_cross_ability) {
            claim_teleop_defense_cross_ability.put(Defense.Moat,
                    DataDefinitions.getInstance()
                            .getCrossCapabilityHeaderDefinition()
                            .get(claim_teleop_cross_ability)
            );
            return this;
        }

        public Builder claim_teleop_sallyport_cross_ability(String claim_teleop_cross_ability) {
            claim_teleop_defense_cross_ability.put(Defense.Sally_Port,
                    DataDefinitions.getInstance()
                            .getCrossCapabilityHeaderDefinition()
                            .get(claim_teleop_cross_ability)
            );
            return this;
        }

        public Builder claim_teleop_drawbridge_cross_ability(String claim_teleop_cross_ability) {
            claim_teleop_defense_cross_ability.put(Defense.Drawbridge,
                    DataDefinitions.getInstance()
                            .getCrossCapabilityHeaderDefinition()
                            .get(claim_teleop_cross_ability)
            );
            return this;
        }

        public Builder claim_teleop_roughterrain_cross_ability(String claim_teleop_cross_ability) {
            claim_teleop_defense_cross_ability.put(Defense.Rough_Terrain,
                    DataDefinitions.getInstance()
                            .getCrossCapabilityHeaderDefinition()
                            .get(claim_teleop_cross_ability)
            );
            return this;
        }

        public Builder claim_teleop_rockwall_cross_ability(String claim_teleop_cross_ability) {
            claim_teleop_defense_cross_ability.put(Defense.Rock_Wall,
                    DataDefinitions.getInstance()
                            .getCrossCapabilityHeaderDefinition()
                            .get(claim_teleop_cross_ability)
            );
            return this;
        }

        public Builder boulder_claims(String boulder_claims){
            claim_teleop_boulder_herd = boulder_claims.contains(DataDefinitions.BoulderClaims.herd_ball);
            claim_teleop_boulder_pickup = boulder_claims.contains(DataDefinitions.BoulderClaims.pickup_ball);
            claim_teleop_boulder_lowgoal = boulder_claims.contains(DataDefinitions.BoulderClaims.low_goal);
            claim_teleop_boulder_highgoal = boulder_claims.contains(DataDefinitions.BoulderClaims.high_goal);
            return this;
        }

        public Builder endgame_claims(String endgame_claims){
            claim_teleop_challenge = endgame_claims.contains(DataDefinitions.EndgameClaims.challenge);
            claim_teleop_scale = endgame_claims.contains(DataDefinitions.EndgameClaims.scale);
            return this;
        }

        public Team build(){
            boolean complete = this.drivetrain != null &&
                this.ground_clearance != null &&
                this.comment != null &&
                this.claim_auto_score_high != null &&
                this.claim_auto_score_low != null &&
                this.claim_auto_cross_low_bar != null &&
                this.claim_auto_cross_other_defense != null &&
                this.claim_auto_reach_defense != null &&
                this.claim_teleop_defense_cross_ability != null &&
                this.claim_teleop_boulder_herd != null &&
                this.claim_teleop_boulder_pickup != null &&
                this.claim_teleop_boulder_lowgoal != null &&
                this.claim_teleop_boulder_highgoal != null &&
                this.claim_teleop_challenge != null &&
                this.claim_teleop_scale != null;

            if (complete){
                return new Team(this);
            } else {
                throw new IllegalStateException("The builder has not been completed");
            }
        }

    }
}
