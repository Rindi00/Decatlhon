package it.unimol.decathlon.app.dice;

import java.util.Random;

/**
 * Classe rapprensentante il dado
 * @author Antonio Pircio
 * @version 3.0
 */
public class Dice {
    private Random valueOfDice;
    private int lastRoll;//Rappresenta il valore del dado lanciato


    public Dice() {
        this.valueOfDice  = new Random();
        this.lastRoll = 0;
    }

    /**
     * Il metodo lancia casualmente un dado.
     * @return il valore del dado lanciato.
     */
    public int rollDice() {
        this.lastRoll = this.valueOfDice.nextInt(6) + 1;
        return this.lastRoll;
    }


}