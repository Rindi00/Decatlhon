package it.unimol.decathlon.GUI.finalScreen;


import it.unimol.decathlon.app.manager.ManagerGeneralClassific;
import javax.swing.*;


public class PanelFinalScreen extends JPanel {

    public PanelFinalScreen() {
        this.add(new JLabel("######CLASSIFICA FINALE######"));
        this.add(new JLabel(String.valueOf(ManagerGeneralClassific.getClassific())));
        this.add(new JLabel("IL VINCITORE E' "));
        this.add(new JLabel(String.valueOf(ManagerGeneralClassific.getClassific().get(0))));
    }
}
