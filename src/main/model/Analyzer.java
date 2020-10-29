package model;

import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.JsonReader;

import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

/*
This class represents an Analyzer that can perform a mathematical
analysis on an Exercise
 */

public class Analyzer {


    //REQUIRES: routine can not be null
    //EFFECTS: For each exercise just completed, it returns the percentage of improvement
    //         from the last time the user performed each exercise
    public Hashtable compareData(Routine routine) throws IOException {

        JsonReader reader = new JsonReader("data/data.json");
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