import java.util.ArrayList;
import java.util.List;

/**
 * Represents a hand of cards held by a player or dealer.
 */
public class Hand {
    private List<Card> cards;
    static final int boundary = 21;

    /**
     * Constructs an empty hand.
     */
    public Hand() {
        cards = new ArrayList<>();
    }

    /**
     * Adds a card to the hand.
     *
     * @param card the card to add
     */
    public void addCard(Card card) {
        cards.add(card);
    }

    /**
     * Calculates the total value of the hand according to Blackjack rules.
     * Aces are worth 11 unless that would cause the hand to bust, in which case they are worth 1.
     *
     * @return the total value of the hand
     */
    public int calculateTotal() {
        int total = 0;
        int aceCount = 0;
        for (Card card : cards) {
            total += card.getValue();
            if (card.getRank() == Rank.ACE) {
                aceCount++;
            }
        }
        while (total > boundary && aceCount > 0) {
            total -= 10;
            aceCount--;
        }
        return total;
    }

    /**
     * Returns the list of cards in the hand.
     *
     * @return the list of cards
     */
    public List<Card> getCards() {
        return cards;
    }

    /**
     * Checks if the hand is a Blackjack (an Ace and a 10-value card).
     *
     * @return true if the hand is a Blackjack, false otherwise
     */
    public boolean isBlackjack() {
        return cards.size() == 2 && calculateTotal() == 21;
    }
}