package it.unimol.decathlon.app.exceptions;

public class WrongHighException extends Exception {
    public WrongHighException() {
        super("ERRORE");
    }

    public WrongHighException(String message) {
        super(message);
    }
}
