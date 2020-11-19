package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestRoutine {

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
    void testRemoveExercise() {
        routine.addExercise(curls);
        routine.addExercise(benchPress);
        routine.addExercise(squat);
        routine.removeExercise("curls");
        assertEquals(routine.getExercise(0), benchPress);
        assertEquals(routine.getExercise(1), squat);
    }

    @Test
    void testRoutineGetName() {

        assertEquals("testRoutine", routine.getName());
    }
}
