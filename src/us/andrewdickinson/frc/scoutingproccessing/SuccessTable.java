package us.andrewdickinson.frc.scoutingproccessing;

import java.util.*;

/**
 * Created by Andrew on 2/28/16.
 */
public class SuccessTable<Task extends Enum, Rating extends Enum> {
    private HashMap<Task, HashMap<Rating, Integer>> successTable;

    public SuccessTable (ArrayList<HashMap<Task, Rating>> successMaps, Class<Task> taskEnumType, Class<Rating> ratingEnumType){
        successTable = new HashMap<>();

        //Initialize and fill with zeros
        Arrays.asList(taskEnumType.getEnumConstants()).forEach(((task)
                -> successTable.put(task, new HashMap<>())));
        successTable.forEach(((task, ratingIntegerHashMap)
                -> Arrays.asList(ratingEnumType.getEnumConstants()).forEach(((rating)
                -> ratingIntegerHashMap.put(rating, 0)))));

        for (HashMap<Task, Rating> matchSuccessMap : successMaps){
            matchSuccessMap.forEach(((task, rating)
                    -> successTable.get(task)
                    .put(rating, successTable
                            .get(task)
                            .get(rating) + 1)));
        }
    }

    public void removeRating(Rating rating){
        successTable.forEach((task, ratingIntegerHashMap) -> ratingIntegerHashMap.remove(rating));
    }

    public HashMap<Rating, Integer> getFrequencyMap(Task task){
        return successTable.get(task);
    }

    public int getEntry(Task task, Rating rating){
        return successTable.get(task).get(rating);
    }
}
