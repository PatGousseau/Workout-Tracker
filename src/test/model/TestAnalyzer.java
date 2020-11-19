package model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TestAnalyzer {

    ArrayList<Long> reps = new ArrayList<Long>();
    ArrayList<Long> weight = new ArrayList<Long>();
    ArrayList<Long> benchWeight = new ArrayList<Long>();
    Exercise curls;
    Exercise benchPress;
    Exercise squat;
    Analyzer analyze;
    Routine routine;

    @BeforeEach
    void before() {
        routine = new Routine("testRoutine");
        long numSets = 3;
        for (int i = 0; i < 3; i++) {
            reps.add(12L);
            weight.add(36L);
        }

        curls = new Exercise("squat", numSets, reps, weight);
        analyze = new Analyzer();
    }


    @Test
    void testCompareData() throws IOException {
        routine.addExercise(curls);


        Hashtable newDatum = analyze.compareData(routine, "data/data_reader_test.json");
        assertEquals(String.valueOf(14.29), newDatum.get("squat"));
        Routine secondRoutine = new Routine("secondRoutine");
        secondRoutine.addExercise(new Exercise("doesNotExist", 1, reps, weight));
        analyze.compareData(secondRoutine, "data/data_reader_test.json");

    }


    @Test
    void testCompareDataExceptions() {

        try {
            analyze.compareData(routine, "data/data_reader_test.json");
        } catch (IOException e) {
            fail();
        }
    }


}
