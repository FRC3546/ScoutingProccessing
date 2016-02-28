package us.andrewdickinson.frc.scoutingproccessing;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by Andrew on 2/28/16.
 */
public class TeamMatchTests {
    @Test(expected = IllegalStateException.class)
    public void testEmptyBuilder(){
        TeamMatch testTeamMatch = new TeamMatch.Builder(3546, 1).build();
    }

    public void testFullBuilder(){
        TeamMatch testTeamMatch = new TeamMatch.Builder(3546, 1)
                .auto_staged_defense(Defense.Cheval_De_Frise)
                .auto_defense_action(AutonomousCrossAction.Nothing)
                .auto_attempted_score_goal(null)
                .auto_score_successful(false)
                .auto_robot_crossed_midline(false)
                .teleop_defense_success(new HashMap<>())
                .teleop_balls_scored_low(0)
                .teleop_balls_scored_high(0)
                .teleop_endgame_action(EndgameAction.Nothing)
                .teleop_defense_quality(Quality.Poor)
                .comment("")
                .build();
    }
}