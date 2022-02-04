package it.unimol.decathlon.app.manager;

import it.unimol.decathlon.app.player.Player;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class PlayerManagerTest {

    @Test
    public void testAddPlayer() throws IOException, ClassNotFoundException {
        PlayerManager playerManager = null;

            playerManager = new PlayerManager();

        Player player = new Player("Antonio");
        playerManager.addPlayer(player);

        assertEquals(1,PlayerManager.getPlayers().size());
    }


    @Test
    public void testReturnPlayer() throws IOException, ClassNotFoundException {
        PlayerManager playerManager = null;

            playerManager = new PlayerManager();

        Player player = new Player("Antonio");
        playerManager.addPlayer(player);

        assertEquals(player,PlayerManager.getPlayers().get(0));
    }


}