package it.unimol.decathlon.GUI.longJump;

import it.unimol.decathlon.GUI.counter.Counter;
import it.unimol.decathlon.GUI.mainScreen.MainFrame;
import it.unimol.decathlon.GUI.principalScreen.PanelPrincipalScreen;
import it.unimol.decathlon.app.exceptions.WrongDiceToSaveException;
import it.unimol.decathlon.app.exceptions.WrongInputChoiceDiceException;
import it.unimol.decathlon.app.manager.ChoiceManager;
import it.unimol.decathlon.app.manager.LongJumpManager;
import it.unimol.decathlon.app.manager.ManagerGeneralClassific;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


public class LongJumpPanel extends JPanel {
    private LongJumpManager longJumpManager;

    private JPanel classificMiniGame;
    private JPanel heartOfGame;
    private JPanel result;
    private JPanel timePanel;
    private JPanel saveDicesPanel;
    private JPanel situationPanel;
    private JButton runUpButton;//savedices
    private JButton nextTurn;
    private JButton finishTurn;
    private JButton classific;
    private JButton savingDices;
    private JButton jumpButton;
    private JButton exit;
    private JTextField saveDicesField;
    private ManagerGeneralClassific managerGeneralClassific;
    private Counter counter;
    private Thread t1;
    private int turn = 0;
    private String dicesSaved = "";
    private int numberDicesSavedRunUp;
    private int numberDicesSavedJump;
    private boolean isJumpTime;
    private int  situationForJump = 0;



    public LongJumpPanel() {
        this.longJumpManager = new LongJumpManager();

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


        this.runUpButton = new JButton("RINCORSA");
        this.runUpButton.setLocation(200, 200);
        this.add(this.runUpButton);
        this.runUpButton.addActionListener(e -> this.handleClickOnRunUp());


        this.jumpButton = new JButton("SALTO");
        this.jumpButton.setLocation(200, 200);
        this.add(this.jumpButton);
        this.jumpButton.addActionListener(e -> this.handleClickOnJump());



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

        this.start();


    }

