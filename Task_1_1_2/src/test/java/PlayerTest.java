import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


/**
 * Test class for Player functionality.
 */
class PlayerTest {

    @Test
    void playerBusts() {
        Player player = new Player("Test");
        player.takeCard(new Card(Suit.HEARTS, Rank.TEN));
        player.takeCard(new Card(Suit.DIAMONDS, Rank.TEN));
        player.takeCard(new Card(Suit.CLUBS, Rank.TWO));
        assertTrue(player.isBusted());
    }

}