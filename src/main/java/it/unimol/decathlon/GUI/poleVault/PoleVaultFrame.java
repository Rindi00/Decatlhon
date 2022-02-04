package it.unimol.decathlon.GUI.poleVault;

import javax.swing.*;
import java.awt.*;

public class PoleVaultFrame extends JFrame {

    public PoleVaultFrame() {
        this.setTitle("SALTO CON L' ASTA");
        this.setMaximumSize(new Dimension(300, 300));
        this.setSize(3000, 500);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.add(new PoleVaultPanel());
        this.setLocationRelativeTo(getParent());

      }
    }

