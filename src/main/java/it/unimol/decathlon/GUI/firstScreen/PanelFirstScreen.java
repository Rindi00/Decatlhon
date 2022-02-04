package it.unimol.decathlon.GUI.firstScreen;

import it.unimol.decathlon.GUI.principalScreen.FramePrincipalScreen;
import it.unimol.decathlon.app.exceptions.WrongNamePlayerException;
import it.unimol.decathlon.app.exceptions.WrongNumberPlayerException;
import it.unimol.decathlon.app.manager.ChoiceManager;
import it.unimol.decathlon.app.manager.PlayerManager;
import it.unimol.decathlon.app.player.Player;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class PanelFirstScreen extends JPanel {
    private JTextField namePlayerField;
    private JButton okButton;
    private JButton finishButton;
    private PlayerManager playerManager;
    private Player player;
    private FramePrincipalScreen framePrincipalScreen;


    public PanelFirstScreen() {
        try {

            this.playerManager = new PlayerManager();
        } catch (IOException e) {

            JOptionPane.showMessageDialog(this, "NON RIESCO A LEGGERE IL FILE, ELIMINARLO PER CONTINUARE");
            System.exit(-1);
        } catch (ClassNotFoundException e) {

            JOptionPane.showMessageDialog(this, "NON RIESCO A TROVARE IL FILE");
            System.exit(-1);
        }

        this.setLayout(new FlowLayout());

        this.namePlayerField = new JTextField(20);
        this.okButton = new JButton("OK");
        this.finishButton = new JButton("SALVA I GIOCATORI E VAI AVANTI");

        this.add(new JLabel("INSERISCI IL NOME DEL GIOCATORE"));
        this.add(this.namePlayerField);

        this.add(new JLabel());
        this.add(this.okButton);

        this.add(new JLabel());
        this.add(this.finishButton);

        this.okButton.addActionListener(e -> handleOkClick());
        this.finishButton.addActionListener(e -> handleFinishClick());

    }

    private void handleFinishClick() {
        try {
            ChoiceManager.controlNumberPlayer(PlayerManager.getPlayers());
            Window window = SwingUtilities.getWindowAncestor(this);
            window.dispose();
            this.playerManager.saveListOfPlayer();
            this.framePrincipalScreen = new FramePrincipalScreen();
            framePrincipalScreen.setVisible(true);


        } catch (WrongNumberPlayerException e) {

            JOptionPane.showMessageDialog(this, e.getMessage());
        }

    }

    private void handleOkClick() {
        try {
                String namePlayer = this.namePlayerField.getText();

                ChoiceManager.getMeNamePlayer(namePlayer);

                this.player = new Player(namePlayer);

                this.playerManager.addPlayer(this.player);

            } catch (WrongNamePlayerException e) {

                JOptionPane.showMessageDialog(this,"NOME NON CONSENTITO" + " " + e.getMessage());
        }
    }
}
