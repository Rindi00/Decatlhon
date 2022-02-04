package it.unimol.decathlon.app.player;


import java.io.Serial;
import java.io.Serializable;

/**
 * Classe rappresentante il giocatore
 *  @author Antonio Pircio
 *   @version 3.0
 */
public class Player implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String name;

    public Player(String name) {
        this.name  = name;

    }

    @Override
    public String toString() {
        return  this.name;
    }

}
