import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.InputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    void resetRoundClearsHands() {
        Game game = new Game();

        // Add cards to hands
        game.getPlayer().takeCard(new Card(Suit.HEARTS, Rank.ACE));
        game.getDealer().takeCard(new Card(Suit.DIAMONDS, Rank.TEN));

        // Reset round
        game.resetRound();

        // Verify hands are empty
        assertEquals(0, game.getPlayer().getHand().getCards().size());
        assertEquals(0, game.getDealer().getHand().getCards().size());
    }

    @Test
    void dealInitialCardsDealsTwoCardsEach() {
        Game game = new Game();
        game.dealInitialCards();

        assertEquals(2, game.getPlayer().getHand().getCards().size());
        assertEquals(2, game.getDealer().getHand().getCards().size());
    }

    @Test
    void playerHasBlackjack() {
        Game game = new Game();

        // Set up player with blackjack
        game.getPlayer().takeCard(new Card(Suit.HEARTS, Rank.ACE));
        game.getPlayer().takeCard(new Card(Suit.CLUBS, Rank.TEN));

        assertTrue(game.getPlayer().hasBlackjack());
    }

    @Test
    void dealerHasBlackjack() {
        Game game = new Game();

        // Set up dealer with blackjack
        game.getDealer().takeCard(new Card(Suit.DIAMONDS, Rank.ACE));
        game.getDealer().takeCard(new Card(Suit.SPADES, Rank.TEN));

        assertTrue(game.getDealer().hasBlackjack());
    }

    @Test
    void playerBusts() {
        Game game = new Game();

        // Set up player with bust hand
        game.getPlayer().takeCard(new Card(Suit.HEARTS, Rank.TEN));
        game.getPlayer().takeCard(new Card(Suit.DIAMONDS, Rank.TEN));
        game.getPlayer().takeCard(new Card(Suit.CLUBS, Rank.TWO));

        assertTrue(game.getPlayer().isBusted());
    }

    @Test
    void gameInitialization() {
        Game game = new Game();
        assertNotNull(game.getPlayer());
        assertNotNull(game.getDealer());
        assertNotNull(game.getDeck());
    }

    @Test
    void gameFlowWithPlayerStanding() {
        // Provide enough input for the entire game flow
        String input = "s\nn\n"; // Stand and don't play again
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Game game = new Game();
        game.start();

        String output = outputStream.toString();
        assertTrue(output.contains("Player's hand:"));
        assertTrue(output.contains("Dealer's hand:"));
    }

    @Test
    void gameFlowWithPlayerHitting() {
        // Provide enough input for the entire game flow
        String input = "h\ns\nn\n"; // Hit, then stand, then don't play again
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Game game = new Game();
        game.start();

        String output = outputStream.toString();
        assertTrue(output.contains("Do you want to (h)it or (s)tand?"));
    }

    @Test
    void playAgainChoice() {
        // Provide enough input for the entire game flow
        String input = "s\ny\ns\nn\n"; // Stand, play again, stand, don't play again
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Game game = new Game();
        game.start();

        String output = outputStream.toString();
        assertTrue(output.contains("Play again? (y/n):"));
    }
}