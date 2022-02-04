package it.unimol.decathlon.app.miniGames;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Meters400Test {

    @Test
    public void testAttempts() {
        Meters400 meters400 = new Meters400();
        int attempts = meters400.getMaxAttempts();
        int attemptsDecrement = meters400.getMaxAttempts() -2;

        assertEquals(attemptsDecrement,attempts -2);
    }

    @Test
    public void testRollDices() {
        Meters400 meters400 = new Meters400();
        int totalResult = meters400.rollDices();
        int sumResult = meters400.getResult1() + meters400.getResult2();

        assertEquals(totalResult,sumResult);
    }

}