package it.unimol.decathlon.app.manager;

import it.unimol.decathlon.app.exceptions.WrongDiceToSaveException;
import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.fail;


class LongJumpManagerTest {

    @Test
    public void testWhiteSpaceToFreeze() {
        LongJumpManager longJumpManager = new LongJumpManager();
        Set<String> test = new HashSet<>();
        test.add("");
        try {
            longJumpManager.freezeDices(test);
        } catch (WrongDiceToSaveException e) {
            return;
        }
        fail();
    }

    @Test
    public void testInputNonCommaSeparatedStringToFreeze() {
        LongJumpManager longJumpManager = new LongJumpManager();
        Set<String> test = new HashSet<>();
        test.add("AWPF");
        try {
            longJumpManager.freezeDices(test);
        } catch (WrongDiceToSaveException e) {
           return;
        }
        fail();
    }

}