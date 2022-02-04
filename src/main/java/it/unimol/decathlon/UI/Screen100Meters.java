package it.unimol.decathlon.UI;

import it.unimol.decathlon.UI.abstra.ScreenForGames;
import it.unimol.decathlon.app.exceptions.WrongInputChoiceException;
import it.unimol.decathlon.app.manager.ChoiceManager;
import it.unimol.decathlon.app.manager.Meters100Manager;
import java.io.IOException;
import java.util.Scanner;

public class Screen100Meters implements ScreenForGames {
    private Meters100Manager meters100Manager;
    private int numberOfSlot = 1;
    private int dice1;
    private int dice2;
    private int dice3;
    private int dice4;
    private int slotFreeze;


    public Screen100Meters() {

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

        while (this.meters100Manager.controlTurn()) {

            this.numberOfSlot = 1;

            this.startOfMiniGame();

            while (this.meters100Manager.getMaxDicesNumber() != 0) {

                this.bodyOfMiniGame();

                if (this.meters100Manager.getMaxAttempts() >= 1) {

                    System.out.println("Rilanci ? si/no" + " " + "(" +this.meters100Manager.getMaxAttempts() +  "  " + "rilanci restanti"  +")");

                    String choice = controlChoice();

                    if (choice.equals("no")) {
                        this.meters100Manager.decMaxDicesNumber();
                        this.freezeDices();
                        this.numberOfSlot++;
                    }

                    if (choice.equals("si")) {
                        this.notFreezeDice();
                    }

                }
                else {
                    this.numberOfSlot++;
                    this.meters100Manager.decMaxDicesNumber();
                }


                if (this.meters100Manager.getMaxDicesNumber() == 0) {

                    this.endOfGame();
                }

            }

        }

    }


    public void classific() {
        System.out.println("");

        System.out.println("*****Classifica  100 METRI*****");

        System.out.println("");

        this.meters100Manager.getPlayersScore().forEach(System.out::println);
        System.out.println("");
        System.out.println("IL vincitore alla disciplina 100 METRI è " + " " + this.meters100Manager.getPlayersScore().get(0));
        System.out.println("");
        System.out.println("");
    }

    private void freezeDices() {
        this.slotFreeze = this.numberOfSlot;
        this.dice1 = this.meters100Manager.getDice1Out();
        this.dice2 = this.meters100Manager.getDice2Out();
        this.dice3 = this.meters100Manager.getDice3Out();
        this.dice4 = this.meters100Manager.getDice4Out();
    }

    private void endOfGame() {
        this.meters100Manager.setCurrentPlayer();

        this.meters100Manager.setScore();

        this.meters100Manager.setPlayersScore();

        System.out.println(this.meters100Manager.getActivePlayer() + " " + this.meters100Manager.toString());

        this.meters100Manager.nextTurn();

    }

    private void bodyOfMiniGame() {
        this.meters100Manager.rollDices();

        if (this.numberOfSlot == 2) {
            System.out.println("SLOT" + " " + slotFreeze + ":" + this.dice1 + "," +
                    this.dice2 + "," + this.dice3 + "," + this.dice4 + " " + "(congelato)");
        }

        System.out.println("SLOT " + " " +  this.numberOfSlot + ":" +  " "  + this.meters100Manager.getDice1Out() +
                "," + this.meters100Manager.getDice2Out() + "," + this.meters100Manager.getDice3Out() +
                "," + this.meters100Manager.getDice4Out());
        System.out.println("TOTALE:" + " " + this.meters100Manager.getScore());

        System.out.println("");
    }


    private void notFreezeDice() {
        this.meters100Manager.decMaxAttempts();

        this.meters100Manager.deleteResultNotFreeze();
    }


    private void startOfMiniGame() {
        this.meters100Manager.goGame();

        this.meters100Manager.resetTemporary();

        System.out.println("E' il turno di :  " + this.meters100Manager.getActivePlayer());

        System.out.println("");
    }


    private void initialize() {
        this.meters100Manager = new Meters100Manager();

        try {
            this.meters100Manager.addPlayer();

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
}
