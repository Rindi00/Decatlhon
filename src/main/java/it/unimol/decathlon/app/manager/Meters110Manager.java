package it.unimol.decathlon.app.manager;

import it.unimol.decathlon.app.abstra.Manager;
import it.unimol.decathlon.app.miniGames.Meters110;
import it.unimol.decathlon.app.player.Player;
import java.util.*;


/**
 * Classe rappresentante il gestore del gioco 110 metri a ostacoli.
 * @author Antonio Pircio
 * @version 3.0
 */
public class Meters110Manager extends Manager {
    private Meters110 meters ;
    private  Map<Player,Integer> playersScoreMeters110;
    private Player currentPlayer;
    private int score;
    private int malus;
    private static List<Integer> scores;



    public Meters110Manager() {
        this.playersScoreMeters110 = new HashMap<>();
        scores = new ArrayList<>();
    }


    public int getScore() {
        return this.meters.getResult();
    }

    public int getScoreWithMalus() {
        return getScore() - this.malus;
    }

    public void incrementMalus() {
        this.malus++;
    }

    public void resetMalus() {
        this.malus = 0;
    }

    public void setCurrentPlayer() {
        this.currentPlayer = getActivePlayer();
    }

    public void setScore() {
        this.score = getScoreWithMalus();
    }


    public void setPlayersScore() {
        scores.add(this.score);
        playersScoreMeters110.put(this.currentPlayer,this.score);
    }


    public int getMalus() {
        return this.malus;
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
        return this.meters.getMaxAttempts();
    }


    public static List getMeScores() {
        return scores;
    }

    /**
     * Metodo utilizzato per ottenere la classifica dei giocatori e il loro punteggio in ordine decrescente
     * @return la lista dei giocatori e il loro rispettivo punteggio in ordine decrescente
     */
    public List getPlayersScore() {
        Set<Map.Entry<Player,Integer>> entrySet = playersScoreMeters110.entrySet();

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
        this.meters = new Meters110();
    }


    public int rollDices() {
        return this.meters.rollDices();
    }


    public void decMaxAttempts() {
        this.meters.decMaxAttempts();
    }


    public List getDices() {
        return this.meters.getDices();
    }

}
