package it.unimol.decathlon.app.exceptions;

public class WrongDiceToSaveException extends Exception {
    public WrongDiceToSaveException() {
        super("ERRORE");
    }

    public WrongDiceToSaveException(String message) {
        super(message);
    }

}
