package it.unimol.decathlon.UI;

import it.unimol.decathlon.app.exceptions.WrongInputChoiceException;
import it.unimol.decathlon.app.manager.ChoiceManager;
import it.unimol.decathlon.app.manager.Meters1500Manager;
import it.unimol.decathlon.UI.abstra.ScreenForGames;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;


public class ScreenMeters1500 implements ScreenForGames {
    private Meters1500Manager meters;
    private List<Integer> freezeDices = new ArrayList<>();

    public ScreenMeters1500() {

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

        while (this.meters.controlTurn()) {

            int numberOfDice = 1;

            this.startOfMiniGame();

            this.freezeDices.clear();

            while (this.meters.getMaxDicesNumber() != 0) {

                System.out.println("DADO " + " " +  numberOfDice + ":" + " " + this.meters.rollDices());

                System.out.println("");

                this.printFreezeValue();

                System.out.println("");

                System.out.println("TOTALE:" + " " + this.meters.getScore());

                System.out.println("");

                if (this.meters.getMaxAttempts() >= 1) {

                    System.out.println("Vuoi rilanciare ? si/no" + "(tenativi" + "  " + this.meters.getMaxAttempts() + ")");

                    String choice = controlChoice();

                    if (choice.equals("no")) {

                        this.freezeDice();

                        numberOfDice++;
                    }

                    if (choice.equals("si")) {
                        this.notFreezeDice();
                    }

                }
                    else {
                        this.freezeDice();

                        numberOfDice++;
                    }


              if (this.meters.getMaxDicesNumber() == 0) {

                  this.endOfGame();
              }
            }
        }
    }


    private void printFreezeValue() {
        int numberDice = 1;

        for (Integer value : this.freezeDices){

            System.out.println((numberDice + ":" + " " + value + " " + "congelato"));

            numberDice++;
        }
    }

    public void classific() {
        System.out.println("");

        System.out.println("*****Classifica  1500 METRI*****");

        System.out.println("");

        this.meters.getPlayersScore().forEach(System.out::println);
        System.out.println("");
        System.out.println("IL vincitore alla disciplina 1500 METRI è " + " " + this.meters.getPlayersScore().get(0));
        System.out.println("");
        System.out.println("");
    }


    private void endOfGame() {
        this.meters.setCurrentPlayer();

        this.meters.setScore();

        this.meters.setPlayersScore();

        System.out.println(this.meters.getActivePlayer() + " " + this.meters.toString());

        System.out.println("");

        this.meters.nextTurn();

    }


    private void freezeDice() {
        this.freezeDices.add(this.meters.getDiceOut());

        System.out.println("");

        this.meters.decMaxDicesNumber();
    }


    private void notFreezeDice() {
        this.meters.decMaxAttempts();

        this.meters.deleteResultNotFreeze();
    }


    private void startOfMiniGame() {
        this.meters.goGame();

        this.meters.resetTemporary();

        System.out.println("E' il turno di :  " + this.meters.getActivePlayer());

        System.out.println("");
    }


    private void initialize() {
        this.meters = new Meters1500Manager();

        System.out.println("");

        try {
            this.meters.addPlayer();

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
