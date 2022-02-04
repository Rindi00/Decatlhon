package it.unimol.decathlon.GUI.meters1500;

import javax.swing.*;
import java.awt.*;

public class Meters1500Frame extends JFrame {

    public Meters1500Frame() {
        this.setTitle("1500 METRI");
        this.setMaximumSize(new Dimension(300, 300));
        this.setSize(3000, 500);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.add(new Meters1500Panel());
        this.setLocationRelativeTo(null);
    }
}
