package it.unimol.decathlon.app.manager;


import it.unimol.decathlon.app.exceptions.WrongInputChoiceDiceException;
import it.unimol.decathlon.app.exceptions.WrongInputChoiceException;
import it.unimol.decathlon.app.exceptions.WrongNamePlayerException;
import it.unimol.decathlon.app.exceptions.WrongNumberPlayerException;
import java.util.List;


public class ChoiceManager {

    public ChoiceManager() {

    }

    public static String getMeChoice(String choice) throws WrongInputChoiceException {
        String settedChoice = " ";

        if ((choice.equals("si")) || (choice.equals("no"))) {
            settedChoice = choice;

        }  else {

            throw new WrongInputChoiceException("IL CARATTERE DIGITATO NON E' CORRETTO, RIDIGITARE PREGO");
        }

        return settedChoice;
    }


    public static int getMeChoiceNumberGame(int choice) throws WrongInputChoiceException {
        int settedChoice = 0;

        if (( choice >= 0) && (choice <= 10)) {
            settedChoice = choice;

           }
            else {

                throw new WrongInputChoiceException("IL CARATTERE DIGITATO NON E' CORRETTO, RIDIGITARE PREGO");
            }

            return settedChoice;
        }


    public static String getMeChoiceDiceToSaveDiscus(String choice) throws WrongInputChoiceDiceException {
        int isWrongChoice = 0;

        char[] contentChoice = choice.toCharArray();

        char theChoice;

        for (int i = 0; i < contentChoice.length ; i++) {

            theChoice = contentChoice[i];

            if (theChoice == 'A') {
                isWrongChoice++;
            }

            if (theChoice == 'B') {
                isWrongChoice++;
            }

            if (theChoice == 'C') {
                isWrongChoice++;
            }

            if (theChoice == 'D') {
                isWrongChoice++;
            }

            if (theChoice == 'E') {
                isWrongChoice++;
            }

            if (theChoice == ',') {
                isWrongChoice++;
            }
        }

        if (isWrongChoice != contentChoice.length) {

                throw new WrongInputChoiceDiceException("ERRORE CARATTERE NON VALIDO");
            }

       return choice;
    }


    public static String getMeChoiceDiceToSaveJavelin(String choice) throws WrongInputChoiceDiceException {
        int isWrongChoice = 0;

        char[] contentChoice = choice.toCharArray();

        char theChoice;

        for (int i = 0; i < contentChoice.length ; i++) {

            theChoice = contentChoice[i];

            if (theChoice == 'A') {
                isWrongChoice++;
            }

            if (theChoice == 'B') {
                isWrongChoice++;
            }

            if (theChoice == 'C') {
                isWrongChoice++;
            }

            if (theChoice == 'D') {
                isWrongChoice++;
            }

            if (theChoice == 'E') {
                isWrongChoice++;
            }

            if (theChoice == 'F') {
                isWrongChoice++;
            }

            if (theChoice == ',') {
                isWrongChoice++;
            }
        }

        if (isWrongChoice != contentChoice.length) {

            throw new WrongInputChoiceDiceException("ERRORE CARATTERE NON VALIDO");
        }

        return choice;
    }


    public static int getMeChoiceNumberPlayer(Integer choice) throws WrongNumberPlayerException {
        int settedChoice;

        if ((choice > 0) && (choice <= 10)) {

            settedChoice = choice;

        }  else {

            throw new WrongNumberPlayerException("IL NUMERO DEI GIOCATORI INSERITO NON E' CONSENTITO");
        }

        return settedChoice;
    }


    public static String  getMeNamePlayer(String namePlayer) throws WrongNamePlayerException {
        String settedName = " ";

        if (!namePlayer.isEmpty()) {

            settedName = namePlayer;


        }  else {

            throw new WrongNamePlayerException("IL NOME DEVE CONTENERE ALMENO UN CARATTERE");
        }

        return settedName;
    }


    public static void controlNumberPlayer( List players) throws WrongNumberPlayerException {

        if (!players.isEmpty()) {

        } else {

            throw new WrongNumberPlayerException("BISOGNA INSERIRE ALMENO UN GIOCATORE");
        }
    }

}

