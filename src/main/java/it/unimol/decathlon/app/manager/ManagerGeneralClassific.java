package it.unimol.decathlon.app.manager;

import it.unimol.decathlon.app.player.Player;

import java.io.*;
import java.util.*;


/**
 * Classe che si occupa della gestione della classifica generale
 * @author Antonio Pircio
 * @version 3.0
 */
public class ManagerGeneralClassific {
    private static Map<Player,Integer> classific;
    private static List<Map.Entry<Player,Integer>> list;
    private static boolean fileExist;
    private int numberOfGamesPlayed = 0;
    private static final String SAVEDNUMBERGAMESPLAYED = "numberGamesPlayed.bin";
    private static  List scores;
    private static final String SAVEDSCORES = "scores.bin";
    private static boolean isScoreUsed = false;
    private static boolean cleanScore = false;

    /**
     * Apre il file con le classifiche salvate  se esiste, altrimenti viene inizializzata una nuova classifica.
     * Apre il file contenente il numero di giochi a cui si è giocato se esiste. Se non esiste il valore dei giochi eseguiti
     * è uguale a 0.
     * In ogni caso viene inizializzata una mappa che conterrà il nome del giocatore e il rispettivo punteggio.
     * @throws IOException se esiste il file salvato ma ci sono problemi nella lettura.
     * @throws ClassNotFoundException se il file presenta degli oggetti di tipo non specificati nel programma.
     */
    public ManagerGeneralClassific() throws IOException, ClassNotFoundException {
        File scoresFile = new File(SAVEDSCORES);

        if (scoresFile.exists()) {
            try (
                    FileInputStream fis = new FileInputStream(SAVEDSCORES);
                    ObjectInputStream objectInputStream = new ObjectInputStream(fis);
            ) {
                scores = new ArrayList<>((ArrayList<Integer>) objectInputStream.readObject());
                fileExist = true;
                isScoreUsed = true;
            }
            } else {

            scores = new ArrayList<>();
        }
///////////////////////////////////////////////////////////////////////
        File savedGamesPlayed = new File(SAVEDNUMBERGAMESPLAYED);

        if (savedGamesPlayed.exists()) {

            try (
                    FileInputStream fis = new FileInputStream(SAVEDNUMBERGAMESPLAYED);
                    ObjectInputStream objectInputStream = new ObjectInputStream(fis);
            ) {
                numberOfGamesPlayed = (int) objectInputStream.readObject();
            }
        }

        classific = new HashMap<>();
    }



    /**
     * Viene salvato il punteggio del salto in alto nella classifica generale.
     */
    public static void highJumpResult() {
        int sum1 = 0;

        for (int i = 0; i < HighJumpManager.getMeScores().size(); i++) {
            if (isScoreUsed || cleanScore) {
                sum1 = (int) scores.get(i);
            }
            int sum2 = (int) HighJumpManager.getMeScores().get(i);
            int totalSum = sum1 + sum2;
            if (isScoreUsed || cleanScore) {
                scores.remove(i);
            }
            scores.add(i,totalSum);
        }
    }

    /**
     * Viene salvato il punteggio dei 400 metri  nella classifica generale.
     */
    public static void meters400Result() {
        int sum1 = 0;

        for (int i = 0; i < Meters400Manager.getMeScores().size(); i++) {
            if (isScoreUsed || cleanScore) {
                sum1 = (int) scores.get(i);
            }
            int sum2 = (int) Meters400Manager.getMeScores().get(i);
            int totalSum = sum1 + sum2;
            if (isScoreUsed || cleanScore) {
                scores.remove(i);
            }
            scores.add(i,totalSum);
        }
    }


    /**
     * Viene salvato il punteggio dei 110 metri a ostacoli nella classifica generale.
     */
    public static void meters110Result() {
        int sum1 = 0;

        for (int i = 0; i < Meters110Manager.getMeScores().size(); i++) {
            if (isScoreUsed || cleanScore) {
                sum1 = (int) scores.get(i);
            }
            int sum2 = (int) Meters110Manager.getMeScores().get(i);
            int totalSum = sum1 + sum2;
            if (isScoreUsed || cleanScore) {
                scores.remove(i);
            }
            scores.add(i,totalSum);
        }
    }

    /**
     * Viene salvato il punteggio dei 100 metri nella classifica generale.
     */
    public static void meters100Result() {
        int sum1 = 0;

        for (int i = 0; i < Meters100Manager.getMeScores().size(); i++) {
            if (isScoreUsed || cleanScore) {
                sum1 = (int) scores.get(i);
            }
            int sum2 = (int) Meters100Manager.getMeScores().get(i);
            int totalSum = sum1 + sum2;
            if (isScoreUsed || cleanScore) {
                scores.remove(i);
            }
            scores.add(i,totalSum);
        }
    }

    /**
     * Viene salvato il punteggio del lancio del peso nella classifica generale.
     */
    public static void shotPutResult() {
        int sum1 = 0;

        for (int i = 0; i < ShotPutManager.getMeScores().size(); i++) {
            if (isScoreUsed || cleanScore) {
                sum1 = (int) scores.get(i);
            }
            int sum2 = (int) ShotPutManager.getMeScores().get(i);
            int totalSum = sum1 + sum2;
            if (isScoreUsed || cleanScore) {
                scores.remove(i);
            }
            scores.add(i,totalSum);
        }
    }

