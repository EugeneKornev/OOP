import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Card functionality.
 */
public class CardTest {

    @Test
    public void testCardCreation() {
        Card card = new Card(Suit.HEARTS, Rank.ACE);
        assertNotNull(card);
        assertEquals(Suit.HEARTS, card.getSuit());
        assertEquals(Rank.ACE, card.getRank());
    }

    @Test
    public void testGetValue() {
        Card card = new Card(Suit.HEARTS, Rank.ACE);
        assertEquals(11, card.getValue());

        card = new Card(Suit.SPADES, Rank.KING);
        assertEquals(10, card.getValue());

        card = new Card(Suit.DIAMONDS, Rank.FIVE);
        assertEquals(5, card.getValue());
    }

    @Test
    public void testToString() {
        Card card = new Card(Suit.HEARTS, Rank.ACE);
        assertEquals("ACE of HEARTS", card.toString());
    }
}