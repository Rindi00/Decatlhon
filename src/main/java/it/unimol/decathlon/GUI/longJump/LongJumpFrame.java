package it.unimol.decathlon.GUI.longJump;

import javax.swing.*;
import java.awt.*;

public class LongJumpFrame extends JFrame {
    public LongJumpFrame() {
        this.setTitle("SALTO IN LUNGO");
        this.setMaximumSize(new Dimension(3000, 500));
        this.setSize(3000, 500);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.add(new LongJumpPanel());
        this.setLocationRelativeTo(getParent());
    }
}
