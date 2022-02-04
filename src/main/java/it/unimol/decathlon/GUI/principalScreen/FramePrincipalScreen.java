package it.unimol.decathlon.GUI.principalScreen;

import javax.swing.*;
import java.awt.*;

public class FramePrincipalScreen extends JFrame {

    public FramePrincipalScreen() {
        this.setTitle("PRINCIPAL SCREEN");
        this.setMaximumSize(new Dimension(300, 300));
        this.setSize(1000, 2000);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.add(new PanelPrincipalScreen());
        this.setLocationRelativeTo(getParent());
    }
}
