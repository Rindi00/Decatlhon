package it.unimol.decathlon.GUI.javelinThrow;

import it.unimol.decathlon.GUI.counter.Counter;
import it.unimol.decathlon.GUI.mainScreen.MainFrame;
import it.unimol.decathlon.GUI.principalScreen.PanelPrincipalScreen;
import it.unimol.decathlon.app.exceptions.WrongDiceToSaveException;
import it.unimol.decathlon.app.exceptions.WrongInputChoiceDiceException;
import it.unimol.decathlon.app.manager.ChoiceManager;
import it.unimol.decathlon.app.manager.JavelinThrowManager;
import it.unimol.decathlon.app.manager.ManagerGeneralClassific;
import javax.swing.*;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class JavelinThrowPanel extends JPanel {
    private JavelinThrowManager javelinThrowManager;

    private JPanel classificMiniGame;
    private JPanel heartOfGame;
    private JPanel result;
    private JPanel timePanel;
    private JPanel saveDicesPanel;
    private JButton saveDices;
    private JButton nextTurn;
    private JButton finishTurn;
    private JButton classific;
    private JButton savingDices;
    private JButton exit;
    private JTextField saveDicesField;
    private ManagerGeneralClassific managerGeneralClassific;
    private Counter counter;
    private Thread t1;
    private int turn = 0;
    private static int numberDiceNotSaved = 0;
    private String dicesSaved = "";


    public JavelinThrowPanel() {
        this.javelinThrowManager = new JavelinThrowManager();

        try {

            this.managerGeneralClassific = new ManagerGeneralClassific();

        } catch (IOException e) {

            JOptionPane.showMessageDialog(this, "NON RIESCO A LEGGERE IL FILE, ELIMINARLO PER CONTINUARE");

            System.exit(-1);

        } catch (ClassNotFoundException e) {

            JOptionPane.showMessageDialog(this, "NON RIESCO A TROVARE IL FILE");

            System.exit(-1);
        }

        this.initialize();
        this.startOfMiniGame();


        this.saveDices = new JButton("SALVA DADI");
        this.saveDices.setLocation(200, 200);
        this.add(this.saveDices);
        this.saveDices.addActionListener(e -> this.handleClickOnSaveDices());


        this.finishTurn = new JButton("TERMINA TURNO");
        this.finishTurn.setLocation(200, 200);
        this.add(this.finishTurn);
        this.finishTurn.addActionListener(e -> this.handleClickOnFinishTurn());


        this.nextTurn = new JButton("VAI AL PROSSIMO TURNO");
        this.nextTurn.setLocation(200, 200);
        this.add(this.nextTurn);
        this.nextTurn.addActionListener(e -> this.handleClickOnNextTurn());


        this.classific = new JButton("VAI ALLA CLASSIFICA");
        this.classific.setLocation(200, 200);
        this.add(this.classific);
        this.classific.addActionListener(e -> this.handleClickOnClassific());

        this.bodyOfMiniGame();

    }


    private void handleClickOnClassific() {
        if (!this.javelinThrowManager.controlTurn() && this.javelinThrowManager.getPlayersScore().size() == this.turn) {
            this.timePanel.removeAll();
            this.result.removeAll();
            this.classificMiniGame = new JPanel();
            this.classificMiniGame.add(new JLabel("*****Classifica  LANCIO DEL GIAVELLOTTO*****"));
            this.classificMiniGame.add(new JLabel(String.valueOf(this.javelinThrowManager.getPlayersScore())));
            this.classificMiniGame.add(new JLabel("IL vincitore alla disciplina LANCIO DEL GIAVELLOTTO è " +
                                                              " " + this.javelinThrowManager.getPlayersScore().get(0)));

            ManagerGeneralClassific.javelinThrowResult();
            PanelPrincipalScreen.setOkClassific(true);

            this.add(this.classificMiniGame);
            this.revalidate();
            this.classific.setEnabled(false);

            this.exit = new JButton("ESCI");
            this.add(this.exit);
            this.revalidate();
            this.exit.addActionListener(e -> handleClickOnExit());


        } else {
            JOptionPane.showMessageDialog(this, "PER GUARDARE LA CLASSIFICA DEVONO GIOCARE TUTTI");
        }
    }


    private void handleClickOnExit() {
        this.managerGeneralClassific.incrementNumberOfGamesPlayed();
        this.managerGeneralClassific.saveScores();
        this.managerGeneralClassific.saveNumberOfGamesPlayed();
        Window window = SwingUtilities.getWindowAncestor(this);
        window.dispose();
        MainFrame frame = new MainFrame();
        frame.setVisible(false);
    }


    private void handleClickOnNextTurn() {

        if (this.javelinThrowManager.controlTurn()) {
            this.counter.removeAll();
            this.counter.reset();
            this.javelinThrowManager.nextTurn();

            this.turn++;

            this.heartOfGame.removeAll();

            if (this.javelinThrowManager.controlTurn()) {
                this.t1.interrupt();
                this.javelinThrowManager.initializeResult();
                this.javelinThrowManager.resetChoice();
               JavelinThrowManager.resetNumberDicesEven();
               this.javelinThrowManager.resetTemporary();
                numberDiceNotSaved = 0;
                this.javelinThrowManager.goGame();
                this.result.removeAll();
                this.bodyOfMiniGame();
                this.timePanel.removeAll();
                this.finishTurn.setEnabled(true);
                this.saveDices.setEnabled(true);
            }

        } else {
            JOptionPane.showMessageDialog(this, "NON CI SONO ALTRI GIOCATORI");
        }
    }


    private void handleClickOnFinishTurn() {
        this.counter.stop();

        this.javelinThrowManager.setCurrentPlayer();

        this.javelinThrowManager.setScore();

        this.javelinThrowManager.setPlayersScore();

        this.result = new JPanel();
        this.result.add(new JLabel(this.javelinThrowManager.getActivePlayer() + " " + this.javelinThrowManager.toString()));
        this.add(result);
        this.result.revalidate();

        this.heartOfGame.removeAll();
        this.finishTurn.setEnabled(false);
        this.saveDicesPanel.removeAll();
        this.saveDices.setEnabled(false);
        this.nextTurn.setEnabled(true);
    }


    private void handleClickOnSaveDices() {

        this.nextTurn.setEnabled(false);

        this.revalidate();

        if ((JavelinThrowManager.getNumberDicesEven() > 0) && (JavelinThrowManager.getNumberDicesEven() == numberDiceNotSaved)) {
            JOptionPane.showMessageDialog(this, "NON PUOI SALVARE DADI TENTATIVO ANNULLATO");
            this.handleClickOnFinishTurn();

        }
        else if (numberDiceNotSaved == 0) {
            JOptionPane.showMessageDialog(this, "IL NUMERO DI DADI DA SALVARE E' STATO RAGGIUNTO");
            this.handleClickOnFinishTurn();
        }
        else {
            this.inputDices();
        }
    }


    private void continueBodyOfMiniGame() {
        numberDiceNotSaved = 0;
        JavelinThrowManager.resetNumberDicesEven();
        this.saveDicesPanel.removeAll();

        this.heartOfGame.removeAll();

        this.heartOfGame.add(new JLabel("E' il turno di :  " + this.javelinThrowManager.getActivePlayer()));

        this.heartOfGame.add((new JLabel("DADI")));

        if (this.javelinThrowManager.diceSavedControl("A")) {
            this.heartOfGame.add(new JLabel("A:" + " " + this.javelinThrowManager.getDiceAOut()));
            numberDiceNotSaved++;

        } else {
            this.heartOfGame.add(new JLabel("A:" + " " + this.javelinThrowManager.getDiceSaved("A") + " " + "(congelato)"));
        }

        if (this.javelinThrowManager.diceSavedControl("B")) {
            this.heartOfGame.add(new JLabel("B:" + " " + this.javelinThrowManager.getDiceBOut()));
            numberDiceNotSaved++;

        } else {
            this.heartOfGame.add(new JLabel("B:" + " " + this.javelinThrowManager.getDiceSaved("B") + " " + "(congelato)"));

        }

        if (this.javelinThrowManager.diceSavedControl("C")) {
            this.heartOfGame.add(new JLabel("C:" + " " + this.javelinThrowManager.getDiceCOut()));
            numberDiceNotSaved++;

        } else {
            this.heartOfGame.add(new JLabel("C:" + " " + this.javelinThrowManager.getDiceSaved("C") + " " + "(congelato)"));
        }

        if (this.javelinThrowManager.diceSavedControl("D")) {
            this.heartOfGame.add(new JLabel("D:" + " " + this.javelinThrowManager.getDiceDOut()));
            numberDiceNotSaved++;

        } else {
            this.heartOfGame.add(new JLabel("D:" + " " + this.javelinThrowManager.getDiceSaved("D") + " " + "(congelato)"));
        }

        if (this.javelinThrowManager.diceSavedControl("E")) {
            this.heartOfGame.add(new JLabel("E:" + " " + this.javelinThrowManager.getDiceEOut()));
            numberDiceNotSaved++;

        } else {
            this.heartOfGame.add(new JLabel("E:" + " " + this.javelinThrowManager.getDiceSaved("E") + " " + "(congelato)"));
        }

        if (this.javelinThrowManager.diceSavedControl("F")) {
            this.heartOfGame.add(new JLabel("F:" + " " + this.javelinThrowManager.getDiceFOut()));
            numberDiceNotSaved++;

        } else {
            this.heartOfGame.add(new JLabel("F:" + " " + this.javelinThrowManager.getDiceSaved("F") + " " + "(congelato)"));
        }

        this.heartOfGame.setLayout(new BoxLayout(this.heartOfGame, BoxLayout.Y_AXIS));

        this.add(heartOfGame);

        this.heartOfGame.revalidate();

        this.saveDices.setEnabled(true);

        this.finishTurn.setEnabled(true);

        this.situation();
    }


    private void bodyOfMiniGame() {

        t1 = new Thread(() -> {
            try {
                this.saveDicesPanel = new JPanel();

                this.counter = new Counter();
                this.timePanel = new JPanel();
                this.timePanel.add(new JLabel("TEMPO"));
                this.timePanel.add(this.counter);
                this.counter.start();
                this.timePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                this.add(this.timePanel);


                this.revalidate();

                this.heartOfGame = new JPanel();

                this.heartOfGame.add(new JLabel("E' il turno di :  " + this.javelinThrowManager.getActivePlayer()));

                this.heartOfGame.add((new JLabel("DADI")));

                if (this.javelinThrowManager.diceSavedControl("A")) {
                    this.heartOfGame.add(new JLabel("A:" + " " + this.javelinThrowManager.getDiceAOut()));
                    numberDiceNotSaved++;

                } else {
                    this.heartOfGame.add(new JLabel("A:" + " " + this.javelinThrowManager.getDiceSaved("A") + " " + "(congelato)"));
                }

                if (this.javelinThrowManager.diceSavedControl("B")) {
                    this.heartOfGame.add(new JLabel("B:" + " " + this.javelinThrowManager.getDiceBOut()));
                    numberDiceNotSaved++;

                } else {
                    this.heartOfGame.add(new JLabel("B:" + " " + this.javelinThrowManager.getDiceSaved("B") + " " + "(congelato)"));

                }

                if (this.javelinThrowManager.diceSavedControl("C")) {
                    this.heartOfGame.add(new JLabel("C:" + " " + this.javelinThrowManager.getDiceCOut()));
                    numberDiceNotSaved++;

                } else {
                    this.heartOfGame.add(new JLabel("C:" + " " + this.javelinThrowManager.getDiceSaved("C") + " " + "(congelato)"));
                }

                if (this.javelinThrowManager.diceSavedControl("D")) {
                    this.heartOfGame.add(new JLabel("D:" + " " + this.javelinThrowManager.getDiceDOut()));
                    numberDiceNotSaved++;

                } else {
                    this.heartOfGame.add(new JLabel("D:" + " " + this.javelinThrowManager.getDiceSaved("D") + " " + "(congelato)"));
                }

                if (this.javelinThrowManager.diceSavedControl("E")) {
                    this.heartOfGame.add(new JLabel("E:" + " " + this.javelinThrowManager.getDiceEOut()));
                    numberDiceNotSaved++;

                } else {
                    this.heartOfGame.add(new JLabel("E:" + " " + this.javelinThrowManager.getDiceSaved("E") + " " + "(congelato)"));
                }

                if (this.javelinThrowManager.diceSavedControl("F")) {
                    this.heartOfGame.add(new JLabel("F:" + " " + this.javelinThrowManager.getDiceFOut()));
                    numberDiceNotSaved++;

                } else {
                    this.heartOfGame.add(new JLabel("F:" + " " + this.javelinThrowManager.getDiceSaved("F") + " " + "(congelato)"));
                }

                this.heartOfGame.setLayout(new BoxLayout(this.heartOfGame, BoxLayout.Y_AXIS));

                this.add(heartOfGame);

                this.heartOfGame.revalidate();

                this.nextTurn.setEnabled(false);

                this.finishTurn.setEnabled(false);

                Thread.sleep(180000);

                this.counter.stop();

                if (!this.t1.isInterrupted()) {
                    JOptionPane.showMessageDialog(this, "TEMPO SCADUTO");
                    this.saveDices.setEnabled(false);
                    this.finishTurn.setEnabled(true);
                }

            } catch (InterruptedException ignored) {
            }

        });

        this.t1.start();
    }


    private void startOfMiniGame() {
        this.javelinThrowManager.goGame();
        this.javelinThrowManager.initializeResult();
    }


    private void inputDices() {
        this.saveDicesPanel.removeAll();
        this.saveDicesPanel.add(new JLabel("Quali dadi vuoi congelare? (inserirli in lettera maiuscola separati da virgola)"));
        this.saveDicesField = new JTextField(20);
        this.saveDicesPanel.add(this.saveDicesField);
        this.saveDicesPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.saveDices.setEnabled(false);


        this.savingDices = new JButton("CONFERMA I DADI DA SALVARE");
        this.saveDicesPanel.add(this.savingDices);
        this.add(this.saveDicesPanel);
        this.savingDices.addActionListener(e -> this.inputDicesToSave());
    }


    private void inputDicesToSave() {
        Set<String> result = new HashSet<>();
        boolean isOk;

        try {
            String diceToSaveString = " ";
            diceToSaveString = ChoiceManager.getMeChoiceDiceToSaveJavelin(this.saveDicesField.getText());

            String[] dicesToSaveArray = diceToSaveString.split(",");
            if (diceToSaveString.length() > 0 && dicesToSaveArray.length == 0) {
                dicesToSaveArray = new String[diceToSaveString.length() + 1];
                for (int i = 0; i < dicesToSaveArray.length; i++) {
                    dicesToSaveArray[i] = "";
                }
            }
            for (String dice : dicesToSaveArray) {
                result.add(dice);
                this.javelinThrowManager.decMaxDicesNumber();
            }
            isOk = true;

        }
        catch (WrongInputChoiceDiceException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
            isOk = false;

        }

        if (isOk) {
            try {

                this.javelinThrowManager.freezeDices(result);

                isOk = true;

            } catch (WrongDiceToSaveException e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
                isOk = false;

            }
        }

        if (isOk) {

            this.continueBodyOfMiniGame();
        }
    }


    public static boolean isAllDicesEven() {
        return ((JavelinThrowManager.getNumberDicesEven() > 0) && (JavelinThrowManager.getNumberDicesEven() == numberDiceNotSaved));
    }

    public void situation() {
        if (this.javelinThrowManager.getScore() != 0 && JavelinThrowManager.getNumberDicesEven() != numberDiceNotSaved) {
            JOptionPane.showMessageDialog(this,"HAI:" + " " + this.javelinThrowManager.getScore() + " " + "PUNTI" + " ");

        }

        if (this.javelinThrowManager.getScore() == 0) {
            JOptionPane.showMessageDialog(this,"TENTATIVO ANNULLATO");
            this.handleClickOnFinishTurn();
        }
    }


    private void initialize() {

        try {
            this.javelinThrowManager.addPlayer();

        } catch (IOException e ){
            JOptionPane.showMessageDialog(this,"NON RIESCO AD APRIRE IL FILE, ELIMINARLO PER CONTINUARE");
            System.exit(-1);

        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this,"NON RIESCO A TROVARE IL FILE, ELIMINARLO PER CONTINUARE");
            System.exit(-1);
        }
    }

}
