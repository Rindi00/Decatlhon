package it.unimol.decathlon.app.exceptions;

public class WrongInputChoiceException extends Exception{

    public WrongInputChoiceException() {
        super("ERRORE");
    }

    public WrongInputChoiceException(String message) {
        super(message);
    }

}
