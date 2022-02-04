package it.unimol.decathlon.GUI.meters1500;

import it.unimol.decathlon.GUI.counter.Counter;
import it.unimol.decathlon.GUI.mainScreen.MainFrame;
import it.unimol.decathlon.GUI.principalScreen.PanelPrincipalScreen;
import it.unimol.decathlon.app.manager.ManagerGeneralClassific;
import it.unimol.decathlon.app.manager.Meters1500Manager;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Meters1500Panel extends JPanel {
    private Meters1500Manager meters1500Manager;
    private List<Integer> freezeDices = new ArrayList<>();

    private JPanel classificMiniGame;
    private JPanel heartOfGame;
    private JPanel total;
    private JPanel result;
    private JPanel timePanel;
    private JButton classific;
    private JButton raises;
    private JButton nextTurn;
    private JButton finishTurn;
    private JButton freeze;
    private JButton exit;
    private ManagerGeneralClassific managerGeneralClassific;
    private Counter counter;
    private Thread t1;
    private int numberOfDice;
    private int turn = 0;


    public Meters1500Panel() {
        this.meters1500Manager = new Meters1500Manager();

        try {

            this.managerGeneralClassific = new ManagerGeneralClassific();

        } catch (IOException e) {

            JOptionPane.showMessageDialog(this,"NON RIESCO A LEGGERE IL FILE, ELIMINARLO PER CONTINUARE");

            System.exit(-1);

        } catch (ClassNotFoundException e) {

            JOptionPane.showMessageDialog(this,"NON RIESCO A TROVARE IL FILE");

            System.exit(-1);
        }

        this.initialize();
        this.startOfMiniGame();


        this.raises = new JButton("RILANCIA");
        this.raises.setLocation(200, 200);
        this.add(this.raises);
        this.raises.addActionListener(e -> this.handleClickOnRaises());


        this.freeze = new JButton("SALVA IL DADO");
        this.freeze.setLocation(200,200);
        this.add(this.freeze);
        this.freeze.addActionListener(e -> this.handleClickOnFreeze());


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
        if (!this.meters1500Manager.controlTurn() && this.meters1500Manager.getPlayersScore().size() == this.turn) {
            this.total.removeAll();
            this.timePanel.removeAll();
            this.result.removeAll();
            this.classificMiniGame = new JPanel();
            this.classificMiniGame.add(new JLabel("*****Classifica  1500 METRI*****"));
            this.classificMiniGame.add(new JLabel(String.valueOf(this.meters1500Manager.getPlayersScore())));
            this.classificMiniGame.add(new JLabel("IL vincitore alla disciplina 1500 METRI è " +
                                                                " " + this.meters1500Manager.getPlayersScore().get(0)));

            ManagerGeneralClassific.meters1500Result();
            PanelPrincipalScreen.setOkClassific(true);
            this.add(this.classificMiniGame);
            this.revalidate();
            this.classific.setEnabled(false);

            this.exit = new JButton("ESCI");
            this.add(this.exit);
            this.revalidate();
            this.exit.addActionListener(e-> handleClickOnExit());
        }
        else {
            JOptionPane.showMessageDialog(this,"PER GUARDARE LA CLASSIFICA DEVONO GIOCARE TUTTI");
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

        if (this.meters1500Manager.controlTurn()) {
            this.counter.removeAll();
            this.counter.reset();

            this.meters1500Manager.nextTurn();

            this.heartOfGame.removeAll();

            this.turn++;


            if (this.meters1500Manager.controlTurn()) {
                this.freezeDices.clear();
                this.t1.interrupt();
                this.meters1500Manager.goGame();
                this.bodyOfMiniGame();
                this.freeze.setEnabled(true);
                this.meters1500Manager.resetTemporary();
                this.total.removeAll();
                this.result.removeAll();
                this.timePanel.removeAll();
                this.finishTurn.setEnabled(true);
                this.raises.setEnabled(true);

            }
        }

        else {
            JOptionPane.showMessageDialog(this,"NON CI SONO ALTRI GIOCATORI");
        }
    }


    private void handleClickOnFinishTurn() {
        if (this.meters1500Manager.getMaxDicesNumber() == 0) {
            this.counter.stop();

            this.meters1500Manager.setCurrentPlayer();

            this.meters1500Manager.setScore();

            this.meters1500Manager.setPlayersScore();

            this.result = new JPanel();
            this.result.add(new JLabel(this.meters1500Manager.getActivePlayer() + " " + this.meters1500Manager.toString()));
            this.add(result);
            this.result.revalidate();

            this.heartOfGame.removeAll();
            this.finishTurn.setEnabled(false);
            this.raises.setEnabled(false);
            this.freeze.setEnabled(false);

            this.nextTurn.setEnabled(true);

        }
        else {
            JOptionPane.showMessageDialog(this,"BISOGNA LANCIARE TUTTI I DADI PER FINIRE IL TURNO");
        }
    }


    private void handleClickOnFreeze() {
        if (this.meters1500Manager.getMaxDicesNumber() != 0) {

            this.meters1500Manager.decMaxDicesNumber();

            this.nextTurn.setEnabled(false);
            this.freezeDices.add(this.meters1500Manager.getDiceOut());

            this.heartOfGame.removeAll();
            this.total.removeAll();

            this.numberOfDice++;

            this.heartOfGame.add(new JLabel("E' il turno di :  " + this.meters1500Manager.getActivePlayer()));
            this.heartOfGame.revalidate();
            this.heartOfGame.add(new JLabel("DADO" + " " + this.numberOfDice + " : " + this.meters1500Manager.rollDices()));
            this.heartOfGame.add(new JLabel());
            this.total.add(new JLabel("TOTALE: " + " " + this.meters1500Manager.getScore()));
            this.heartOfGame.revalidate();


            if (this.meters1500Manager.getMaxAttempts() >= 1) {
                this.heartOfGame.add(new JLabel(("VUOI RILANCIARE ?" + " " + "(tenativi" + "  " + this.meters1500Manager.getMaxAttempts() + ")")));
                this.heartOfGame.revalidate();
            }

            this.heartOfGame.setLayout(new BoxLayout(this.heartOfGame, BoxLayout.Y_AXIS));
            this.add(heartOfGame);
            this.heartOfGame.add(new JLabel());
            this.heartOfGame.revalidate();

        } else {
            JOptionPane.showMessageDialog(this, "IL NUMERO DEI DADI DA CONGELARE E' STATO RAGGIUNTO");
            this.raises.setEnabled(false);
        }
    }


    private void handleClickOnRaises() {

        if (this.meters1500Manager.getMaxAttempts() > 0) {

            this.meters1500Manager.decMaxAttempts();

            this.heartOfGame.removeAll();

            this.total.removeAll();

            this.nextTurn.setEnabled(false);

            this.meters1500Manager.deleteResultNotFreeze();

            this.heartOfGame.add(new JLabel("E' il turno di :  " + this.meters1500Manager.getActivePlayer()));
            this.heartOfGame.revalidate();
            this.heartOfGame.add(new JLabel("DADO" + " " +  this.numberOfDice + " : "  + this.meters1500Manager.rollDices()));
            this.heartOfGame.add(new JLabel());
            this.total.add(new JLabel("TOTALE: " + " " + this.meters1500Manager.getScore()));

            this.heartOfGame.add(new JLabel());

            if (this.meters1500Manager.getMaxAttempts() >= 1 ) {
                this.heartOfGame.add(new JLabel(("VUOI RILANCIARE ?" + " " + "(tenativi" + "  " + this.meters1500Manager.getMaxAttempts() + ")")));
            }


            this.heartOfGame.setLayout(new BoxLayout(this.heartOfGame, BoxLayout.Y_AXIS));
            this.add(heartOfGame);


            this.revalidate();


        } else {
            JOptionPane.showMessageDialog(this, "NUMERO DI TENTATIVI FINITI");
            this.raises.setEnabled(false);
        }
    }


    private void bodyOfMiniGame() {
        t1 = new Thread(() -> {
            try {
                this.numberOfDice = 1;
                this.counter = new Counter();
                this.timePanel = new JPanel();
                this.timePanel.add(new JLabel("TEMPO"));
                this.counter.start();
                this.add(this.timePanel);
                this.add(this.counter);

                this.revalidate();

                this.total = new JPanel();
                this.heartOfGame = new JPanel();
                this.heartOfGame.add(new JLabel("E' il turno di :  " + this.meters1500Manager.getActivePlayer()));
                this.heartOfGame.revalidate();
                this.heartOfGame.add(new JLabel("DADO" + " " +  this.numberOfDice + " : "  + this.meters1500Manager.rollDices()));
                this.heartOfGame.add(new JLabel());
                this.total.add(new JLabel("TOTALE: " + " " + this.meters1500Manager.getScore()));

                this.heartOfGame.add(new JLabel(("VUOI RILANCIARE ?" + " " +"(tenativi" + "  " + this.meters1500Manager.getMaxAttempts() + ")")));

                this.heartOfGame.setLayout(new BoxLayout(this.heartOfGame, BoxLayout.Y_AXIS));
                this.add(this.heartOfGame);
                this.add(this.total);

                this.meters1500Manager.decMaxDicesNumber();

                this.heartOfGame.revalidate();
                this.nextTurn.setEnabled(false);


                Thread.sleep(180000);

                this.counter.stop();

                if (!this.t1.isInterrupted()) {
                    JOptionPane.showMessageDialog(this, "TEMPO SCADUTO");
                    this.raises.setEnabled(false);

                }

            } catch (InterruptedException ignored) {}

            this.raises.setEnabled(false);

        });

        this.t1.start();
    }


    private void startOfMiniGame(){
        this.meters1500Manager.goGame();
    }


    private void initialize() {

        try {
            this.meters1500Manager.addPlayer();

        } catch (IOException e ){
            JOptionPane.showMessageDialog(this,"NON RIESCO AD APRIRE IL FILE, ELIMINARLO PER CONTINUARE");
            System.exit(-1);

        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this,"NON RIESCO A TROVARE IL FILE, ELIMINARLO PER CONTINUARE");
            System.exit(-1);

        }
    }
}

