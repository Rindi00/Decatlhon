package it.unimol.decathlon.UI;

import it.unimol.decathlon.app.exceptions.WrongInputChoiceException;
import it.unimol.decathlon.app.manager.ChoiceManager;
import it.unimol.decathlon.app.manager.ManagerGeneralClassific;
import it.unimol.decathlon.app.manager.PlayerManager;

import java.io.*;
import java.util.*;


public class PrincipalScreen {
    private ManagerGeneralClassific managerGeneralClassific;
    private PlayerManager playerManager;


    public PrincipalScreen() {
        try {

            this.managerGeneralClassific = new ManagerGeneralClassific();

        } catch (IOException e) {

            System.err.println("NON RIESCO A LEGGERE IL FILE, ELIMINARLO PER CONTINUARE");

            System.exit(-1);

        } catch (ClassNotFoundException e) {

            System.err.println("NON RIESCO A TROVARE IL FILE");

            System.exit(-1);
        }

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

        int choice;

        if (!this.managerGeneralClassific.getFileExist()) {
            FirstScreen firstScreen = new FirstScreen();
            firstScreen.start();
        }

        do {

            this.gameList();

            choice = this.controlChoice();

            switch (choice) {

                case 1:
                    ScreenHighJump screenHighJump = new ScreenHighJump();
                    screenHighJump.start();
                    ManagerGeneralClassific.highJumpResult();
                    this.printClassific();
                    this.managerGeneralClassific.incrementNumberOfGamesPlayed();

                    break;

                case 2:
                    ScreenMeters400 screenMeters400 = new ScreenMeters400();
                    screenMeters400.start();
                    ManagerGeneralClassific.meters400Result();
                    this.printClassific();
                    this.managerGeneralClassific.incrementNumberOfGamesPlayed();

                    break;

                case 3:
                    Screen110Meters screen110meters = new Screen110Meters();
                    screen110meters.start();
                    ManagerGeneralClassific.meters110Result();
                    this.printClassific();
                    this.managerGeneralClassific.incrementNumberOfGamesPlayed();

                    break;

                case 4:
                    ScreenDiscusThrow screenDiscusThrow = new ScreenDiscusThrow();
                    screenDiscusThrow.start();
                    ManagerGeneralClassific.discusThrowResult();
                    this.printClassific();
                    this.managerGeneralClassific.incrementNumberOfGamesPlayed();

                    break;

                case 5:
                    Screen100Meters screen100meters = new Screen100Meters();
                    screen100meters.start();
                    ManagerGeneralClassific.meters100Result();
                    this.printClassific();
                    this.managerGeneralClassific.incrementNumberOfGamesPlayed();

                    break;

                case 6:
                    ScreenLongJump screenLongJump = new ScreenLongJump();
                    screenLongJump.start();
                    ManagerGeneralClassific.longJumpResult();
                    this.printClassific();
                    this.managerGeneralClassific.incrementNumberOfGamesPlayed();

                    break;

                case 7:
                    ScreenShotPut ScreenshotPut = new ScreenShotPut();
                    ScreenshotPut.start();
                    ManagerGeneralClassific.shotPutResult();
                    this.printClassific();
                    this.managerGeneralClassific.incrementNumberOfGamesPlayed();

                    break;

                case 8:
                    ScreenPoleVault screenPoleVault = new ScreenPoleVault();
                    screenPoleVault.start();
                    ManagerGeneralClassific.poleVaultResult();
                    this.printClassific();
                    this.managerGeneralClassific.incrementNumberOfGamesPlayed();

                    break;

                case 9:
                   ScreenJavelinThrow screenJavelinThrow = new ScreenJavelinThrow();
                   screenJavelinThrow.start();
                   ManagerGeneralClassific.javelinThrowResult();
                   this.printClassific();
                   this.managerGeneralClassific.incrementNumberOfGamesPlayed();

                   break;

                case 10:
                    ScreenMeters1500 screenMeters1500meters = new ScreenMeters1500();
                    screenMeters1500meters.start();
                    ManagerGeneralClassific.meters1500Result();
                    this.printClassific();
                    this.managerGeneralClassific.incrementNumberOfGamesPlayed();

                    break;

                case 0:
                    System.out.println("ARRIVEDERCI");
                    this.managerGeneralClassific.saveScores();
                    this.managerGeneralClassific.saveNumberOfGamesPlayed();

                    break;

                default:
                    System.out.println("HAI DIGITATO UN TASTO SBAGLIATO");

                    break;

            }

        } while ((choice != 0) && (this.managerGeneralClassific.getNumberOfGamesPlayed() != 10));


        if (this.managerGeneralClassific.getNumberOfGamesPlayed() == 10) {

            FinalScreen finalScreen = new FinalScreen();
            finalScreen.start();
        }
    }


    private void gameList() {
        System.out.println("");
        System.out.println("Scegli un gioco");
        System.out.println("");
        System.out.println("**************");
        System.out.println("");
        System.out.println("1) SALTO IN ALTO");
        System.out.println("");
        System.out.println("2) 400 METRI");
        System.out.println("");
        System.out.println("3) 110 METRI OSTACOLI");
        System.out.println("");
        System.out.println("4) LANCIO DEL DISCO");
        System.out.println("");
        System.out.println("5) 100 METRI");
        System.out.println("");
        System.out.println("6) SALTO IN LUNGO");
        System.out.println("");
        System.out.println("7) LANCIO DEL PESO");
        System.out.println("");
        System.out.println("8) SALTO CON L' ASTA");
        System.out.println("");
        System.out.println("9) LANCIO DEL GIAVELLOTTO");
        System.out.println("");
        System.out.println("10) 1500 METRI");
        System.out.println("");
        System.out.println("0) SALVA ED ESCI");

    }


    private void printClassific() {
        System.out.println("######CLASSIFICA TEMPORANEA######");

        ManagerGeneralClassific.getClassific().forEach(name -> {

            System.out.println(name);
        });
    }


    public int controlChoice() {
        int error;

        int a = 0;

        do {

            error = 0;

            try {

                a = ChoiceManager.getMeChoiceNumberGame(this.inputChoice());

            } catch (WrongInputChoiceException e) {

                error = 1;

                System.err.println(e.getMessage());
            }

        } while (error != 0);

        return a;
    }

    private int inputChoice() {
        int output = -1;
        boolean isOK = true;

        while (isOK) {
            try {
                Scanner choice = new Scanner(System.in);
                output = choice.nextInt();

                isOK = false;


            } catch (InputMismatchException e) {
                System.err.println("ERRORE IL CARATTERE DEVE ESSERE UN NUMERO INTERO COMPRESO TRA 0 E 10");
            }
        }
        return output;
    }
}