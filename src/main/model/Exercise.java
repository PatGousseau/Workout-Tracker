package model;

import java.util.ArrayList;

/*
This class represents an exercise which has a number of sets, reps, weight and a name
 */

public class Exercise {

    private String name;
    private long numSets;
    private ArrayList<Long> reps;
    private ArrayList<Long> weight;


    public Exercise(String name, long numSets, ArrayList<Long> reps, ArrayList<Long> weight) {
        this.name = name;
        this.numSets = numSets;
        this.reps = reps;
        this.weight = weight;
    }

    public String getName() {
        return this.name;
    }

    public long getNumSets() {
        return this.numSets;
    }

    public ArrayList<Long> getReps() {
        return this.reps;
    }

    public ArrayList<Long> getWeight() {
        return this.weight;
    }

    //EFFECTS: Returns the workout volume for this
    public double getVol() {
        double vol = 0;
        for (int i = 0; i < reps.size(); i++) {
            vol += (reps.get(i) * weight.get(i));
        }
        return vol;
    }
}