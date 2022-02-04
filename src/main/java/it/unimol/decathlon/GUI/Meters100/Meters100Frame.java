package it.unimol.decathlon.GUI.Meters100;

import javax.swing.*;
import java.awt.*;

public class Meters100Frame extends JFrame {

    public Meters100Frame() {
        this.setTitle("100 METRI");
        this.setMaximumSize(new Dimension(300, 300));
        this.setSize(3000, 500);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.add(new Meters100Panel());
        this.setLocationRelativeTo(null);
    }
}
