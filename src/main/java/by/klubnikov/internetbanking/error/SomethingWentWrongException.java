package by.klubnikov.internetbanking.error;

public class SomethingWentWrongException extends RuntimeException{
    public SomethingWentWrongException(String message) {
        super(message);
    }
}
