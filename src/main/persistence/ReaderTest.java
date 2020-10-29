package persistence;

import model.Exercise;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

public class ReaderTest {

    private JsonReader reader;


    @Test
    void testInvalidFile() {

        try {
            reader = new JsonReader("data/invalid_file.json");
            reader.read("squat");
            fail();
        } catch (IOException e) {
            //expected
        }
    }

    @Test
    void testJsonReader() {


        try {
            reader = new JsonReader("data/data_reader_test.json");
            Set<String> keys = reader.getKeys();
            Object[] x = keys.toArray();
            System.out.println(x[0]);
            ArrayList<String> keysShouldBe = new ArrayList<String>();
            keysShouldBe.add("squats");
            keysShouldBe.add("ohp");
            assertEquals(3,keys.size());
            assertTrue(keys.contains("squat"));
            assertTrue(keys.contains("chinups"));
            assertTrue(keys.contains("ohp"));

            Exercise squats = reader.read("squat");
            assertEquals(3,squats.getNumSets());

        } catch (IOException e) {
            fail();
        }
    }

}
