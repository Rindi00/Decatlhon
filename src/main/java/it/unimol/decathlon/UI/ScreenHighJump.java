package it.unimol.decathlon.UI;

import it.unimol.decathlon.app.exceptions.WrongInputChoiceException;
import it.unimol.decathlon.app.exceptions.WrongHighException;
import it.unimol.decathlon.app.manager.ChoiceManager;
import it.unimol.decathlon.app.manager.HighJumpManager;
import it.unimol.decathlon.UI.abstra.ScreenForGames;
import java.io.IOException;
import java.util.Scanner;


public class ScreenHighJump implements ScreenForGames {
    private HighJumpManager highJumpManager;


    public ScreenHighJump() {

    }


    public void start() {
        int time = 1;

        while (time != 0) {

            System.out.println("");

            letPlay();

            classific();

            time = 0;
        }
    }


    public void letPlay() {
        this.initialize();

        boolean condition;


        while (this.highJumpManager.controlTurn()) {

            this.startOfMiniGame();

            this.setHigh();

            System.out.println("");

            condition = true;

            while ((condition) && (this.highJumpManager.getMaxAttempts() != 0)) {

                this.bodyOfMiniGame();

                if (this.highJumpManager.getMaxAttempts() >= 1) {

                    System.out.println("Vuoi rilanciare ? si/no" + "(tenativi" + "  " + this.highJumpManager.getMaxAttempts() + ")");

                    String choice = controlChoice();

                    if (choice.equals("no")) {
                        condition = false;

                        this.endOfMiniGame();
                    }

                } else {

                    this.endOfMiniGame();
                }
            }
        }
    }


    public  void classific() {
        System.out.println("");

        System.out.println("*****Classifica  SALTO IN ALTO*****");

        System.out.println("");

        this.highJumpManager.getPlayersScore().forEach(System.out::println);
        System.out.println("");
        System.out.println("IL vincitore alla disciplina SALTO IN ALTO è " + " " + this.highJumpManager.getPlayersScore().get(0));
        System.out.println("");
        System.out.println("");
    }


    private void endOfMiniGame() {
        this.highJumpManager.setCurrentPlayer();

        this.highJumpManager.setScore();

        this.highJumpManager.setPlayersScore();

        System.out.println( this.highJumpManager.getActivePlayer() + " " + this.highJumpManager.toString());

        System.out.println("");

        this.highJumpManager.nextTurn();

    }


    private void bodyOfMiniGame() {
        System.out.println("RISULTATO: " + this.highJumpManager.rollDices());

        this.highJumpManager.decMaxAttempts();

        System.out.println("");

        System.out.println("DADI USCITI:" + " " + this.highJumpManager.getDices());

        System.out.println("");
    }


    private void startOfMiniGame() {
        this.highJumpManager.goGame();

        System.out.println("E' il turno di :  " + this.highJumpManager.getActivePlayer());

        System.out.println("");
    }


    private void initialize() {
        this.highJumpManager  = new HighJumpManager();

        try {
            this.highJumpManager.addPlayer();

        } catch (IOException e ){
            System.err.println("NON RIESCO AD APRIRE IL FILE, ELIMINARLO PER CONTINUARE");
            System.exit(-1);

        } catch (ClassNotFoundException e) {
            System.err.println("NON RIESCO A TROVARE IL FILE, ELIMINARLO PER CONTINUARE");
            System.exit(-1);
        }
    }


    private void setHigh() {
        int error;

        do {
            error = 1;

            System.out.println("Inserisci l' altezza che vuoi raggiungere    (tra 5 e 30)");

            try {

                int high = this.highInput();

                this.highJumpManager.setHigh(high);

                System.out.println("");

                System.out.println("Altezza inserita correttamente");


            } catch (WrongHighException e) {
                error = 0;

                System.err.println("ALTEZZA NON CONSENTITA, " + "  " + e.getMessage());

            } catch (NumberFormatException e) {
                error = 0;
                System.err.println(e.getMessage());
            }

        } while (error != 1);
    }


    private  int highInput() throws NumberFormatException {
        Scanner high = new Scanner(System.in);

        if (high.hasNextInt()) {

            return high.nextInt();
        }
        else {
            throw new NumberFormatException("BISOGNA INSERIRE UN NUMERO INTERO");
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

                choice = ChoiceManager.getMeChoice(this.inputChoice());

            }

            catch(WrongInputChoiceException e ){

                error = 1;

                System.err.println(e.getMessage());
            }

        }  while (error != 0);

        return choice;
    }

}
