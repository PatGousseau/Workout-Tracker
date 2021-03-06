package persistence;

import model.Exercise;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Stream;

// Represents a reader that reads Exercises from JSON data stored in file
// Some code borrowed from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    //MODIFIES: this
    //EFFECTS: reads the last entry of the user for the exercise of choice.
    // Turns the last data into an Exercise object and returns it.
    public Exercise read(String exerciseKey) throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONObject allExercises = (JSONObject) jsonObject.get("exercise");
        JSONObject exercise = (JSONObject) allExercises.get(exerciseKey);
        JSONArray allData = (JSONArray) exercise.get("entries");
        JSONObject data = (JSONObject) allData.get(allData.length() - 1);
        return jsonToExercise(data, exerciseKey);
    }

    //MODIFIES: this
    //EFFECTS: reads the last entry of the user for the exercise of choice.
    // Turns the last data into an Exercise object and returns it.
    public HashMap readVolAndDate(String exerciseKey) throws IOException {
        ArrayList<Exercise> allExerciseEntries = new ArrayList<>();
        HashMap volAndDate = new HashMap();
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONObject allExercises = (JSONObject) jsonObject.get("exercise");
        JSONObject exercise = (JSONObject) allExercises.get(exerciseKey);
        JSONArray allData = (JSONArray) exercise.get("entries");
        for (int i = 0; i < allData.length(); i++) {
            allExerciseEntries.add(jsonToExercise((JSONObject) allData.get(i), exerciseKey));
        }
        for (int i = 0; i < allExerciseEntries.size(); i++) {
            volAndDate.put(allExerciseEntries.get(i).getDate(), allExerciseEntries.get(i).getVol());
        }
        JSONObject data = (JSONObject) allData.get(allData.length() - 1);
        return volAndDate;
    }

    //REQUIRES: parameters must not be null
    //EFFECTS: turns the jsonObject to an Exercise object and returns it
    private Exercise jsonToExercise(JSONObject allData, String name) {
        ArrayList reps = new ArrayList();
        ArrayList weight = new ArrayList();
        JSONArray jsonReps = (JSONArray) allData.get("numReps");
        JSONArray jsonWeight = (JSONArray) allData.get("weight");
        String jsonDate = (String) allData.get("Date");
        for (int i = 0; i < (Integer) allData.get("numSets"); i++) {
            reps.add(jsonReps.getInt(i));
            weight.add(jsonWeight.getInt(i));
        }
        Exercise exercise = new Exercise(name, (Integer) allData.get("numSets"), reps, weight, jsonDate);
        return exercise;
    }

    //REQUIRES: parameters must not be null
    //EFFECTS: returns all exercise names in the data
    public Set<String> getKeys() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONObject allExercises = (JSONObject) jsonObject.get("exercise");
        Set<String> keys = allExercises.keySet();
        return keys;
    }

    //REQUIRES: parameters must not be null
    // EFFECTS: reads source file as string and returns it
    public String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }
}
