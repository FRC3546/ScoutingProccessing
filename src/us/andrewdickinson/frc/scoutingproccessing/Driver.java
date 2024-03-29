package us.andrewdickinson.frc.scoutingproccessing;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;
import us.andrewdickinson.frc.scoutingproccessing.schedule.ScheduleGenerator;
import us.andrewdickinson.frc.scoutingproccessing.schedule.ScoutingSchedule;

import java.io.File;
import java.io.FileOutputStream;
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

                    if (args[2] != null && !args[2].equals("") && !args[2].equals("-o")) {
                        gen.createPDF("schedule.pdf", true, Integer.parseInt(args[2]) % 2 == 0);
                    } else {
                        gen.createPDF("schedule.pdf");
                    }
                } else {
                    ScheduleGenerator gen = new ScheduleGenerator(Integer.parseInt(args[2]));

                    System.out.println("Seed Score: " + gen.getScoutingSchedule().getScore());

                    Map<Integer, Integer> map = gen.getScoutingSchedule().getDistribution();
                    System.out.println("Seed Distribution: " + map);

                    System.out.println("Generating...");
                    gen.generate(Double.parseDouble(args[3]));

                    System.out.println("Final Score: " + gen.getScoutingSchedule().getScore());

                    map = gen.getScoutingSchedule().getDistribution();
                    System.out.println("Final Distribution: " + map);

                    if (args[2] != null && !args[2].equals("") && !args[2].equals("-o")) {
                        gen.createPDF("schedule.pdf", true, Integer.parseInt(args[2]) % 2 == 0);
                    } else {
                        gen.createPDF("schedule.pdf");
                    }

                    gen.export("scouting_schedule");
                }

            case "eventschedule":
                TBACommunication.getInstance().createPDF("eventschedule.pdf");

            case "pitgaps":
                List<Integer> event_teams = TBACommunication.getInstance().getTeamNumbers();
                List<Integer> pit_scouted = new ArrayList<>();
                pit_scouted.addAll(teams.keySet());

                List<Integer> not_scouted = new ArrayList<>();
                for (Integer team : event_teams){
                    if (!pit_scouted.contains(team)) not_scouted.add(team);
                }

                if (path.equals("")) path = "pitgaps.pdf";

                String outputPath1 = path + ".pdf";

                if (path.contains(".pdf")){
                    outputPath1 = path;
                }

                Document document = new Document(PageSize.LETTER);
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(outputPath1));
                writer.setMargins(0, 0, 0, 0);
                document.open();

                Collections.sort(not_scouted);

                for (Integer team : not_scouted){
                    document.add(new Phrase(team.toString() + "\n"));
                }

                document.close();
        }
    }
}
