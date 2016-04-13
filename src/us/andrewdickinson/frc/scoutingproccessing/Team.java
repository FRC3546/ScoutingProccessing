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
    private String pit_scout_name;
    private String comment;

    private boolean claim_auto_score_high;
    private boolean claim_auto_score_low;
    private boolean claim_auto_cross_low_bar;
    private boolean claim_auto_cross_other_defense;

    private HashMap<Defense, CrossCapability> claim_teleop_defense_cross_ability;
    private boolean claim_teleop_boulder_lowgoal;
    private boolean claim_teleop_boulder_highgoal;

    private boolean claim_teleop_scale;

    private Team(Builder b) {
        this.team_number = b.team_number;
        this.pit_scout_name = b.pit_scout_name;
        this.match_data = new ArrayList<>();
        this.drivetrain = b.drivetrain;
        this.comment = b.comment;
        this.claim_auto_score_high = b.claim_auto_score_high;
        this.claim_auto_score_low = b.claim_auto_score_low;
        this.claim_auto_cross_low_bar = b.claim_auto_cross_low_bar;
        this.claim_auto_cross_other_defense = b.claim_auto_cross_other_defense;
        this.claim_teleop_defense_cross_ability = b.claim_teleop_defense_cross_ability;
        this.claim_teleop_boulder_lowgoal = b.claim_teleop_boulder_lowgoal;
        this.claim_teleop_boulder_highgoal = b.claim_teleop_boulder_highgoal;
        this.claim_teleop_scale = b.claim_teleop_scale;
    }

    public void addTeamMatch(TeamMatch teamMatch){
        match_data.add(teamMatch);
    }

    public int getTeamNumber() {
        return team_number;
    }

    public String getPitScoutName() {
        return pit_scout_name;
    }

    public ArrayList<TeamMatch> getTeamMatches() {
        return match_data;
    }

    public String getDrivetrain() {
        return drivetrain;
    }

    public String getComment() {
        return comment;
    }

    public boolean getClaimAutoScoreHigh() {
        return claim_auto_score_high;
    }

    public boolean getClaimAutoScoreLow() {
        return claim_auto_score_low;
    }

    public boolean getClaimAutoCrossLowBar() {
        return claim_auto_cross_low_bar;
    }

    public boolean getClaimAutoCrossOtherDefense() {
        return claim_auto_cross_other_defense;
    }

    public HashMap<Defense, CrossCapability> getClaimTeleopDefenseCrossAbility() {
        return claim_teleop_defense_cross_ability;
    }

    public boolean getClaimTeleopBoulderLowgoal() {
        return claim_teleop_boulder_lowgoal;
    }

    public boolean getClaimTeleopBoulderHighgoal() {
        return claim_teleop_boulder_highgoal;
    }

    public boolean getClaimTeleopScale() {
        return claim_teleop_scale;
    }

    public static class Builder {
        private int team_number;
        private String pit_scout_name;
        private String drivetrain;
        private String comment;
        private Boolean claim_auto_score_high;
        private Boolean claim_auto_score_low;
        private Boolean claim_auto_cross_low_bar;
        private Boolean claim_auto_cross_other_defense;
        private HashMap<Defense, CrossCapability> claim_teleop_defense_cross_ability;
        private Boolean claim_teleop_boulder_lowgoal;
        private Boolean claim_teleop_boulder_highgoal;
        private Boolean claim_teleop_scale;

        public Builder(int team_number) {
            this.team_number = team_number;
            claim_teleop_defense_cross_ability = new HashMap<>();
        }

        public Builder(String team_number) {
            this(Double.valueOf(team_number).intValue());
        }

        public Builder scout_name(String scout_name){
            this.pit_scout_name = scout_name;
            return this;
        }

        public Builder drivetrain(String drivetrain) {
            this.drivetrain = drivetrain;
            return this;
        }

        public Builder comment(String comment) {
            this.comment = comment;
            return this;
        }

        public Builder auto_claims(String auto_claims){
            claim_auto_cross_low_bar = auto_claims.contains(DataDefinitions.AutoClaims.low_bar);
            claim_auto_cross_other_defense = auto_claims.contains(DataDefinitions.AutoClaims.other_defense);
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
            claim_teleop_boulder_lowgoal = boulder_claims.contains(DataDefinitions.BoulderClaims.low_goal);
            claim_teleop_boulder_highgoal = boulder_claims.contains(DataDefinitions.BoulderClaims.high_goal);
            return this;
        }

        public Builder endgame_claims(String endgame_claims){
            claim_teleop_scale = endgame_claims.contains(DataDefinitions.EndgameClaims.scale);
            return this;
        }

        public Team build(){
            boolean complete = this.drivetrain != null &&
                this.pit_scout_name != null &&
                this.comment != null &&
                this.claim_auto_score_high != null &&
                this.claim_auto_score_low != null &&
                this.claim_auto_cross_low_bar != null &&
                this.claim_auto_cross_other_defense != null &&
                this.claim_teleop_defense_cross_ability != null &&
                this.claim_teleop_boulder_lowgoal != null &&
                this.claim_teleop_boulder_highgoal != null &&
                this.claim_teleop_scale != null;

            if (complete){
                return new Team(this);
            } else {
                throw new UnsupportedOperationException("The builder has not been completed");
            }
        }

    }
}
