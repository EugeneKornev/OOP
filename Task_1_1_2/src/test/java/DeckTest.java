import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.ArrayList;
import java.util.List;


/**
 * Test class for Deck functionality.
 */
class DeckTest {

    @Test
    void deckInitialization() {
        Deck deck = new Deck(1);
        int cardCount = 0;
        while (true) {
            try {
                Card card = deck.dealCard();
                assertNotNull(card);
                cardCount++;
            } catch (IllegalStateException e) {
                break; // Колода пуста
            }
        }
        assertEquals(52, cardCount);
    }

    @Test
    void dealCardReturnsCardsInSequence() {
        List<Card> testCards = new ArrayList<>();
        testCards.add(new Card(Suit.HEARTS, Rank.ACE));
        testCards.add(new Card(Suit.HEARTS, Rank.TWO));
        testCards.add(new Card(Suit.HEARTS, Rank.THREE));

        Deck deck = new Deck(testCards);

        assertEquals(testCards.get(0), deck.dealCard());
        assertEquals(testCards.get(1), deck.dealCard());
        assertEquals(testCards.get(2), deck.dealCard());
    }

    @Test
    void dealCardThrowsWhenEmpty() {
        // Создаем колоду с одной картой
        List<Card> singleCard = new ArrayList<>();
        singleCard.add(new Card(Suit.HEARTS, Rank.ACE));

        Deck deck = new Deck(singleCard);

        // Раздаем единственную карту
        deck.dealCard();

        // Проверяем, что следующая попытка раздать карту вызывает исключение
        assertThrows(IllegalStateException.class, deck::dealCard);
    }
}