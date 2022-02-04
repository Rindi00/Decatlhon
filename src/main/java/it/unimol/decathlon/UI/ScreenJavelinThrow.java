package it.unimol.decathlon.UI;

import it.unimol.decathlon.UI.abstra.ScreenForGames;
import it.unimol.decathlon.app.exceptions.WrongDiceToSaveException;
import it.unimol.decathlon.app.exceptions.WrongInputChoiceDiceException;
import it.unimol.decathlon.app.exceptions.WrongInputChoiceException;
import it.unimol.decathlon.app.manager.ChoiceManager;
import it.unimol.decathlon.app.manager.JavelinThrowManager;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class ScreenJavelinThrow implements ScreenForGames {
    private JavelinThrowManager javelinThrowManager;
    private String choice = "";
    private static int numberDiceNotSaved = 0;

    public ScreenJavelinThrow() {

    }


    public void start() {
        int time = 1;

        while (time != 0) {

            letPlay();

            classific();

            time = 0;

        }

    }


    public void letPlay() {

        this.initialize();

        while (this.javelinThrowManager.controlTurn()) {

            this.startOfMiniGame();

            this.choice = "";

            while (!this.choice.equals("no")) {

                this.bodyOfMiniGame();
                this.saveDicesChoice();
                this.situation();
            }
        }
    }


    public void classific() {

        System.out.println("");

        System.out.println("*****Classifica  LANCIO DEL GIAVELLOTTO *****");

        System.out.println("");

        this.javelinThrowManager.getPlayersScore().forEach(System.out::println);
        System.out.println("");
        System.out.println("IL vincitore alla disciplina LANCIO DEL GIAVELLOTTO è " + " " + this.javelinThrowManager.getPlayersScore().get(0));
        System.out.println("");
        System.out.println("");
    }


    private void endOfGame() {
        this.javelinThrowManager.setCurrentPlayer();

        this.javelinThrowManager.setScoreUI();

        this.javelinThrowManager.setPlayersScore();

        System.out.println(this.javelinThrowManager.getActivePlayer() + " " + this.javelinThrowManager.toString());

        System.out.println("");

        this.javelinThrowManager.initializeResult();
        this.javelinThrowManager.resetChoice();
        JavelinThrowManager.resetNumberDicesEven();
        numberDiceNotSaved = 0;

        this.javelinThrowManager.nextTurn();
    }

    private void bodyOfMiniGame() {
        numberDiceNotSaved = 0;
        JavelinThrowManager.resetNumberDicesEven();
        System.out.println("DADI");

        if (this.javelinThrowManager.diceSavedControl("A")) {
            System.out.println( "A:" + " " + this.javelinThrowManager.getDiceAOut());
            numberDiceNotSaved++;

        }
        else {
            System.out.println( "A:" + " " + this.javelinThrowManager.getDiceSaved("A") + " " + "(congelato)");
        }

        if (this.javelinThrowManager.diceSavedControl("B")) {
            System.out.println( "B:" + " " + this.javelinThrowManager.getDiceBOut());
            numberDiceNotSaved++;
        }
        else {
            System.out.println( "B:" + " " + this.javelinThrowManager.getDiceSaved("B") + " " + "(congelato)");

        }

        if (this.javelinThrowManager.diceSavedControl("C")) {
            System.out.println( "C:" + " " + this.javelinThrowManager.getDiceCOut());
            numberDiceNotSaved++;
        }
        else {
            System.out.println( "C:" + " " + this.javelinThrowManager.getDiceSaved("C") + " " + "(congelato)");
        }

        if (this.javelinThrowManager.diceSavedControl("D")) {
            System.out.println( "D:" + " " + this.javelinThrowManager.getDiceDOut());
            numberDiceNotSaved++;
        }
        else {
            System.out.println( "D:" + " " + this.javelinThrowManager.getDiceSaved("D") + " " + "(congelato)");
        }

        if (this.javelinThrowManager.diceSavedControl("E")) {
            System.out.println( "E:" + " " + this.javelinThrowManager.getDiceEOut());
            numberDiceNotSaved++;
        }
        else {
            System.out.println( "E:" + " " + this.javelinThrowManager.getDiceSaved("E") + " " + "(congelato)");
        }

        if (this.javelinThrowManager.diceSavedControl("F")) {
            System.out.println( "F:" + " " + this.javelinThrowManager.getDiceFOut());
            numberDiceNotSaved++;
        }
        else {
            System.out.println( "F:" + " " + this.javelinThrowManager.getDiceSaved("F") + " " + "(congelato)");
        }
    }


    public void situation() {
        if (this.javelinThrowManager.getScore() != 0 && JavelinThrowManager.getNumberDicesEven() != numberDiceNotSaved ) {
            System.out.print("HAI:" + " " + this.javelinThrowManager.getScore() + " " + "PUNTI" + " ");

            if (this.javelinThrowManager.getMaxDicesNumber() != 0) {
                System.out.println("Vui rilanciare i dadi restanti? si/no");
                this.choice = controlChoice();

                if (this.choice.equals("no")) {

                    this.endOfGame();

                }
            }
        }

        if (this.javelinThrowManager.getScore() == 0) {
            System.out.println("TENTATIVO ANNULLATO");
            this.endOfGame();
            this.choice = "no";
        }

    }

    public void saveDicesChoice() {
        boolean isOK = true;

        if  ((JavelinThrowManager.getNumberDicesEven() > 0) && (JavelinThrowManager.getNumberDicesEven() == numberDiceNotSaved)) {
            System.out.println("NON PUOI SALVARE DADI TENTATIVO ANNULLATO");
            this.endOfGame();
            this.choice = "no";
        }

        else if (numberDiceNotSaved == 0) {
            System.out.println("IL NUMERO DI DADI DA SALVARE E' STATO RAGGIUNTO");
            this.endOfGame();
            this.choice = "no";

        }

        else {
            while (isOK) {
                System.out.println("Quali dadi vuoi congelare? (inserirli in lettera maiuscola separati da virgola)");
                try {
                    this.javelinThrowManager.freezeDices(this.inputDicesToSave());
                    isOK = false;
                } catch (WrongDiceToSaveException e) {
                    System.err.println(e.getMessage());

                }
            }
        }
    }


    private void startOfMiniGame() {
        this.javelinThrowManager.goGame();
        this.javelinThrowManager.resetTemporary();
        this.javelinThrowManager.initializeResult();

        System.out.println("E' il turno di :  " + this.javelinThrowManager.getActivePlayer());

        System.out.println("");
    }



    private void initialize() {
        this.javelinThrowManager = new JavelinThrowManager();

        System.out.println("");

        try {
            this.javelinThrowManager.addPlayer();

        } catch (IOException e ){
            System.err.println("NON RIESCO AD APRIRE IL FILE, ELIMINARLO PER CONTINUARE");
            System.exit(-1);

        } catch (ClassNotFoundException e) {
            System.err.println("NON RIESCO A TROVARE IL FILE, ELIMINARLO PER CONTINUARE");
            System.exit(-1);

        }
    }


    private String inputChoice() {

        Scanner choice = new Scanner(System.in);

        return choice.nextLine();
    }


    public String controlChoice() {
        int error;

        String choice = " ";

        do {

            error = 0;

            try {

                choice =  ChoiceManager.getMeChoice(this.inputChoice());

            }

            catch(WrongInputChoiceException e ){

                error = 1;

                System.err.println(e.getMessage());
            }

        }  while (error != 0);

        return choice;
    }


    private String inputChoiceDiceToSve() {

        Scanner choice = new Scanner(System.in);

        return choice.nextLine();
    }

    private Set<String> inputDicesToSave() {
        Set<String> result = new HashSet<>();
        boolean is0k = true;

        while (is0k) {
            try {
                String diceToSaveString = " ";
                diceToSaveString = ChoiceManager.getMeChoiceDiceToSaveJavelin(this.inputChoiceDiceToSve());

                String[] dicesToSaveArray = diceToSaveString.split(",");
                if (diceToSaveString.length() > 0 && dicesToSaveArray.length == 0) {
                    dicesToSaveArray = new String[diceToSaveString.length() + 1];
                    for (int i = 0; i < dicesToSaveArray.length; i++) {
                        dicesToSaveArray[i] = "";
                    }
                }
                for (String dice : dicesToSaveArray) {
                    result.add(dice);
                    this.javelinThrowManager.decMaxDicesNumber();

                }
                is0k = false;

            } catch (WrongInputChoiceDiceException e) {
                System.err.println(e.getMessage());
            }
        }

        return result;

    }

    public static boolean isAllDicesEven() {
        return ((JavelinThrowManager.getNumberDicesEven() > 0) && (JavelinThrowManager.getNumberDicesEven() == numberDiceNotSaved));
    }
}


