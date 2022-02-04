package it.unimol.decathlon.GUI.principalScreen;

import it.unimol.decathlon.GUI.Meters100.Meters100Frame;
import it.unimol.decathlon.GUI.discusThrow.DiscusThrowFrame;
import it.unimol.decathlon.GUI.highJump.HighJumpFrame;
import it.unimol.decathlon.GUI.javelinThrow.JavelinThrowFrame;
import it.unimol.decathlon.GUI.longJump.LongJumpFrame;
import it.unimol.decathlon.GUI.meters110.Meters110Frame;
import it.unimol.decathlon.GUI.meters1500.Meters1500Frame;
import it.unimol.decathlon.GUI.meters400.Meters400Frame;
import it.unimol.decathlon.GUI.poleVault.PoleVaultFrame;
import it.unimol.decathlon.GUI.shotPut.ShotPutFrame;
import it.unimol.decathlon.app.manager.ManagerGeneralClassific;
import javax.swing.*;
import java.awt.*;
import java.io.*;


public class PanelPrincipalScreen extends JPanel {
    private JButton highJumpButton;
    private JButton meters400Button;
    private JButton meters110Button;
    private JButton discusThrowButton;
    private JButton meters100Button;
    private JButton longJumpButton;
    private JButton shotPutButton;
    private JButton poleVaultButton;
    private JButton javelinThrowButton;
    private JButton meters1500Button;
    private JButton exitButton;
    private JPanel classific;
    private static boolean okClassific;

    private ManagerGeneralClassific managerGeneralClassific;
    private static final String STATUS110METERSBUTTON = "Button110MetersStatus.bin";
    private static final String STATUS1500METERSBUTTON = "Button1500MetersStatus.bin";
    private static final String STATUSHIGHJUMPBUTTON = "ButtonHighJumpStatus.bin";
    private static final String STATUSPOLEVAULTBUTTON = "ButtonPoleVaultStatus.bin";
    private static final String STATUSSHOTPUTBUTTON = "ButtonShotPutStatus.bin";
    private static final String STATUSMETERS400BUTTON = "ButtonMeters400Status.bin";
    private static final String STATUS100METERSBUTTON = "ButtonMeters100Status.bin";
    private static final String STATUSDISCUSTHROWBUTTON = "ButtonDiscusThrowStatus.bin";
    private static final String STATUSJAVELINTHROWBUTTON = "ButtonJavelinThrowStatus.bin";
    private static final String STATUSLONGJUMPBUTTON = "ButtonLongJumpStatus.bin";



    public PanelPrincipalScreen() {
        this.setLayout(new GridLayout(6, 0));

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
         this.controlStatusMeters110Button();
         this.controlStatusMeters1500Button();
         this.controlStatusHighJumpButton();
         this.controlStatusPoleVaultButton();
         this.controlStatusShotPutButton();
         this.controlStatusMeters400Button();
         this.controlStatusMeters100Button();
         this.controlStatusDiscusThrowButton();
         this.controlStatusJavelinThrowButton();
         this.controlStatusLongJumpButton();

        if (this.getOkClassific()) {
             this.printClassific();
         }
}



    private void initialize() {

        this.highJumpButton = new JButton("SALTO IN ALTO");
        this.meters400Button = new JButton("400 METRI");
        this.meters110Button = new JButton("110 METRI OSTACOLI");
        this.discusThrowButton = new JButton("LANCIO DEL DISCO");
        this.meters100Button = new JButton("100 METRI");
        this.longJumpButton = new JButton("SALTO IN LUNGO");
        this.shotPutButton = new JButton("LANCIO DEL PESO");
        this.poleVaultButton = new JButton("SALTO CON L' ASTA");
        this.javelinThrowButton = new JButton("LANCIO DEL GIAVELLOTTO");
        this.meters1500Button = new JButton("1500 METRI");
        this.exitButton = new JButton("ESCI");


        this.add(new JLabel());
        this.add(this.highJumpButton);

        this.add(new JLabel());
        this.add(this.meters400Button);

        this.add(new JLabel());
        this.add(this.meters110Button);

        this.add(new JLabel());
        this.add(this.discusThrowButton);

        this.add(new JLabel());
        this.add(this.meters100Button);

        this.add(new JLabel());
        this.add(this.longJumpButton);

        this.add(new JLabel());
        this.add(this.shotPutButton);

        this.add(new JLabel());
        this.add(this.poleVaultButton);

        this.add(new JLabel());
        this.add(this.javelinThrowButton);

        this.add(new JLabel());
        this.add(this.meters1500Button);

        this.add(new JLabel());
        this.add(this.exitButton);


        this.highJumpButton.addActionListener(e -> handleHighJumpClick());
        this.meters110Button.addActionListener(e -> handleMeters110Click());
        this.meters1500Button.addActionListener(e -> handleMeters1500Click());
        this.poleVaultButton.addActionListener(e -> handlePoleVaultClick());
        this.shotPutButton.addActionListener(e -> handleShotPutClick());
        this.meters400Button.addActionListener(e -> handleMeters400Click());
        this.meters100Button.addActionListener(e -> handleMeters100Click());
        this.discusThrowButton.addActionListener(e -> handleDiscusThrowClick());
        this.javelinThrowButton.addActionListener(e -> handleJavelinThrowClick());
        this.longJumpButton.addActionListener(e -> handleLongJumpClick());
        this.exitButton.addActionListener(e-> handleClickOnExit());

    }

