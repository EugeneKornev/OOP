import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Hand functionality
 */
public class HandTest {

    @Test
    public void testEmptyHand() {
        Hand hand = new Hand();
        assertEquals(0, hand.calculateTotal());
        assertEquals(0, hand.getCards().size());
    }

    @Test
    public void testAddCardToHand() {
        Hand hand = new Hand();
        Card card = new Card(Suit.HEARTS, Rank.ACE);
        hand.addCard(card);

        assertEquals(1, hand.getCards().size());
        assertEquals(card, hand.getCards().get(0));
    }

    @Test
    public void testCalculateTotalBasic() {
        Hand hand = new Hand();
        hand.addCard(new Card(Suit.HEARTS, Rank.TEN));
        hand.addCard(new Card(Suit.SPADES, Rank.SEVEN));
        assertEquals(17, hand.calculateTotal());
    }

    @Test
    public void testAceValueAdjustment() {
        Hand hand = new Hand();
        hand.addCard(new Card(Suit.HEARTS, Rank.ACE));
        hand.addCard(new Card(Suit.SPADES, Rank.TEN));
        hand.addCard(new Card(Suit.DIAMONDS, Rank.FIVE));
        assertEquals(16, hand.calculateTotal()); // Ace becomes 1
    }

    @Test
    public void testBlackjackDetection() {
        Hand hand = new Hand();
        hand.addCard(new Card(Suit.HEARTS, Rank.ACE));
        hand.addCard(new Card(Suit.SPADES, Rank.TEN));
        assertTrue(hand.isBlackjack());

        hand = new Hand();
        hand.addCard(new Card(Suit.HEARTS, Rank.ACE));
        hand.addCard(new Card(Suit.SPADES, Rank.SEVEN));
        hand.addCard(new Card(Suit.DIAMONDS, Rank.THREE));
        assertFalse(hand.isBlackjack());
    }
}