package it.unimol.decathlon.GUI.meters110;

import javax.swing.*;
import java.awt.*;

public class Meters110Frame extends JFrame {

    public Meters110Frame() {

        this.setTitle("110 METRI OSTACOLI");
        this.setMaximumSize(new Dimension(300, 300));
        this.setSize(3000, 500);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.add(new Meters110Panel());
        this.setLocationRelativeTo(null);
    }
}
