package it.unimol.decathlon.UI;

import it.unimol.decathlon.UI.abstra.ScreenForGames;
import it.unimol.decathlon.app.exceptions.WrongInputChoiceException;
import it.unimol.decathlon.app.manager.ChoiceManager;
import it.unimol.decathlon.app.manager.ShotPutManager;
import java.io.IOException;
import java.util.Scanner;

public class ScreenShotPut implements ScreenForGames {
    private ShotPutManager shotPut;



    public ScreenShotPut() {

    }


    public void start() {
        int time = 1;

        while(time != 0) {

            letPlay();

            classific();

            time = 0;
        }

    }


    public  void letPlay() {

        this.initialize();

        boolean condition;

        String choice = " ";

        while (this.shotPut.controlTurn()) {

            int attempt = 1;

            this.startOfMiniGame();

            condition = true;

            while ((condition) && (this.shotPut.getMaxAttempts() != 0) && (this.shotPut.getMaxDicesNumber() != 0)) {


                System.out.println("TENTATIVO " + " " + attempt);

                 this.shotPut.rollDices();

                if (!this.shotPut.controlNumberDices()){

                    this.penalty();

                    attempt++;
                }

                else {

                    this.bodyOfMiniGame();

                }

                    if ((this.shotPut.getMaxDicesNumber() >= 1) && (this.shotPut.getMaxAttempts() > 0)) {

                        System.out.println("Vuoi continuare ? (si/no)" );

                        choice = controlChoice();
                    }


                    if (choice.equals("no")) {
                        condition = false;

                       this.endOfMiniGame();

                    }


                    if ((this.shotPut.getMaxDicesNumber() <= 0) || (this.shotPut.getMaxAttempts() <= 0)){

                    this.endOfMiniGame();

                }
            }
        }
    }




    public  void classific() {
        System.out.println("");

        System.out.println("*****CLASSIFICA LANCIO DEL PESO*****");

        System.out.println("");

        this.shotPut.getPlayersScore().forEach(System.out::println);
        System.out.println("");
        System.out.println("IL vincitore alla disciplina LANCIO DEL PESO è " + " " + this.shotPut.getPlayersScore().get(0));
        System.out.println("");
        System.out.println("");
    }


    private void endOfMiniGame() {
        this.shotPut.setCurrentPlayer();

        this.shotPut.setScore();

        this.shotPut.setPlayersScore();

        System.out.println(this.shotPut.getActivePlayer() + " " + this.shotPut.toString());

        System.out.println("");

        this.shotPut.resetResults();

        this.shotPut.nextTurn();
    }


    private void bodyOfMiniGame() {
        System.out.println("");

        System.out.println("DADI:" + " " + this.shotPut.getDices());

        System.out.println("TOTALE:" + "  " + this.shotPut.getTotal());

        this.shotPut.decMaxDicesNumber();

        System.out.println("");

    }


    private void penalty() {
        System.out.println("");

        System.out.println("TENTATIVO ANNULLATO");

        System.out.println("");

        this.shotPut.resetResults();

        this.shotPut.decMaxAttempts();

        this.shotPut.resetMaxDicesNumber();
    }


    private void startOfMiniGame() {
        this.shotPut.goGame();

        System.out.println("E' il turno di :  " + this.shotPut.getActivePlayer());

        System.out.println("");
    }



    private void initialize() {
        this.shotPut = new ShotPutManager();

        System.out.println("");

        try {
            this.shotPut.addPlayer();

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
