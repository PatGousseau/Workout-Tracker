package model;

import java.util.ArrayList;

/*
This class represents an exercise which has a number of sets, lists of reps and weight and a name
 */

public class Exercise {

    private String name;
    private long numSets;
    private ArrayList<Long> reps;
    private ArrayList<Long> weight;
    private String date;

    //EFFECTS: Constructs a new Exercise with a name, reps, weight and number of sets
    public Exercise(String name, long numSets, ArrayList<Long> reps, ArrayList<Long> weight) {
        this.name = name;
        this.numSets = numSets;
        this.reps = reps;
        this.weight = weight;
    }

    //EFFECTS: Constructs a new Exercise with a name, reps, weight and number of sets, and a date
    public Exercise(String name, long numSets, ArrayList<Long> reps, ArrayList<Long> weight,String date) {
        this.name = name;
        this.numSets = numSets;
        this.reps = reps;
        this.weight = weight;
        this.date = date;
    }

    public String getDate() {
        return this.date;
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

    //REQUIRES: the fields reps and weight can not be null
    //EFFECTS: Returns the workout volume for this
    public double getVol() {
        double vol = 0;
        for (int i = 0; i < reps.size(); i++) {
            int rep = (Integer.parseInt(String.valueOf(reps.get(i))));
            int weights = Integer.parseInt(String.valueOf(weight.get(i)));
            vol += (double) rep + weights;
        }
        return vol;
    }
}