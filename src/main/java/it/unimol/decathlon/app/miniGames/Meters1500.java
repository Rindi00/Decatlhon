package it.unimol.decathlon.app.miniGames;

import it.unimol.decathlon.app.abstra.Discipline;
import it.unimol.decathlon.app.dice.Dice;

/**
 * Classe rappresentante il gioco dei 1500 metri.
 * @author Antonio Pircio
 * @version 3.0
 */
public class Meters1500 implements Discipline {
    private int maxAttempts;
    private int maxDicesNumber;
    private int diceOut;
    private Dice dice;
    private int attempts = 5;
    private int dicesNumber = 8;
    private  static final int PENALTY = 6;
    private static final int GAGE = -6;

    public Meters1500() {
        this.maxAttempts = attempts;
        this.maxDicesNumber = dicesNumber;
    }

    /**
     * Viene lanciato casualmente un dado e si controlla se il suo valore è uguale a 6.
     * Se si viene applicato un malus. Viene salvato il valore del dado.
     * @return il valore del dado lanciato.
     */
    public int rollDices() {

        resetDiceOut();

        int result = 0;

       this.dice = new Dice();

          int numberRandom  = this.dice.rollDice();

            result += numberRandom;

            if ( result == PENALTY) {

                setDiceOut(GAGE);
            }
            else {
                setDiceOut(result);
            }

            return result;
    }



    public int getDiceOut() {
        return  this.diceOut;
    }

    public void resetDiceOut() {
        this.diceOut = 0;
    }

    public void decMaxAttempts() {
        this.maxAttempts--;
    }

    public void setDiceOut(int number) {
        this.diceOut = number;
    }

    public int getMaxAttempts() {
        return this.maxAttempts;
    }


    public int getMaxDicesNumber() {
        return this.maxDicesNumber;
    }


    public void decMaxDicesNumber() {
        this.maxDicesNumber--;
    }
}

