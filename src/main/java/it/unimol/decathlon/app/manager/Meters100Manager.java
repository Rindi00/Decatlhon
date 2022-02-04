package it.unimol.decathlon.app.manager;

import it.unimol.decathlon.app.abstra.Manager;
import it.unimol.decathlon.app.miniGames.Meters100;
import it.unimol.decathlon.app.player.Player;
import java.util.*;



/**
 * Classe rappresentante il gestore del gioco 100 metri.
 * @author Antonio Pircio
 * @version 3.0
 */
public class Meters100Manager extends Manager {
    private Meters100 meters100;
    private static Map<Player,Integer> playersScoreMeters100;
    private List<Integer> temporary;
    private Player currentPlayer;
    private int score;
    private static List<Integer> scores;


    public Meters100Manager () {
        playersScoreMeters100 = new HashMap<>();
        this.temporary = new ArrayList<>();
        scores = new ArrayList<>();
    }


    public int getScore() {
        temporary.add(this.meters100.getResult1());
        temporary.add(this.meters100.getResult2());
        temporary.add(this.meters100.getResult3());
        temporary.add(this.meters100.getResult4());

        int sum = 0;

        for (Integer number : this.temporary) {
            sum += number;
        }
        return sum;
    }

    /**
     * Metodo utilizzato per eliminare i valori dei dadi non desiderati.
     */
    public void deleteResultNotFreeze() {
        int index1 = this.temporary.size() -1 ;

        int index2 = this.temporary.size() -2 ;

        int index3 = this.temporary.size() -3 ;

        int index4 = this.temporary.size() -4 ;

        this.temporary.remove(index1);
        this.temporary.remove(index2);
        this.temporary.remove(index3);
        this.temporary.remove(index4);
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
        playersScoreMeters100.put(this.currentPlayer,this.score);
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
        return this.meters100.getMaxAttempts();
    }


    /**
     * Metodo utilizzato per ottenere la classifica dei giocatori e il loro punteggio in ordine decrescente.
     * @return la lista dei giocatori e il loro rispettivo punteggio in ordine decrescente.
     */
    public List getPlayersScore() {

        Set<Map.Entry<Player, Integer>> entrySet = playersScoreMeters100.entrySet();

        List<Map.Entry<Player, Integer>> list = new ArrayList<>(entrySet);

        Collections.sort(list, Collections.reverseOrder(new Comparator<Map.Entry<Player, Integer>>() {

            @Override
            public int compare(Map.Entry<Player, Integer> o1, Map.Entry<Player, Integer> o2) {

                return o1.getValue().compareTo(o2.getValue());
            }

        }));

        return list;
    }

    public void goGame() {
        this.meters100 = new Meters100();
    }

    public int rollDices() {
        return this.meters100.rollDices();
    }

    public int getDice1Out() {
        return this.meters100.getDice1Out();
    }

    public int getDice2Out() {
        return this.meters100.getDice2Out();
    }

    public int getDice3Out() {
        return this.meters100.getDice3Out();
    }

    public int getDice4Out() {
        return this.meters100.getDice4Out();
    }

    public void decMaxAttempts() {
        this.meters100.decMaxAttempts();
    }

    public int getMaxDicesNumber() {
        return this.meters100.getMaxNumberDices();
    }

    public void decMaxDicesNumber() {
        this.meters100.decMaxDicesNumber();
    }

}
