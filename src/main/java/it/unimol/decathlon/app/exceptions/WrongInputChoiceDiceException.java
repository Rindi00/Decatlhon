package it.unimol.decathlon.app.exceptions;

public class WrongInputChoiceDiceException extends Exception {

    public WrongInputChoiceDiceException() {
        super("ERRORE");
    }

    public WrongInputChoiceDiceException(String message) {
        super(message);
    }
}
