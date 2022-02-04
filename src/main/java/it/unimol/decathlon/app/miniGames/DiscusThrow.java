package it.unimol.decathlon.app.miniGames;

import it.unimol.decathlon.app.abstra.Discipline;
import it.unimol.decathlon.app.dice.Dice;


/**
 * Classe rapprensentante il gioco del Lancio del disco
 * @author Antonio Pircio
 * @version 3.0
 */
public class DiscusThrow implements Discipline {
    private Dice dice;
    private int maxNumberDices;
    private int numberDices = 5;


    public DiscusThrow() {
        this.maxNumberDices = this.numberDices;
    }


    /**
     * Viene lanciato casualmente un dado.
     * @return il valore del dado lanciato.
     */
    public int rollDices() {

        this.dice = new Dice();

        int result = this.dice.rollDice();
        return result;

    }

    public void decMaxDicesNumber() {
        this.maxNumberDices--;
    }

    public int getMaxNumberDices() {
        return this.maxNumberDices;
    }


}
