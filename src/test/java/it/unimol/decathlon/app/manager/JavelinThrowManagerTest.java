package it.unimol.decathlon.app.manager;

import it.unimol.decathlon.app.exceptions.WrongDiceToSaveException;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.fail;


class JavelinThrowManagerTest {

    @Test
    public void testWhiteSpaceToFreeze() {
        JavelinThrowManager javelinThrowManager = new JavelinThrowManager();
        Set<String > test = new HashSet<>();
        test.add("");
        try {
            javelinThrowManager.freezeDices(test);
        } catch (WrongDiceToSaveException e) {
            return;
        }
        fail();
    }

    @Test
    public void testInputNonCommaSeparatedStringToFreeze() {
        JavelinThrowManager javelinThrowManager = new JavelinThrowManager();
        Set<String > test = new HashSet<>();
        test.add("ADF");
        try {
            javelinThrowManager.freezeDices(test);
        } catch (WrongDiceToSaveException e) {
            return;
        }
        fail();
    }

}