package it.unimol.decathlon.app.exceptions;

public class WrongNamePlayerException extends Exception {
    public WrongNamePlayerException() {
        super("ERRORE");
    }

    public WrongNamePlayerException(String message) {
        super(message);
    }
}
