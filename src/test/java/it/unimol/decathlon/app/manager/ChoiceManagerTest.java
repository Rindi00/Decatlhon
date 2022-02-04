package it.unimol.decathlon.app.manager;

import it.unimol.decathlon.app.exceptions.WrongInputChoiceException;
import it.unimol.decathlon.app.exceptions.WrongNamePlayerException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ChoiceManagerTest {

    @Test
    public void testGetMeChoiceNumberGames() throws WrongInputChoiceException {

        assertEquals(10,ChoiceManager.getMeChoiceNumberGame(10));

    }

    @Test
    public void testGetMeNamePlayer() throws WrongNamePlayerException {

        assertEquals( "Antonio",ChoiceManager.getMeNamePlayer("Antonio"));

    }
}