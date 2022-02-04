package it.unimol.decathlon.app.miniGames;

import it.unimol.decathlon.app.abstra.Discipline;
import it.unimol.decathlon.app.dice.Dice;

/**
 * Classe rappresentante il gioco dei 400 metri.
 * @author Antonio Pircio
 * @version 3.0
 */
public class Meters400 implements Discipline {
    private int maxNumberDices;
    private int maxAttempt;
    private int numberDices = 8;
    private int attempt = 7;
    private int result1;
    private int result2;
    private int dice1;
    private int dice2;
    private  static final int PENALTY = 6;
    private static final int GAGE = -6;
    private Dice dice;


    public Meters400() {
        this.maxNumberDices = this.numberDices;
        this.maxAttempt = this.attempt;
    }

    /**
     * Vengono lanciati casualmente due dadi e si controlla se il loro valore è uguale a 6.
     * Se si viene applicato un malus. Viene salvato il valore di ogni dado uscito e il valore di ogni dado modificato dal malus.
     * @return la somma dei valori dei dadi lanciati dopo aver controllato il malus.
     */
    public int rollDices() {

        this.dice = new Dice();

        int numberRandom1 = this.dice.rollDice();

        int numberRandom2 = this.dice.rollDice();

        this.dice1 = numberRandom1;
        this.dice2 = numberRandom2;

        if (numberRandom1 == PENALTY) {

           numberRandom1 = GAGE;
        }

        if (numberRandom2 == PENALTY) {

            numberRandom2 = GAGE;
        }

        this.result1 = numberRandom1;
        this.result2 = numberRandom2;

         int sum = numberRandom1 + numberRandom2;

        return sum;
    }


    public int getResult1() {
        return this.result1;
    }

    public int getResult2() {
        return this.result2;
    }

    public void decMaxAttempts() {
        this.maxAttempt--;
    }

    public void decMaxDicesNumber() {
        this.maxNumberDices--;
        this.maxNumberDices--;
    }

    public int getDice1Out() {
        return this.dice1;
    }

    public int getDice2Out() {
        return this.dice2;
    }

    public int getMaxNumberDices() {
        return this.maxNumberDices;
    }

    public int getMaxAttempts() {
        return this.maxAttempt;
    }

}
