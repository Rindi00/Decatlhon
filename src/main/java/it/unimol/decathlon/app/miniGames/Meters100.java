package it.unimol.decathlon.app.miniGames;

import it.unimol.decathlon.app.abstra.Discipline;
import it.unimol.decathlon.app.dice.Dice;



/**
 * Classe rappresentante il gioco dei 100 metri.
 * @author Antonio Pircio
 * @version 3.0
 */
public class Meters100 implements Discipline {

    private int maxNumberDices;
    private int maxAttempt;
    private int numberDices = 8;
    private int attempt = 5;
    private int result1;
    private int result2;
    private int result3;
    private int result4;
    private int dice1;
    private int dice2;
    private int dice3;
    private int dice4;
    private  static final int PENALTY = 6;
    private static final int GAGE = -6;
    private Dice dice;

    public Meters100() {
        this.maxNumberDices = this.numberDices;
        this.maxAttempt = this.attempt;

    }

    /**
     * Vengono lanciati casualmente quattro dadi e si controlla se il loro valore è uguale a 6.
     * Se si viene applicato un malus. Viene salvato il valore di ogni dado uscito e il valore di ogni dado modificato dal malus.
     * @return la somma dei valori dei dadi lanciati dopo aver controllato il malus.
     */
    public int rollDices() {

        this.dice = new Dice();

        int numberRandom1 = this.dice.rollDice();

        int numberRandom2 = this.dice.rollDice();

        int numberRandom3 = this.dice.rollDice();

        int numberRandom4 = this.dice.rollDice();

        this.dice1 = numberRandom1;
        this.dice2 = numberRandom2;
        this.dice3 = numberRandom3;
        this.dice4 = numberRandom4;

        if (numberRandom1 == PENALTY) {

            numberRandom1 = GAGE;
        }

        if (numberRandom2 == PENALTY) {

            numberRandom2 = GAGE;
        }

        if (numberRandom3 == PENALTY) {

            numberRandom3 = GAGE;
        }

        if (numberRandom4 == PENALTY) {

            numberRandom4 = GAGE;
        }

        this.result1 = numberRandom1;
        this.result2 = numberRandom2;
        this.result3 = numberRandom3;
        this.result4 = numberRandom4;

        int sum = numberRandom1 + numberRandom2 + numberRandom3 + numberRandom4;

        return sum;
    }


    public int getResult1() {
        return this.result1;
    }

    public int getResult2() {
        return this.result2;
    }

    public int getResult3() {
        return this.result3;
    }

    public int getResult4() {
        return this.result4;
    }

    public void decMaxAttempts() {
        this.maxAttempt--;
    }

    public void decMaxDicesNumber() {
        this.maxNumberDices--;
        this.maxNumberDices--;
        this.maxNumberDices--;
        this.maxNumberDices--;
    }


    public int getDice1Out() {
        return this.dice1;
    }

    public int getDice2Out() {
        return this.dice2;
    }

    public int getDice3Out() {
        return this.dice3;
    }

    public int getDice4Out() {
        return this.dice4;
    }

    public int getMaxNumberDices() {
        return this.maxNumberDices;
    }

    public int getMaxAttempts() {
        return this.maxAttempt;
    }

}
