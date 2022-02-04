package it.unimol.decathlon.app.manager;

import it.unimol.decathlon.app.player.Player;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe che si occupa della gestione dei giocatori
 * @author Antonio Pircio
 * @version 3.0
 */
public class PlayerManager {
    private static List<Player> players;
    private static final String SAVEDPLAYERS = "players.bin";

    /**
     * Apre il file con i giocatori precedentemente inseriti se esiste, altrimenti viene creata una nuova lista di giocatori.
     * @throws IOException se esiste il file salvato ma ci sono problemi nella lettura.
     * @throws ClassNotFoundException se il file presenta degli oggetti di tipo non specificati nel programma.
     */
    public PlayerManager() throws IOException, ClassNotFoundException {
        File classificFile = new File(SAVEDPLAYERS);

        if (classificFile.exists()) {

            try (
                    FileInputStream fis = new FileInputStream(SAVEDPLAYERS);
                    ObjectInputStream objectInputStream = new ObjectInputStream(fis);
            ) {
                players = (List) objectInputStream.readObject();

            }

        } else {

            players = new ArrayList<>();
        }

    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public static List<Player> getPlayers() {
        return players;
    }

    private  String getSavedPlayers() {
        return SAVEDPLAYERS;
    }

    public void saveListOfPlayer() {
        List<Player> savedListOfPlayer = new ArrayList<>(getPlayers());

        try (
                FileOutputStream fos = new FileOutputStream(this.getSavedPlayers());
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fos);
        ) {
            objectOutputStream.writeObject(savedListOfPlayer);

        } catch (IOException e) {
            System.err.println("ERRORE GIOCATORI NON SALVATI");
            e.printStackTrace();
        }
    }

}








