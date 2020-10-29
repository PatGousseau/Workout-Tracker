package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Stream;
import model.Exercise;
import org.json.*;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    public Exercise read(String exerciseKey) throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONObject allExercises = (JSONObject) jsonObject.get("exercise");
        JSONObject exercise = (JSONObject) allExercises.get(exerciseKey);
        JSONArray allData = (JSONArray) exercise.get("entries");
        JSONObject data = (JSONObject) allData.get(allData.length() - 1);
        return jsonToExercise(data,exerciseKey);
    }

    private Exercise jsonToExercise(JSONObject allData, String name) {
        ArrayList reps = new ArrayList();
        ArrayList weight = new ArrayList();
        JSONArray jsonReps = (JSONArray) allData.get("numReps");
        JSONArray jsonWeight = (JSONArray) allData.get("weight");
        for (int i = 0; i < (Integer) allData.get("numSets"); i++) {
            reps.add(jsonReps.getInt(i));
            weight.add(jsonWeight.getInt(i));
        }
        Exercise exercise = new Exercise(name,(Integer) allData.get("numSets"),reps,weight);
        return exercise;
    }

    public Set<String> getKeys() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        JSONObject allExercises = (JSONObject) jsonObject.get("exercise");
        Set<String> keys = allExercises.keySet();
        return keys;
    }

    // EFFECTS: reads source file as string and returns it
    public String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }
}
