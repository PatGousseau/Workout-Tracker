package model;

//This class represents an exception that is thrown when the user enters an invalid input

public class InvalidInputException extends Exception {
    public InvalidInputException() {
        super("You entered an invalid character, please try again");
    }
}
