/**
 * Represents the dealer in the Blackjack game.
 * Extends the Player class with dealer-specific behavior.
 */
public class Dealer extends Player {

    /**
     * Constructs a dealer with the name "Dealer".
     */
    public Dealer() {
        super("Dealer");
    }

    /**
     * Implements the dealer's decision-making process.
     * The dealer must hit until their hand totals 17 or higher.
     *
     * @param deck the deck to draw cards from
     */
    public void makeDecision(Deck deck) {
        System.out.println("Dealer's turn:");
        System.out.println("Dealer's hand: " + hand.getCards() + " | Total: " + hand.calculateTotal());
        while (hand.calculateTotal() < 17) {
            System.out.println("Dealer hits.");
            takeCard(deck.dealCard());
            System.out.println("Dealer's hand: " + hand.getCards() + " | Total: " + hand.calculateTotal());
            if (isBusted()) {
                System.out.println("Dealer busted!");
                break;
            }
        }
    }
}