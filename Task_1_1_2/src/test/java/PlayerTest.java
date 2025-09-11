import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


/**
 * Test class for Player functionality.
 */
public class PlayerTest {

    @Test
    public void testPlayerCreation() {
        Player player = new Player("TestPlayer");
        assertEquals("TestPlayer", player.getName());
        assertNotNull(player.getHand());
    }

    @Test
    public void testPlayerTakeCard() {
        Player player = new Player("TestPlayer");
        Card card = new Card(Suit.HEARTS, Rank.ACE);
        player.takeCard(card);

        assertEquals(1, player.getHand().getCards().size());
        assertEquals(card, player.getHand().getCards().get(0));
    }

    @Test
    public void testPlayerBust() {
        Player player = new Player("TestPlayer");
        player.takeCard(new Card(Suit.HEARTS, Rank.TEN));
        player.takeCard(new Card(Suit.SPADES, Rank.TEN));
        player.takeCard(new Card(Suit.DIAMONDS, Rank.FIVE));
        assertTrue(player.isBusted());
    }
}