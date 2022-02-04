package it.unimol.decathlon.app.manager;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Meters400ManagerTest  {

    @Test
    public void testGetResult() {
        Meters400Manager meters400Manager = new Meters400Manager();
        meters400Manager.goGame();
        meters400Manager.rollDices();

        assertNotEquals(0,meters400Manager.getScore());
    }

    @Test
    public void testDice1Out() {
        Meters400Manager meters400Manager = new Meters400Manager();
        meters400Manager.goGame();
        meters400Manager.rollDices();

        assertNotEquals(0,meters400Manager.getDice1Out());
    }


}