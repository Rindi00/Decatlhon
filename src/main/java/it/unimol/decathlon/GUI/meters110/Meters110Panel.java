package it.unimol.decathlon.GUI.meters110;

import it.unimol.decathlon.GUI.counter.Counter;
import it.unimol.decathlon.GUI.mainScreen.MainFrame;
import it.unimol.decathlon.GUI.principalScreen.PanelPrincipalScreen;
import it.unimol.decathlon.app.manager.ManagerGeneralClassific;
import it.unimol.decathlon.app.manager.Meters110Manager;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class Meters110Panel extends JPanel {
    private Meters110Manager meters110Manager;

    private JPanel classificMiniGame;
    private JPanel heartOfGame;
    private JPanel result;
    private JPanel timePanel;
    private JButton raises;
    private JButton nextTurn;
    private JButton finishTurn;
    private JButton classific;
    private JButton exit;
    private ManagerGeneralClassific managerGeneralClassific;
    private Counter counter;
    private Thread t1;
    private int futureMalus;
    private int turn = 0;


    public Meters110Panel()  {
        this.meters110Manager = new Meters110Manager();

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
    this.bodyOfMiniGame();



    this.raises = new JButton("RILANCIA");
    this.raises.setLocation(200, 200);
    this.add(this.raises);
    this.raises.addActionListener(e -> this.handleClickOnRaises());


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
        if (!this.meters110Manager.controlTurn() && this.meters110Manager.getPlayersScore().size() == this.turn) {
            this.timePanel.removeAll();
            this.result.removeAll();
            this.classificMiniGame = new JPanel();
            this.classificMiniGame.add(new JLabel("*****Classifica  110 METRI A OSTACOLI*****"));
            this.classificMiniGame.add(new JLabel(String.valueOf(this.meters110Manager.getPlayersScore())));
            this.classificMiniGame.add(new JLabel("IL vincitore alla disciplina 110 METRI A OSTACOLI è " +
                                                                " " + this.meters110Manager.getPlayersScore().get(0)));

            ManagerGeneralClassific.meters110Result();
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

        if (this.meters110Manager.controlTurn()) {
            this.counter.removeAll();
            this.counter.reset();
            this.meters110Manager.nextTurn();

            this.turn++;

            this.heartOfGame.removeAll();

            if (this.meters110Manager.controlTurn()) {
                this.t1.interrupt();
                this.timePanel.removeAll();
                this.result.removeAll();
                this.meters110Manager.goGame();
                this.bodyOfMiniGame();
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

        this.meters110Manager.setCurrentPlayer();

        this.meters110Manager.setScore();

        this.meters110Manager.setPlayersScore();

        this.result = new JPanel();
        this.result.add(new JLabel(this.meters110Manager.getActivePlayer() + " " + this.meters110Manager.toString()));
        this.add(result);
        this.result.revalidate();

        this.meters110Manager.resetMalus();

        this.heartOfGame.removeAll();
        this.finishTurn.setEnabled(false);
        this.raises.setEnabled(false);
        this.nextTurn.setEnabled(true);
    }


    private void handleClickOnRaises() {

            if (this.meters110Manager.getMaxAttempts() > 0) {

                this.meters110Manager.decMaxAttempts();

                this.heartOfGame.removeAll();

                this.meters110Manager.rollDices();

                this.nextTurn.setEnabled(false);

                this.meters110Manager.incrementMalus();
                this.futureMalus = this.meters110Manager.getMalus() + 1;

                this.heartOfGame.add(new JLabel("E' il turno di :  " + this.meters110Manager.getActivePlayer()));
                this.heartOfGame.revalidate();
                this.heartOfGame.add(new JLabel("(" + this.meters110Manager.getScore() + " " + "punti" + " " + "|" + " " +
                        this.meters110Manager.getMalus() + " " + "malus" + " " + "|" + " " + "totale" + " " +
                        this.meters110Manager.getScoreWithMalus() + "" + ")"));

                this.heartOfGame.add(new JLabel("DADI :" + " " + this.meters110Manager.getDices()));
                this.heartOfGame.setLayout(new BoxLayout(this.heartOfGame, BoxLayout.Y_AXIS));
                this.add(this.heartOfGame);
                this.heartOfGame.add(new JLabel());

                if (this.meters110Manager.getMaxAttempts() >= 1) {
                    this.heartOfGame.add(new JLabel((" Rilanci ? (Verrà applicato un malus di " + " " + this.futureMalus +
                            " " + "punti)") + "  " + " " + "(TENTATIVI:" + " " + this.meters110Manager.getMaxAttempts() + ")"));

                }

                this.heartOfGame.revalidate();


            } else {
                JOptionPane.showMessageDialog(this, "NUMERO DI TENTATIVI FINITI");
                this.raises.setEnabled(false);
            }
    }


    private void bodyOfMiniGame() {
        t1 = new Thread(() -> {
            try {
                this.counter = new Counter();
                this.timePanel = new JPanel();
                this.timePanel.add(new JLabel("TEMPO"));
                this.counter.start();
                this.add(this.timePanel);
                this.add(this.counter);

                this.revalidate();

                this.meters110Manager.rollDices();

                this.heartOfGame = new JPanel();
                this.heartOfGame.add(new JLabel("E' il turno di :  " + this.meters110Manager.getActivePlayer()));
                this.heartOfGame.revalidate();
                this.heartOfGame.add(new JLabel("(" + this.meters110Manager.getScore() + " " + "punti" + " " + "|" + " " +
                        this.meters110Manager.getMalus() + " " + "malus" + " " + "|" + " " + "totale" + " " +
                        this.meters110Manager.getScoreWithMalus() + "" + ")"));

                this.heartOfGame.add(new JLabel("DADI :" + " " + this.meters110Manager.getDices()));
                this.heartOfGame.setLayout(new BoxLayout(this.heartOfGame, BoxLayout.Y_AXIS));
                this.add(this.heartOfGame);

                this.futureMalus = this.meters110Manager.getMalus() + 1;

                this.heartOfGame.add(new JLabel((" Rilanci ? (Verrà applicato un malus di " + " " + this.futureMalus +
                        " " + "punti)") + "  " + " " + "(TENTATIVI:" + " " + this.meters110Manager.getMaxAttempts() + ")"));

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



    private void startOfMiniGame(){
        this.meters110Manager.goGame();
    }


    private void initialize() {

        try {
            this.meters110Manager.addPlayer();

        } catch (IOException e ){
            JOptionPane.showMessageDialog(this,"NON RIESCO AD APRIRE IL FILE, ELIMINARLO PER CONTINUARE");
            System.exit(-1);

        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this,"NON RIESCO A TROVARE IL FILE, ELIMINARLO PER CONTINUARE");
            System.exit(-1);
        }
    }
}
