package it.unimol.decathlon.app.miniGames;

import it.unimol.decathlon.app.abstra.Discipline;
import it.unimol.decathlon.app.dice.Dice;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe rappresentante il gioco del salto con l' asta
 * @author Antonio Pircio
 * @version 3.0
 */
public class PoleVault implements Discipline {
    private int maxAttempts;
    private int settedNumberDices;
    private Dice dice;
    private int high;
    private List<Integer> dices;
    private int attempts = 3;
    private int controlMalus;
    private int sum;
    private  static final int PENALTY = 1;




    public PoleVault() {
        this.maxAttempts = attempts;
        this.dices = new ArrayList<> ();
    }


    public void setNumberDices(int numberDices) {
        this.settedNumberDices = numberDices;
    }


    public void setHigh(int high) {
        this.high = high;
    }


    public int getHigh() {
        return this.high;
    }


    public List getDices() {
        return this.dices;
    }

    /**
     * Vengono lanciati casualmente un numero di dadi scelti dal giocatore.
     * I dadi sono salvati in una lista specifica.
     * Viene cancellata la lista di dadi precedentemente salvata.
     * Viene resettata la variabile che tiene conto se vi è un malus.
     * @return la somma dei dadi lanciati.
     */
    public int rollDices() {
        this.sum = 0;

        this.controlMalus = 0;

        this.dices.clear();

        int sum = 0;

       this.dice = new Dice();

        for (int i = 0; i <  this.settedNumberDices; i++) {

           int  numberRandom  = this.dice.rollDice();

            this.dices.add (numberRandom);

            if ( numberRandom == PENALTY) {

                this.controlMalus = 1;
            }

            sum += numberRandom;
        }
        this.sum = sum;

        return sum;
    }

    /**
     * Si controlla se tra i dadi lanciati vi è uno per il quale bisogna assegnare un malus
     * @return {@code false} se è stato trovato un dado per il quale bisogna assegare il malus, {@code true} altrimenti
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

    public int getSum() {
        return this.sum;
    }



}