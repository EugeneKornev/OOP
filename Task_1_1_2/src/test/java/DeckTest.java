import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


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
    void dealCardReturnsCardsInSequence() {
        Deck deck = new Deck(1);
        List<Card> originalCards = new ArrayList<>(deck.getCards());

        Card firstDealt = deck.dealCard();
        Card secondDealt = deck.dealCard();

        assertEquals(originalCards.get(0), firstDealt);
        assertEquals(originalCards.get(1), secondDealt);
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