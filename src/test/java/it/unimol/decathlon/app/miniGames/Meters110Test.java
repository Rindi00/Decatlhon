package it.unimol.decathlon.app.miniGames;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Meters110Test {

    @Test
    public void testResult() {
        Meters110 meters110 = new Meters110();
        assertEquals(meters110.rollDices(),meters110.getResult());
    }

    @Test
    public void testAttempts() {
    Meters110 meters110 = new Meters110();
    int attempts = meters110.getMaxAttempts();
    int attemptsDecrement = meters110.getMaxAttempts() -1;

    assertEquals(attemptsDecrement,attempts -1);
    }
}