package persistence;

import jdk.nashorn.internal.parser.JSONParser;
import model.Exercise;
import model.Routine;
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.*;
import java.util.ArrayList;
import java.util.Set;

// Represents a writer that writes JSON representation of workroom to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    public void write(Routine routine) throws IOException {

        JsonReader read = new JsonReader(destination);
        ArrayList<Exercise> newExercises  = routine.getAllExercises();
        Set<String> keys = read.getKeys();

        //create new entry object
        for (Exercise exercise: newExercises) {
            JSONObject newEntry = new JSONObject();

            newEntry = new JSONObject();
            newEntry.put("Date", "10/24/2020");
            newEntry.put("numSets",exercise.getNumSets());
            newEntry.put("numReps",exercise.getReps());
            newEntry.put("weight", exercise.getWeight());

            String jsonData = read.readFile(destination);
            JSONObject jsonObject = new JSONObject(jsonData);

            if (keys.contains(exercise.getName())) {

                addToEntries(jsonObject, newEntry, exercise);
            } else {
                System.out.println(exercise.getName());
                addNewExercise(jsonObject,newEntry,exercise);
            }


            writer = new PrintWriter(new File(destination));
            writer.println(jsonObject);
            writer.close();
        }

    }

    private void addNewExercise(JSONObject jsonObject, JSONObject newEntry, Exercise exercise) {
        JSONObject allExercises = (JSONObject) jsonObject.get("exercise");
        JSONObject jsonExercise = new JSONObject();
        JSONArray entryList = new JSONArray();
        entryList.put(newEntry);
        jsonExercise.put("entries", entryList);
        allExercises.put(exercise.getName(), jsonExercise);
    }

    private void addToEntries(JSONObject jsonObject, JSONObject newEntry, Exercise exercise) {
        JSONObject allExercises = (JSONObject) jsonObject.get("exercise");
        JSONObject pastExercise = (JSONObject) allExercises.get(exercise.getName());
        pastExercise.append("entries", newEntry);
    }





























//    // EFFECTS: constructs writer to write to destination file
//    public JsonWriter(String destination) {
//        this.destination = destination;
//    }
//
//    // MODIFIES: this
//    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
//    // be opened for writing
//    public void open() throws FileNotFoundException {
//        writer = new PrintWriter(new File(destination));
//    }
//
//    // MODIFIES: this
//    // EFFECTS: writes JSON representation of workroom to file
//    public void write(WorkRoom wr) {
//        JSONObject json = wr.toJson();
//        saveToFile(json.toString(TAB));
//    }
//
//    // MODIFIES: this
//    // EFFECTS: closes writer
//    public void close() {
//        writer.close();
//    }
//
//    // MODIFIES: this
//    // EFFECTS: writes string to file
//    private void saveToFile(String json) {
//        writer.print(json);
//    }
}
