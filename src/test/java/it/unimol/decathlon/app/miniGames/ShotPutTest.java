package it.unimol.decathlon.app.miniGames;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ShotPutTest {


    @Test
    public void testResult() {
        ShotPut shotput  = new ShotPut();

        assertEquals(shotput.rollDices(),shotput.getResult());
    }


    @Test
    public void testGetDices() {
        ShotPut shotPut = new ShotPut();

        shotPut.rollDices();
        shotPut.rollDices();

        assertEquals(2,shotPut.getDices().size());
    }
}