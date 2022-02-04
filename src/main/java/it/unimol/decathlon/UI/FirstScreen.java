package it.unimol.decathlon.UI;

import it.unimol.decathlon.app.exceptions.WrongNamePlayerException;
import it.unimol.decathlon.app.exceptions.WrongNumberPlayerException;
import it.unimol.decathlon.app.manager.ChoiceManager;
import it.unimol.decathlon.app.manager.PlayerManager;
import it.unimol.decathlon.app.player.Player;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FirstScreen {
   private PlayerManager playerManager;
   private  Player player;
   private int numberOfPlayers;


    public FirstScreen() {

        try {

            this.playerManager = new PlayerManager();

        } catch (IOException e) {

            System.err.println("NON RIESCO A LEGGERE IL FILE, ELIMINARLO PER CONTINUARE");

            System.exit(-1);

        } catch (ClassNotFoundException e) {

            System.err.println("NON RIESCO A TROVARE IL FILE");

            System.exit(-1);
        }

    }


    public void start() {
            this.setNumberPlayer();
            this.setNamePlayer();
    }


   private void setNumberPlayer()  {
        int error;

        do {

            error = 0;

            System.out.println("Salve, inserisci il numero di giocatori");

            try {

                int numberOfPlayer = this.numberPlayersInput();
                this.numberOfPlayers = ChoiceManager.getMeChoiceNumberPlayer(numberOfPlayer);

            } catch (WrongNumberPlayerException e) {
                System.err.println(e.getMessage());
                error = 1;

            } catch (NumberFormatException e ) {
                System.err.println(e.getMessage());
                error = 1;
            }

        } while (error != 0);
   }


   private void setNamePlayer() {
        int error = 0;
       int i = 0;
       String name = " ";

       while (error != 1) {

           for (i = 1; i <= this.numberOfPlayers;) {

            System.out.println("Inserisci il nome del giocatore" + " " + i);

            System.out.println("");

            try {

                name = this.inputName();

                ChoiceManager.getMeNamePlayer(name);

                this.player = new Player(name);

                this.playerManager.addPlayer(this.player);

                i++;

              error = 1;

            } catch (WrongNamePlayerException e) {

                System.err.println(e.getMessage());

           error = 0;
            }
        }

        }
       this.playerManager.saveListOfPlayer();
    }


    private int numberPlayersInput() throws NumberFormatException {

        Scanner input = new Scanner(System.in);

        if (input.hasNextInt()) {

            return input.nextInt();
        }
        else {
            throw new NumberFormatException("BISOGNA INSERIRE UN NUMERO INTERO");
        }
    }


    private String inputName() {

        Scanner input = new Scanner(System.in);

        return input.nextLine();

    }

}