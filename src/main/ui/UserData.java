package ui;

import model.Analyzer;
import model.Exercise;
import model.InvalidInputException;
import model.Routine;
import persistence.JsonReader;
import persistence.JsonWriter;
import java.io.IOException;
import java.util.*;

/*
This class is used to retrieve user input
 */

public class UserData {

    private Scanner userInput = new Scanner(System.in);  // Create a Scanner object
    private boolean  gettingUserInput = true; // Is the user done entering input
    private Routine routine;
    private JsonReader reader = new JsonReader("data/data.json");
    private JsonWriter writer = new JsonWriter("data/data.json");

    //EFFECTS: Creates a Routine using the name given by user
    private Routine createRoutine() {
        System.out.println("Enter the name of your routine:");
        String routineName = userInput.nextLine();
        routine = new Routine(routineName);
        return routine;
    }


    //MODIFIES: this
    //EFFECTS: Asks user if they are done adding exercises. If so, it modifies gettingUserInput to
    //false, otherwise it does nothing. If the user enters an invalid character, it prompts the user again
    private void isUserDone(Routine routine) {

        boolean invalidInput = true; // Has the user provided valid input

        while (invalidInput) {
            System.out.println("select from the following: \n a: Add another exercise to your routine "
                    + "\n r: remove an exercise \n d: done");
            String decision = userInput.nextLine();
            if (decision.equals("a")) {
                invalidInput = false;
                System.out.println("Keep working hard!");
            } else if (decision.equals("d")) {
                gettingUserInput = false;
                invalidInput = false;
                System.out.println("Good work today!");
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
    //positive integers
    //EFFECTS: Returns an Arraylist of the user's reps and an ArrayList of the
    //user's weight for each set
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
    //to their routine
    private void addExerciseToRoutine(Routine routine) {

        while (gettingUserInput) {

            System.out.println("Enter the name of the exercise or choose from the following:");
            String exerciseName = displayPastExercises();

            System.out.println("Enter the number of sets");
            int numSets = Integer.parseInt(userInput.nextLine());
            List<Object> userInput = getSetsAndWeight(numSets);
            ArrayList<Long> weight = (ArrayList<Long>) userInput.get(1);
            routine.addExercise(new Exercise(exerciseName,numSets, (ArrayList<Long>) userInput.get(0), weight));
            System.out.println("Successfully added " + exerciseName + " to your " + routine.getName() + " routine!");
            isUserDone(routine);
        }
    }

    //EFFECTS: displays all past exercises and prompts the user to choose one of the
    // exercises or add a new exercise
    private String displayPastExercises() {

        String name = new String();
        Hashtable allExercises = new Hashtable();
        try {
            Set<String> keys = reader.getKeys();
            int index = 1;
            for (String key: keys) {
                System.out.println(index + ": " + key);
                allExercises.put(index, key);
                index++;
            }
            String exercise = userInput.nextLine();
            try {
                Integer.parseInt(exercise);
                name = (String) allExercises.get(Integer.parseInt(exercise));
            } catch (NumberFormatException e) {
                name = exercise;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return name;
    }

    //EFFECTS: saves the data the user entered
    public void saveData() {
        try {
            System.out.println(routine.getExercise(0).getNumSets());
            writer.write(routine);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //MODIFIES: this
    //EFFECTS:  Gets the routine and exercises from the user
    // and compares that data to the last time they performed these exercises
    public void getData() {

        Routine routine = createRoutine(); // Create routine
        addExerciseToRoutine(routine); // Add exercises to the routine
        boolean userSavesData = true;

        userSavesData = true;
        while (userSavesData) {
            try {
                promptViewStats();
                userSavesData = false;
            } catch (InvalidInputException e) {
                userSavesData = true;
            }
        }
        userSavesData = true;
        while (userSavesData) {
            try {
                promptSaveData();
                userSavesData = false;
            } catch (InvalidInputException e) {
                userSavesData = true;
            }
        }
    }


    //EFFECTS: if user enters 'y', it calls a method to save data
    // if the user enters 'n', nothing happens. Otherwise, it throws an
    //InvalidInputException
    private void promptSaveData() throws InvalidInputException {
        System.out.println("Would you like to save your data? \n y: yes \n n: no");
        String input = userInput.nextLine();
        if (input.equals("y")) {
            saveData();
        } else if (!input.equals("n")) {
            throw new InvalidInputException();
        }
    }

    //EFFECTS: if user enters 'v', it shows the percent improvement
    // if the user enters 'q', it prints out a goodbye message. Otherwise,
    // it throws an InvalidInputException
    private void promptViewStats() throws InvalidInputException {
        System.out.println("Choose one of the following: \n v: view percent improvement \n q: quit");
        String answer = userInput.nextLine();
        if (answer.equals("v")) {
            Analyzer analyze = new Analyzer();
            try {
                Hashtable comparedData = analyze.compareData(routine, "data/data.json");
                Set<String> keys = comparedData.keySet();
                for (String key: keys) {
                    System.out.println(key + " : " + comparedData.get(key) + "%");
                }
                if (comparedData.isEmpty()) {
                    System.out.println("No stats to view");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (answer.equals("q")) {
            System.out.println("See you next time!");
        } else {
            throw new InvalidInputException();
        }
    }
}
