import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for Dealer functionality.
 */
public class DealerTest {

    @Test
    public void testDealerCreation() {
        Dealer dealer = new Dealer();
        assertEquals("Dealer", dealer.getName());
    }

    @Test
    public void testDealerHitsBelow17() {
        Dealer dealer = new Dealer();
        Deck deck = new Deck(1);

        // Give dealer a low hand
        dealer.takeCard(new Card(Suit.HEARTS, Rank.TEN));
        dealer.takeCard(new Card(Suit.SPADES, Rank.SIX)); // Total = 16

        // Dealer should hit
        dealer.makeDecision(deck);

        // After hitting, dealer should have at least 3 cards
        assertTrue(dealer.getHand().getCards().size() >= 3);
    }
}