    private void handleClickOnClassific() {
        if (!this.longJumpManager.controlTurn() && this.longJumpManager.getPlayersScore().size() == this.turn) {
            this.timePanel.removeAll();
            this.result.removeAll();
            this.situationPanel.removeAll();
            this.saveDicesPanel.removeAll();
            this.classificMiniGame = new JPanel();
            this.classificMiniGame.add(new JLabel("*****Classifica  SALTO IN LUNGO*****"));
            this.classificMiniGame.add(new JLabel(String.valueOf(this.longJumpManager.getPlayersScore())));
            this.classificMiniGame.add(new JLabel("IL vincitore alla disciplina SALTO IN LUNGO è " +
                    " " + this.longJumpManager.getPlayersScore().get(0)));

            ManagerGeneralClassific.longJumpResult();
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

        if (this.longJumpManager.controlTurn()) {
            this.counter.removeAll();
            this.counter.reset();
            this.longJumpManager.nextTurn();

            this.turn++;

            this.heartOfGame.removeAll();

            if (this.longJumpManager.controlTurn()) {
                this.t1.interrupt();
                this.longJumpManager.initializeResult();
                this.longJumpManager.resetChoice();
                this.longJumpManager.resetTemporary();
                this.isJumpTime = false;
                this.longJumpManager.goGame();
                this.situationPanel.removeAll();
                this.result.removeAll();
                this.saveDicesPanel.removeAll();
                this.runUpButton.setEnabled(true);
                this.jumpButton.setEnabled(false);
                this.numberDicesSavedRunUp = 0;
                this.numberDicesSavedJump = 0;
                this.start();
                this.timePanel.removeAll();
                this.finishTurn.setEnabled(false);


            }

        } else {
            JOptionPane.showMessageDialog(this, "NON CI SONO ALTRI GIOCATORI");
        }
    }

    private void handleClickOnFinishTurn() {
        this.counter.stop();

        this.longJumpManager.setCurrentPlayer();

        this.longJumpManager.setScore();

        this.longJumpManager.setPlayersScore();

        this.result = new JPanel();
        this.result.add(new JLabel(this.longJumpManager.getActivePlayer() + " " + this.longJumpManager.toString()));
        this.add(result);
        this.result.revalidate();
        this.situationForJump = 0;
        this.heartOfGame.removeAll();
        this.saveDicesPanel.removeAll();
        this.finishTurn.setEnabled(false);
        this.runUpButton.setEnabled(false);
        this.nextTurn.setEnabled(true);
    }

    private void handleClickOnRunUp() {
        if (this.longJumpManager.getScore() < 9) {

            this.inputDices();
        }

        else {
            this.situation();
        }
    }

    private void handleClickOnJump() {
        this.situationForJump++;
        if (situationForJump == 1) {
            this.longJumpManager.resetTemporary();
            this.longJumpManager.resetChoice();
        }

        if (this.numberDicesSavedJump < this.numberDicesSavedRunUp) {

            this.runUpButton.setEnabled(false);
            this.situationPanel.removeAll();
            this.isJumpTime = true;
            this.jump();
            this.inputDices();

        }
        else {
            this.situation();
        }


    }

    private void jump() {

        this.saveDicesPanel.removeAll();

        this.heartOfGame.removeAll();

        this.heartOfGame.add(new JLabel("E' il turno di :  " + this.longJumpManager.getActivePlayer()));

        this.heartOfGame.add((new JLabel("SALTO")));

        if (this.longJumpManager.diceSavedControl("A")) {
            this.heartOfGame.add(new JLabel("A:" + " " + this.longJumpManager.getDiceAOut()));

        } else {
            this.heartOfGame.add(new JLabel("A:" + " " + this.longJumpManager.getDiceSaved("A") + " " + "(congelato)"));

        }


        if (this.numberDicesSavedRunUp > 1) {
            if (this.longJumpManager.diceSavedControl("B")) {
                this.heartOfGame.add(new JLabel("B:" + " " + this.longJumpManager.getDiceBOut()));
            } else {
                this.heartOfGame.add(new JLabel("B:" + " " + this.longJumpManager.getDiceSaved("B") + " " + "(congelato)"));

            }
        }

        if(this.numberDicesSavedRunUp > 2) {
            if (this.longJumpManager.diceSavedControl("C")) {
                this.heartOfGame.add(new JLabel("C:" + " " + this.longJumpManager.getDiceCOut()));
            } else {
                this.heartOfGame.add(new JLabel("C:" + " " + this.longJumpManager.getDiceSaved("C") + " " + "(congelato)"));

            }
        }


        if (this.numberDicesSavedRunUp > 3) {
            if (this.longJumpManager.diceSavedControl("D")) {
                this.heartOfGame.add(new JLabel("D:" + " " + this.longJumpManager.getDiceDOut()));
            } else {
                this.heartOfGame.add(new JLabel("D:" + " " + this.longJumpManager.getDiceSaved("D") + " " + "(congelato)"));

            }
        }


        if (this.numberDicesSavedRunUp > 4) {
            if (this.longJumpManager.diceSavedControl("E")) {
                this.heartOfGame.add(new JLabel("E:" + " " + this.longJumpManager.getDiceEOut()));

            } else {
                this.heartOfGame.add(new JLabel("E:" + " " + this.longJumpManager.getDiceSaved("E") + " " + "(congelato)"));

            }
        }


        this.heartOfGame.setLayout(new BoxLayout(this.heartOfGame, BoxLayout.Y_AXIS));

        this.add(heartOfGame);

        this.heartOfGame.revalidate();

        this.revalidate();

        this.finishTurn.setEnabled(true);

    }



    private void start() {

        t1 = new Thread(() -> {
            try {
                this.situationPanel = new JPanel();

                this.counter = new Counter();
                this.timePanel = new JPanel();
                this.timePanel.add(new JLabel("TEMPO"));
                this.timePanel.add(this.counter);
                this.counter.start();
                this.timePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                this.add(this.timePanel);


                this.revalidate();

                this.heartOfGame = new JPanel();

                this.heartOfGame.add(new JLabel("E' il turno di :  " + this.longJumpManager.getActivePlayer()));

                this.heartOfGame.add((new JLabel("RINCORSA")));

                if (this.longJumpManager.diceSavedControl("A")) {
                    this.heartOfGame.add(new JLabel("A:" + " " + this.longJumpManager.getDiceAOut()));

                } else {
                    this.heartOfGame.add(new JLabel("A:" + " " + this.longJumpManager.getDiceSaved("A") + " " + "(congelato)"));
                }

                if (this.longJumpManager.diceSavedControl("B")) {
                    this.heartOfGame.add(new JLabel("B:" + " " + this.longJumpManager.getDiceBOut()));

                } else {
                    this.heartOfGame.add(new JLabel("B:" + " " + this.longJumpManager.getDiceSaved("B") + " " + "(congelato)"));

                }

                if (this.longJumpManager.diceSavedControl("C")) {
                    this.heartOfGame.add(new JLabel("C:" + " " + this.longJumpManager.getDiceCOut()));

                } else {
                    this.heartOfGame.add(new JLabel("C:" + " " + this.longJumpManager.getDiceSaved("C") + " " + "(congelato)"));
                }

                if (this.longJumpManager.diceSavedControl("D")) {
                    this.heartOfGame.add(new JLabel("D:" + " " + this.longJumpManager.getDiceDOut()));

                } else {
                    this.heartOfGame.add(new JLabel("D:" + " " + this.longJumpManager.getDiceSaved("D") + " " + "(congelato)"));
                }

                if (this.longJumpManager.diceSavedControl("E")) {
                    this.heartOfGame.add(new JLabel("E:" + " " + this.longJumpManager.getDiceEOut()));

                } else {
                    this.heartOfGame.add(new JLabel("E:" + " " + this.longJumpManager.getDiceSaved("E") + " " + "(congelato)"));
                }


                this.heartOfGame.setLayout(new BoxLayout(this.heartOfGame, BoxLayout.Y_AXIS));

                this.add(heartOfGame);

                this.heartOfGame.revalidate();

                this.nextTurn.setEnabled(false);

                this.finishTurn.setEnabled(false);

                this.jumpButton.setEnabled(false);

                Thread.sleep(180000);

                this.counter.stop();

                if (!this.t1.isInterrupted()) {
                    JOptionPane.showMessageDialog(this, "TEMPO SCADUTO");
                    this.longJumpManager.resetChoice();
                    this.longJumpManager.resetTemporary();
                    this.runUpButton.setEnabled(false);
                    this.jumpButton.setEnabled(false);
                    this.finishTurn.setEnabled(true);
                }

            } catch (InterruptedException ignored) {
            }

        });

        this.t1.start();
    }


    private void runUp() {

        this.saveDicesPanel.removeAll();

        this.heartOfGame.removeAll();

        this.revalidate();

        this.heartOfGame.add(new JLabel("E' il turno di :  " + this.longJumpManager.getActivePlayer()));

        this.heartOfGame.add((new JLabel("RINCORSA")));

        if (this.longJumpManager.diceSavedControl("A")) {
            this.heartOfGame.add(new JLabel("A:" + " " + this.longJumpManager.getDiceAOut()));

        } else {
            this.heartOfGame.add(new JLabel("A:" + " " + this.longJumpManager.getDiceSaved("A") + " " + "(congelato)"));
        }

        if (this.longJumpManager.diceSavedControl("B")) {
            this.heartOfGame.add(new JLabel("B:" + " " + this.longJumpManager.getDiceBOut()));

        } else {
            this.heartOfGame.add(new JLabel("B:" + " " + this.longJumpManager.getDiceSaved("B") + " " + "(congelato)"));

        }

        if (this.longJumpManager.diceSavedControl("C")) {
            this.heartOfGame.add(new JLabel("C:" + " " + this.longJumpManager.getDiceCOut()));

        } else {
            this.heartOfGame.add(new JLabel("C:" + " " + this.longJumpManager.getDiceSaved("C") + " " + "(congelato)"));
        }

        if (this.longJumpManager.diceSavedControl("D")) {
            this.heartOfGame.add(new JLabel("D:" + " " + this.longJumpManager.getDiceDOut()));

        } else {
            this.heartOfGame.add(new JLabel("D:" + " " + this.longJumpManager.getDiceSaved("D") + " " + "(congelato)"));
        }

        if (this.longJumpManager.diceSavedControl("E")) {
            this.heartOfGame.add(new JLabel("E:" + " " + this.longJumpManager.getDiceEOut()));

        } else {
            this.heartOfGame.add(new JLabel("E:" + " " + this.longJumpManager.getDiceSaved("E") + " " + "(congelato)"));
        }


        this.heartOfGame.setLayout(new BoxLayout(this.heartOfGame, BoxLayout.Y_AXIS));

        this.add(heartOfGame);

        this.heartOfGame.revalidate();

        this.nextTurn.setEnabled(false);

        this.finishTurn.setEnabled(false);

        this.jumpButton.setEnabled(true);

    }


    private void startOfMiniGame() {
        this.longJumpManager.goGame();
        this.longJumpManager.initializeResult();
    }


    private void inputDices() {
        this.saveDicesPanel = new JPanel();
        this.saveDicesPanel.add(new JLabel("Quali dadi vuoi congelare? (inserirli in lettera maiuscola separati da virgola)"));
        this.saveDicesField = new JTextField(20);
        this.saveDicesPanel.add(this.saveDicesField);
        this.saveDicesPanel.setLayout(new FlowLayout(FlowLayout.CENTER));


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
            diceToSaveString = ChoiceManager.getMeChoiceDiceToSaveDiscus(this.saveDicesField.getText());

            String[] dicesToSaveArray = diceToSaveString.split(",");
            if (diceToSaveString.length() > 0 && dicesToSaveArray.length == 0) {
                dicesToSaveArray = new String[diceToSaveString.length() + 1];
                for (int i = 0; i < dicesToSaveArray.length; i++) {
                    dicesToSaveArray[i] = "";
                }
            }
            for (String dice : dicesToSaveArray) {
                result.add(dice);
            }
            isOk = true;

            if (this.isJumpTime) {
                this.numberDicesSavedJump += result.size();
            }

            if (!this.isJumpTime) {
                this.numberDicesSavedRunUp += result.size();
            }

        } catch (WrongInputChoiceDiceException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
            isOk = false;

        }

        if (isOk) {
            try {

                if (this.longJumpManager.getChoices().size() < 5) {

                    this.longJumpManager.freezeDices(result);

                    isOk = true;

                }

            } catch (WrongDiceToSaveException e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
                isOk = false;

            }
        }

            if (isOk) {
                this.situation();

            }
    }

