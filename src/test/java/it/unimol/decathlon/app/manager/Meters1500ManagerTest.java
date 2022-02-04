package it.unimol.decathlon.app.manager;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Meters1500ManagerTest {

    @Test
    public void testRollDices() {
        Meters1500Manager meters1500Manager = new Meters1500Manager();
        meters1500Manager.goGame();

        assertNotEquals(0,meters1500Manager.rollDices());
    }

    @Test
    public void testDeleteResultNotFreeze() {
        Meters1500Manager meters1500Manager = new Meters1500Manager();

        meters1500Manager.goGame();
        meters1500Manager.rollDices();

        assertNotEquals(0,meters1500Manager.getScore());
    }

}