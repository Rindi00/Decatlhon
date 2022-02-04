package it.unimol.decathlon.GUI.javelinThrow;

import javax.swing.*;
import java.awt.*;

public class JavelinThrowFrame extends JFrame {

    public JavelinThrowFrame() {
        this.setTitle("LANCIO DEL GIAVELLOTTO");
        this.setMaximumSize(new Dimension(3000, 500));
        this.setSize(3000, 500);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.add(new JavelinThrowPanel());
        this.setLocationRelativeTo(getParent());
    }
}
