package it.unimol.decathlon.app.manager;

import it.unimol.decathlon.app.exceptions.WrongDiceToSaveException;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.fail;


class DiscusThrowManagerTest {

    @Test
    public void testInputWhiteSpaceToFreeze() {
        DiscusThrowManager discusThrowManager = new DiscusThrowManager();
        Set<String > test = new HashSet<>();
        test.add("");
        try {
           discusThrowManager.freezeDices(test);
        } catch (WrongDiceToSaveException e) {
            return;
        }
        fail();
    }

    @Test
    public void testInputNonCommaSeparatedStringToFreeze() {
       DiscusThrowManager discusThrowManager = new DiscusThrowManager();
        Set<String> test = new HashSet<>();
        test.add("ADF");
        try {
            discusThrowManager.freezeDices(test);
        } catch (WrongDiceToSaveException e) {
            return;
        }
        fail();

    }

}