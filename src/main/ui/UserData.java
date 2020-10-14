package ui;

import model.Analyzer;
import model.Exercise;
import model.Routine;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.util.*;

/*
This class is used to retrieve user input
 */

public class UserData {

    private Scanner userInput = new Scanner(System.in);  // Create a Scanner object
    private boolean  gettingUserInput = true; // Is the user done entering input

    //EFFECTS: Creates a Routine using the name given by user
    private Routine createRoutine() {
        System.out.println("Enter the name of your routine:");
        String routineName = userInput.nextLine();
        Routine routine = new Routine(routineName);
        return routine;
    }

    //REQUIRES: User must input either 'a', 'r' or 'd'
    //MODIFIES: this
    //EFFECTS: Asks user if they are done adding exercises. If so, it modifies gettingUserInput to
    //         false, otherwise it does nothing.
    private void isUserDone(Routine routine) {

        boolean invalidInput = true; // Has the user provided valid input

        while (invalidInput) {
            System.out.println("select from the following: \n a: Add another exercise to your routine "
                    + "\n r: remove an exercise \n d: done");
            String decision = userInput.nextLine();
            if (decision.equals("a")) {
                invalidInput = false;
                System.out.println("Great effort today!");
            } else if (decision.equals("d")) {
                gettingUserInput = false;
                invalidInput = false;
                System.out.println("Keep working hard!");
            } else if (decision.equals("r")) {
                System.out.println("Enter the name of the exercise you wish to remove");
                String removeName = userInput.nextLine();
                routine.removeExercise(removeName);
                System.out.println("Successfully removed " + removeName + " from your routine");
            } else {
                System.out.println("You entered an invalid character, try again");
            }
        }
    }


    //REQUIRES: numSets needs to be a positive integer, user must input
    //          positive integers
    //EFFECTS: Returns an Arraylist of the user's reps and an ArrayList of the
    //         user's weight for each set
    private List<Object> getSetsAndWeight(int numSets) {

        ArrayList<Long> reps = new ArrayList<Long>();
        ArrayList<Long> weight = new ArrayList<Long>();

        // Gets user input for the reps and weight for each set and adds it to Arraylists
        for (int i = 0; i < numSets; i++) {
            int setNum = i + 1;
            System.out.println("Enter the number of reps for set number " + setNum);
            reps.add(Long.parseLong(userInput.nextLine()));
            System.out.println("Enter the weight for set number " + setNum);
            weight.add(Long.parseLong(userInput.nextLine()));
        }
        return Arrays.asList(reps, weight);
    }

    //REQUIRES: routine can not be null
    //MODIFIES: this
    //EFFECTS: Creates an Exercise based on input from the user and adds it
    //         to their routine
    private void addExerciseToRoutine(Routine routine) {

        while (gettingUserInput) {

            System.out.println("Enter the name of the exercise or choose from the following:");
            System.out.println(" c: curls \n b: bench press \n s: squat");
            String exerciseName = userInput.nextLine();
            switch (exerciseName) {
                case "c":
                    exerciseName = "curls";
                    break;
                case "b":
                    exerciseName = "bench press";
                    break;
                case "s":
                    exerciseName = "squat";
                    break;
            }

            System.out.println("Enter the number of sets");
            int numSets = Integer.parseInt(userInput.nextLine());
            List<Object> userInput = getSetsAndWeight(numSets);
            ArrayList<Long> weight = (ArrayList<Long>) userInput.get(1);
            routine.addExercise(new Exercise(exerciseName, numSets, (ArrayList<Long>) userInput.get(0), weight));
            System.out.println("Successfully added " + exerciseName + " to your " + routine.getName() + " routine!");
            isUserDone(routine);
        }
    }

    //REQUIRES: User must input either 'i' or 'q'
    //MODIFIES: this
    //EFFECTS:  Gets the routine and exercises from the user and compares that data to
    //          the last time they performed these exercises
    public void getData() {
        Routine routine = createRoutine(); // Create routine
        addExerciseToRoutine(routine); // Add exercises to the routine
        System.out.println("Choose one of the following: \n v: view percent improvement \n q: quit");
        if (userInput.nextLine().equals("v")) {
            Analyzer analyze = new Analyzer();
            try {
                Hashtable comparedData = analyze.compareData(routine);
                Set<String> keys = comparedData.keySet();
                for (String key: keys) {
                    System.out.println(key + " : " + comparedData.get(key) + "%");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
