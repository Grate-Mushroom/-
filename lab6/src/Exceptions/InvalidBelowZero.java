package Exceptions;

public class InvalidBelowZero extends RuntimeException {
    public InvalidBelowZero(String message) {
        super(message);
    }
}
