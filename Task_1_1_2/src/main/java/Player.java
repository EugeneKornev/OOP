import java.util.Scanner;

/**
 * Represents a player in the Blackjack game.
 */
public class Player {
    protected Hand hand;
    protected String name;

    /**
     * Constructs a player with the specified name.
     *
     * @param name the name of the player
     */
    public Player(String name) {
        this.name = name;
        hand = new Hand();
    }

    /**
     * Adds a card to the player's hand.
     *
     * @param card the card to add
     */
    public void takeCard(Card card) {
        hand.addCard(card);
    }

    /**
     * Checks if the player has busted (hand total exceeds 21).
     *
     * @return true if the player has busted, false otherwise
     */
    public boolean isBusted() {
        return hand.calculateTotal() > 21;
    }

    /**
     * Returns the player's hand.
     *
     * @return the player's hand
     */
    public Hand getHand() {
        return hand;
    }

    /**
     * Returns the player's name.
     *
     * @return the player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Checks if the player has a Blackjack.
     *
     * @return true if the player has a Blackjack, false otherwise
     */
    public boolean hasBlackjack() {
        return hand.isBlackjack();
    }

    /**
     * Allows the player to make a decision to hit or stand.
     *
     * @param deck the deck to draw cards from
     * @param scanner the Scanner object for user input
     */
    public void makeDecision(Deck deck, Scanner scanner) {
        while (true) {
            System.out.println("Your hand: " + hand.getCards() + " | Total: "
                    + hand.calculateTotal());
            System.out.print("Do you want to (h)it or (s)tand? ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("h")) {
                takeCard(deck.dealCard());
                if (isBusted()) {
                    System.out.println("You busted with total: " + hand.calculateTotal());
                    break;
                }
            } else if (input.equals("s")) {
                break;
            } else {
                System.out.println("Invalid input. Please enter 'h' or 's'.");
            }
        }
    }
}