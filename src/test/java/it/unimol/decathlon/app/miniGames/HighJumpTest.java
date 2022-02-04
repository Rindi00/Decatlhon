package it.unimol.decathlon.app.miniGames;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HighJumpTest {
    @Test
    public void testResult() {
       HighJump highJump = new HighJump();
        assertEquals(highJump.rollDices(),highJump.getSum());
    }

    @Test
    public void testAttempts() {
        HighJump highJump = new HighJump();
        int attempts = highJump.getMaxAttempts();
        int attemptsDecrement = highJump.getMaxAttempts() -1;
        assertEquals(attemptsDecrement,attempts -1);
    }
}

