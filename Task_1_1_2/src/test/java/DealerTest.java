
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DealerTest {
    private Dealer dealer;

    @BeforeEach
    void setUp() {
        dealer = new Dealer();
    }

    @Test
    void dealerHitsUntilSeventeenWithFixedDeck() {
        // Create a fixed deck
        List<Card> cards = List.of(
                new Card(Suit.HEARTS, Rank.TEN),
                new Card(Suit.SPADES, Rank.SIX),
                new Card(Suit.DIAMONDS, Rank.ACE)
        );

        Deck fixedDeck = new Deck(cards);

        dealer.takeCard(fixedDeck.dealCard());
        dealer.takeCard(fixedDeck.dealCard());

        dealer.makeDecision(fixedDeck);

        assertEquals(17, dealer.getHand().calculateTotal());
        assertEquals(3, dealer.getHand().getCards().size());
    }
}