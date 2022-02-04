package it.unimol.decathlon.GUI.finalScreen;

import javax.swing.*;
import java.awt.*;

public class FrameFinalScreen extends JFrame {

    public FrameFinalScreen() {
        this.setTitle("FINAL SCREEN");
        this.setMaximumSize(new Dimension(300, 0));
        this.setSize(300, 300);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.add(new PanelFinalScreen());
        this.setLocationRelativeTo(null);
    }
}
