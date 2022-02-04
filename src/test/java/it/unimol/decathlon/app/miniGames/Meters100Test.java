package it.unimol.decathlon.app.miniGames;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Meters100Test {

    @Test
    public void testAttempts() {
        Meters100 meters100 = new Meters100();
        int attempts = meters100.getMaxAttempts();
        int attemptsDecrement = meters100.getMaxAttempts() -4;

        assertEquals(attemptsDecrement,attempts -4);
    }

    @Test
    public void testRollDices() {
        Meters100 meters100 = new Meters100();
        int totalResult = meters100.rollDices();
        int sumResult = meters100.getResult1() + meters100.getResult2() +
                        meters100.getResult3() + meters100.getResult4();

        assertEquals(totalResult,sumResult);
    }

}