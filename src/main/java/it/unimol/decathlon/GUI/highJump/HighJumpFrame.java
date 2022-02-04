package it.unimol.decathlon.GUI.highJump;

import javax.swing.*;
import java.awt.*;

public class HighJumpFrame extends JFrame {

    public HighJumpFrame() {
        this.setTitle("SALTO IN ALTO");
        this.setMaximumSize(new Dimension(300, 300));
        this.setSize(1000, 800);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.add(new HighJumpPanel());
        this.setLocationRelativeTo(getParent());

    }
}
