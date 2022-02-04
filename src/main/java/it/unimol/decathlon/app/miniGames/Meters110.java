package it.unimol.decathlon.app.miniGames;

import it.unimol.decathlon.app.abstra.Discipline;
import it.unimol.decathlon.app.dice.Dice;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe rappresentante il gioco dei 110 metri a ostacoli.
 * @author Antonio Pircio
 * @version 3.0
 */
public class Meters110  implements Discipline {
    private int maxAttempts;
    private int maxDicesNumber;
    private int result;
    private List<Integer> dices;
    private Dice dice;
    private int attempts = 6;
    private int dicesNumber = 5;



   public Meters110 () {
       this.maxAttempts = attempts;
       this.maxDicesNumber = dicesNumber;
       this.dices = new ArrayList<>();
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

       for (int i = 0; i < this.maxDicesNumber; i++) {

           int numberRandom = this.dice.rollDice();

           this.dices.add(numberRandom);

           sum += numberRandom;
       }

       this.result = sum;

       return sum;
   }

    public void decMaxAttempts() {
        this.maxAttempts--;
    }

    public int getMaxAttempts() {
        return this.maxAttempts;
    }

    public List getDices() {
        return this.dices;
    }

    public int getResult() {
       return this.result;
    }

}
