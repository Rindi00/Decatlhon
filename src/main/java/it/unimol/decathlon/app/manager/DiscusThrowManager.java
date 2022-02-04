package it.unimol.decathlon.app.manager;

import it.unimol.decathlon.GUI.discusThrow.DiscusThrowPanel;
import it.unimol.decathlon.UI.ScreenDiscusThrow;
import it.unimol.decathlon.app.abstra.Manager;
import it.unimol.decathlon.app.exceptions.WrongDiceToSaveException;
import it.unimol.decathlon.app.miniGames.DiscusThrow;
import it.unimol.decathlon.app.player.Player;
import java.util.*;

/**
 * Classe rappresentante il gestore del gioco Lancio del disco.
 * @author Antonio Pircio
 * @version 3.0
 */
public class DiscusThrowManager extends Manager {
    private DiscusThrow discusThrow;
    private static Map<Player,Integer> playersScoreDiscusThrow;
    private List<Integer> temporary;
    private Map<String,Integer> result;
    private Player currentPlayer;
    private int score;
    private List<String> choices;
    private int diceA;
    private int diceB;
    private int diceC;
    private int diceD;
    private int diceE;
    private static List<Integer> scores;
    private static int odd = 0;

    public DiscusThrowManager() {
        playersScoreDiscusThrow = new HashMap<>();
        this.temporary = new ArrayList<>();
        this.choices = new ArrayList<>();
        scores = new ArrayList<>();
    }


    /**
     * Metodo utilizzato per salvare i dadi lanciati.
     * Viene salvato il nome di ogni dado in una specifica lista.
     * Viene salvato il valore di ogni dado in una specifica lista.
     * @param choiceDiceToSave insieme contenente i dadi da voler salvare.
     * @throws WrongDiceToSaveException viene lanciata se l' insieme preso in input presenta un dado già salvato in precedenza,
     * se contiene spazi vuoti oppure se i dadi non sono separati da una virgola.
     */
    public void freezeDices(Set<String> choiceDiceToSave) throws WrongDiceToSaveException {
        for (String choice : choiceDiceToSave) {
            if (this.choices.contains(choice)) {
                throw new WrongDiceToSaveException("ERRORE, IL DADO E' STATO GIA' SALVATO");
            }
        }
        for (String choice : choiceDiceToSave) {
            if (choice.isEmpty()) {
                throw new WrongDiceToSaveException("ERRORE, NON SONO ACCETTATI SPAZI VUOTI" + " " +
                                                           "INSERIRE NUOVAMENTE I DADI DA SALVARE SEPARATI DA VIRGOLA");
            }
        }
        for (String choice : choiceDiceToSave) {
            if (choice.length() > 1) {
                throw new WrongDiceToSaveException("ERRORE,BISOGNA SEPARARE I DADI SCELTI DA UNA VIRGOLA" + " " +
                                                           "INSERIRE NUOVAMENTE I DADI DA SALVARE SEPARATI DA VIRGOLA");
            }
        }

        Iterator iterator = choiceDiceToSave.iterator();
        while (iterator.hasNext()) {
            String dice = (String) iterator.next();
            this.choices.add(dice);
            this.temporary.add(this.result.get(dice));
        }
    }


    /**
     * Metodo utilizzato per assegnare un punteggio temporaneo in base ai dadi salvati fino a quel momento.
     * Verrà assegnato un punteggio uguale a 0 nel caso in cui il valore di un dado salvato è dispari.
     * @return il punteggio ottenuto in base ai dadi salvati
     */
    public int getScore() {
        int sum = 0;

        for (Integer value : this.temporary) {

            if ( value % 2 == 0) {
                sum += value;
            }

            else {
               resetChoice();
                return  0;
            }
        }
        return sum;
    }


    /**
     * Metodo utilizzato per calcolare il risultato  finale ottenuto dal giocatore, in base al controllo fatto
     * utilizzando {@link DiscusThrowManager#getScore()}.
     * @return la somma dei dadi salvati dopo il controllo.
     */
    public int fixedRsult() {
        int sum = 0;

        if (this.getScore() != 0) {

            List<Integer> fixed = new ArrayList<>(this.temporary);

            for (Integer number : fixed) {

                sum += number;
            }
        }
        else  {
            sum = 0;
        }

        return sum;
    }


