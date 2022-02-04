package it.unimol.decathlon.app.manager;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Meters100ManagerTest {

    @Test
    public void testGetDice1Out() {
        Meters100Manager meters100Manager = new Meters100Manager();
        meters100Manager.goGame();
        meters100Manager.rollDices();

        assertNotEquals(0, meters100Manager.getDice1Out());
    }

    @Test
    public void testGetScore() {
        Meters100Manager meters100Manager = new Meters100Manager();
        meters100Manager.goGame();
        meters100Manager.rollDices();

        assertNotEquals(0, meters100Manager.getScore());
    }
}