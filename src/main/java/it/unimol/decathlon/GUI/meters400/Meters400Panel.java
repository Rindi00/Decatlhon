package it.unimol.decathlon.GUI.meters400;

import it.unimol.decathlon.GUI.counter.Counter;
import it.unimol.decathlon.GUI.mainScreen.MainFrame;
import it.unimol.decathlon.GUI.principalScreen.PanelPrincipalScreen;
import it.unimol.decathlon.app.manager.ManagerGeneralClassific;
import it.unimol.decathlon.app.manager.Meters400Manager;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class Meters400Panel extends JPanel {
    private Meters400Manager meters400Manager;

    private JPanel classificMiniGame;
    private JPanel heartOfGame;
    private JPanel result;
    private JPanel timePanel;
    private JButton raises;
    private JButton nextTurn;
    private JButton finishTurn;
    private JButton classific;
    private JButton freeze;
    private JButton exit;
    private ManagerGeneralClassific managerGeneralClassific;
    private Counter counter;
    private Thread t1;
    private int turn = 0;
    private int numberOfSlot = 1;


     public Meters400Panel() {
         this.meters400Manager = new Meters400Manager();

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

         this.freeze = new JButton("SALVA I DADI");
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
        if (!this.meters400Manager.controlTurn() && this.meters400Manager.getPlayersScore().size() == this.turn) {
            this.timePanel.removeAll();
            this.result.removeAll();
            this.classificMiniGame = new JPanel();
            this.classificMiniGame.add(new JLabel("*****Classifica  400 METRI*****"));
            this.classificMiniGame.add(new JLabel(String.valueOf(this.meters400Manager.getPlayersScore())));
            this.classificMiniGame.add(new JLabel("IL vincitore alla disciplina 400 METRI è " +
                                                                 " " + this.meters400Manager.getPlayersScore().get(0)));

            ManagerGeneralClassific.meters400Result();
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

        if (this.meters400Manager.controlTurn()) {
            this.counter.removeAll();
            this.counter.reset();
            this.meters400Manager.nextTurn();

            this.turn++;

            this.heartOfGame.removeAll();


            if (this.meters400Manager.controlTurn()) {
                this.t1.interrupt();
                this.timePanel.removeAll();
                this.result.removeAll();
                this.meters400Manager.goGame();
                this.meters400Manager.resetTemporary();
                this.numberOfSlot = 1;
                this.bodyOfMiniGame();
                this.finishTurn.setEnabled(true);
                this.raises.setEnabled(true);
                this.freeze.setEnabled(true);
            }
        }

        else {
            JOptionPane.showMessageDialog(this,"NON CI SONO ALTRI GIOCATORI");
        }
     }


    private void handleClickOnFinishTurn() {
        this.counter.stop();

        this.meters400Manager.setCurrentPlayer();

        this.meters400Manager.setScore();

        this.meters400Manager.setPlayersScore();

        this.result = new JPanel();
        this.result.add(new JLabel(this.meters400Manager.getActivePlayer() + " " + this.meters400Manager.toString()));
        this.add(result);
        this.result.revalidate();

        this.heartOfGame.removeAll();
        this.finishTurn.setEnabled(false);
        this.raises.setEnabled(false);
        this.nextTurn.setEnabled(true);
        this.freeze.setEnabled(false);
     }


    private void handleClickOnFreeze() {
         this.meters400Manager.decMaxDicesNumber();
        if (this.meters400Manager.getMaxDicesNumber() != 0) {
            this.numberOfSlot++;

            this.nextTurn.setEnabled(false);

            this.heartOfGame.removeAll();

            this.heartOfGame.add(new JLabel("E' il turno di :  " + this.meters400Manager.getActivePlayer()));
            this.heartOfGame.revalidate();
            this.heartOfGame.add(new JLabel("SLOT " + " " +  this.numberOfSlot + ":" +  " " + "TOTALE SLOT" + " " + this.numberOfSlot +
                    " : " + " " + this.meters400Manager.rollDices()));

            this.heartOfGame.add(new JLabel("DADI" +   " " + this.meters400Manager.getDice1Out() + "," + this.meters400Manager.getDice2Out()));

            this.heartOfGame.add(new JLabel("TOTALE:" + " " + this.meters400Manager.getScore()));


            if (this.meters400Manager.getMaxAttempts() >= 1 && this.meters400Manager.getMaxDicesNumber() > 0) {

                this.heartOfGame.add(new JLabel("Rilanci ? si/no" + " " + "(" + this.meters400Manager.getMaxAttempts() +
                                                                                      "  " + "rilanci restanti" + ")"));
            }

            this.heartOfGame.setLayout(new BoxLayout(this.heartOfGame, BoxLayout.Y_AXIS));
            this.add(this.heartOfGame);

            this.heartOfGame.add(new JLabel());
            this.heartOfGame.revalidate();

        }
        else {
            JOptionPane.showMessageDialog(this, "IL NUMERO DEI DADI DA CONGELARE E' STATO RAGGIUNTO");
            this.raises.setEnabled(false);
            this.finishTurn.setEnabled(true);
            this.freeze.setEnabled(false);
        }
     }


    private void handleClickOnRaises() {

        if (this.meters400Manager.getMaxAttempts() > 0) {

            this.meters400Manager.decMaxAttempts();

            this.meters400Manager.deleteResultNotFreeze();

            this.heartOfGame.removeAll();

            this.nextTurn.setEnabled(false);

            this.heartOfGame.add(new JLabel("E' il turno di :  " + this.meters400Manager.getActivePlayer()));
            this.heartOfGame.revalidate();
            this.heartOfGame.add(new JLabel("SLOT " + " " +  this.numberOfSlot + ":" +  " " + "TOTALE SLOT" + " " + this.numberOfSlot +
                    " : " + " " + this.meters400Manager.rollDices()));

            this.heartOfGame.add(new JLabel("DADI" +   " " + this.meters400Manager.getDice1Out() + "," + this.meters400Manager.getDice2Out()));

            this.heartOfGame.add(new JLabel("TOTALE:" + " " + this.meters400Manager.getScore()));


               if (this.meters400Manager.getMaxAttempts() >= 1 && this.meters400Manager.getMaxDicesNumber() > 0) {

                   this.heartOfGame.add(new JLabel("Rilanci ? si/no" + " " + "(" +
                                      this.meters400Manager.getMaxAttempts() + "  " + "rilanci restanti" + ")"));
               }

               this.heartOfGame.setLayout(new BoxLayout(this.heartOfGame, BoxLayout.Y_AXIS));
               this.add(this.heartOfGame);

               this.heartOfGame.revalidate();

               this.finishTurn.setEnabled(false);


        } else {
            JOptionPane.showMessageDialog(this, "NUMERO DI TENTATIVI FINITI");
            this.raises.setEnabled(false);
            this.finishTurn.setEnabled(false);
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

                this.heartOfGame = new JPanel();
                this.heartOfGame.add(new JLabel("E' il turno di :  " + this.meters400Manager.getActivePlayer()));
                this.heartOfGame.revalidate();
                this.heartOfGame.add(new JLabel("SLOT " + " " +  this.numberOfSlot + ":" +  " " + "TOTALE SLOT" + " " + this.numberOfSlot +
                        " : " + " " + this.meters400Manager.rollDices()));

                this.heartOfGame.add(new JLabel("DADI" +   " " + this.meters400Manager.getDice1Out() + "," + this.meters400Manager.getDice2Out()));

                this.heartOfGame.add(new JLabel("TOTALE:" + " " + this.meters400Manager.getScore()));

                this.heartOfGame.add(new JLabel("Rilanci ? si/no" + " " + "(" +
                        this.meters400Manager.getMaxAttempts() + "  " + "rilanci restanti" + ")"));

                this.heartOfGame.setLayout(new BoxLayout(this.heartOfGame, BoxLayout.Y_AXIS));

                this.add(this.heartOfGame);
                this.heartOfGame.revalidate();
                this.nextTurn.setEnabled(false);
                this.finishTurn.setEnabled(false);

                Thread.sleep(180000);

                this.counter.stop();

                if (!this.t1.isInterrupted()) {
                    JOptionPane.showMessageDialog(this, "TEMPO SCADUTO");
                    this.raises.setEnabled(false);
                    this.freeze.setEnabled(false);
                    this.finishTurn.setEnabled(true);


                }

            } catch (InterruptedException ignored) {}

            this.raises.setEnabled(false);

        });

        this.t1.start();
     }


    private void startOfMiniGame(){
        this.meters400Manager.goGame();
    }


    private void initialize() {

        try {
            this.meters400Manager.addPlayer();

        } catch (IOException e ){
            JOptionPane.showMessageDialog(this,"NON RIESCO AD APRIRE IL FILE, ELIMINARLO PER CONTINUARE");
            System.exit(-1);

        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this,"NON RIESCO A TROVARE IL FILE, ELIMINARLO PER CONTINUARE");
            System.exit(-1);
        }
    }
}


