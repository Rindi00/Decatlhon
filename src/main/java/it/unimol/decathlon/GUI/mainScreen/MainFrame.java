package it.unimol.decathlon.GUI.mainScreen;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        this.setTitle("MAIN FRAME");
        this.setMaximumSize(new Dimension(300, 0));
        this.setSize(300, 300);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.add(new MainPanel());
        this.setLocationRelativeTo(null);

    }

}
