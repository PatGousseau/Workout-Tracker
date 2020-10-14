package model;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

public class TestModel {

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
            weight.add(35L);
            benchWeight.add(12L);
            benchWeight.add(40L);
        }

        curls = new Exercise("curls", numSets, reps, weight);
        squat = new Exercise("squat", numSets, reps, weight);
        benchPress = new Exercise("bench press", numSets, reps, benchWeight);
        analyze = new Analyzer();
    }

    @Test
    void testAddExercise() {
        routine.addExercise(curls);
        routine.addExercise(squat);
        assertEquals(2, routine.getAllExercises().size());
        assertEquals(curls, routine.getExercise(0));
        assertEquals(squat, routine.getExercise(1));
    }



    @Test
    void testGetVol() {
        assertEquals(1260, curls.getVol());
    }

    @Test
    void testCompareData() throws IOException, ParseException {
        routine.addExercise(curls);
        routine.addExercise(benchPress);
        routine.addExercise(squat);

        Hashtable newDatum = analyze.compareData(routine);
        assertEquals(String.valueOf(16.67), newDatum.get("curls"));
        assertEquals(String.valueOf(-28.89), newDatum.get("bench press"));
        assertEquals(String.valueOf(16.67), newDatum.get("squat"));
    }

    @Test
    void testGetHistoricalData() throws IOException, ParseException {
        routine.addExercise(curls);
        routine.addExercise(benchPress);
        routine.addExercise(squat);

        ArrayList<Exercise> oldData = analyze.getHistoricalData();
        ArrayList<Long> weightShouldBe = new ArrayList<Long>();
        for (int i = 0; i < 3; i++) {
            weightShouldBe.add(30L);
        }

        for (int i = 0; i < routine.getAllExercises().size(); i++) {
            assertEquals(oldData.get(i).getName(), oldData.get(i).getName()); // Compare name
            assertTrue(oldData.get(i).getNumSets() == oldData.get(i).getNumSets()); // Compare number of sets
            assertTrue(oldData.get(i).getName().equals(oldData.get(i).getName()));

            // Compare reps and weight
            for (int j = 0; j < oldData.get(i).getNumSets(); j++) {
                assertEquals(oldData.get(i).getReps().get(j), routine.getAllExercises().get(i).getReps().get(j));
                assertEquals(oldData.get(i).getWeight().get(j), weightShouldBe.get(j));
            }
        }
        assertEquals(3, curls.getNumSets());
    }

    @Test
    void testRemoveExercise() {
        routine.addExercise(curls);
        routine.addExercise(benchPress);
        routine.addExercise(squat);
        routine.removeExercise("curls");
        assertEquals(routine.getExercise(0),benchPress);
        assertEquals(routine.getExercise(1),squat);
    }

    @Test
    void testGetHistoricalDataExceptions() {

        try { analyze.getHistoricalData();}
        catch (IOException | ParseException ee) { fail(); }
    }

    @Test
    void testCompareDataExceptions() throws ParseException {

        try { analyze.compareData(routine);}
        catch (IOException e) { fail(); }
    }

    @Test
    void testRoutineGetName() {

        assertEquals("testRoutine",routine.getName());
    }
}
