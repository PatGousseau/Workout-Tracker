package model;

public class InvalidInputException extends Exception {
    public InvalidInputException() {
        super("You entered an invalid character, please try again");
    }
}
