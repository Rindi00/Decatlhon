package it.unimol.decathlon.GUI.firstScreen;

import javax.swing.*;
import java.awt.*;

public class FrameFirstScreen extends JFrame {


    public FrameFirstScreen() {
        this.setTitle("FIRST SCREEN");
        this.setMaximumSize(new Dimension(300,0));
        this.setSize(300,300);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.add(new PanelFirstScreen());
        this.setLocationRelativeTo(null);
    }


}
