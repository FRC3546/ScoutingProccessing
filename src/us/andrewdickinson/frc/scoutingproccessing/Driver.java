package us.andrewdickinson.frc.scoutingproccessing;

import com.itextpdf.text.DocumentException;
import us.andrewdickinson.frc.scoutingproccessing.schedule.ScheduleGenerator;
import us.andrewdickinson.frc.scoutingproccessing.schedule.ScoutingSchedule;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Andrew on 2/28/16.
 */
public class Driver {
    public static void main(String[] args) throws IOException, DocumentException, ClassNotFoundException {
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
            case "alliance":
                MatchReport mr2 = new MatchReport(teams, Integer.parseInt(args[2]), true);
                mr2.generateMatchPDF(path);
                break;
            case "matchteams":
                MatchReport mr3 = new MatchReport(teams, Integer.parseInt(args[2]));
                mr3.generateMatchTeamsPDF(path);
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
            case "schedule":
                if (new File("scouting_schedule").exists()){
                    ScheduleGenerator gen = new ScheduleGenerator("scouting_schedule");

                    Map<Integer, Integer> map = gen.getScoutingSchedule().getDistribution();
                    System.out.println("Distribution: " + map);
                    System.out.println("Score: " + gen.getScoutingSchedule().getScore());

                    gen.createPDF("schedule.pdf");
                } else {
                    ScheduleGenerator gen = new ScheduleGenerator();

                    System.out.println("Seed Score: " + gen.getScoutingSchedule().getScore());

                    Map<Integer, Integer> map = gen.getScoutingSchedule().getDistribution();
                    System.out.println("Seed Distribution: " + map);

                    System.out.println("Generating...");
                    gen.generate(Double.parseDouble(args[2]));

                    System.out.println("Final Score: " + gen.getScoutingSchedule().getScore());

                    map = gen.getScoutingSchedule().getDistribution();
                    System.out.println("Final Distribution: " + map);

                    gen.createPDF("schedule.pdf");
                    gen.export("scouting_schedule");
                }

            case "eventschedule":
                TBACommunication.getInstance().createPDF("eventschedule.pdf");
        }
    }
}
