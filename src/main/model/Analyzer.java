package model;

import persistence.JsonReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Hashtable;
import java.util.Set;

/*
This class represents an Analyzer that can perform a mathematical
analysis on an Exercise
 */

public class Analyzer {


    //REQUIRES: routine and fileName can not be null
    //EFFECTS: For each exercise just completed, it returns the percentage of improvement
    //         from the last time the user performed each exercise
    public Hashtable compareData(Routine routine, String fileName) throws IOException {

        JsonReader reader = new JsonReader(fileName);
        Hashtable improvement = new Hashtable();
        Set<String> keys = reader.getKeys();
        for (int i = 0; i < routine.getSize(); i++) {

            if (keys.contains(routine.getExercise(i).getName())) {
                Exercise pastExercise = reader.read(routine.getExercise(i).getName());
                double percentIncrease = 0;
                DecimalFormat df = new DecimalFormat("0.00");
                percentIncrease = ((routine.getExercise(i).getVol() / pastExercise.getVol()) * 100) - 100;
                improvement.put(routine.getExercise(i).getName(), df.format(percentIncrease));
            }
        }
        return improvement;
    }
}