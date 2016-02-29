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

    }
}