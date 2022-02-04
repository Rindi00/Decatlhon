package it.unimol.decathlon.app.miniGames;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Meters1500Test {

    @Test
    public void testResetDice() {
        Meters1500 meters1500 = new Meters1500();

        meters1500.resetDiceOut();

        assertEquals(0,meters1500.getDiceOut());
    }

    @Test
    public void testAttempts() {
        Meters1500 meters1500 = new Meters1500();
        int attempts = meters1500.getMaxAttempts();
        int attemptsDecrement = meters1500.getMaxAttempts() -1;

        assertEquals(attemptsDecrement,attempts -1);
    }
}

