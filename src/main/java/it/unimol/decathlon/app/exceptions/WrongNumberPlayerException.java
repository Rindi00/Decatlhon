package it.unimol.decathlon.app.exceptions;

public class WrongNumberPlayerException extends Exception {

  public  WrongNumberPlayerException() {
        super("ERRORE");
    }

    public WrongNumberPlayerException(String message) {
      super(message);
    }
}
