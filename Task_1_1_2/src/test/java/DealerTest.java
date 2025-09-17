import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

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

        Deck fixedDeck = new Deck(0);
        fixedDeck.getCards().addAll(cards);

        dealer.takeCard(fixedDeck.dealCard());
        dealer.takeCard(fixedDeck.dealCard());

        dealer.makeDecision(fixedDeck);

        assertEquals(17, dealer.getHand().calculateTotal());
        assertEquals(3, dealer.getHand().getCards().size());
    }
}