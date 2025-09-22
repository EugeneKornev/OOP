import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a deck of playing cards.
 */
public class Deck {
    private List<Card> cards;
    private int currentIndex;

    /**
     * Constructs a deck with the specified number of standard 52-card decks.
     *
     * @param numberOfDecks the number of decks to include
     */
    public Deck(int numberOfDecks) {
        cards = new ArrayList<>();
        for (int i = 0; i < numberOfDecks; i++) {
            for (Suit suit : Suit.values()) {
                for (Rank rank : Rank.values()) {
                    cards.add(new Card(suit, rank));
                }
            }
        }
        shuffle();
    }

    /**
     * Constructs a deck with a specified cards.
     *
     * @param cards еhe list of cards objects to initialize the deck with
     */
    public Deck(List<Card> cards) {
        this.cards = new ArrayList<>(cards);
        currentIndex = 0;
    }

    /**
     * Shuffles the deck.
     */
    private void shuffle() {
        Collections.shuffle(cards);
        currentIndex = 0;
    }

    /**
     * Deals the next card from the deck.
     *
     * @return the next card from the deck
     * @throws IllegalStateException if there are no more cards in the deck
     */
    public Card dealCard() {
        if (currentIndex >= cards.size()) {
            throw new IllegalStateException("No more cards in the deck");
        }
        return cards.get(currentIndex++);
    }
}