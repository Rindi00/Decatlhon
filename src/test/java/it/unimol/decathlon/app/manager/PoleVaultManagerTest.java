package it.unimol.decathlon.app.manager;

import it.unimol.decathlon.app.exceptions.WrongHighException;
import it.unimol.decathlon.app.exceptions.WrongNumberDiceException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.fail;

class PoleVaultManagerTest {

    @Test
    public void testCheckNumberDices() {
        PoleVaultManager poleVaultManager = new PoleVaultManager();
        try {
            poleVaultManager.setNumberDices(10);
        } catch (WrongNumberDiceException e) {
            return;
        }
        fail();
    }


    @Test
    public void testSetHigh() {
        PoleVaultManager poleVaultManager = new PoleVaultManager();
        poleVaultManager.goGame();
        try {
            poleVaultManager.setHigh(-1);
        } catch (WrongHighException e) {
            return;
        }
        fail();
    }


}