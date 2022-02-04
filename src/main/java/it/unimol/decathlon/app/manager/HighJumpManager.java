package it.unimol.decathlon.app.manager;

import it.unimol.decathlon.app.abstra.Manager;
import it.unimol.decathlon.app.miniGames.HighJump;
import it.unimol.decathlon.app.player.Player;
import it.unimol.decathlon.app.exceptions.WrongHighException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.Comparator;
import java.util.Map.Entry;
import java.util.Collections;


/**
 * Classe rappresentante il gestore del gioco del salto in alto.
 * @author Antonio Pircio
 * @version 3.0
 */
public class HighJumpManager extends Manager {
    private HighJump highJump;
    private Player currentPlayer;
    private static Map<Player,Integer> playersScoreHighJump;
    private int score = 0;
    private static List<Integer> scores;



    public HighJumpManager() {
        playersScoreHighJump = new HashMap<> ();
        scores = new ArrayList<>();
    }


    /**
     *Il metodo  controlla che il risultato ottenuto dal giocatore abbia superato l' altezza inserita.
     * @return {@code 1} se il risultato ha superato l 'altezza inserita, {@code -1} altrimenti.
     */
    public int checkResult() {

        if (this.highJump.getSum() > this.highJump.getHigh())

            return 1;

        return -1;
    }


    /**
     * Metodo che assegna il punteggio al giocatore in base all 'andamento del gioco controllato da {@link HighJumpManager#checkResult()}.
     * @return {@code 0} se il punteggio ottenuto dal giocatore non ha superato il controllo, altrimenti l' altezza inserita.
     */
    public int getScore() {

       if (checkResult()  ==  1) {

            return this.highJump.getHigh();
        }

        else {
           return 0;
       }
    }

    public void setCurrentPlayer() {

        this.currentPlayer  = getActivePlayer();
    }


    public void setScore() {

        this.score  = getScore();
    }


    public void setPlayersScore() {
        scores.add(this.score);
        playersScoreHighJump.put(this.currentPlayer,this.score);
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

        Set<Entry<Player,Integer>> entrySet = playersScoreHighJump.entrySet();

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
        this.highJump = new HighJump();
    }

    public void setHigh(int high) throws WrongHighException {
        int minimumHigh = 5;
        int maximumHigh = 30;
        if (high > maximumHigh) {

            throw new WrongHighException("ALTEZZA INSERITA MAGGIORE DI QUELLA INSERIBILE");

        }


        if (high < minimumHigh) {

            throw new WrongHighException("ALTEZZA INSERITA MINORE DI QUELLA INSERIBILE");
        }

          else {
                this.highJump.setHigh(high);
        }
    }


    public int rollDices() {
        return this.highJump.rollDices();
    }


    public List getDices() {
        return this.highJump.getDices();
    }


    public void decMaxAttempts() {
        this.highJump.decMaxAttempts();
    }


    public int getMaxAttempts() {
        return this.highJump.getMaxAttempts();
    }


}
