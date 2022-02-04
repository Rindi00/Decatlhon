package it.unimol.decathlon.GUI.poleVault;

import it.unimol.decathlon.GUI.counter.Counter;
import it.unimol.decathlon.GUI.mainScreen.MainFrame;
import it.unimol.decathlon.GUI.principalScreen.PanelPrincipalScreen;
import it.unimol.decathlon.app.exceptions.WrongHighException;
import it.unimol.decathlon.app.exceptions.WrongNumberDiceException;
import it.unimol.decathlon.app.manager.ManagerGeneralClassific;
import it.unimol.decathlon.app.manager.PoleVaultManager;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class PoleVaultPanel extends JPanel {

    private PoleVaultManager poleVaultManager;

    private JPanel classificMiniGame;
    private JPanel heartOfGame;
    private JPanel result;
    private JPanel timePanel;
    private JPanel high;
    private JPanel dices;
    private JPanel buttonPanel;
    private JButton classific;
    private JButton raises;
    private JButton nextTurn;
    private JButton finishTurn;
    private JButton settingHigh;
    private JButton settingDices;
    private JButton exit;
    private JTextField highField;
    private JTextField dicesField;
    private ManagerGeneralClassific managerGeneralClassific;
    private Counter counter;
    private Thread t1;
    private int turn = 0;



    public PoleVaultPanel()  {
        this.poleVaultManager = new PoleVaultManager();

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
        if (!this.poleVaultManager.controlTurn() && this.poleVaultManager.getPlayersScore().size() == this.turn) {
            this.timePanel.removeAll();
            this.result.removeAll();
            this.classificMiniGame = new JPanel();
            this.classificMiniGame.add(new JLabel("*****Classifica  SALTO CON L' ASTA*****"));
            this.classificMiniGame.add(new JLabel(String.valueOf(this.poleVaultManager.getPlayersScore())));
            this.classificMiniGame.add(new JLabel("IL vincitore alla disciplina SALTO CON L' ASTA è " +
                                                                 " " + this.poleVaultManager.getPlayersScore().get(0)));

            ManagerGeneralClassific.poleVaultResult();
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

        if (this.poleVaultManager.controlTurn()) {
            this.counter.removeAll();
            this.counter.reset();
            this.poleVaultManager.nextTurn();

            this.turn++;

            this.heartOfGame.removeAll();

            if (this.poleVaultManager.controlTurn()) {
                this.t1.interrupt();
                this.result.removeAll();
                this.timePanel.removeAll();
                this.poleVaultManager.goGame();
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

        this.poleVaultManager.setCurrentPlayer();

        this.poleVaultManager.setScore();

        this.poleVaultManager.setPlayersScore();

        this.result= new JPanel();
        this.result.add(new JLabel(this.poleVaultManager.getActivePlayer() + " " + this.poleVaultManager.toString()));
        this.add(result);
        this.result.revalidate();

        this.heartOfGame.removeAll();
        this.finishTurn.setEnabled(false);
        this.raises.setEnabled(false);
        this.nextTurn.setEnabled(true);

    }


    private void handleClickOnRaises() {

        if (this.poleVaultManager.getMaxAttempts() > 0 ) {

            this.poleVaultManager.decMaxAttempts();

            this.heartOfGame.removeAll();

            this.nextTurn.setEnabled(false);

            this.heartOfGame.add(new JLabel("E' il turno di :  " + this.poleVaultManager.getActivePlayer()));

            int  result = this.poleVaultManager.rollDices();

            this.heartOfGame.add(new JLabel("DADI USCITI :" + " " + this.poleVaultManager.getDices()));

            if (!this.poleVaultManager.controlNumberDices()) {

                this.heartOfGame.add(new JLabel("TENTATIVO NULLO"));

                if (this.poleVaultManager.getMaxAttempts() >= 1) {
                    this.heartOfGame.add((new JLabel("RILANCI? " + " " + " " + "(tentativi:" + " " + this.poleVaultManager.getMaxAttempts() + ")")));
                }
            }

            else {
                this.heartOfGame.add(new JLabel("RISULTATO: " + " " + result));

                if (this.poleVaultManager.getMaxAttempts() >= 1) {
                    this.heartOfGame.add((new JLabel("RILANCI? " + " " + " " + "(tentativi:" + " " + this.poleVaultManager.getMaxAttempts() + ")")));
                }
            }

            this.heartOfGame.setLayout(new BoxLayout(this.heartOfGame,BoxLayout.Y_AXIS));
            this.add(this.heartOfGame);
            this.revalidate();

        }
          else {
            JOptionPane.showMessageDialog(this, "NUMERO DI TENTATIVI FINITI");
            this.raises.setEnabled(false);
        }
    }


    private void setHigh() {
        this.high = new JPanel();
        this.high.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.high.add(new JLabel("E' il turno di :  " + this.poleVaultManager.getActivePlayer()));
        this.high.add(new JLabel());
        this.high.add(new JLabel("CHE ALTEZZA VUOI RAGGIUNGERE? (tra 8 e 48)"));

        this.highField = new JTextField(20);
        this.high.add(this.highField);
        this.revalidate();

        this.settingHigh = new JButton("IMPOSTA L' ALTEZZA");
        this.high.add(this.settingHigh);
        this.add(this.high);
        this.revalidate();
        this.settingHigh.addActionListener(e -> hanldeClickOnSetHigh());
    }


    private void setDices() {
        this.dices = new JPanel();
        this.dices.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.dices.add(new JLabel("E' il turno di :  " + this.poleVaultManager.getActivePlayer()));
        this.dices.add(new JLabel());
        this.dices.add(new JLabel("QUANTI DADI VUOI USARE? (tra 2 e 8)"));

        this.dicesField = new JTextField(20);
        this.dices.add(this.dicesField);
        this.revalidate();

        this.settingDices= new JButton("IMPOSTA IL NUMERO DI DADI");
        this.dices.add(this.settingDices);
        this.add(this.dices);
        this.revalidate();
        this.settingDices.addActionListener(e -> hanldeClickOnSetDices());
    }


    private void bodyOfMiniGame() {
        this.revalidate();

        t1 = new Thread(() -> {
            int result = 0;
            try {
                this.counter = new Counter();
                this.timePanel = new JPanel();
                this.timePanel.add(new JLabel("TEMPO"));
                this.timePanel.setLayout( new FlowLayout(FlowLayout.RIGHT));
                this.counter.start();
                this.timePanel.add(this.counter);
                this.add(this.timePanel);


                this.heartOfGame = new JPanel();
                this.heartOfGame.add(new JLabel("E' il turno di :  " + this.poleVaultManager.getActivePlayer()));

                result = this.poleVaultManager.rollDices();

                this.heartOfGame.add(new JLabel("DADI USCITI :" + " " + this.poleVaultManager.getDices()));

               if (!this.poleVaultManager.controlNumberDices()) {

                   this.heartOfGame.add(new JLabel("TENTATIVO NULLO"));
                   this.heartOfGame.add((new JLabel("RILANCI? " + " " + " " + "(tentativi:" + " " + this.poleVaultManager.getMaxAttempts() + ")")));

               }

               else {
                   this.heartOfGame.add(new JLabel("RISULTATO: " + " " + result));
                   this.heartOfGame.add((new JLabel("RILANCI? " + " " + " " + "(tentativi:" + " " + this.poleVaultManager.getMaxAttempts() + ")")));
               }

                this.heartOfGame.setLayout(new BoxLayout(this.heartOfGame,BoxLayout.Y_AXIS));
                this.add(this.heartOfGame);
                this.revalidate();

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
            this.poleVaultManager.setHigh(valueHigh);

            this.high.removeAll();
            this.high.revalidate();

            this.setDices();

        } catch (WrongHighException e) {
            JOptionPane.showMessageDialog(this,e.getMessage());
        }
    }

    private void hanldeClickOnSetDices() {
        Integer numberDices = Integer.parseInt((this.dicesField.getText()));

        try {
            this.poleVaultManager.setNumberDices(numberDices);

            this.dices.removeAll();
            this.dices.revalidate();

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

            this.buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

            this.add(this.buttonPanel);

        } catch (WrongNumberDiceException e) {
            JOptionPane.showMessageDialog(this,e.getMessage());
        }


    }


    private void startOfMiniGame(){
        this.poleVaultManager.goGame();
    }


    private void initialize() {

        try {
            this.poleVaultManager.addPlayer();

        } catch (IOException e ){
            JOptionPane.showMessageDialog(this,"NON RIESCO AD APRIRE IL FILE, ELIMINARLO PER CONTINUARE");
            System.exit(-1);

        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this,"NON RIESCO A TROVARE IL FILE, ELIMINARLO PER CONTINUARE");
            System.exit(-1);

        }
    }
}
