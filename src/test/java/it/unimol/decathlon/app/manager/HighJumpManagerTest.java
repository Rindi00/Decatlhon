package it.unimol.decathlon.app.manager;

import it.unimol.decathlon.app.exceptions.WrongHighException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.fail;


class HighJumpManagerTest {

    @Test
    public void testSetLowHigh() {
        HighJumpManager highJumpManager = new HighJumpManager();
        highJumpManager.goGame();
        try {
            highJumpManager.setHigh(0);
        } catch (WrongHighException e) {
           return;
        }
        fail();
    }


    @Test
    public void testSetHighestHigh() {
        HighJumpManager highJumpManager = new HighJumpManager();
        highJumpManager.goGame();
        try {
            highJumpManager.setHigh(50);
        } catch (WrongHighException e) {
            return;
        }
        fail();
    }



}