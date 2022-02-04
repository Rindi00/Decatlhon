package it.unimol.decathlon.UI;

import it.unimol.decathlon.UI.abstra.ScreenForGames;
import it.unimol.decathlon.app.exceptions.WrongInputChoiceException;
import it.unimol.decathlon.app.manager.ChoiceManager;
import it.unimol.decathlon.app.manager.Meters110Manager;
import java.io.IOException;
import java.util.Scanner;

public class Screen110Meters implements ScreenForGames{
        private Meters110Manager meters;


        public Screen110Meters() {

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

            while (this.meters.controlTurn()) {

                this.startOfMiniGame();

                int raises = 7;

                condition = true;

                while ((condition ) && (raises != 0)) {

                    this.bodyOfMiniGame();

                    raises--;


                    if (this.meters.getMaxAttempts() >= 1) {

                        int futureMalus = this.meters.getMalus()+1;

                        System.out.print((" Rilanci ? (Verrà applicato un malus di " + " " + futureMalus + " " + "punti)") + "  ");
                        System.out.println("( si/no" +  ")");

                        String choice = controlChoice();

                              if (choice.equals("no")) {
                                  condition = false;

                                   this.endOfMiniGame();
                             }

                        if (choice.equals("si")) {

                            this.raises();

                        }

                    }

                      if (raises  <= 0) {

                          this.endOfMiniGame();
                     }
                }
            }
        }


        public  void classific() {
            System.out.println("");

            System.out.println("*****Classifica  110 METRI*****");

            System.out.println("");

            this.meters.getPlayersScore().forEach(System.out::println);
            System.out.println("");
            System.out.println("IL vincitore alla disciplina 110 METRI è " + " " + this.meters.getPlayersScore().get(0));
            System.out.println("");
            System.out.println("");
        }


    private void endOfMiniGame() {
        this.meters.setCurrentPlayer();

        this.meters.setScore();

        this.meters.setPlayersScore();

        System.out.println(this.meters.getActivePlayer() + " " + this.meters.toString());

        this.meters.resetMalus();

        this.meters.nextTurn();
    }


    private void bodyOfMiniGame() {
        this.meters.rollDices();

        System.out.print("(" + this.meters.getScore() + " " + "punti" + " " + "|" + " " + this.meters.getMalus() + " " +
                              "malus" + " " + "|" + " " + "totale" + " " + this.meters.getScoreWithMalus() + "" + ")");

        System.out.println("");

        System.out.print("DADI :" + " " + this.meters.getDices());

        System.out.println("");
    }


    private void raises() {
        this.meters.decMaxAttempts();

        this.meters.incrementMalus();
    }


    private void startOfMiniGame(){
        this.meters.goGame();

        System.out.println("E' il turno di :  " + this.meters.getActivePlayer());

        System.out.println("");
    }


    public String controlChoice() {
        int error;

        String a = " ";

        do {

            error = 0;

            try {

                a =  ChoiceManager.getMeChoice(this.inputChoice());

            }

            catch(WrongInputChoiceException e ) {

                error = 1;

                System.err.println(e.getMessage());
            }

        }  while (error != 0);

        return a;
    }


    private void initialize() {
        this.meters = new Meters110Manager();

        try {
            this.meters.addPlayer();

        } catch (IOException e) {
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

}
