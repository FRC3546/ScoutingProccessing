package us.andrewdickinson.frc.scoutingproccessing.schedule;

import us.andrewdickinson.frc.scoutingproccessing.TBACommunication;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Andrew on 3/9/16.
 */
public class ScoutingSchedule {
    private List<int[]> matches;
    private ArrayList<int[]> scoutingMatches;

    public ScoutingSchedule(TBACommunication tba){
        matches = new ArrayList<>();
        scoutingMatches = new ArrayList<>();

        if (tba.scheduleGenerated()){
            for (int i = 1; i <= tba.getNumberOfMatches(); i++){
                if (tba.isMatchNotOnLastDay(i)) matches.add(tba.getTeams(i));
            }
        }

        for (int[] match : matches){
            Random r = new Random();
            List<Integer> matchList = Arrays.stream(match).boxed().collect(Collectors.toList());
            int[] match_reduced = new int[4];
            for (int i = 0; i < 4; i++){
                int team = r.nextInt(6 - i);
                match_reduced[i] = matchList.remove(team);
            }
            scoutingMatches.add(match_reduced);
//            scoutingMatches.add(Arrays.copyOfRange(match, 0, 4));
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
        Map<Integer, Integer> numberOfMatchesDistribution = new HashMap<>(45);
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

        throw new IllegalArgumentException("Team: " + find + " not found for given match");
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
