package it.unimol.decathlon.app.miniGames;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DiscusThrowTest {

    @Test
    public void testRollDices() {
       DiscusThrow discusThrow = new DiscusThrow();
        int result = discusThrow.rollDices();
        assertNotEquals(0,result);
    }

    @Test
    public void testNumberDices() {
        DiscusThrow discusThrow = new DiscusThrow();
        discusThrow.decMaxDicesNumber();
        assertNotEquals(5,discusThrow.getMaxNumberDices());
    }

}