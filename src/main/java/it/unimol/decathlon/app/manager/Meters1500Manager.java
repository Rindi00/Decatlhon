package it.unimol.decathlon.app.manager;

import it.unimol.decathlon.app.abstra.Manager;
import it.unimol.decathlon.app.miniGames.Meters1500;
import it.unimol.decathlon.app.player.Player;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Collections;
import java.util.Comparator;

/**
 * Classe rappresentante il gestore del gioco 1500 metri.
 * @author Antonio Pircio
 * @version 3.0
 */
public class Meters1500Manager extends Manager {
    private Meters1500 meters ;
    private static Map<Player,Integer> playersScoreMeters1500;
    private List<Integer> temporary;
    private Player currentPlayer;
    private int score;

    private static List<Integer> scores;


    public Meters1500Manager() {
        playersScoreMeters1500 = new HashMap<>();
        this.temporary = new ArrayList<>();
        scores = new ArrayList<>();
    }


    public int getScore() {
        temporary.add(this.meters.getDiceOut());

        int sum = 0;

        for (Integer number : this.temporary){
            sum += number;
        }
        return sum;
    }


    /**
     * Metodo utilizzato per eliminare i valori dei dadi non desiderati
     */
    public void deleteResultNotFreeze() {
        int index = this.temporary.size() -1 ;

        this.temporary.remove(index);
    }


    public void resetTemporary() {//VIENE RESETTATA LA LISTA DEI RISULTATI TEMPORANEI
        this.temporary.clear();
    }

    /**
     * Metodo utilizzato per calcolare il risultato ottenuto dal giocatore in base ai valori dei dadi accettati.
     * @return la somma dei dadi accettati.
     */
    public int fixedRsult() {
        int sum = 0;

        List<Integer> fixed = new ArrayList<>(this.temporary);

        for (Integer number : fixed){

            sum += number;
        }
        return sum;
    }


    public void setCurrentPlayer() {
        this.currentPlayer = getActivePlayer();
    }

    public void setScore() {
        this.score = fixedRsult();
    }

    public void setPlayersScore() {
        scores.add(this.score);
        playersScoreMeters1500.put(this.currentPlayer,this.score);
    }

    public static List getMeScores() {
        return scores;
    }


    /**
     * Metodo utilizzato per la stampa del punteggio ottenuto dal giocatore.
     * @return il punteggio del giocatore sotto forma di stringa.
     */
    @Override
    public String toString() {// ritorna il punteggio ottenuto dal giocatore che ha terminato il turno
        return (   "Score:"  +  "  "  +  this.score);
    }

    public int getMaxAttempts() {
        return this.meters.getMaxAttempts();
    }


    /**
     * Metodo utilizzato per ottenere la classifica dei giocatori e il loro punteggio in ordine decrescente.
     * @return la lista dei giocatori e il loro rispettivo punteggio in ordine decrescente.
     */
    public List getPlayersScore() {

        Set<Map.Entry<Player,Integer>> entrySet = playersScoreMeters1500.entrySet();

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
        this.meters = new Meters1500();
    }

    public int rollDices() {
        return this.meters.rollDices();
    }

    public int getDiceOut() {
        return this.meters.getDiceOut();
    }

    public void decMaxAttempts() {
        this.meters.decMaxAttempts();
    }

    public int getMaxDicesNumber() {
        return this.meters.getMaxDicesNumber();
    }

    public void decMaxDicesNumber() {
        this.meters.decMaxDicesNumber();
    }

}