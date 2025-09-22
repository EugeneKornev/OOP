import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Test class for Hand functionality.
 */
class HandTest {

    @Test
    void calculateTotalWithAces() {
        Hand hand = new Hand();
        hand.addCard(new Card(Suit.HEARTS, Rank.ACE));
        hand.addCard(new Card(Suit.DIAMONDS, Rank.SEVEN));
        assertEquals(18, hand.calculateTotal());
    }

    @Test
    void isBlackjack() {
        Hand hand = new Hand();
        hand.addCard(new Card(Suit.HEARTS, Rank.ACE));
        hand.addCard(new Card(Suit.CLUBS, Rank.TEN));
        assertTrue(hand.isBlackjack());
    }
}