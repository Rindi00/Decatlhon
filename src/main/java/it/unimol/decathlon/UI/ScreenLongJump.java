package it.unimol.decathlon.UI;

import it.unimol.decathlon.UI.abstra.ScreenForGames;;
import it.unimol.decathlon.app.exceptions.WrongDiceToSaveException;
import it.unimol.decathlon.app.exceptions.WrongInputChoiceDiceException;
import it.unimol.decathlon.app.exceptions.WrongInputChoiceException;
import it.unimol.decathlon.app.manager.ChoiceManager;
import it.unimol.decathlon.app.manager.LongJumpManager;

import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class ScreenLongJump implements ScreenForGames {
    private LongJumpManager longJumpManager;
    private int numberDicesSavedRunUp = 0;
    private boolean isJumpTime;
    private int numberDicesSavedJump = 0;
    private boolean situationForJump;
    private boolean exit;



    public ScreenLongJump() {

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

        while (this.longJumpManager.controlTurn()) {

            this.startOfMiniGame();

            while (!this.exit) {

                if (!this.isJumpTime) {
                    this.runUp();
                    this.situation();

                }

                if (this.isJumpTime) {
                    this.jump();
                    this.situation();
                }
            }
        }
    }

    public void classific() {
        System.out.println("");

        System.out.println("*****Classifica  SALTO IN LUNGO *****");

        System.out.println("");

        this.longJumpManager.getPlayersScore().forEach(System.out::println);
        System.out.println("");
        System.out.println("IL vincitore alla disciplina SALTO IN LUNGO è " + " " + this.longJumpManager.getPlayersScore().get(0));
        System.out.println("");
        System.out.println("");
    }


    private void endOfGame() {
        this.longJumpManager.setCurrentPlayer();

        this.longJumpManager.setScore();

        this.longJumpManager.setPlayersScore();

        System.out.println(this.longJumpManager.getActivePlayer() + " " + this.longJumpManager.toString());

        this.longJumpManager.initializeResult();
        this.longJumpManager.resetChoice();
        this.situationForJump = false;
        this.numberDicesSavedJump = 0;
        this.numberDicesSavedRunUp = 0;

        this.longJumpManager.nextTurn();
    }

    private void runUp() {
        System.out.println("RINCORSA");

        if (this.longJumpManager.diceSavedControl("A")) {
            System.out.println("A:" + " " + this.longJumpManager.getDiceAOut());

        } else {
            System.out.println("A:" + " " + this.longJumpManager.getDiceSaved("A") + " " + "(congelato)");

        }

        if (this.longJumpManager.diceSavedControl("B")) {
            System.out.println("B:" + " " + this.longJumpManager.getDiceBOut());
        } else {
            System.out.println("B:" + " " + this.longJumpManager.getDiceSaved("B") + " " + "(congelato)");

        }

        if (this.longJumpManager.diceSavedControl("C")) {
            System.out.println("C:" + " " + this.longJumpManager.getDiceCOut());
        } else {
            System.out.println("C:" + " " + this.longJumpManager.getDiceSaved("C") + " " + "(congelato)");

        }

        if (this.longJumpManager.diceSavedControl("D")) {
            System.out.println("D:" + " " + this.longJumpManager.getDiceDOut());
        } else {
            System.out.println("D:" + " " + this.longJumpManager.getDiceSaved("D") + " " + "(congelato)");

        }

        if (this.longJumpManager.diceSavedControl("E")) {
            System.out.println("E:" + " " + this.longJumpManager.getDiceEOut());

        } else {
            System.out.println("E:" + " " + this.longJumpManager.getDiceSaved("E") + " " + "(congelato)");

        }

        /*if (this.longJumpManager.getChoices().size() != 4 &&  !this.exit) {
            System.out.println();
            System.out.println("Quali dadi vuoi congelare? (inserirli in lettera maiuscola separati da virgola)");
        }*/

        this.saveDicesChoice();
    }


    private void jump() {
        System.out.println("SALTO");

        this.situationForJump = true;


        if (this.longJumpManager.diceSavedControl("A")) {
            System.out.println("A:" + " " + this.longJumpManager.getDiceAOut());

        } else {
            System.out.println("A:" + " " + this.longJumpManager.getDiceSaved("A") + " " + "(congelato)");

        }


        if (this.numberDicesSavedRunUp > 1) {
            if (this.longJumpManager.diceSavedControl("B")) {
                System.out.println("B:" + " " + this.longJumpManager.getDiceBOut());
            } else {
                System.out.println("B:" + " " + this.longJumpManager.getDiceSaved("B") + " " + "(congelato)");

            }
        }

        if(this.numberDicesSavedRunUp > 2) {
            if (this.longJumpManager.diceSavedControl("C")) {
                System.out.println("C:" + " " + this.longJumpManager.getDiceCOut());
            } else {
                System.out.println("C:" + " " + this.longJumpManager.getDiceSaved("C") + " " + "(congelato)");

            }
        }


        if (this.numberDicesSavedRunUp > 3) {
            if (this.longJumpManager.diceSavedControl("D")) {
                System.out.println("D:" + " " + this.longJumpManager.getDiceDOut());
            } else {
                System.out.println("D:" + " " + this.longJumpManager.getDiceSaved("D") + " " + "(congelato)");

            }
        }


        if (this.numberDicesSavedRunUp > 4) {
            if (this.longJumpManager.diceSavedControl("E")) {
                System.out.println("E:" + " " + this.longJumpManager.getDiceEOut());

            } else {
                System.out.println("E:" + " " + this.longJumpManager.getDiceSaved("E") + " " + "(congelato)");

            }
        }

            if (this.numberDicesSavedJump < this.numberDicesSavedRunUp -1 ) {
                System.out.println();
                System.out.println("Quali dadi vuoi congelare? (inserirli in lettera maiuscola separati da virgola)");

            }



        this.saveDicesChoice();
    }


    private void startOfMiniGame() {
        this.longJumpManager.goGame();
        this.exit = false;
        this.isJumpTime = false;
        this.longJumpManager.resetTemporary();
        this.longJumpManager.initializeResult();

        System.out.println("E' il turno di :  " + this.longJumpManager.getActivePlayer());

        System.out.println("");
    }


    public void situation() {

        if (!this.isJumpTime) {

            if (this.longJumpManager.getScoreRunUp() != 0 && !this.exit && this.longJumpManager.getScore() < 9
                                                                                    && this.numberDicesSavedRunUp < 5) {
                System.out.println("Totale rincorsa :" + " " + this.longJumpManager.getScoreRunUp() + "/9");
            }

            if (this.longJumpManager.getScoreRunUp() != 0 &&  this.numberDicesSavedRunUp < 5 && !this.exit
                                                                             && this.longJumpManager.getScore() < 9) {
                System.out.println("Vuoi continuare la rincorsa?");


                String choice = controlChoice();

                if (choice.equals("no")) {
                    System.out.println("");
                    System.out.println("Rincorsa:" + " " + this.longJumpManager.getScoreRunUp() + " " + "punti" +
                            " " + this.longJumpManager.getChoices().size() + " " + "dadi congelati");

                    this.isJumpTime = true;
                    this.longJumpManager.resetChoice();
                    this.longJumpManager.resetTemporary();
                }
            }

                if ((this.longJumpManager.getScore() == 9 ) || (this.longJumpManager.getScore() < 9 && this.numberDicesSavedRunUp == 5)) {
                    System.out.println("Totale rincorsa :" + " " + this.longJumpManager.getScoreRunUp() + "/9");
                    System.out.println("Rincorsa:" + " " + this.longJumpManager.getScoreRunUp() + " " + "punti" +
                            " " + this.longJumpManager.getChoices().size() + " " + "dadi congelati");

                    this.isJumpTime = true;
                    this.numberDicesSavedRunUp = this.longJumpManager.getChoices().size();
                    this.longJumpManager.resetChoice();
                    this.longJumpManager.resetTemporary();
                }

                if (this.longJumpManager.getScore() > 9) {
                    System.out.println("HAI SUPERATO IL VALORE MASSIMO DI RINCORSA, NON PUOI ANDARE AVANTI");
                    this.endOfGame();
                    this.exit = true;
                }

            }

            if (this.situationForJump) {
                int total = this.longJumpManager.getScore() + this.longJumpManager.runUpSettedScore();

                System.out.println("Totale salto :" + " " + this.longJumpManager.getScore() +
                        " " + ("totale complessivo" + " " + total));
            }

        }



    public void saveDicesChoice() {
        boolean isOK = true;

        if ((this.numberDicesSavedJump > 0) && (this.numberDicesSavedJump == this.numberDicesSavedRunUp)) {
            System.out.println("IL NUMERO DI DADI DA SALVARE E' STATO RAGGIUNTO");
            this.endOfGame();
            this.exit = true;
        }

        else {
            while (isOK) {
                try {
                    if (this.numberDicesSavedRunUp < 4 && this.longJumpManager.getScore() < 9 && !this.isJumpTime) {
                        System.out.println();
                        System.out.println("Quali dadi vuoi congelare? (inserirli in lettera maiuscola separati da virgola)");
                    }

                    if (this.longJumpManager.getChoices().size() < 5) {

                        this.longJumpManager.freezeDices(this.inputDicesToSave());
                        isOK = false;
                    }
                } catch (WrongDiceToSaveException e) {
                    System.err.println(e.getMessage());

                }
            }
        }
    }

    private void initialize() {
        this.longJumpManager = new LongJumpManager();

        try {
            this.longJumpManager.addPlayer();

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

                }
                is0k = false;

            } catch (WrongInputChoiceDiceException e) {
                System.err.println(e.getMessage());
            }
        }
        if(this.isJumpTime) {
            this.numberDicesSavedJump += result.size();
        }

        if (!this.isJumpTime) {
            this.numberDicesSavedRunUp += result.size();
        }

        return result;
    }
}
