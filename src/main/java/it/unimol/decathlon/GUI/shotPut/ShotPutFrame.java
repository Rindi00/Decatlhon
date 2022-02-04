package it.unimol.decathlon.GUI.shotPut;

import javax.swing.*;
import java.awt.*;

public class ShotPutFrame extends JFrame {

    public ShotPutFrame() {
        this.setTitle("LANCIO DEL PESO");
        this.setMaximumSize(new Dimension(3000, 500));
        this.setSize(3000, 500);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.add(new ShotPutPanel());
        this.setLocationRelativeTo(getParent());
    }
}
