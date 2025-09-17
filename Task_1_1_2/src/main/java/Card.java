/**
 * Represents a playing card with a suit and rank.
 */
public class Card {
    final private Suit suit;
    final private Rank rank;

    /**
     * Constructs a Card with the specified suit and rank.
     *
     * @param suit the suit of the card
     * @param rank the rank of the card
     */
    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    /**
     * Returns the value of the card in Blackjack.
     *
     * @return the point value of the card
     */
    public int getValue() {
        return rank.value;
    }

    /**
     * Returns a string representation of the card.
     *
     * @return a string in the format "RANK of SUIT"
     */
    @Override
    public String toString() {
        return rank + " of " + suit;
    }

    /**
     * Returns the suit of the card.
     *
     * @return the suit of the card
     */
    public Suit getSuit() {
        return this.suit;
    }

    /**
     * Returns the rank of the card.
     *
     * @return the rank of the card
     */
    public Rank getRank() {
        return this.rank;
    }
}