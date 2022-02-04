package it.unimol.decathlon.GUI.meters400;

import javax.swing.*;
import java.awt.*;

public class Meters400Frame extends JFrame {

    public Meters400Frame() {
        this.setTitle("400 METRI");
        this.setMaximumSize(new Dimension(300, 300));
        this.setSize(3000, 500);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.add(new Meters400Panel());
        this.setLocationRelativeTo(null);
    }
}
