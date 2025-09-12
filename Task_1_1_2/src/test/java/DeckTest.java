import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;


/**
 * Test class for Deck functionality.
 */
class DeckTest {

    @Test
    void deckInitialization() {
        Deck deck = new Deck(1);
        assertEquals(52, deck.getCards().size());
    }

    @Test
    void shuffleResetsIndex() {
        Deck deck = new Deck(1);
        deck.dealCard();
        deck.shuffle();
        assertEquals(0, deck.getCurrentIndex());
    }

    @Test
    void dealCardThrowsWhenEmpty() {
        Deck deck = new Deck(1);
        for (int i = 0; i < 52; i++) {
            deck.dealCard();
        }
        assertThrows(IllegalStateException.class, deck::dealCard);
    }
}