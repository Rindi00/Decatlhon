package it.unimol.decathlon.app.miniGames;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PoleVaultTest {

    @Test
    public void testResult() {
        PoleVault poleVault = new PoleVault();
        assertEquals(poleVault.rollDices(),poleVault.getSum());
    }


    @Test
    public void testAttempts() {
        PoleVault poleVault = new PoleVault();
        int attempts = poleVault.getMaxAttempts();
        int attemptsDecrement = poleVault.getMaxAttempts() -1;

        assertEquals(attemptsDecrement,attempts -1);
    }



}