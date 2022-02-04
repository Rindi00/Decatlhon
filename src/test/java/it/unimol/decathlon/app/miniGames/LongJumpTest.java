package it.unimol.decathlon.app.miniGames;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LongJumpTest {

    @Test
    public void testRollDice() {
        LongJump longJump = new LongJump();

        assertNotEquals(0,longJump.rollDices());
    }

}