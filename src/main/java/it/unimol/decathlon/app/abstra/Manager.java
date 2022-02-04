package it.unimol.decathlon.app.abstra;

import it.unimol.decathlon.app.manager.PlayerManager;
import it.unimol.decathlon.app.player.Player;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

/**
 * Classe astratta che si occupa della gestione di alcuni aspetti dei mini giochi
 * @author Antonio Pircio
 * @version 3.0
 */
public abstract class Manager {
    private  List<Player> players;
    private int  activePlayerIndex;
    private PlayerManager playerManager;

    public Manager() {
        this.players  = new ArrayList<> ();
    }


    public void addPlayer() throws IOException, ClassNotFoundException {
        this.playerManager = new PlayerManager();

        for (Player p : PlayerManager.getPlayers()) {

            this.players.add(p);
        }
    }



    public Player getActivePlayer() {
        return this.players.get(this.activePlayerIndex);
    }


    /**
     * Questo metodo permette lo scorrimento dei giocatori nella lista dei giocatori soltanto dopo un controllo del
     * metodo {@link Manager#controlTurn} il quale impedisce l' avanzamento ad un elemento della lista di tipo {@code null}
     * @return {@code false} se il controllo non è stato superato, {@code true} altrimenti
     */
    public boolean nextTurn() {

        if ( controlTurn()  == false) {

            return false;
        }
        this.activePlayerIndex += 1;

        return true;
    }


    /**
     * Appena il numero relativo al giocatore nella lista eguaglia la taglia della lista questo metodo blocca l' avanzamento al prossimo giocatore
     * @return {@code true} se il numero del giocatore è diverso dal umero della taglia della lista dei giocatori, {@code false} altrimenti
     */
    public boolean controlTurn() {

        if (this.activePlayerIndex != this.players.size()) {

             return true;
       }
                 return false;
    }


    public abstract int getScore();

    public abstract void setCurrentPlayer();

    public abstract void setScore();

    public abstract  void setPlayersScore();

    @Override
    public abstract  String toString();

    public  abstract  List getPlayersScore();

    public abstract  void goGame();

    public abstract int rollDices();

}