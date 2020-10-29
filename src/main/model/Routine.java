package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/*
This class represents a workout routine, comprised of Exercises and a name
 */

public class Routine {

    private ArrayList<Exercise> allExercises; // The list of all exercises in the routine
    private String name; // The name of the routine

    public Routine(String name) {
        allExercises = new ArrayList<Exercise>();
        this.name = name;

    }

    // Add an Exercise
    //REQUIRES: exercise can not be null
    //MODIFIES: this
    //EFFECTS: Adds an exercise to the end of the routine
    public void addExercise(Exercise exercise) {
        allExercises.add(exercise);
    }

    public int getSize() {
        return allExercises.size();
    }

    public ArrayList<Exercise> getAllExercises() {
        return allExercises;
    }

    public Exercise getExercise(int index) {
        return allExercises.get(index);
    }

    // Remove an Exercise
    //REQUIRES: Exercise in routine must have the name in parameter
    //MODIFIES: this
    //EFFECTS: Removes an exercise to the routine
    public void removeExercise(String name) {
        for (int i = 0; i < this.getSize(); i++) {
            if (getExercise(i).getName().equals(name)) {
                allExercises.remove(getExercise(i));
            }
        }
    }

    public String getName() {
        return name;
    }


}