    /**
     * Viene salvato il punteggio del salto con l' asta nella classifica generale.
     */
    public static void poleVaultResult() {
        int sum1 = 0;

        for (int i = 0; i < PoleVaultManager.getMeScores().size(); i++) {
            if (isScoreUsed || cleanScore) {
                sum1 = (int) scores.get(i);
            }
            int sum2 = (int) PoleVaultManager.getMeScores().get(i);
            int totalSum = sum1 + sum2;
            if (isScoreUsed || cleanScore) {
                scores.remove(i);
            }
            scores.add(i,totalSum);
        }
    }

    /**
     * Viene salvato il punteggio dei 1500 metri nella classifica generale.
     */
    public static void meters1500Result() {
        int sum1 = 0;

        for (int i = 0; i < Meters1500Manager.getMeScores().size(); i++) {
            if (isScoreUsed || cleanScore) {
                sum1 = (int) scores.get(i);
            }
            int sum2 = (int) Meters1500Manager.getMeScores().get(i);
            int totalSum = sum1 + sum2;
            if (isScoreUsed || cleanScore) {
                scores.remove(i);
            }
            scores.add(i,totalSum);
        }
    }

    /**
     * Viene salvato il punteggio del lancio del disco nella classifica generale.
     */
    public static void discusThrowResult() {
        int sum1 = 0;

        for (int i = 0; i < DiscusThrowManager.getMeScores().size(); i++) {
            if (isScoreUsed || cleanScore) {
                sum1 = (int) scores.get(i);
            }
            int sum2 = (int) DiscusThrowManager.getMeScores().get(i);
            int totalSum = sum1 + sum2;
            if (isScoreUsed || cleanScore) {
                scores.remove(i);
            }
            scores.add(i, totalSum);
        }
    }

    /**
     * Viene salvato il punteggio del lancio del giavellotto nella classifica generale.
     */
    public static void javelinThrowResult() {
        int sum1 = 0;

        for (int i = 0; i < JavelinThrowManager.getMeScores().size(); i++) {
            if (isScoreUsed || cleanScore) {
                sum1 = (int) scores.get(i);
            }
            int sum2 = (int) JavelinThrowManager.getMeScores().get(i);
            int totalSum = sum1 + sum2;
            if (isScoreUsed || cleanScore) {
                scores.remove(i);
            }
            scores.add(i, totalSum);
        }
    }

        /**
         * Viene salvato il punteggio del salto in lungo nella classifica generale.
         */
        public static void longJumpResult() {
            int sum1 = 0;

            for (int i = 0; i < LongJumpManager.getMeScores().size(); i++) {
                if (isScoreUsed || cleanScore) {
                    sum1 = (int) scores.get(i);
                }
                int sum2 = (int) LongJumpManager.getMeScores().get(i);
                int totalSum = sum1 + sum2;
                if (isScoreUsed || cleanScore) {
                    scores.remove(i);
                }
                scores.add(i,totalSum);
            }
    }


    /**
     * Metodo utilizzato per ottenere la classifica dei giocatori e il loro punteggio in ordine decrescente.
     * Viene scorsa la lista dei punteggi, ognuno di essi viene fatto corrispondere ad un giocatore.
     * @return la lista dei giocatori e il loro rispettivo punteggio in ordine decrescente.
     */
    public static  List getClassific() {

        classific.clear();

        for (int i = 0; i < scores.size(); i++) {
            Player player = PlayerManager.getPlayers().get(i);
            Integer score = (Integer) scores.get(i);
            classific.put(player,score);
        }

        Set<Map.Entry<Player, Integer>> entrySet = classific.entrySet();
        list = new ArrayList<>(entrySet);

        Collections.sort(list, Collections.reverseOrder(new Comparator<Map.Entry<Player, Integer>>() {

            @Override
            public int compare(Map.Entry<Player, Integer> o1, Map.Entry<Player, Integer> o2) {

                return o1.getValue().compareTo(o2.getValue());
            }

        } ) );

      cleanScore = true;
       return list;
    }


    public boolean getFileExist() {
        return fileExist;
    }

    public int getNumberOfGamesPlayed() {
        return this.numberOfGamesPlayed;
    }

    private String getNumberOfGamesSaved() {
        return SAVEDNUMBERGAMESPLAYED;
    }

    public void incrementNumberOfGamesPlayed() {
        this.numberOfGamesPlayed++;
    }

    private List getScoresToSave() {
        return scores;
    }

    private String getSavedScores() {
        return SAVEDSCORES;
    }

    public static Object getWinner() {
        return list.get(0);
    }


    public void saveScores() {
        List savedScores = new ArrayList(this.getScoresToSave());

        try (
                FileOutputStream fos = new FileOutputStream(this.getSavedScores());
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fos);
        ) {
            objectOutputStream.writeObject(savedScores);

        } catch (IOException e) {
            System.err.println("ERRORE CLASSIFICA NON SALVATA");
            e.printStackTrace();
        }
    }


    public void saveNumberOfGamesPlayed() {
        try (
                FileOutputStream fos = new FileOutputStream(this.getNumberOfGamesSaved());
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fos);
        ) {
            objectOutputStream.writeObject(getNumberOfGamesPlayed());
        } catch (IOException e) {
            System.err.println("non riesco a salvare il file, eliminarlo eliminarlo per continuare");
        }

    }


}
