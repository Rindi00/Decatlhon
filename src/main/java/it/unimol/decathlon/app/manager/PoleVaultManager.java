package it.unimol.decathlon.app.manager;

import it.unimol.decathlon.app.abstra.Manager;
import it.unimol.decathlon.app.exceptions.WrongHighException;
import it.unimol.decathlon.app.exceptions.WrongNumberDiceException;
import it.unimol.decathlon.app.miniGames.PoleVault;
import it.unimol.decathlon.app.player.Player;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.Comparator;
import java.util.Map.Entry;
import java.util.Collections;

/**
 * Classe rappresentante il gestore del gioco del salto con l' asta.
 * @author Antonio Pircio
 * @version 3.0
 */
public class PoleVaultManager extends Manager {
    private PoleVault poleVault;
    private Player currentPlayer;
    private static Map<Player,Integer> playersScorePoleVault;
    private int score;
    private static List<Integer> scores;


    public PoleVaultManager() {
        playersScorePoleVault = new HashMap<> ();
        scores = new ArrayList<>();
    }

    /**
     * Controlla se è stato attribuito un malus al giocatore utilizzando il metodo {@link PoleVault#controlNumberDices}.
     * @return {@code 0} se è stato attribuito un malus, {@code 1} altrimenti.
     */
    public int checkResult() {
        if (!this.poleVault.controlNumberDices()) {

            return 0;
        }

            return 1;
    }


    public boolean controlNumberDices() {//controlla se tra i dadi lanciati sono presenti degli 1
        return this.poleVault.controlNumberDices();
    }

    public void setNumberDices(int numberDices) throws WrongNumberDiceException {
         int dicesNumberMax = 8;
         int dicesNumberMin = 2;

        if (numberDices < dicesNumberMin) {

            throw new WrongNumberDiceException("NUMERO DI DADI TROPPO PICCOLO");
        }

        if (numberDices > dicesNumberMax) {

            throw new WrongNumberDiceException("NUMERO DI DADI TROPPO GRANDE");
        }

        this.poleVault.setNumberDices(numberDices);
    }


    /**
     * Metodo che assegna il punteggio al giocatore in base all 'andamento del gioco controllato da {@link PoleVaultManager#checkResult}.
     * @return {@code 0} se il punteggio ottenuto dal giocatore non ha superato il controllo, altrimenti l' altezza inserita.
     */
    public int getScore() {

        if ((checkResult()  ==  1) && (this.poleVault.getSum() > this.poleVault.getHigh()))

            return this.poleVault.getHigh();

        return 0;
    }


    public void setCurrentPlayer() {//IMPOSTA IL GIOCATORE CORRENTE CHE HA TERMINATO IL TURNO, IN BASE ALL' INDICE. SERVE PER INSERIRLO IN MAPPA
        this.currentPlayer  = getActivePlayer();
    }

    public void setScore() {//IMPOSTA LO SCORE DEL GIOCATORE CORRENTE CHE HA FINITO IL TURNO PER METTERLO IN MAPPA
        this.score  = getScore();
    }

    public void setPlayersScore() {
        scores.add(this.score);
        playersScorePoleVault.put(this.currentPlayer,this.score);
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



    /**
     * Metodo utilizzato per ottenere la classifica dei giocatori e il loro punteggio in ordine decrescente.
     * @return la lista dei giocatori e il loro rispettivo punteggio in ordine decrescente.
     */
    public List getPlayersScore() {

        Set<Entry<Player,Integer>> entrySet = playersScorePoleVault.entrySet();

        List<Entry<Player,Integer>> list = new ArrayList<>(entrySet);

        Collections.sort(list, Collections.reverseOrder(new Comparator<Entry<Player, Integer>>() {

            @Override
            public int compare(Entry<Player, Integer> o1, Entry<Player, Integer> o2) {

                return o1.getValue().compareTo(o2.getValue());

            }

        } ) );

        return list;
    }


    public void goGame() {// Viene istanziato un nuovo gioco
        this.poleVault = new PoleVault();
    }

    public void setHigh(int high) throws WrongHighException {
         int minimumHigh = 8;
         int maximumHigh = 48;

        if (high > maximumHigh) {

            throw new WrongHighException("ALTEZZA INSERITA MAGGIORE DI QUELLA INSERIBILE");

        }

        if (high < minimumHigh) {

            throw new WrongHighException("ALTEZZA INSERITA MINORE DI QUELLA INSERIBILE");
        }

        else {
            this.poleVault.setHigh(high);
        }

    }


    public int rollDices() {
        return this.poleVault.rollDices();
    }

    public List getDices() {
        return this.poleVault.getDices();
    }

    public void decMaxAttempts() {
        this.poleVault.decMaxAttempts();
    }

    public int getMaxAttempts() {
        return this.poleVault.getMaxAttempts();
    }

}
