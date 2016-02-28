package us.andrewdickinson.frc.scoutingproccessing;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Andrew on 2/28/16.
 */
public class Importer {
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
            b.drivetrain(row.get(hm.indexForUID("drivetrain")));
            b.ground_clearance(row.get(hm.indexForUID("ground_clearance")));
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
            b.comment(row.get(hm.indexForUID("comments")));

            teams.add(new Team(b));
        }

        return teams;
    }

    public static ArrayList<Team> importTeamMatches(String xlsxFilePath) throws IOException {
        ArrayList<ArrayList<String>> team_data_sheet =
                getArrayListFromFile(xlsxFilePath, DataDefinitions.SheetNames.matchScouting);

        //We'll deal with the headers separately
        ArrayList<String> headers = team_data_sheet.remove(0);

        HeaderManagement hm = DataDefinitions.getInstance().getMatchScoutingHeaders();
        hm.addSheetHeaders(headers);

        ArrayList<Team> teams = new ArrayList<>();

        for (ArrayList<String> row : team_data_sheet){
            String team_number = row.get(hm.indexForUID("team_number"));
            if (team_number.equals("")){
                continue;
            }

            Team.Builder b = new Team.Builder(team_number);
            b.drivetrain(row.get(hm.indexForUID("drivetrain")));
            b.ground_clearance(row.get(hm.indexForUID("ground_clearance")));
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
            b.comment(row.get(hm.indexForUID("comments")));

            teams.add(new Team(b));
        }

        return teams;
    }

    private static ArrayList<ArrayList<String>> getArrayListFromFile(
            String xlsxFilePath, String sheetname) throws IOException {
        XSSFWorkbook master_spreadsheet = new XSSFWorkbook(xlsxFilePath);
        XSSFSheet pit_sheet = master_spreadsheet.getSheet(sheetname);

        ArrayList<ArrayList<String>> sheetRows = new ArrayList<>();
        for (int i = 0; i <= pit_sheet.getLastRowNum(); i++) {
            ArrayList<String> sheetRow = new ArrayList<>();
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

        return sheetRows;
    }
}