    public void situation() {
        if (!this.isJumpTime) {

            if (this.longJumpManager.getScoreRunUp() != 0  && this.longJumpManager.getScore() < 9
                                                                                    && this.numberDicesSavedRunUp < 5) {
                this.situationPanel.removeAll();
                this.situationPanel.add(new JLabel("Totale rincorsa :" + " " + this.longJumpManager.getScoreRunUp() + "/9"));

                this.jumpButton.setEnabled(true);
                this.add(this.situationPanel);

                this.revalidate();
            }

            if (this.longJumpManager.getScoreRunUp() != 0 &&  this.numberDicesSavedRunUp < 5
                                                                              && this.longJumpManager.getScore() < 9) {
                this.situationPanel.add(new JLabel("Vuoi continuare la rincorsa?"));
                this.saveDicesPanel.removeAll();
                this.runUp();
            }

            if ((this.longJumpManager.getScore() == 9 ) || (this.longJumpManager.getScore() < 9 && this.numberDicesSavedRunUp == 5)) {
                this.situationPanel.removeAll();
                this.situationPanel.add(new JLabel("Totale rincorsa :" + " " + this.longJumpManager.getScoreRunUp() + "/9" +
                " " + "Rincorsa:" + " " + this.longJumpManager.getScoreRunUp() + " " + "punti" +
                                            " " + this.longJumpManager.getChoices().size() + " " + "dadi congelati"));

                this.isJumpTime = true;
                this.longJumpManager.resetChoice();
                this.longJumpManager.resetTemporary();
                this.add(this.situationPanel);
                this.runUpButton.setEnabled(false);
                this.jumpButton.setEnabled(true);
                this.finishTurn.setEnabled(false);
                this.jump();
            }

            if (this.longJumpManager.getScore() > 9) {
                JOptionPane.showMessageDialog(this,"HAI SUPERATO IL VALORE MASSIMO DI RINCORSA, NON PUOI ANDARE AVANTI");
                this.situationPanel.removeAll();
                this.saveDicesPanel.removeAll();
                this.situationPanel.removeAll();
                this.runUpButton.setEnabled(false);
                this.jumpButton.setEnabled(false);
                this.handleClickOnFinishTurn();

            }

        }

        if (this.isJumpTime) {
            int total = this.longJumpManager.getScore() + this.longJumpManager.runUpSettedScore();

            this.situationPanel.removeAll();
            this.saveDicesPanel.removeAll();
            this.situationPanel.add(new JLabel("Totale salto :" + " " + this.longJumpManager.getScore() +
                    " " + ("totale complessivo" + " " + total)));

           this.add(this.situationPanel);


            if ((this.numberDicesSavedJump > 0) && (this.numberDicesSavedJump == this.numberDicesSavedRunUp)) {
                JOptionPane.showMessageDialog(this,"IL NUMERO DI DADI DA SALVARE E' STATO RAGGIUNTO");
                this.jumpButton.setEnabled(false);
                this.saveDicesPanel.removeAll();
            }
        }

    }

    private void initialize() {

        try {
            this.longJumpManager.addPlayer();

        } catch (IOException e ){
            JOptionPane.showMessageDialog(this,"NON RIESCO AD APRIRE IL FILE, ELIMINARLO PER CONTINUARE");
            System.exit(-1);

        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this,"NON RIESCO A TROVARE IL FILE, ELIMINARLO PER CONTINUARE");
            System.exit(-1);
        }
    }

}
