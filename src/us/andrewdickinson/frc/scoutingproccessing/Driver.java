package us.andrewdickinson.frc.scoutingproccessing;

import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.util.*;

/**
 * Created by Andrew on 2/28/16.
 */
public class Driver {
    public static void main(String[] args) throws IOException, DocumentException {
        HashMap<Integer, Team> teams = Importer
                .importAllData("/home/Andrew/gdrive/Master_Spreadsheet/Master_Spreadsheet.xlsx");
//        Team t = teams.get(4098);
//        TeamReport tr = new TeamReport(t);
//
//        ReportGenerator rg = new ReportGenerator(tr, "reports/team/");
//        rg.generate();
//
//        t = teams.get(2386);
//        tr = new TeamReport(t);
//
//        rg = new ReportGenerator(tr, "reports/team/");
//        rg.generate();
//
//        ArrayList<Integer> team_nums = new ArrayList<>();
//        team_nums.add(4098);
//        team_nums.add(2386);
//        ReportGenerator.mergeTeams("reports/team/", team_nums);

        MatchReport mr = new MatchReport(teams, 1);
        mr.generateMatchPDF();
    }
}
