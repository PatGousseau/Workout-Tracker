package persistence;

import model.Analyzer;
import model.Exercise;
import model.Routine;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

public class WriterTest {

    private Routine routine;
    private JsonWriter writer;
    private Analyzer analyzer = new Analyzer();
    private JsonReader reader;

    @BeforeEach
    void before() {
        routine = new Routine("myRoutine");
        writer = new JsonWriter("data/data_writer_test.json");
        reader = new JsonReader("data/data_writer_test.json");
    }

    @Test
    void testWriteToExistingExercise() {
        try {
            ArrayList reps = new ArrayList();
            ArrayList weight = new ArrayList();
            for (int i = 0; i < 3; i++) {
                reps.add(12);
                weight.add(400);
            }
            Exercise exercise = new Exercise("chinups",3,reps,weight);
            routine.addExercise(exercise);
            writer.write(routine);
            Exercise chinUps = reader.read("chinups");
            assertEquals(3,chinUps.getNumSets());
           // assertEquals(400,chinUps.getWeight().get(0));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testWriteNewExercise() {
        try {
            ArrayList reps = new ArrayList();
            ArrayList weight = new ArrayList();
            for (int i = 0; i < 3; i++) {
                reps.add(12);
                weight.add(500);
            }
            Exercise exercise = new Exercise("newExercise",3,reps,weight);
            routine.addExercise(exercise);
            writer.write(routine);
            Exercise newExercise = reader.read("newExercise");
            assertEquals(3,newExercise.getNumSets());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
