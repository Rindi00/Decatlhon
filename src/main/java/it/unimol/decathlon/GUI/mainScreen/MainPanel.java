package it.unimol.decathlon.GUI.mainScreen;

import it.unimol.decathlon.GUI.finalScreen.FrameFinalScreen;
import it.unimol.decathlon.GUI.firstScreen.FrameFirstScreen;
import it.unimol.decathlon.GUI.principalScreen.FramePrincipalScreen;
import it.unimol.decathlon.app.manager.ManagerGeneralClassific;
import javax.swing.*;
import java.io.IOException;


public class MainPanel extends JPanel {

    private ManagerGeneralClassific managerGeneralClassific;

    public MainPanel() {
        try {

            this.managerGeneralClassific = new ManagerGeneralClassific();

        } catch (IOException e) {

            JOptionPane.showMessageDialog(this,"NON RIESCO A LEGGERE IL FILE, ELIMINARLO PER CONTINUARE");

            System.exit(-1);

        } catch (ClassNotFoundException e) {

           JOptionPane.showMessageDialog(this,"NON RIESCO A TROVARE IL FILE");

            System.exit(-1);
        }

        if (!this.managerGeneralClassific.getFileExist()) {

            FrameFirstScreen frameFirstScreen = new FrameFirstScreen();
            frameFirstScreen.setVisible(true);

        }

        if (this.managerGeneralClassific.getFileExist() && this.managerGeneralClassific.getNumberOfGamesPlayed() != 10) {
            FramePrincipalScreen framePrincipalScreen = new FramePrincipalScreen();
            framePrincipalScreen.setVisible(true);

        }

            if (this.managerGeneralClassific.getNumberOfGamesPlayed() == 10) {
                FrameFinalScreen frameFinalScreen = new FrameFinalScreen();
                frameFinalScreen.setVisible(true);

        }
    }
}
