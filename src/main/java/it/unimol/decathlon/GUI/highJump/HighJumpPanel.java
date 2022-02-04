package it.unimol.decathlon.GUI.highJump;

import it.unimol.decathlon.GUI.counter.Counter;
import it.unimol.decathlon.GUI.mainScreen.MainFrame;
import it.unimol.decathlon.GUI.principalScreen.PanelPrincipalScreen;
import it.unimol.decathlon.app.exceptions.WrongHighException;
import it.unimol.decathlon.app.manager.HighJumpManager;
import it.unimol.decathlon.app.manager.ManagerGeneralClassific;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class HighJumpPanel extends JPanel {
    private HighJumpManager highJumpManager;

    private JPanel classificMiniGame;
    private JPanel heartOfGame;
    private JPanel result;
    private JPanel buttonPanel;
    private JPanel timePanel;
    private JButton classific;
    private JButton raises;
    private JButton nextTurn;
    private JButton finishTurn;
    private JButton settingHigh;
    private JButton exit;
    private JTextField highField;
    private ManagerGeneralClassific managerGeneralClassific;
    private Counter counter;
    private Thread t1;
    private int turn = 0;



    public HighJumpPanel()  {
        this.highJumpManager = new HighJumpManager();

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
        this.setHigh();

    }


    private void handleClickOnClassific() {
        if (!this.highJumpManager.controlTurn() && this.highJumpManager.getPlayersScore().size() == this.turn) {
            this.timePanel.removeAll();
            this.result.removeAll();

            this.classificMiniGame = new JPanel();
            this.classificMiniGame.add(new JLabel("*****Classifica  SALTO IN ALTO*****"));
            this.classificMiniGame.add(new JLabel(String.valueOf(this.highJumpManager.getPlayersScore())));
            this.classificMiniGame.add(new JLabel("IL vincitore alla disciplina SALTO IN ALTO è " +
                                                               " " + this.highJumpManager.getPlayersScore().get(0)));

            ManagerGeneralClassific.highJumpResult();
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

        if (this.highJumpManager.controlTurn()) {
            this.counter.removeAll();
            this.counter.reset();
            this.highJumpManager.nextTurn();

            this.turn++;

            this.heartOfGame.removeAll();

            if (this.highJumpManager.controlTurn()) {
                this.t1.interrupt();
                this.result.removeAll();
                this.timePanel.removeAll();
                this.highJumpManager.goGame();
                this.buttonPanel.removeAll();
                this.setHigh();
                this.finishTurn.setEnabled(true);
                this.raises.setEnabled(true);
            }
        }

        else {
            JOptionPane.showMessageDialog(this,"NON CI SONO ALTRI GIOCATORI");
        }
    }


    private void handleClickOnFinishTurn() {
        this.counter.stop();

        this.highJumpManager.setCurrentPlayer();

        this.highJumpManager.setScore();

        this.highJumpManager.setPlayersScore();

        this.result= new JPanel();
        this.result.add(new JLabel(this.highJumpManager.getActivePlayer() + " " + this.highJumpManager.toString()));
        this.add(result);
        this.result.revalidate();

        this.heartOfGame.removeAll();
        this.finishTurn.setEnabled(false);
        this.raises.setEnabled(false);
        this.nextTurn.setEnabled(true);
    }


    private void handleClickOnRaises() {

        if (this.highJumpManager.getMaxAttempts() > 0) {

            this.highJumpManager.decMaxAttempts();

            this.heartOfGame.removeAll();

            this.nextTurn.setEnabled(false);


            this.heartOfGame.add(new JLabel("E' il turno di :  " + this.highJumpManager.getActivePlayer()));
            this.heartOfGame.revalidate();
            this.heartOfGame.add(new JLabel("RISULTATO: " + " " +  this.highJumpManager.rollDices()));

            this.heartOfGame.add(new JLabel("DADI USCITI :" + " " + this.highJumpManager.getDices()));
            this.heartOfGame.setLayout(new BoxLayout(this.heartOfGame, BoxLayout.Y_AXIS));
            this.add(heartOfGame);
            this.heartOfGame.add(new JLabel());
            this.revalidate();

            if (this.highJumpManager.getMaxAttempts() >= 1) {
                this.heartOfGame.add(new JLabel(" Rilanci ? " + "(TENTATIVI:" + " " + this.highJumpManager.getMaxAttempts() + ")"));

                this.heartOfGame.revalidate();
            }


        } else {
            JOptionPane.showMessageDialog(this, "NUMERO DI TENTATIVI FINITI");
            this.raises.setEnabled(false);
        }
    }


    private void setHigh() {
        this.add(new JLabel("E' il turno di :  " + this.highJumpManager.getActivePlayer()));
        this.add(new JLabel("CHE ALTEZZA VUOI RAGGIUNGERE? (tra 5 e 30)"));

        this.highField = new JTextField(20);
        this.add(this.highField);
        this.revalidate();

        this.settingHigh = new JButton("IMPOSTA L' ALTEZZA");
        this.add(this.settingHigh);
        this.revalidate();
        this.settingHigh.addActionListener(e -> hanldeClickOnSetHigh());

    }

    private void bodyOfMiniGame() {

        t1 = new Thread(() -> {
            try {
                this.counter = new Counter();
                this.timePanel = new JPanel();
                this.timePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                this.timePanel.add(new JLabel("TEMPO"));
                this.timePanel.add(this.counter);
                this.counter.start();
                this.add(this.timePanel);

                this.revalidate();

                this.heartOfGame = new JPanel();
                this.heartOfGame.add(new JLabel("E' il turno di :  " + this.highJumpManager.getActivePlayer()));
                this.heartOfGame.revalidate();
                this.heartOfGame.add(new JLabel("RISULTATO: " + " " +  this.highJumpManager.rollDices()));

                this.heartOfGame.add(new JLabel("DADI USCITI :" + " " + this.highJumpManager.getDices()));

                this.heartOfGame.add(new JLabel(" Rilanci ?  " + "(TENTATIVI:" + " " + this.highJumpManager.getMaxAttempts() + ")"));

                this.heartOfGame.setLayout(new BoxLayout(this.heartOfGame, BoxLayout.Y_AXIS));
                this.add(heartOfGame);
                this.heartOfGame.revalidate();

                this.nextTurn.setEnabled(false);

                Thread.sleep(180000);

                this.counter.stop();

                if (!this.t1.isInterrupted()) {
                    JOptionPane.showMessageDialog(this, "TEMPO SCADUTO");
                    this.raises.setEnabled(false);
                    this.finishTurn.setEnabled(true);
                }

            } catch (InterruptedException ignored) {}

            this.raises.setEnabled(false);

        });

        this.t1.start();

    }


    private void hanldeClickOnSetHigh() {
        Integer valueHigh = Integer.parseInt(this.highField.getText());

        try {
            this.highJumpManager.setHigh(valueHigh);

            this.removeAll();
            this.revalidate();
            this.bodyOfMiniGame();


            this.buttonPanel = new JPanel();

            this.raises = new JButton("RILANCIA");
            this.raises.setLocation(200, 200);
            this.buttonPanel.add(this.raises);
            this.raises.addActionListener(e -> this.handleClickOnRaises());


            this.finishTurn = new JButton("TERMINA TURNO");
            this.finishTurn.setLocation(200, 200);
            this.buttonPanel.add(this.finishTurn);
            this.finishTurn.addActionListener(e -> this.handleClickOnFinishTurn());


            this.nextTurn = new JButton("VAI AL PROSSIMO TURNO");
            this.nextTurn.setLocation(200, 200);
            this.buttonPanel.add(this.nextTurn);
            this.nextTurn.addActionListener(e -> this.handleClickOnNextTurn());


            this.classific = new JButton("VAI ALLA CLASSIFICA");
            this.classific.setLocation(200, 200);
            this.buttonPanel.add(this.classific);
            this.classific.addActionListener(e -> this.handleClickOnClassific());

            this.add(this.buttonPanel);

        } catch (WrongHighException e) {
            JOptionPane.showMessageDialog(this,e.getMessage());
        }
    }


    private void startOfMiniGame(){
        this.highJumpManager.goGame();
    }


    private void initialize() {

        try {
            this.highJumpManager.addPlayer();

        } catch (IOException e ){
            JOptionPane.showMessageDialog(this,"NON RIESCO AD APRIRE IL FILE, ELIMINARLO PER CONTINUARE");
            System.exit(-1);

        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this,"NON RIESCO A TROVARE IL FILE, ELIMINARLO PER CONTINUARE");
            System.exit(-1);
        }
    }

   }


