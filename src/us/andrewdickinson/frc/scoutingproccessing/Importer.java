package us.andrewdickinson.frc.scoutingproccessing;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * Created by Andrew on 2/28/16.
 */
public class Importer {
    public static TreeMap<Integer, Team> importAllData(String xlsxFilePath) throws IOException{
        ArrayList<Team> teams = importTeams(xlsxFilePath);

        TreeMap<Integer, Team> teamHashMap = new TreeMap<>();
        for (Team team : teams){
            teamHashMap.put(team.getTeamNumber(), team);
        }

        ArrayList<TeamMatch> teamMatches = importTeamMatches(xlsxFilePath);

        for (TeamMatch teamMatch : teamMatches){
            if (teamHashMap.containsKey(teamMatch.getTeamNumber())){
                teamHashMap.get(teamMatch.getTeamNumber()).addTeamMatch(teamMatch);
            } else {
                //TODO: Re-enable this error
//                throw new IndexOutOfBoundsException("No pit scouting entry for match-scouted team: " + teamMatch.getTeamNumber());
            }
        }

        return teamHashMap;
    }


    public static ArrayList<Team> importTeams(String xlsxFilePath) throws IOException {
        ArrayList<ArrayList<String>> team_data_sheet =
                getArrayListFromFile(xlsxFilePath, DataDefinitions.SheetNames.pitScouting);

        //We'll deal with the headers separately
        ArrayList<String> headers = team_data_sheet.remove(0);

        HeaderManagement hm = DataDefinitions.getInstance().getPitScoutingHeaders();
        hm.addSheetHeaders(headers);

        ArrayList<Team> teams = new ArrayList<>();

        for (ArrayList<String> row : team_data_sheet){
            String team_number = row.get(hm.indexForUID("team_number"));
            if (team_number.equals("")){
                continue;
            }

            Team.Builder b = new Team.Builder(team_number);
            b.scout_name(row.get(hm.indexForUID("scout_name")));
            b.drivetrain(row.get(hm.indexForUID("drivetrain")));
            b.auto_claims(row.get((hm.indexForUID("autonomous_claims"))));
            b.claim_teleop_lowbar_cross_ability(row.get(hm.indexForUID("lowbar_claim")));
            b.claim_teleop_chevaldefrise_cross_ability(row.get(hm.indexForUID("chevaldefrise_claim")));
            b.claim_teleop_portcullis_cross_ability(row.get(hm.indexForUID("portcullis_claim")));
            b.claim_teleop_ramparts_cross_ability(row.get(hm.indexForUID("ramparts_claim")));
            b.claim_teleop_moat_cross_ability(row.get(hm.indexForUID("moat_claim")));
            b.claim_teleop_sallyport_cross_ability(row.get(hm.indexForUID("sallyport_claim")));
            b.claim_teleop_drawbridge_cross_ability(row.get(hm.indexForUID("drawbridge_claim")));
            b.claim_teleop_roughterrain_cross_ability(row.get(hm.indexForUID("roughterrain_claim")));
            b.claim_teleop_rockwall_cross_ability(row.get(hm.indexForUID("rockwall_claim")));
            b.boulder_claims(row.get(hm.indexForUID("boulder_claims")));
            b.endgame_claims(row.get(hm.indexForUID("endgame_claims")));
            if (row.size() > hm.indexForUID("comments")){
                b.comment(row.get(hm.indexForUID("comments")));
            } else {
                b.comment("");
            }

            teams.add(b.build());
        }

        return teams;
    }

    public static ArrayList<TeamMatch> importTeamMatches(String xlsxFilePath) throws IOException {
        ArrayList<ArrayList<String>> team_data_sheet =
                getArrayListFromFile(xlsxFilePath, DataDefinitions.SheetNames.matchScouting);

        //We'll deal with the headers separately
        ArrayList<String> headers = team_data_sheet.remove(0);

        HeaderManagement hm = DataDefinitions.getInstance().getMatchScoutingHeaders();
        hm.addSheetHeaders(headers);

        ArrayList<TeamMatch> teamMatches = new ArrayList<>();

        for (ArrayList<String> row : team_data_sheet){
            if (row.get(hm.indexForUID("matchnum")).equals("") || row.get(hm.indexForUID("team_number")).equals("")){
                continue;
            }

            int match_number = Double.valueOf(row.get(hm.indexForUID("matchnum"))).intValue();
            int team_number = Double.valueOf(row.get(hm.indexForUID("team_number"))).intValue();

            TeamMatch.Builder b = new TeamMatch.Builder(team_number, match_number);
            b.scout_name(row.get(hm.indexForUID("scout_name")));
            b.auto_staged_defense(row.get(hm.indexForUID("auto_staged_defense")));
            b.auto_defense_action(row.get(hm.indexForUID("auto_defense_action")));
            b.auto_attempted_score(row.get(hm.indexForUID("auto_score")));
            b.auto_robot_crossed_midline(row.get(hm.indexForUID("auto_robot_crossed_midline")));
            b.teleop_lowbar_cross_success(row.get(hm.indexForUID("lowbar_success")));
            b.teleop_chevaldefrise_cross_success(row.get(hm.indexForUID("chevaldefrise_success")));
            b.teleop_portcullis_cross_success(row.get(hm.indexForUID("portcullis_success")));
            b.teleop_ramparts_cross_success(row.get(hm.indexForUID("ramparts_success")));
            b.teleop_moat_cross_success(row.get(hm.indexForUID("moat_success")));
            b.teleop_sallyport_cross_success(row.get(hm.indexForUID("sallyport_success")));
            b.teleop_drawbridge_cross_success(row.get(hm.indexForUID("drawbridge_success")));
            b.teleop_roughterrain_cross_success(row.get(hm.indexForUID("roughterrain_success")));
            b.teleop_rockwall_cross_success(row.get(hm.indexForUID("rockwall_success")));
            b.teleop_balls_scored_low(row.get(hm.indexForUID("teleop_balls_scored_low")));
            b.teleop_balls_scored_high(row.get(hm.indexForUID("teleop_balls_scored_high")));
            b.scoringSuccess(row.get(hm.indexForUID("teleop_scoring_success")));
            b.teleop_endgame_action(row.get(hm.indexForUID("teleop_endgame_action")));
            b.teleop_defense_quality(row.get(hm.indexForUID("teleop_defense_quality")));
            b.drivingCoordination(row.get(hm.indexForUID("teleop_driving_coordination")));
            b.comment(row.get(hm.indexForUID("comment")));

            teamMatches.add(b.build());
        }

        return teamMatches;
    }

    private static ArrayList<ArrayList<String>> getArrayListFromFile(
            String xlsxFilePath, String sheetname) throws IOException {
        XSSFWorkbook master_spreadsheet = new XSSFWorkbook(xlsxFilePath);
        XSSFSheet pit_sheet = master_spreadsheet.getSheet(sheetname);

        ArrayList<ArrayList<String>> sheetRows = new ArrayList<>();
        for (int i = 0; i <= pit_sheet.getLastRowNum(); i++) {
            ArrayList<String> sheetRow = new ArrayList<>();
            if (pit_sheet.getRow(i) != null) {
                for (int j = 0; j <= pit_sheet.getRow(i).getLastCellNum(); j++) {
                    XSSFCell cell = pit_sheet.getRow(i).getCell(j); //Might be null
                    if (cell != null) {
                        sheetRow.add(cell.toString());
                    } else {
                        sheetRow.add("");
                    }
                }
                sheetRows.add(sheetRow);
            }
        }

        return sheetRows;
    }
}
