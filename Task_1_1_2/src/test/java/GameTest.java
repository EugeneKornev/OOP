import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Game functionality
 */
public class GameTest {

    @Test
    public void testGameCreation() {
        Game game = new Game();
        assertNotNull(game);
    }

    @Test
    public void testInitialDeal() {
        Game game = new Game();
        game.dealInitialCards();

        assertEquals(2, game.getPlayer().getHand().getCards().size());
        assertEquals(2, game.getDealer().getHand().getCards().size());
    }

    @Test
    public void testRoundReset() {
        Game game = new Game();
        game.dealInitialCards();
        game.resetRound();

        assertEquals(0, game.getPlayer().getHand().getCards().size());
        assertEquals(0, game.getDealer().getHand().getCards().size());
    }
}