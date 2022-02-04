package it.unimol.decathlon.app.miniGames;

import it.unimol.decathlon.app.abstra.Discipline;
import it.unimol.decathlon.app.dice.Dice;

/**
 * Classe rappresentante il gestore del gioco salto in lungo.
 * @author Antonio Pircio
 * @version 3.0
 */
public class LongJump implements Discipline {
    private Dice dice;

    public LongJump() {

    }

    public int rollDices() {
        this.dice = new Dice();

        return this.dice.rollDice();
    }

}
