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
     * Shuffles the deck and resets the current index.
     */
    public void shuffle() {
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

    /**
     * Returns the current index position in the deck.
     *
     * @return the current index position in the deck
     */
    public int getCurrentIndex() {
        return currentIndex;
    }

    /**
     * Returns the list of cards in the hand.
     *
     * @return the list of {@link Card} objects representing the cards in the hand
     */
    public List<Card> getCards() {
        return this.cards;
    }

    /**
     * Sets the cards in the deck to the specified list and resets
     * the current index to 0.
     *
     * @param cards the list of cards to set in the deck
     */
    public void setCards(List<Card> cards) {
        this.cards = cards;
        currentIndex = 0;
    }
}