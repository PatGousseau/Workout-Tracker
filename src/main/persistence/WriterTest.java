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

    @BeforeEach
    void before() {
        routine = new Routine("myRoutine");
        ArrayList reps = new ArrayList();
        ArrayList weight = new ArrayList();
        for (int i = 0; i < 3; i++) {
            reps.add(12);
            weight.add(40);
        }
//        Exercise exercise = new Exercise("ohp",3,reps,weight);
//        routine.addExercise(exercise);
        Exercise exercise = new Exercise("chinups",3,reps,weight);
        routine.addExercise(exercise);

        writer = new JsonWriter("data/data_writer_test.json");
    }

    @Test
    void testWriteToExistingExercise() {
        try {
            writer.write(routine);
            //routine.addExercise(new Exercise(squat));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testCompareData() {
        try {
            analyzer.compareData(routine);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
