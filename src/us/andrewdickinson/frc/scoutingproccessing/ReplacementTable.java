package us.andrewdickinson.frc.scoutingproccessing;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Andrew on 3/6/16.
 */
public class ReplacementTable {
    TreeMap<Integer, Replacement> replacements;

    public ReplacementTable(){
        replacements = new TreeMap<>();
    }

    public void addReplacement(String match, String replace){
        replacements.put(replacements.size(), new Replacement(match, replace));
    }

    public String replaceInString(String original){
        String shortened = original;
        for (Replacement replacement : replacements.values()){
            shortened = shortened.replace(replacement.match, replacement.replace);
        }

        return shortened;
    }

    private class Replacement {
        private String match;
        private String replace;

        public Replacement(String match, String replace){
            this.match = match;
            this.replace = replace;
        }
    }
}
