package us.andrewdickinson.frc.scoutingproccessing.schedule;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPHeaderCell;
import com.itextpdf.text.pdf.PdfPTable;
import us.andrewdickinson.frc.scoutingproccessing.TBACommunication;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Andrew on 3/9/16.
 */
public class ScoutingSchedule implements Serializable {
    private List<int[]> matches;
    private ArrayList<int[]> scoutingMatches;
    private int scouts_per_match;

    public ScoutingSchedule(TBACommunication tba, int scouts_per_match){
        if (!tba.scheduleGenerated()) throw new IllegalStateException("Event Schedule must be generated first");

        matches = new ArrayList<>();
        scoutingMatches = new ArrayList<>();

        this.scouts_per_match = scouts_per_match;

        if (tba.scheduleGenerated()){
            for (int i = 1; i <= tba.getNumberOfMatches(); i++){
                if (tba.isMatchNotOnLastDay(i)) matches.add(tba.getTeams(i));
            }
        }

        for (int[] match : matches){
            Random r = new Random();
            List<Integer> matchList = Arrays.stream(match).boxed().collect(Collectors.toList());
            int[] match_reduced = new int[scouts_per_match];
            for (int i = 0; i < scouts_per_match; i++){
                int team = r.nextInt(6 - i);
                match_reduced[i] = matchList.remove(team);
            }
            scoutingMatches.add(match_reduced);
//            scoutingMatches.add(Arrays.copyOfRange(match, 0, scouts_per_match));
        }
    }

    protected int[] getMatch(int match){
        return scoutingMatches.get(match - 1);
    }

    public String toString(){
        String out = "";
        for (int[] match : scoutingMatches){
            for (int team : match){
                out += team + ", ";
            }
            out += "\n";
        }

        return out;
    }

    public double getScore(){
        Map<Integer, Integer> frequencyMap = getFrequencyMap();
        List<Integer> freqs = frequencyMap.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());

        //Compute the variance
        double sum = 0.0;
        double size = freqs.size();
        for(double a : freqs)
            sum += a;
        double mean = sum/size;

        double temp = 0;
        for(double a :freqs) {
            temp += (mean - a) * (mean - a);
        }

        return temp/size;
    }

    public Map<Integer, Integer> getDistribution(){
        Map<Integer, Integer> frequencyMap = getFrequencyMap();
        Map<Integer, Integer> distribution = new HashMap<>();
        List<Integer> freqs = frequencyMap.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());

        for (int matches : freqs){
            if (distribution.containsKey(matches)){
                distribution.put(matches, distribution.get(matches) + 1);
            } else {
                distribution.put(matches, 1);
            }
        }

        return distribution;
    }

    public double getAverageMatchesPerTeam(){
        Map<Integer, Integer> frequencyMap = getFrequencyMap();
        List<Integer> freqs = frequencyMap.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());

        //Compute the variance
        double sum = 0.0;
        double size = freqs.size();
        for(double a : freqs)
            sum += a;
        return sum/size;
    }

    public Map<Integer, Integer> getFrequencyMap(){
        Map<Integer, Integer> numberOfMatchesDistribution = new HashMap<>(105);
        for (int[] match : scoutingMatches){
            for (int team : match){
                if (numberOfMatchesDistribution.containsKey(team)){
                    numberOfMatchesDistribution.put(team, numberOfMatchesDistribution.get(team) + 1);
                } else {
                    numberOfMatchesDistribution.put(team, 1);
                }
            }
        }

        return numberOfMatchesDistribution;
    }

    protected void replaceTeamInMatch(int match, int find, int replace){
        for (int i = 0; i < scoutingMatches.get(match - 1).length; i++){
            if (scoutingMatches.get(match - 1)[i] == find){
                scoutingMatches.get(match - 1)[i] = replace;
                return;
            }
        }

        throw new IllegalArgumentException("Team: " + find + " not found for given match: " + match);
    }

    public int[] getTeamReplacementOptions(int match){
        List<Integer> options = Arrays.stream(matches.get(match - 1)).boxed().collect(Collectors.toList());
        options.removeAll(Arrays.stream(scoutingMatches.get(match - 1)).boxed().collect(Collectors.toList()));
        return options.stream().mapToInt(Integer::intValue).toArray();
    }

    public List<Integer> getTeamsOverAverage(){
        Map<Integer, Integer> frequencyMap = getFrequencyMap();
        double average_matches = getAverageMatchesPerTeam();
        return frequencyMap.entrySet().stream()
                .filter(entry -> entry.getValue() > average_matches)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public int numberOfMatches(){
        return scoutingMatches.size();
    }

    public PdfPTable getPDFTable(){
        return getPDFTable(false, false);
    }

    public PdfPTable getPDFTable(boolean seperate_odd_even, boolean requesting_odd){
        Font small = new Font(Font.FontFamily.TIMES_ROMAN, 11.0f);

        PdfPTable table = new PdfPTable(1 + scouts_per_match);
        table.addCell(new Phrase("Match", small));
        for (int i = 0; i < scouts_per_match; i++){
            table.addCell(new Phrase("" + (i + 1), small));
        }

        int delta = 1;
        if (seperate_odd_even) delta = 2;

        int start = 0;
        if (requesting_odd) start = 1;

        int row = 0;
        for (int i = start; i < scoutingMatches.size(); i = i + delta){
            PdfPCell cell = new PdfPCell(new Phrase((i + 1) + "", small));
            if (row % 2 == 0){
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            }

            table.addCell(cell);
            for (int team : scoutingMatches.get(i)){
                PdfPCell cell2 = new PdfPCell(new Phrase(team + "", small));
                if (row % 2 == 0){
                    cell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
                }

                table.addCell(cell2);
            }

            row++;
        }

        return table;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScoutingSchedule that = (ScoutingSchedule) o;

        for (int i = 0; i < scoutingMatches.size(); i++){
            if (!Arrays.equals(this.scoutingMatches.get(i), that.scoutingMatches.get(i))) return false;
        }

        for (int i = 0; i < matches.size(); i++){
            if (!Arrays.equals(this.matches.get(i), that.matches.get(i))) return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = matches.hashCode();
        result = 31 * result + scoutingMatches.hashCode();
        return result;
    }
}
