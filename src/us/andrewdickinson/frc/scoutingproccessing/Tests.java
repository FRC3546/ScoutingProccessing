package us.andrewdickinson.frc.scoutingproccessing;

import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.TreeMap;

import static org.junit.Assert.*;

/**
 * Created by Andrew on 2/28/16.
 */
public class Tests {
    @Test
    public void testTeamEverything(){
        try {
            TreeMap<Integer, Team> teams = Importer
                    .importAllData("/home/Andrew/gdrive/Master_Spreadsheet/Master_Spreadsheet.xlsx");
            Team t = teams.get(4098);
            assertEquals("Andrew D.", t.getPitScoutName());

            assertTrue(4098 == t.getTeamNumber());

            assertEquals("4 Wheel Tank", t.getDrivetrain());
            assertTrue(t.getClaimAutoCrossLowBar());
            assertTrue(t.getClaimAutoCrossOtherDefense());
            assertFalse(t.getClaimAutoScoreLow());
            assertFalse(t.getClaimAutoScoreHigh());

            HashMap<Defense, CrossCapability> claimedDefCross = new HashMap<>();
            claimedDefCross.put(Defense.Low_Bar, CrossCapability.CrossSwiftly);
            claimedDefCross.put(Defense.Cheval_De_Frise, CrossCapability.Cross);
            claimedDefCross.put(Defense.Portcullis, CrossCapability.NotAble);
            claimedDefCross.put(Defense.Ramparts, CrossCapability.CrossSwiftly);
            claimedDefCross.put(Defense.Moat, CrossCapability.CrossSwiftly);
            claimedDefCross.put(Defense.Sally_Port, CrossCapability.NotAble);
            claimedDefCross.put(Defense.Drawbridge, CrossCapability.NotAble);
            claimedDefCross.put(Defense.Rough_Terrain, CrossCapability.CrossSwiftly);
            claimedDefCross.put(Defense.Rock_Wall, CrossCapability.CrossSwiftly);
            assertEquals(claimedDefCross, t.getClaimTeleopDefenseCrossAbility());

            assertTrue(t.getClaimTeleopBoulderLowgoal());
            assertFalse(t.getClaimTeleopBoulderHighgoal());

            assertTrue(t.getClaimTeleopScale());

            assertEquals("", t.getComment());

        } catch (IOException e){
            throw new IllegalStateException("An IO exception happened");
        }
    }

    @Test
    public void testTeamMatchEverything(){
        try {
            TreeMap<Integer, Team> teams = Importer
                    .importAllData("/home/Andrew/gdrive/Master_Spreadsheet/Master_Spreadsheet.xlsx");
            TeamMatch tm = teams.get(4098).getTeamMatches().get(0);
            assertEquals("Andrew D.", tm.getScoutName());
            assertTrue(1 == tm.getMatchNumber());

            assertEquals(Defense.Portcullis, tm.getAutoStagedDefense());
            assertEquals(AutonomousCrossAction.Reach, tm.getAutoDefenseAction());
            assertFalse(tm.inAutoRobotCrossedMidline());
            assertEquals(null, tm.getAutoAttemptedScoreGoal());
            assertFalse(tm.wasAutoScoreSuccessful());

            HashMap<Defense, CrossAction> defCross = new HashMap<>();
            defCross.put(Defense.Low_Bar, CrossAction.NotAttempted);
            defCross.put(Defense.Cheval_De_Frise, CrossAction.CrossedWell);
            defCross.put(Defense.Portcullis, CrossAction.CrossedPoorly);
            defCross.put(Defense.Ramparts, CrossAction.NotAttempted);
            defCross.put(Defense.Moat, CrossAction.NotAttempted);
            defCross.put(Defense.Sally_Port, CrossAction.NotAttempted);
            defCross.put(Defense.Drawbridge, CrossAction.NotAttempted);
            defCross.put(Defense.Rough_Terrain, CrossAction.NotAttempted);
            defCross.put(Defense.Rock_Wall, CrossAction.NotAttempted);
            assertEquals(defCross, tm.getTeleopDefenseSuccess());

            assertTrue(3 == tm.getTeleopBallsScoredLow());
            assertTrue(2 == tm.getTeleopBallsScoredHigh());

            assertEquals(ScoringSuccess.MissedSometimes, tm.getScoringQuality());

            assertEquals(EndgameAction.Nothing, tm.getTeleopEndgameAction());
            assertEquals(null, tm.getTeleopDefenseQuality());
            assertEquals(DrivingCoordination.Smooth, tm.getDrivingCoordination());

            assertEquals("", tm.getComment());

        } catch (IOException e){
            throw new IllegalStateException("An IO exception happened");
        }
    }
}