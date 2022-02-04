package it.unimol.decathlon.app.manager;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ShotPutManagerTest {

    @Test
    public void testGetTotal() {
        ShotPutManager shotPutManager = new ShotPutManager();
        shotPutManager.goGame();
        shotPutManager.rollDices();

        assertNotEquals(0,shotPutManager.getTotal());
    }


    @Test
    public void testScore() {
        ShotPutManager shotPutManager = new ShotPutManager();
        shotPutManager.goGame();
        shotPutManager.rollDices();
        shotPutManager.getTotal();

        if (shotPutManager.checkResult() == 1) {
            assertNotEquals(0, shotPutManager.getScore());

        } else {

            assertEquals(0, shotPutManager.getScore());
        }
    }

}