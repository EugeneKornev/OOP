import java.util.Scanner;

/**
 * Represents the main Blackjack game controller.
 * Manages the game flow, rounds, and determines the winner.
 */
public class Game {
    protected Deck deck;
    private Player player;
    private Dealer dealer;
    private Scanner scanner;

    /**
     * Constructs a new Blackjack game with a single deck, a player, and a dealer.
     */
    public Game() {
        deck = new Deck(1);
        player = new Player("Player");
        dealer = new Dealer();
        scanner = new Scanner(System.in);
    }

    /**
     * Resets the round by clearing both player's and dealer's hands.
     */
    public void resetRound() {
        player.getHand().getCards().clear();
        dealer.getHand().getCards().clear();
    }

    /**
     * Deals the initial two cards to both the player and the dealer.
     */
    public void dealInitialCards() {
        player.takeCard(deck.dealCard());
        dealer.takeCard(deck.dealCard());
        player.takeCard(deck.dealCard());
        dealer.takeCard(deck.dealCard());
    }

    /**
     * Prints the current status of the game, showing
     * the player's hand and the dealer's visible card.
     */
    private void printStatus() {
        System.out.println("Player's hand: " + player.getHand().getCards()
                + " | Total: " + player.getHand().calculateTotal());
        System.out.println("Dealer's hand: [HIDDEN, "
                + dealer.getHand().getCards().get(1) + "]");
    }

    /**
     * Starts the main game loop, managing rounds and determining winners.
     */
    public void start() {
        while (true) {
            resetRound();
            dealInitialCards();

            System.out.println("\nNew round starts!");
            printStatus();

            if (player.hasBlackjack() || dealer.hasBlackjack()) {
                if (player.hasBlackjack() && dealer.hasBlackjack()) {
                    System.out.println("Both have Blackjack! It's a tie.");
                } else if (player.hasBlackjack()) {
                    System.out.println("Blackjack! Player wins!");
                } else {
                    System.out.println("Dealer has Blackjack! Dealer wins.");
                }
            } else {
                player.makeDecision(deck, scanner);
                if (!player.isBusted()) {
                    dealer.makeDecision(deck);
                }

                int playerTotal = player.getHand().calculateTotal();
                int dealerTotal = dealer.getHand().calculateTotal();

                System.out.println("Player's total: " + playerTotal
                        + " | Dealer's total: " + dealerTotal);

                if (player.isBusted()) {
                    System.out.println("Dealer wins.");
                } else if (dealer.isBusted()) {
                    System.out.println("Player wins!");
                } else if (playerTotal > dealerTotal) {
                    System.out.println("Player wins!");
                } else if (playerTotal < dealerTotal) {
                    System.out.println("Dealer wins.");
                } else {
                    System.out.println("It's a tie.");
                }
            }

            System.out.print("Play again? (y/n): ");
            String choice = scanner.nextLine().trim().toLowerCase();
            if (!choice.equals("y")) {
                break;
            }
        }

    }

    /**
     * Returns the player instance of the game.
     * This method provides access to the player object for testing purposes.
     *
     * @return the Player object representing the human player in the game
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Returns the dealer instance of the game.
     * This method provides access to the dealer object for testing purposes.
     *
     * @return the Dealer object representing the computer-controlled dealer in the game
     */
    public Dealer getDealer() {
        return this.dealer;
    }

    /**
     * The main entry point for the Blackjack game.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }

    /**
     * Returns the deck instance of the game.
     *
     * @return the Deck object representing the game's card deck
     */
    public Deck getDeck() {
        return deck;
    }
}