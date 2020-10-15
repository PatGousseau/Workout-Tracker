package model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Hashtable;

/*
This class represents an Analyzer that can perform a mathematical
analysis on an Exercise
 */

public class Analyzer {

    //REQUIRES: Data must not be null
    //EFFECTS: Reads the json file containing previous the historical data and
    //         returns a list of of those exercises
    public static ArrayList jsonDataToList(JSONArray data, String name, ArrayList<Exercise> pastExercises) {

        // Loop over each exercise in Json file
        for (int j = 0; j < data.size(); j++) {
            JSONObject entry = (JSONObject) data.get(j);
            JSONArray repsJson = (JSONArray) entry.get("numReps");
            JSONArray weightJson = (JSONArray) entry.get("weight");
            ArrayList<Long> reps = new ArrayList<Long>();
            ArrayList<Long> weight = new ArrayList<Long>();

            // Loop over the arrays of reps and weight in Json file
            for (int k = 0; k < repsJson.size(); k++) {
                reps.add((Long)repsJson.get(k));
                weight.add((Long)weightJson.get(k));
            }

            long numSets = (Long)(entry.get("numSets"));
            String date = (String) entry.get("date");
            Exercise newExercise =  new Exercise(name, numSets, reps, weight);
            pastExercises.add(newExercise);
        }
        return pastExercises;
    }

    //EFFECTS: Returns the data from the last time the user performed
    //         each exercise
    public static ArrayList<Exercise> getHistoricalData() throws IOException, ParseException {

        ArrayList<Exercise> pastExercises  = new ArrayList<Exercise>();
        JSONParser parser = new JSONParser();
        JSONObject jsonObject  = (JSONObject) parser.parse(new FileReader("data/data.json"));
        JSONArray allExercise = (JSONArray) jsonObject.get("exercise");

        for (int i = 0; i < allExercise.size(); i++) {
            JSONObject jsonExercise = (JSONObject) allExercise.get(i);
            String name = (String) jsonExercise.get("name");
            JSONArray data = (JSONArray) jsonExercise.get("data");
            pastExercises = jsonDataToList(data, name, pastExercises);
        }
        return pastExercises;
    }

    //REQUIRES: routine can not be null
    //EFFECTS: For each exercise just completed, it returns the percentage of improvement
    //         from the last time the user performed each exercise
    public Hashtable compareData(Routine routine) throws ParseException, IOException {

        Hashtable improvement = new Hashtable();
        ArrayList<Exercise> data = getHistoricalData();

        for (int i = 0; i < routine.getSize(); i++) {
            for (int j = 0; j < data.size(); j++) {
                double percentIncrease = 0;
                DecimalFormat df = new DecimalFormat("0.00");
                if (routine.getExercise(i).getName().equals(data.get(j).getName())) {
                    percentIncrease = ((routine.getExercise(i).getVol() / data.get(i).getVol()) * 100) - 100;
                    improvement.put(routine.getExercise(i).getName(), df.format(percentIncrease));
                }
            }
        }
        return improvement;
    }
}