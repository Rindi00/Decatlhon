package it.unimol.decathlon.app.miniGames;

import it.unimol.decathlon.app.abstra.Discipline;
import it.unimol.decathlon.app.dice.Dice;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe rappresentante il gioco del lancio del peso.
 * @author Antonio Pircio
 * @version 3.0
 */
public class ShotPut implements Discipline {
    private int maxAttempts;
    private int maxDicesNumber;
    private int dicesNumber = 8;
    private Dice dice;
    private List<Integer> dices;
    private int attempts = 3;
    private int controlMalus;
    private int result;
    private  static final int PENALTY = 1;


    public ShotPut() {
        this.dices = new ArrayList<>();
        this.maxAttempts = this.attempts;
        this.maxDicesNumber = this.dicesNumber;
    }


    public List getDices() {
        return this.dices;
    }



     /**
     * Viene lanciato casualmente un dado e si controlla se il suo valore è uguale a 1.
     * Se si viene applicato un malus.
      * Viene salvato il dado lanciato.
      * Viene resettata la variabile che tiene conto del malus.
     * @return  il valore del dado lanciato.
     */
    public int rollDices() {

        this.controlMalus = 0;

        int sum = 0;

        this.dice = new Dice();

        int numberRandom = this.dice.rollDice();

        this.dices.add(numberRandom);

        if (numberRandom == PENALTY) {

            this.controlMalus = 1;
        }

        sum += numberRandom;

        this.result = sum;

        return sum;
    }

    /**
     * Si controlla se per il dado lanciato bisogna assegnare un malus.
     * @return {@code false} se è stato trovato un dado per il quale bisogna assegare il malus, {@code true} altrimenti.
     */
    public boolean controlNumberDices() {

        if (this.controlMalus == 1)

            return false;
        return true;
    }


    public void decMaxAttempts() {
        this.maxAttempts--;
    }

    public int getMaxAttempts() {
        return this.maxAttempts;
    }

    public int getResult() {
        return this.result;
    }

    public void decMaxDicesNumber() {
        this.maxDicesNumber--;
    }

    public int getMaxDicesNumber() {
        return this.maxDicesNumber;
    }

    public void resetResult() {
        this.dices.clear();
    }

    public void resetMaxDicesNumber(){
        this.maxDicesNumber = this.dicesNumber;
    }

}