    public void decMaxDicesNumber() {
        this.discusThrow.decMaxDicesNumber();
    }

    public void resetTemporary() {
        this.temporary.clear();
    }


    public void setCurrentPlayer() {
        this.currentPlayer = getActivePlayer();
    }


    /**
     * Imposta il punteggio finale uguale alla somma dei dadi salvati se tra i dadi lanciati vi è almeno uno non dispari.
     * altrimenti il punteggio sarà uguale a 0.
     * Usato per GUI.
     */
    public void setScore() {
        if (!DiscusThrowPanel.isAllDicesOdd()) {
            this.score = fixedRsult();
        }
        else {
            this.score = 0;
        }
    }

    /**
     * Imposta il punteggio finale uguale alla somma dei dadi salvati se tra i dadi lanciati vi è almeno uno non dispari.
     * altrimenti il punteggio sarà uguale a 0.
     * Usato per UI.
     */
    public void setScoreUI() {
        if (!ScreenDiscusThrow.isAllDicesOdd()) {
            this.score = fixedRsult();
        } else {
            this.score = 0;
        }
    }


    public void setPlayersScore() {
        scores.add(this.score);
        playersScoreDiscusThrow.put(this.currentPlayer,this.score);
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


    /**
     * Metodo utilizzato per ottenere la classifica dei giocatori e il loro punteggio in ordine decrescente.
     * @return la lista dei giocatori e il loro rispettivo punteggio in ordine decrescente
     */
    public List getPlayersScore() {

        Set<Map.Entry<Player,Integer>> entrySet = playersScoreDiscusThrow.entrySet();

        List<Map.Entry<Player,Integer>> list = new ArrayList<>(entrySet);

        Collections.sort(list, Collections.reverseOrder(new Comparator<Map.Entry<Player, Integer>>() {

            @Override
            public int compare(Map.Entry<Player, Integer> o1, Map.Entry<Player, Integer> o2) {

                return o1.getValue().compareTo(o2.getValue());
            }

        } ) );

        return list;
    }


    public void goGame() {
        this.discusThrow = new DiscusThrow();
    }

    public int rollDices() {
        return this.discusThrow.rollDices();
    }


    public int getDiceAOut() {
        int result =  rollDices();

        if ((result % 2) != 0) {
            odd++;
        }

        this.diceA = result;

        this.result.put("A", this.diceA);

        return this.diceA;

    }

    public int getDiceBOut() {
        int result =  rollDices();

        if ((result % 2) != 0) {
            odd++;
        }

        this.diceB = result;

        this.result.put("B", this.diceB);

        return this.diceB;
    }

    public int getDiceCOut() {
        int result =  rollDices();

        if ((result % 2) != 0) {
            odd++;
        }

        this.diceC = result;

        this.result.put("C", this.diceC);

        return this.diceC;
    }

    public int getDiceDOut() {
        int result =  rollDices();

        if ((result % 2) != 0) {
            odd++;
        }

        this.diceD = result;

        this.result.put("D", this.diceD);

        return this.diceD;
    }

    public int getDiceEOut() {
        int result =  rollDices();

        if ((result % 2) != 0) {
            odd++;
        }

        this.diceE = result;

        this.result.put("E", this.diceE);

        return this.diceE;
    }

    public int getMaxDicesNumber() {
        return this.discusThrow.getMaxNumberDices();
    }

    /**
     * Si controlla che un dado sia stato già salvato. Se è così non verrà rilanciato.
     * @param choice il nome del dado da controllare.
     * @return {@code false} se il dado è stato già salvato, {@code true} altrimenti.
     */
    public boolean diceSavedControl(String choice) {

        if  (this.choices.contains(choice)) {
            return false;
        }
        else {

            return true;
        }
    }

    public int getDiceSaved(String choice) {
      return this.result.get(choice);
    }

    public void initializeResult() {
        this.result = new HashMap<>();
    }

    public void resetChoice() {
        this.choices.clear();
    }

    public static int getNumberDicesOdd() {
        return odd;
    }

    public static void resetNumberDicesOdd() {
        odd = 0;
    }

}
