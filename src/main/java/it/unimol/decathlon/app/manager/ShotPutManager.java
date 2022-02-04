package it.unimol.decathlon.app.manager;

import it.unimol.decathlon.app.abstra.Manager;
import it.unimol.decathlon.app.miniGames.ShotPut;
import it.unimol.decathlon.app.player.Player;
import java.util.*;

/**
 * Classe rappresentante il gestore del gioco del lancio del peso.
 * @author Antonio Pircio
 * @version 3.0
 */
public class ShotPutManager extends Manager {
    private ShotPut shotPut;
    private Player currentPlayer;
    private List<Integer> results;
    private int total;
    private static Map<Player,Integer> playersScoreShotPut;
    private int score;
    private static List<Integer> scores;

    public ShotPutManager() {
        playersScoreShotPut = new HashMap<>();
        this.results = new ArrayList<>();
        scores = new ArrayList<>();
    }

    /**
     * Controlla se è stato attribuito un malus al giocatore utilizzando il metodo {@link ShotPut#controlNumberDices}.
     * @return {@code 0} se è stato attribuito un malus, {@code 1} altrimenti.
     */
    public int checkResult() {
      if (!this.shotPut.controlNumberDices()) {

            return 0;
        }

        return 1;
    }

    public boolean controlNumberDices() {//controlla se tra i dadi lanciati sono presenti degli 1
        return this.shotPut.controlNumberDices();
    }

    /**
     * Metodo che assegna il punteggio al giocatore in base all 'andamento del gioco controllato da {@link ShotPutManager#checkResult}.
     * @return {@code 0} se il punteggio ottenuto dal giocatore non ha superato il controllo, altrimenti l' altezza inserita.
     */
    public int getScore() {

        if (checkResult()  ==  1)

            return this.total;

        return 0;
    }


    public void setCurrentPlayer() {
        this.currentPlayer = getActivePlayer();
    }


    public void resetResults() {
        this.shotPut.resetResult();
        this.results.clear();
    }


    public int getTotal() {
        int total = 0;

        this.results.add(this.shotPut.getResult());

        for (Integer number : this.results){

            total += number;
        }
        this.total = total;

        return total;
    }

    public void setScore() {
        this.score = getScore();
    }

    public void setPlayersScore() {
        scores.add(this.score);
        playersScoreShotPut.put(this.currentPlayer,this.score);
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
        return this.shotPut.getMaxAttempts();
    }


    /**
     * Metodo utilizzato per ottenere la classifica dei giocatori e il loro punteggio in ordine decrescente.
     * @return la lista dei giocatori e il loro rispettivo punteggio in ordine decrescente.
     */
    public List getPlayersScore() {

        Set<Map.Entry<Player,Integer>> entrySet = playersScoreShotPut.entrySet();

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
        this.shotPut = new ShotPut();
    }

    public int rollDices() {
        return this.shotPut.rollDices();
    }

    public void decMaxAttempts() {
        this.shotPut.decMaxAttempts();
    }

    public List getDices() {
        return this.shotPut.getDices();
    }

    public void decMaxDicesNumber() {
        this.shotPut.decMaxDicesNumber();
    }

    public int getMaxDicesNumber() {
       return  this.shotPut.getMaxDicesNumber();
    }

    public void resetMaxDicesNumber() {
        this.shotPut.resetMaxDicesNumber();
    }

}
