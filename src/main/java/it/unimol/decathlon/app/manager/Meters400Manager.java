package it.unimol.decathlon.app.manager;

import it.unimol.decathlon.app.abstra.Manager;
import it.unimol.decathlon.app.miniGames.Meters400;
import it.unimol.decathlon.app.player.Player;
import java.util.*;

/**
 * Classe rappresentante il gestore del gioco 400 metri
 * @author Antonio Pircio
 * @version 3.0
 */
public class Meters400Manager extends Manager {
    private Meters400 meters400;
    private static Map<Player,Integer> playersScoreMeters400;
    private List<Integer> temporary;
    private Player currentPlayer;
    private int score;
    private static List<Integer> scores;


    public Meters400Manager () {
        playersScoreMeters400 = new HashMap<>();
        this.temporary = new ArrayList<>();
        scores = new ArrayList<>();
    }


    public int getScore() {
        temporary.add(this.meters400.getResult1());
        temporary.add(this.meters400.getResult2());

        int sum = 0;

        for (Integer number : this.temporary) {
            sum += number;
        }
        return sum;
    }

    /**
     * Metodo utilizzato per eliminare i valori dei dadi non desiderati
     */
    public void deleteResultNotFreeze() {
        int index = this.temporary.size() -1 ;

        int index2 = this.temporary.size() -2 ;

        this.temporary.remove(index);
        this.temporary.remove(index2);

    }

    /**
     * Metodo utilizzato per calcolare il risultato ottenuto dal giocatore in base ai valori dei dadi accettati
     * @return la somma dei dadi accettati
     */
    public int fixedRsult() {
        int sum = 0;

        List<Integer> fixed = new ArrayList<>(this.temporary);

        for (Integer number : fixed){

            sum += number;
        }
        return sum;
    }


    public void resetTemporary() {//VIENE RESETTATA LA LISTA DEI RISULTATI TEMPORANEI
        this.temporary.clear();
    }

    public void setCurrentPlayer() {
        this.currentPlayer = getActivePlayer();
    }

    public void setScore() {
        this.score = fixedRsult();
    }

    public void setPlayersScore() {
        scores.add(this.score);
        playersScoreMeters400.put(this.currentPlayer,this.score);
    }

    public static List getMeScores() {
        return scores;
    }


    /**
     * Metodo utilizzato per la stampa del punteggio ottenuto dal giocatore
     * @return il punteggio del giocatore sotto forma di stringa
     */
    @Override
    public String toString() {// ritorna il punteggio ottenuto dal giocatore che ha terminato il turno
        return (   "Score:"  +  "  "  +  this.score);
    }


    public int getMaxAttempts() {
        return this.meters400.getMaxAttempts();
    }


    /**
     * Metodo utilizzato per ottenere la classifica dei giocatori e il loro punteggio in ordine decrescente
     * @return la lista dei giocatori e il loro rispettivo punteggio in ordine decrescente
     */
    public List getPlayersScore() {

        Set<Map.Entry<Player,Integer>> entrySet = playersScoreMeters400.entrySet();

        List<Map.Entry<Player,Integer>> list = new ArrayList<>(entrySet);

        Collections.sort(list, Collections.reverseOrder(new Comparator<Map.Entry<Player, Integer>>() {

            @Override
            public int compare(Map.Entry<Player, Integer> o1, Map.Entry<Player, Integer> o2) {

                return o1.getValue().compareTo(o2.getValue());
            }

        } ) );

        return list;
    }


    public void goGame() {// Viene istanziato un nuovo gioco
        this.meters400 = new Meters400();
    }

    public int rollDices() {
        return this.meters400.rollDices();
    }

    public int getDice1Out() {
        return this.meters400.getDice1Out();
    }

    public int getDice2Out() {
        return this.meters400.getDice2Out();
    }

    public void decMaxAttempts() {
        this.meters400.decMaxAttempts();
    }

    public int getMaxDicesNumber() {
        return this.meters400.getMaxNumberDices();
    }

    public void decMaxDicesNumber() {
        this.meters400.decMaxDicesNumber();
    }

}
