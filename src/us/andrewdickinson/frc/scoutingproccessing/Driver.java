package us.andrewdickinson.frc.scoutingproccessing;

import com.itextpdf.text.DocumentException;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Andrew on 2/28/16.
 */
public class Driver {
    public static void main(String[] args) throws IOException, DocumentException {
        TreeMap<Integer, Team> teams = Importer.importAllData(args[0]);

        int path_index = Arrays.asList(args).indexOf("-o") + 1;
        String path = "";
        if (path_index != 0) {
            path = args[path_index];
        }

        switch (args[1]) {
            case "team":
                TeamReport tr = new TeamReport(teams.get(Integer.parseInt(args[2])));
                ReportGenerator generator = new ReportGenerator(tr, path);
                generator.generate();
                break;
            case "match":
                MatchReport mr = new MatchReport(teams, Integer.parseInt(args[2]));
                mr.generateMatchPDF(path);
                break;
            case "matchteams":
                MatchReport mr2 = new MatchReport(teams, Integer.parseInt(args[2]));
                mr2.generateMatchTeamsPDF(path);
                break;
            case "allteams":
                String outputPath = path + "allteams.pdf";
                String directory = path;

                if (path.contains(".pdf")) {
                    outputPath = path;
                    directory = new File(path).getAbsoluteFile().getParentFile().getAbsolutePath();
                }

                for (Team team : teams.values()) {
                    new TeamReport(team).generatePDF(directory);
                }

                List<Integer> teamNumbers = teams.keySet().stream().collect(Collectors.toList());

                ReportGenerator.mergeTeams(directory, outputPath, new ArrayList<>(teamNumbers));
                break;
        }
    }
}
