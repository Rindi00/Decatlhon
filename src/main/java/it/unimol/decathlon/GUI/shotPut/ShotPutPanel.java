package it.unimol.decathlon.GUI.shotPut;

import it.unimol.decathlon.GUI.counter.Counter;
import it.unimol.decathlon.GUI.mainScreen.MainFrame;
import it.unimol.decathlon.GUI.principalScreen.PanelPrincipalScreen;
import it.unimol.decathlon.app.manager.ManagerGeneralClassific;
import it.unimol.decathlon.app.manager.ShotPutManager;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class ShotPutPanel extends JPanel {
    private ShotPutManager shotPutManager;

    private JPanel classificMiniGame;
    private JPanel heartOfGame;
    private JPanel result;
    private JPanel timePanel;
    private JButton goOn;
    private JButton nextTurn;
    private JButton finishTurn;
    private JButton classific;
    private JButton exit;
    private ManagerGeneralClassific managerGeneralClassific;
    private Counter counter;
    private Thread t1;
    private int attempt = 1;
    private int turn = 0;



    public ShotPutPanel() {
        this.shotPutManager = new ShotPutManager();

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
        this.bodyOfMiniGame();


        this.goOn = new JButton("CONTINUA");
        this.goOn.setLocation(200, 200);
        this.add(this.goOn);
        this.goOn.addActionListener(e -> this.handleClickOnGoOn());


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
    }


    private void handleClickOnClassific() {
        if (!this.shotPutManager.controlTurn() && this.shotPutManager.getPlayersScore().size() == this.turn) {
            this.timePanel.removeAll();
            this.result.removeAll();
            this.classificMiniGame = new JPanel();
            this.classificMiniGame.add(new JLabel("*****Classifica  LANCIO DEL PESO*****"));
            this.classificMiniGame.add(new JLabel(String.valueOf(this.shotPutManager.getPlayersScore())));
            this.classificMiniGame.add(new JLabel("IL vincitore alla disciplina LANCIO DEL PESO è " +
                                                                  " " + this.shotPutManager.getPlayersScore().get(0)));

            ManagerGeneralClassific.shotPutResult();
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

        if (this.shotPutManager.controlTurn()) {
            this.counter.removeAll();
            this.counter.reset();
            this.shotPutManager.nextTurn();

            this.turn++;

            this.heartOfGame.removeAll();

            if (this.shotPutManager.controlTurn()) {
                this.t1.interrupt();
                this.attempt = 1;
                this.shotPutManager.goGame();
                this.result.removeAll();
                this.shotPutManager.resetResults();
                this.bodyOfMiniGame();
                this.timePanel.removeAll();
                this.finishTurn.setEnabled(true);
                this.goOn.setEnabled(true);
            }

        } else {
            JOptionPane.showMessageDialog(this, "NON CI SONO ALTRI GIOCATORI");
        }
    }


    private void handleClickOnFinishTurn() {
        this.counter.stop();

        this.shotPutManager.setCurrentPlayer();

        this.shotPutManager.setScore();

        this.shotPutManager.setPlayersScore();

        this.result = new JPanel();
        this.result.add(new JLabel(this.shotPutManager.getActivePlayer() + " " + this.shotPutManager.toString()));
        this.add(result);
        this.result.revalidate();

        this.heartOfGame.removeAll();
        this.finishTurn.setEnabled(false);
        this.goOn.setEnabled(false);
        this.nextTurn.setEnabled(true);


    }



    private void handleClickOnGoOn() {

        if (this.shotPutManager.getMaxAttempts() > 0 && this.shotPutManager.getMaxDicesNumber() > 0) {

            this.heartOfGame.removeAll();

            this.nextTurn.setEnabled(false);

            this.revalidate();

            this.heartOfGame.add(new JLabel("E' il turno di :  " + this.shotPutManager.getActivePlayer()));
            this.heartOfGame.add(new JLabel("TENTATIVO " + " " + this.attempt));
            this.heartOfGame.revalidate();
            this.shotPutManager.rollDices();
            this.heartOfGame.add(new JLabel("DADI :" + " " + this.shotPutManager.getDices()));
            this.heartOfGame.add(new JLabel());

            if (this.shotPutManager.controlNumberDices() != true) {
                this.heartOfGame.add(new JLabel("TENTATIVO ANNULLATO"));
                this.attempt++;
                this.shotPutManager.decMaxAttempts();
                this.shotPutManager.resetResults();
                this.shotPutManager.resetMaxDicesNumber();

            } else {
                this.heartOfGame.add(new JLabel("TOTALE " + " " + this.shotPutManager.getTotal()));
                this.heartOfGame.add(new JLabel(" VUOI CONTINUARE?"));
                this.shotPutManager.decMaxDicesNumber();
            }

            this.heartOfGame.setLayout(new BoxLayout(this.heartOfGame, BoxLayout.Y_AXIS));

            this.add(heartOfGame);

            this.revalidate();


        } else {
            JOptionPane.showMessageDialog(this, "NUMERO DI TENTATIVI FINITI");
            this.goOn.setEnabled(false);
        }
    }


    private void bodyOfMiniGame() {

        t1 = new Thread(() -> {
            try {
                this.counter = new Counter();
                this.timePanel = new JPanel();
                this.timePanel.add(new JLabel("TEMPO"));
                this.timePanel.add(this.counter);
                this.counter.start();
                this.timePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
                this.add(this.timePanel);

                this.revalidate();

                this.heartOfGame = new JPanel();
                this.heartOfGame.add(new JLabel("TENTATIVO " + " " + this.attempt));
                this.heartOfGame.add(new JLabel("E' il turno di :  " + this.shotPutManager.getActivePlayer()));

                this.shotPutManager.rollDices();

                this.heartOfGame.add(new JLabel("DADI :" + " " + this.shotPutManager.getDices()));
                this.heartOfGame.add(new JLabel());

                if (!this.shotPutManager.controlNumberDices()){
                    this.heartOfGame.add(new JLabel("TENTATIVO ANNULLATO"));
                    this.attempt++;
                    this.shotPutManager.decMaxAttempts();
                    this.shotPutManager.resetResults();
                    this.shotPutManager.resetMaxDicesNumber();
                }

                else {
                    this.heartOfGame.add(new JLabel("TOTALE " + " " + this.shotPutManager.getTotal()));
                    this.heartOfGame.add(new JLabel(" VUOI CONTINUARE?" ));
                    this.shotPutManager.decMaxDicesNumber();
                }

                this.heartOfGame.setLayout(new BoxLayout(this.heartOfGame, BoxLayout.Y_AXIS));

                this.add(heartOfGame);

                this.heartOfGame.revalidate();

                this.nextTurn.setEnabled(false);

                Thread.sleep(180000);

                this.counter.stop();

                if (!this.t1.isInterrupted()) {
                    JOptionPane.showMessageDialog(this, "TEMPO SCADUTO");
                    this.goOn.setEnabled(false);
                    this.finishTurn.setEnabled(true);
                }

            } catch (InterruptedException ignored) {}

            this.goOn.setEnabled(false);


        });

        this.t1.start();
    }


    private void startOfMiniGame(){
        this.shotPutManager.goGame();
    }


    private void initialize() {

        try {
            this.shotPutManager.addPlayer();

        } catch (IOException e ){
            JOptionPane.showMessageDialog(this,"NON RIESCO AD APRIRE IL FILE, ELIMINARLO PER CONTINUARE");
            System.exit(-1);

        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this,"NON RIESCO A TROVARE IL FILE, ELIMINARLO PER CONTINUARE");
            System.exit(-1);

        }
    }
}
