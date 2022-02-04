package it.unimol.decathlon.UI;

import it.unimol.decathlon.UI.abstra.ScreenForGames;
import it.unimol.decathlon.app.exceptions.WrongInputChoiceException;
import it.unimol.decathlon.app.manager.ChoiceManager;
import it.unimol.decathlon.app.manager.Meters400Manager;
import java.io.IOException;
import java.util.Scanner;

public class ScreenMeters400 implements ScreenForGames {
        private Meters400Manager meters400Manager;
        private int numberOfSlot;

        public ScreenMeters400() {

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

            while (this.meters400Manager.controlTurn()) {

                this.numberOfSlot = 1;

                this.startOfMiniGame();

                while (this.meters400Manager.getMaxDicesNumber() != 0) {

                    this.bodyOfMiniGame();

                    if (this.meters400Manager.getMaxAttempts() >= 1) {

                        System.out.println("Rilanci ? si/no" + " " + "(" +this.meters400Manager.getMaxAttempts() +  "  " + "rilanci restanti"  +")");

                        String choice = controlChoice();

                        if (choice.equals("no")) {

                            this.meters400Manager.decMaxDicesNumber();

                            this.numberOfSlot++;
                        }

                        if (choice.equals("si")) {
                            this.notFreezeDice();
                        }

                    }
                    else  {


                        this.numberOfSlot++;
                        this.meters400Manager.decMaxDicesNumber();
                    }


                    if (this.meters400Manager.getMaxDicesNumber() == 0) {

                        this.endOfGame();
                    }

                }

            }
        }


        public void classific() {
            System.out.println("");

            System.out.println("*****Classifica  400 METRI*****");

            System.out.println("");

            this.meters400Manager.getPlayersScore().forEach(System.out::println);
            System.out.println("");
            System.out.println("IL vincitore alla disciplina 400 METRI è " + " " + this.meters400Manager.getPlayersScore().get(0));
            System.out.println("");
            System.out.println("");
        }


        private void endOfGame() {
            this.meters400Manager.setCurrentPlayer();

            this.meters400Manager.setScore();

            this.meters400Manager.setPlayersScore();

            System.out.println(this.meters400Manager.getActivePlayer() + " " + this.meters400Manager.toString());

            System.out.println("");

            this.meters400Manager.nextTurn();
        }

        private void bodyOfMiniGame() {
            System.out.println("SLOT " + " " +  this.numberOfSlot + ":" +  " " + "TOTALE SLOT" + " " + this.numberOfSlot +
                    " : " + " " + this.meters400Manager.rollDices());

            System.out.println("DADI" +   " " + this.meters400Manager.getDice1Out() + "," + this.meters400Manager.getDice2Out());

            System.out.println("TOTALE:" + " " + this.meters400Manager.getScore());

            System.out.println("");

        }


        private void notFreezeDice() {
            this.meters400Manager.decMaxAttempts();

            this.meters400Manager.deleteResultNotFreeze();
        }


        private void startOfMiniGame() {
            this.meters400Manager.goGame();

            this.meters400Manager.resetTemporary();

            System.out.println("E' il turno di :  " + this.meters400Manager.getActivePlayer());

            System.out.println("");
        }



        private void initialize() {
            this.meters400Manager = new Meters400Manager();

            System.out.println("");

            try {
                this.meters400Manager.addPlayer();

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