    private void handleMeters1500Click() {
        Meters1500Frame frame = new Meters1500Frame();
        frame.setVisible(true);
        this.saveStatusMeters1500Button();
        Window window = SwingUtilities.getWindowAncestor(this);
        window.dispose();
    }


    private void controlStatusMeters1500Button() {
        File meters1500Button = new File(STATUS1500METERSBUTTON);

        if (meters1500Button.exists()) {

                this.meters1500Button.setEnabled(false);
        }
    }

    private void saveStatusMeters1500Button() {

        try (
                FileOutputStream fos = new FileOutputStream(this.getStatusMeters1500Button());
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fos);
        ) {
            objectOutputStream.writeObject("false");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,"non riesco a salvare il file, eliminarlo eliminarlo per continuare");
        }
    }


    private String getStatusMeters1500Button() {
        return STATUS1500METERSBUTTON;

    }


    private void handleMeters110Click() {
        Meters110Frame frame = new Meters110Frame();
        frame.setVisible(true);
        this.saveStatusMeters110Button();
        Window window = SwingUtilities.getWindowAncestor(this);
        window.dispose();
    }


    private void controlStatusMeters110Button() {
        File meters110Button = new File(STATUS110METERSBUTTON);

        if (meters110Button.exists()) {

            this.meters110Button.setEnabled(false);
        }
    }


    private void saveStatusMeters110Button() {

        try (
                FileOutputStream fos = new FileOutputStream(this.getStatusMeters110Button());
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fos);
        ) {
            objectOutputStream.writeObject("false");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,"non riesco a salvare il file, eliminarlo eliminarlo per continuare");
        }
    }

    private String getStatusMeters110Button() {
        return STATUS110METERSBUTTON;
    }


    private void handleHighJumpClick() {
            HighJumpFrame frame = new HighJumpFrame();
            frame.setVisible(true);
            this.saveStatusHighJumpButton();
            Window window = SwingUtilities.getWindowAncestor(this);
            window.dispose();
        }



        private void controlStatusHighJumpButton () {
            File highJumpButton= new File(STATUSHIGHJUMPBUTTON);

            if (highJumpButton.exists()) {

                this.highJumpButton.setEnabled(false);
            }
        }


        private void saveStatusHighJumpButton () {

            try (
                    FileOutputStream fos = new FileOutputStream(this.getStatusHighJumpButton());
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(fos);
            ) {
                objectOutputStream.writeObject("false");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "non riesco a salvare il file, eliminarlo eliminarlo per continuare");
            }
        }


    private String getStatusHighJumpButton() {
            return STATUSHIGHJUMPBUTTON;
    }


    private void handlePoleVaultClick() {
        PoleVaultFrame frame = new PoleVaultFrame();
        frame.setVisible(true);
        this.saveStatusPoleVaultButton();
        Window window = SwingUtilities.getWindowAncestor(this);
        window.dispose();
    }


    private void controlStatusPoleVaultButton() {
        File poleVaultButton = new File(STATUSPOLEVAULTBUTTON);

        if (poleVaultButton.exists()) {

            this.poleVaultButton.setEnabled(false);
        }
    }


    private void saveStatusPoleVaultButton() {

        try (
                FileOutputStream fos = new FileOutputStream(this.getStatusPoleVaultButton());
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fos);
        ) {
            objectOutputStream.writeObject("false");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,"non riesco a salvare il file, eliminarlo eliminarlo per continuare");
        }
    }


    private String getStatusPoleVaultButton() {
        return STATUSPOLEVAULTBUTTON;
    }


    private void handleMeters100Click() {
        Meters100Frame frame = new Meters100Frame();
        frame.setVisible(true);
        this.saveStatusMeters100Button();
        Window window = SwingUtilities.getWindowAncestor(this);
        window.dispose();
    }


    private void controlStatusMeters100Button() {
        File meters100Button = new File(STATUS100METERSBUTTON);

        if (meters100Button.exists()) {

            this.meters100Button.setEnabled(false);
        }
    }


    private void saveStatusMeters100Button() {

        try (
                FileOutputStream fos = new FileOutputStream(this.getStatusMeters100Button());
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fos);
        ) {
            objectOutputStream.writeObject("false");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,"non riesco a salvare il file, eliminarlo eliminarlo per continuare");
        }
    }


    private String getStatusMeters100Button() {
        return STATUS100METERSBUTTON;

    }


    private void handleShotPutClick() {
        ShotPutFrame frame = new ShotPutFrame();
        frame.setVisible(true);
        this.saveStatusShotputButton();
        Window window = SwingUtilities.getWindowAncestor(this);
        window.dispose();
    }


    private void controlStatusShotPutButton() {
        File shotPutButton = new File(STATUSSHOTPUTBUTTON);

        if (shotPutButton.exists()) {

            this.shotPutButton.setEnabled(false);
        }
    }


    private void saveStatusShotputButton() {

        try (
                FileOutputStream fos = new FileOutputStream(this.getStatusShotPutButton());
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fos);
        ) {
            objectOutputStream.writeObject("false");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,"non riesco a salvare il file, eliminarlo eliminarlo per continuare");
        }
    }


    private String getStatusShotPutButton() {
        return STATUSSHOTPUTBUTTON;
    }


    private void handleMeters400Click() {
        Meters400Frame frame = new Meters400Frame();
        frame.setVisible(true);
        this.saveStatusMeters400Button();
        Window window = SwingUtilities.getWindowAncestor(this);
        window.dispose();
    }


    private void controlStatusMeters400Button() {
        File meters400Button = new File(STATUSMETERS400BUTTON);

        if (meters400Button.exists()) {

            this.meters400Button.setEnabled(false);

        }
    }


    private void saveStatusMeters400Button() {

        try (
                FileOutputStream fos = new FileOutputStream(this.getStatusMeters400Button());
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fos);
        ) {
            objectOutputStream.writeObject("false");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,"non riesco a salvare il file, eliminarlo eliminarlo per continuare");
        }
    }


    private String getStatusMeters400Button() {
        return STATUSMETERS400BUTTON;
    }


    private void handleDiscusThrowClick() {
        DiscusThrowFrame frame = new DiscusThrowFrame();
        frame.setVisible(true);
        this.saveStatusDiscusThrowButton();
        Window window = SwingUtilities.getWindowAncestor(this);
        window.dispose();
    }


    private void controlStatusDiscusThrowButton() {
        File discusThrowButton = new File(STATUSDISCUSTHROWBUTTON);

        if (discusThrowButton.exists()) {

            this.discusThrowButton.setEnabled(false);

        }
    }


    private void saveStatusDiscusThrowButton() {

        try (
                FileOutputStream fos = new FileOutputStream(this.getStatusDiscusThrowButton());
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fos);
        ) {
            objectOutputStream.writeObject("false");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,"non riesco a salvare il file, eliminarlo eliminarlo per continuare");
        }
    }


    private String getStatusDiscusThrowButton() {
        return STATUSDISCUSTHROWBUTTON;
    }


    private void handleJavelinThrowClick() {
        JavelinThrowFrame frame = new JavelinThrowFrame();
        frame.setVisible(true);
        this.saveStatusJavelinThrowButton();
        Window window = SwingUtilities.getWindowAncestor(this);
        window.dispose();
    }


    private void controlStatusJavelinThrowButton() {
        File javelinThrowButton = new File(STATUSJAVELINTHROWBUTTON);

        if (javelinThrowButton.exists()) {

            this.javelinThrowButton.setEnabled(false);
        }
    }


    private void saveStatusJavelinThrowButton() {

        try (
                FileOutputStream fos = new FileOutputStream(this.getStatusJavelinThrowButton());
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fos);
        ) {
            objectOutputStream.writeObject("false");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,"non riesco a salvare il file, eliminarlo eliminarlo per continuare");
        }
    }


    private String getStatusJavelinThrowButton() {
        return STATUSJAVELINTHROWBUTTON;
    }


    private void handleLongJumpClick() {
        LongJumpFrame frame = new LongJumpFrame();
        frame.setVisible(true);
        this.saveStatusLongJumpButton();
        Window window = SwingUtilities.getWindowAncestor(this);
        window.dispose();
    }


    private void controlStatusLongJumpButton() {
        File longJumpButton = new File(STATUSLONGJUMPBUTTON);

        if (longJumpButton.exists()) {

            this.longJumpButton.setEnabled(false);
        }
    }


    private void saveStatusLongJumpButton() {

        try (
                FileOutputStream fos = new FileOutputStream(this.getStatusLongJumpButton());
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fos);
        ) {
            objectOutputStream.writeObject("false");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,"non riesco a salvare il file, eliminarlo eliminarlo per continuare");
        }
    }


    private String getStatusLongJumpButton() {
        return STATUSLONGJUMPBUTTON;
    }


    private void handleClickOnExit() {
        PanelPrincipalScreen.setOkClassific(false);
        Window window = SwingUtilities.getWindowAncestor(this);
        window.dispose();
    }


    private void printClassific() {
        this.classific = new JPanel();
        this.classific.add(new JLabel("######CLASSIFICA TEMPORANEA######"));
        this.classific.add(new JLabel(String.valueOf(ManagerGeneralClassific.getClassific())));
        this.add(new JLabel());
        this.add(this.classific);
        this.classific.revalidate();

    }

    public static void  setOkClassific(boolean condition) {
        okClassific = condition;
    }

    private  boolean getOkClassific() {
        return okClassific;
    }

}





