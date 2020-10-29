package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/*
This class represents an exercise which has a number of sets, lists of reps and weight and a name
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

    //REQUIRES: the fields reps and weight can not be null
    //EFFECTS: Returns the workout volume for this
    public double getVol() {
        double vol = 0;
        for (int i = 0; i < reps.size(); i++) {

            vol += (double)(Integer.parseInt(String.valueOf(reps.get(i))) * Integer.parseInt(String.valueOf(weight.get(i))));
        }
        return vol;
    }

//    public JSONObject toJson() {
//        JSONObject json = new JSONObject();
//        json.put("name", name);
//        json.put("thingies", thingiesToJson());
//        return json;
//    }

//    // EFFECTS: returns things in this workroom as a JSON array
//    private JSONArray thingiesToJson() {
//        JSONArray jsonArray = new JSONArray();
//
//        for (Thingy t : thingies) {
//            jsonArray.put(t.toJson());
//        }
//
//        return jsonArray;
//    }
}