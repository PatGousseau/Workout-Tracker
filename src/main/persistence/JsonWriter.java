package persistence;

import model.Exercise;
import model.Routine;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Set;

// Represents a writer that writes JSON representation of workroom to file
// Some code borrowed from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonWriter {

    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    //MODIFIES: this
    //EFFECTS: writes the routine into the database
    public void write(Routine routine) throws IOException {

        JsonReader read = new JsonReader(destination);
        ArrayList<Exercise> newExercises = routine.getAllExercises();
        Set<String> keys = read.getKeys();

        //create new entry object
        for (Exercise exercise : newExercises) {
            JSONObject newEntry = new JSONObject();

            newEntry = new JSONObject();
            newEntry.put("Date", "10/24/2020");
            newEntry.put("numSets", exercise.getNumSets());
            newEntry.put("numReps", exercise.getReps());
            newEntry.put("weight", exercise.getWeight());

            String jsonData = read.readFile(destination);
            JSONObject jsonObject = new JSONObject(jsonData);

            if (keys.contains(exercise.getName())) {

                addToEntries(jsonObject, newEntry, exercise);
            } else {
                addNewExercise(jsonObject, newEntry, exercise);
            }

            writer = new PrintWriter(new File(destination));
            writer.println(jsonObject);
            writer.close();
        }

    }

    //REQUIRES: parameters must not be null
    //EFFECTS: adds a new exercise to the database
    private void addNewExercise(JSONObject jsonObject, JSONObject newEntry, Exercise exercise) {
        JSONObject allExercises = (JSONObject) jsonObject.get("exercise");
        JSONObject jsonExercise = new JSONObject();
        JSONArray entryList = new JSONArray();
        entryList.put(newEntry);
        jsonExercise.put("entries", entryList);
        allExercises.put(exercise.getName(), jsonExercise);
    }

    //REQUIRES: parameters must not be null
    //EFFECTS: adds an entry to an already existing exercise in the database
    private void addToEntries(JSONObject jsonObject, JSONObject newEntry, Exercise exercise) {
        JSONObject allExercises = (JSONObject) jsonObject.get("exercise");
        JSONObject pastExercise = (JSONObject) allExercises.get(exercise.getName());
        pastExercise.append("entries", newEntry);
    }
}
