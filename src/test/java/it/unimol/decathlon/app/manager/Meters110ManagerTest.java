package it.unimol.decathlon.app.manager;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Meters110ManagerTest {

    @Test
    public void testRollDices() {
        Meters110Manager meters110Manager = new Meters110Manager();
        meters110Manager.goGame();

        assertNotEquals(0,meters110Manager.rollDices());

    }


    @Test
    public void testGetDices() {
        Meters110Manager meters110Manager = new Meters110Manager();
        meters110Manager.goGame();
        meters110Manager.rollDices();

        assertNotEquals(true,meters110Manager.getDices().isEmpty());

    }



}