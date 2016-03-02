package us.andrewdickinson.frc.scoutingproccessing;

import com.sun.deploy.util.StringUtils;
import sun.plugin.navig.motif.OJIPlugin;

import java.util.*;

/**
 * Created by Andrew on 3/1/16.
 */
public class ReportStrings {
    public static final String neverCrossedMidline = "Never Crossed Midline";
    public static final String neverDemonstrated = "Never Demonstrated";

    public static <T> String getItemSummary(ArrayList<T> items){
        HashMap<T, Integer> frequency = new HashMap<>();

        for (T item : items){
            if (frequency.containsKey(item)){
                frequency.put(item, frequency.get(item) + 1);
            } else {
                frequency.put(item, 1);
            }
        }

        return getHashMapSummary(frequency);
    }

    public static <T> String getPerformanceSummary(ArrayList<T> performance){
        String summary = getItemSummary(performance);
        if (summary.equals("")){
            return neverDemonstrated;
        } else {
            return summary;
        }
    }


    public static <T> String getPerformanceSummary(HashMap<T, Integer> performance){
        String summary = getHashMapSummary(performance);
        if (summary.equals("")){
            return neverDemonstrated;
        } else {
            return summary;
        }
    }

    public static <T> String getHashMapSummary(HashMap<T, Integer> frequency){
        SortedSet<Map.Entry<T, Integer>> sortedByFrequency = new TreeSet<>(
                new Comparator<Map.Entry<T, Integer>>() {
                    @Override
                    public int compare(Map.Entry<T, Integer> e1, Map.Entry<T, Integer> e2) {
                        int freqcomp = Integer.compare(e2.getValue(), e1.getValue());
                        if (freqcomp == 0) {
                            return e1.getKey().toString().compareTo(e2.getKey().toString());
                        } else {
                            return freqcomp;
                        }
                    }
                }
        );

        sortedByFrequency.addAll(frequency.entrySet());

        ArrayList<String> out = new ArrayList<>();

        for (Map.Entry<T, Integer> e : sortedByFrequency){
            T item = e.getKey();
            int freq = e.getValue();

            if (freq != 0) out.add(item.toString() + " (x" + freq + ")");
        }

        return StringUtils.join(out, ", ");
    }
}
