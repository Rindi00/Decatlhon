package it.unimol.decathlon.app.manager;

import it.unimol.decathlon.GUI.javelinThrow.JavelinThrowPanel;
import it.unimol.decathlon.UI.ScreenJavelinThrow;
import it.unimol.decathlon.app.abstra.Manager;
import it.unimol.decathlon.app.exceptions.WrongDiceToSaveException;
import it.unimol.decathlon.app.miniGames.JavelinThrow;
import it.unimol.decathlon.app.player.Player;

import java.util.*;

/**
 * Classe rappresentante il gestore del gioco Lancio del giavellotto.
 * @author Antonio Pircio
 * @version 3.0
 */
public class JavelinThrowManager extends Manager {
    private JavelinThrow javelinThrow;
    private static Map<Player, Integer> playersScoreDiscusThrow;
    private List<Integer> temporary;
    private Map<String, Integer> result;
    private Player currentPlayer;
    private int score;
    private List<String> choices;
    private int diceA;
    private int diceB;
    private int diceC;
    private int diceD;
    private int diceE;
    private int diceF;
    private static List<Integer> scores;
    private static int even = 0;


    public JavelinThrowManager() {
        playersScoreDiscusThrow = new HashMap<>();
        this.temporary = new ArrayList<>();
        this.choices = new ArrayList<>();
        scores = new ArrayList<>();
    }

    /**
     * Metodo utilizzato per salvare i dadi lanciati.
     * Viene salvato il nome di ogni dado in una specifica lista.
     * Viene salvato il valore di ogni dado in una specifica lista.
     *@param choiceDiceToSave insieme contenente i dadi da voler salvare
     * @throws WrongDiceToSaveException viene lanciata se l' insieme preso in input contiene spazi vuoti oppure se i dadi
     * non sono separati da una virgola.
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
     * Verrà assegnato un punteggio uguale a 0 nel caso in cui il valore di un dado salvato è pari.
     * @return il punteggio ottenuto in base ai dadi salvati.
     */
    public int getScore() {
        int sum = 0;

        for (Integer value : this.temporary) {
            if (value % 2 != 0) {
                sum += value;
            } else {
                resetChoice();
                return 0;
            }
        }
        return sum;
    }

    /**
     * Metodo utilizzato per calcolare il risultato  finale ottenuto dal giocatore, in base al controllo fatto.
     * utilizzando {@link JavelinThrowManager#getScore()}.
     * @return la somma dei dadi salvati dopo il controllo.
     */
    public int fixedRsult() {
        int sum = 0;

        if (this.getScore() != 0) {

            List<Integer> fixed = new ArrayList<>(this.temporary);

            for (Integer number : fixed) {

                sum += number;
            }
        } else {
            sum = 0;
        }

        return sum;
    }


    public void decMaxDicesNumber() {
        this.javelinThrow.decMaxDicesNumber();
    }

    public void resetTemporary() {
        this.temporary.clear();
    }


    public void setCurrentPlayer() {
        this.currentPlayer = getActivePlayer();
    }

    /**
     * Imposta il punteggio finale uguale alla somma dei dadi salvati se tra i dadi lanciati vi è almeno uno non pari.
     * altrimenti il punteggio sarà uguale a 0.
     * Usato per GUI.
     */
    public void setScore() {
        if (!JavelinThrowPanel.isAllDicesEven()) {
            this.score = fixedRsult();
        }
        else {
            this.score = 0;
        }
    }

    /**
     * Imposta il punteggio finale uguale alla somma dei dadi salvati se tra i dadi lanciati vi è almeno uno non pari.
     * altrimenti il punteggio sarà uguale a 0.
     * Usato per UI.
     */
    public void setScoreUI() {
        if (!ScreenJavelinThrow.isAllDicesEven()) {
            this.score = fixedRsult();

        } else {
            this.score = 0;
        }
    }


    public void setPlayersScore() {
        scores.add(this.score);
        playersScoreDiscusThrow.put(this.currentPlayer, this.score);
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
        return ("Score:" + "  " + this.score);
    }


    /**
     * Metodo utilizzato per ottenere la classifica dei giocatori e il loro punteggio in ordine decrescente.
     * @return la lista dei giocatori e il loro rispettivo punteggio in ordine decrescente.
     */
    public List getPlayersScore() {

        Set<Map.Entry<Player, Integer>> entrySet = playersScoreDiscusThrow.entrySet();

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
        this.javelinThrow = new JavelinThrow();
    }


    public int rollDices() {
        return this.javelinThrow.rollDices();
    }


    public int getDiceAOut() {
        int result = rollDices();

        if (result % 2 == 0) {
            even++;
        }

        this.diceA = result;

        this.result.put("A", this.diceA);

        return this.diceA;

    }

    public int getDiceBOut() {
        int result = rollDices();

        if (result % 2 == 0) {
            even++;
        }

        this.diceB = result;

        this.result.put("B", this.diceB);

        return this.diceB;
    }

    public int getDiceCOut() {
        int result = rollDices();

        if (result % 2 == 0) {
            even++;
        }

        this.diceC = result;

        this.result.put("C", this.diceC);

        return this.diceC;
    }

    public int getDiceDOut() {
        int result = rollDices();

        if (result % 2 == 0) {
            even++;
        }

        this.diceD = result;

        this.result.put("D", this.diceD);

        return this.diceD;
    }

    public int getDiceEOut() {
        int result = rollDices();

        if (result % 2 == 0) {
            even++;
        }

        this.diceE = result;

        this.result.put("E", this.diceE);

        return this.diceE;
    }


    public int getDiceFOut() {
        int result = rollDices();

        if (result % 2 == 0) {
            even++;
        }

        this.diceF = result;

        this.result.put("F", this.diceF);

        return this.diceF;
    }


    public int getMaxDicesNumber() {
        return this.javelinThrow.getMaxNumberDices();
    }

    /**
     * Si controlla che un dado sia stato già salvato. Se è così non verrà rilanciato.
     * @param choice il nome del dado da controllare.
     * @return {@code false} se il dado è stato già salvato, {@code true} altrimenti.
     */
    public boolean diceSavedControl(String choice) {

        if (this.choices.contains(choice)) {
            return false;
        } else {

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

    public static int getNumberDicesEven() {
        return even;
    }

    public static void resetNumberDicesEven() {
        even = 0;
    }
}
