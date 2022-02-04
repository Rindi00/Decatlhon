package it.unimol.decathlon.UI;

import it.unimol.decathlon.app.exceptions.WrongInputChoiceException;
import it.unimol.decathlon.app.exceptions.WrongHighException;
import it.unimol.decathlon.app.exceptions.WrongNumberDiceException;
import it.unimol.decathlon.app.manager.ChoiceManager;
import it.unimol.decathlon.app.manager.PoleVaultManager;
import it.unimol.decathlon.UI.abstra.ScreenForGames;
import java.io.IOException;
import java.util.Scanner;


public class ScreenPoleVault implements ScreenForGames {
    private PoleVaultManager poleVaultManager;


    public ScreenPoleVault() {

    }


    public void start() {
        int time = 1;

        while(time != 0) {

            System.out.println("");

            letPlay();

            classific();

            time = 0;

        }
    }


    public  void letPlay() {
        this.initialize();

        boolean condition;

        int result;

        while (this.poleVaultManager.controlTurn()) {

            this.startOfMiniGame();

             this.setHigh();

             this.setNumberDices();

            condition = true;

            while ((condition) && (this.poleVaultManager.getMaxAttempts() != 0)) {

                result =  this.poleVaultManager.rollDices();

                if (!this.poleVaultManager.controlNumberDices()){

                   this.penalty();
                }

                else {

                    System.out.println("RISULTATO:" + result);
                }

                   this.bodyOfMiniGame();

                if (this.poleVaultManager.getMaxAttempts() >= 1) {

                    System.out.println("Vuoi rilanciare ? si/no"  +  "(tenativi" +  "  "  + this.poleVaultManager.getMaxAttempts() + ")" );

                    String choice = controlChoice();

                    if (choice.equals("no")) {
                        condition = false;

                        this.endOfMiniGame();
                    }

                }

                else {

                    this.endOfMiniGame();
                }

            }

        }

    }


    public  void classific() {
        System.out.println("");

        System.out.println("*****CLASSIFICA SALTO  CON L' ASTA*****");

        System.out.println("");

        this.poleVaultManager.getPlayersScore().forEach(System.out::println);
        System.out.println("");
        System.out.println("IL vincitore alla disciplina SALTO CON L' ASTA  è " + " " + this.poleVaultManager.getPlayersScore().get(0));
        System.out.println("");
        System.out.println("");
    }


    private void endOfMiniGame() {
        this.poleVaultManager.setCurrentPlayer();

        this.poleVaultManager.setScore();

        this.poleVaultManager.setPlayersScore();

        System.out.println( this.poleVaultManager.getActivePlayer() + " " + this.poleVaultManager.toString());

        System.out.println("");

        this.poleVaultManager.nextTurn();
    }


    private void bodyOfMiniGame() {
        this.poleVaultManager.decMaxAttempts();

        System.out.println("");

        System.out.println("DADI USCITI:" + " " + this.poleVaultManager.getDices());

        System.out.println("");
    }


    private void penalty() {
        System.out.println("");

        System.out.println("TENTATIVO NULLO");

        System.out.println("");
    }


    private void startOfMiniGame() {
        this.poleVaultManager.goGame();

        System.out.println("E' il turno di :  " + this.poleVaultManager.getActivePlayer());

        System.out.println("");

    }


    private void initialize() {
        this.poleVaultManager  = new PoleVaultManager();

        System.out.println("");

        try {
            this.poleVaultManager.addPlayer();

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

            System.out.println("Inserisci l' altezza che vuoi raggiungere    (tra 8 e 48)");

            try {

                this.poleVaultManager.setHigh(this.highInput());

                System.out.println("");

                System.out.println("Altezza inserita correttamente");



            } catch (WrongHighException e) {
                error = 0;

                System.err.println("ALTEZZA NON CONSENTITA, " + "  " + e.getMessage());

            } catch (NumberFormatException e) {
                error = 0;
                System.err.println(e.getMessage());
            }

        }while (error != 1);


    }


    private void setNumberDices() {
        int error;

        do {
            error = 0;

            System.out.println("Inserisci il numero di dadi che vuoi lanciare (tra 2 e 8)");

            try {

                this.poleVaultManager.setNumberDices(this.dicesInput());

                System.out.println("Numero di dadi inserito correttamente");

            } catch (WrongNumberDiceException e) {

                error= 1;

                System.err.println(e.getMessage());

            } catch (NumberFormatException e) {

                System.err.println(e.getMessage());
                error = 1;
            }

        } while (error != 0);
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


    private int dicesInput() throws NumberFormatException {

        Scanner dices = new Scanner(System.in);

        if ( dices.hasNextInt()) {

            return dices.nextInt();
        }
        else {
            throw new NumberFormatException("BISOGNA INSERIRE UN NUMERO INTERO");
        }
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
