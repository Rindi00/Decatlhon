package it.unimol.decathlon.UI;

import it.unimol.decathlon.UI.abstra.ScreenForGames;
import it.unimol.decathlon.app.exceptions.WrongInputChoiceDiceException;
import it.unimol.decathlon.app.exceptions.WrongInputChoiceException;
import it.unimol.decathlon.app.exceptions.WrongDiceToSaveException;
import it.unimol.decathlon.app.manager.ChoiceManager;
import it.unimol.decathlon.app.manager.DiscusThrowManager;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class ScreenDiscusThrow implements ScreenForGames {
    private DiscusThrowManager discusThrowManager;
    private String choice = "";
    private static int numberDiceNotSaved = 0;

    public ScreenDiscusThrow() {

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

        while (this.discusThrowManager.controlTurn()) {

            this.startOfMiniGame();

            this.choice = " ";

            while (!this.choice.equals("no")) {

                this.bodyOfMiniGame();
                this.saveDicesChoice();
                this.situation();
            }
        }
    }


    public void classific() {
        System.out.println("");

        System.out.println("*****Classifica  LANCIO DEL DISCO *****");

        System.out.println("");

        this.discusThrowManager.getPlayersScore().forEach(System.out::println);
        System.out.println("");
        System.out.println("IL vincitore alla disciplina LANCIO DEL DISCO è " + " " + this.discusThrowManager.getPlayersScore().get(0));
        System.out.println("");
        System.out.println("");
    }


    private void endOfGame() {
        this.discusThrowManager.setCurrentPlayer();

        this.discusThrowManager.setScoreUI();

        this.discusThrowManager.setPlayersScore();

        System.out.println(this.discusThrowManager.getActivePlayer() + " " + this.discusThrowManager.toString());

        System.out.println("");

        this.discusThrowManager.initializeResult();
        this.discusThrowManager.resetChoice();
        DiscusThrowManager.resetNumberDicesOdd();
        numberDiceNotSaved = 0;

        this.discusThrowManager.nextTurn();
    }

    private void bodyOfMiniGame() {
        numberDiceNotSaved = 0;
        DiscusThrowManager.resetNumberDicesOdd();
        System.out.println("DADI");

        if (this.discusThrowManager.diceSavedControl("A")) {
            System.out.println( "A:" + " " + this.discusThrowManager.getDiceAOut());
            numberDiceNotSaved++;

        }
        else {
            System.out.println( "A:" + " " + this.discusThrowManager.getDiceSaved("A") + " " + "(congelato)");
        }

        if (this.discusThrowManager.diceSavedControl("B")) {
            System.out.println( "B:" + " " + this.discusThrowManager.getDiceBOut());
            numberDiceNotSaved++;
        }
        else {
            System.out.println( "B:" + " " + this.discusThrowManager.getDiceSaved("B") + " " + "(congelato)");

        }

        if (this.discusThrowManager.diceSavedControl("C")) {
            System.out.println( "C:" + " " + this.discusThrowManager.getDiceCOut());
            numberDiceNotSaved++;
        }
        else {
            System.out.println( "C:" + " " + this.discusThrowManager.getDiceSaved("C") + " " + "(congelato)");
        }

        if (this.discusThrowManager.diceSavedControl("D")) {
            System.out.println( "D:" + " " + this.discusThrowManager.getDiceDOut());
            numberDiceNotSaved++;
        }
        else {
            System.out.println( "D:" + " " + this.discusThrowManager.getDiceSaved("D") + " " + "(congelato)");
        }

        if (this.discusThrowManager.diceSavedControl("E")) {
            System.out.println( "E:" + " " + this.discusThrowManager.getDiceEOut());
            numberDiceNotSaved++;
        }
        else {
            System.out.println( "E:" + " " + this.discusThrowManager.getDiceSaved("E") + " " + "(congelato)");
        }
    }

    public void situation() {
        if (this.discusThrowManager.getScore() != 0 && DiscusThrowManager.getNumberDicesOdd() != numberDiceNotSaved ) {
            System.out.print("HAI:" + " " + this.discusThrowManager.getScore() + " " + "PUNTI" + " ");

            if (this.discusThrowManager.getMaxDicesNumber() != 0) {
                System.out.println("Vui rilanciare i dadi restanti? si/no");
                this.choice = controlChoice();

                if (this.choice.equals("no")) {

                    this.endOfGame();

                }
            }
        }

        if (this.discusThrowManager.getScore() == 0) {
            System.out.println("TENTATIVO ANNULLATO");
            this.endOfGame();
            this.choice = "no";
        }

    }

    public void saveDicesChoice() {
        boolean isOK = true;

        if  ((DiscusThrowManager.getNumberDicesOdd() > 0) && (DiscusThrowManager.getNumberDicesOdd() == numberDiceNotSaved)) {
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
                    this.discusThrowManager.freezeDices(this.inputDicesToSave());
                    isOK = false;
                } catch (WrongDiceToSaveException e) {
                    System.err.println(e.getMessage());

                }
            }
        }
    }


    private void startOfMiniGame() {
        this.discusThrowManager.goGame();
        this.discusThrowManager.resetTemporary();
        this.discusThrowManager.initializeResult();

        System.out.println("E' il turno di :  " + this.discusThrowManager.getActivePlayer());

        System.out.println("");
    }



    private void initialize() {
        this.discusThrowManager = new DiscusThrowManager();

        try {
            this.discusThrowManager.addPlayer();

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
                diceToSaveString = ChoiceManager.getMeChoiceDiceToSaveDiscus(this.inputChoiceDiceToSve());

                String[] dicesToSaveArray = diceToSaveString.split(",");
                if (diceToSaveString.length() > 0 && dicesToSaveArray.length == 0) {
                    dicesToSaveArray = new String[diceToSaveString.length() + 1];
                    for (int i = 0; i < dicesToSaveArray.length; i++) {
                        dicesToSaveArray[i] = "";
                    }
                }
                for (String dice : dicesToSaveArray) {
                    result.add(dice);
                    this.discusThrowManager.decMaxDicesNumber();

                }
                is0k = false;

            } catch (WrongInputChoiceDiceException e) {
                System.err.println(e.getMessage());
            }
        }

            return result;
    }

    public static boolean isAllDicesOdd() {
        return ((DiscusThrowManager.getNumberDicesOdd() > 0) && (DiscusThrowManager.getNumberDicesOdd() == numberDiceNotSaved));
    }
}
