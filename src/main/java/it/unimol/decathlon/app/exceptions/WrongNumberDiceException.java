package it.unimol.decathlon.app.exceptions;

public class WrongNumberDiceException extends Exception {
    public WrongNumberDiceException() {
        super("ERRORE");
    }

    public WrongNumberDiceException(String message) {
        super(message);
    }
}
