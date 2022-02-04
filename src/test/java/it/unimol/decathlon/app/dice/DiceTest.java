package it.unimol.decathlon.app.dice;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiceTest {

    @Test
    public void testRollDice() {
        Dice dice = new Dice();
        assertNotEquals(0,dice.rollDice());

    }
}