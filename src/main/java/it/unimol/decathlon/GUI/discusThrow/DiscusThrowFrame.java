package it.unimol.decathlon.GUI.discusThrow;

import javax.swing.*;
import java.awt.*;

public class DiscusThrowFrame extends JFrame {

    public DiscusThrowFrame() {
        this.setTitle("LANCIO DEL DISCO");
        this.setMaximumSize(new Dimension(3000, 500));
        this.setSize(3000, 500);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.add(new DiscusThrowPanel());
        this.setLocationRelativeTo(getParent());
    }
}
