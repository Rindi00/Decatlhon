package it.unimol.decathlon.app.miniGames;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JavelinThrowTest {

    @Test
    public void testRollDices() {
        JavelinThrow javelinThrow = new JavelinThrow();
        int result = javelinThrow.rollDices();
        assertNotEquals(0,result);
    }

    @Test
    public void testNumberDices() {
        JavelinThrow javelinThrow = new JavelinThrow();
        javelinThrow.decMaxDicesNumber();
        assertNotEquals(6,javelinThrow.getMaxNumberDices());
    }

}