package it.unimol.decathlon.app.miniGames;

import it.unimol.decathlon.app.abstra.Discipline;
import it.unimol.decathlon.app.dice.Dice;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe rapprensentante il gioco del salto in alto
 * @author Antonio Pircio
 * @version 3.0
 */
public class HighJump implements Discipline {
    private int  maxAttempts;
    private int  maxDicesNumber;
    private int  high;
    private int sum;
    private Dice dice;
    private static int attempts = 3;
    private static int dicesNumber = 5;
    private List<Integer> dices;



    public HighJump() {
        this.maxAttempts  = attempts;
        this.maxDicesNumber  = dicesNumber;
        this.dices = new ArrayList<> ();
    }


    public int getHigh() {
        return this.high;
    }


    /**
     * Vengono lanciati casualmente un numero di dadi che il gioco impone.
     * I dadi sono salvati in una lista specifica.
     * Viene cancellata la lista di dadi precedentemente salvata.
     * @return la somma dei dadi lanciati.
     */
    public int rollDices() {

        this.dices.clear();

        int sum = 0;

       this.dice = new Dice();

        for (int i = 0; i <  this.maxDicesNumber; i++) {

            int numberRandom  = this.dice.rollDice();

            this.dices.add (numberRandom);

            sum += numberRandom;
        }

        this.sum = sum;

        return sum;
    }


    public void  setHigh(int high){
        this.high = high;
    }

    public List getDices() {
        return this.dices;
    }

    public void decMaxAttempts() {
        this.maxAttempts--;
    }

    public int getMaxAttempts() {
        return this.maxAttempts;
    }

    public int getSum(){
        return this.sum;
    }



}
