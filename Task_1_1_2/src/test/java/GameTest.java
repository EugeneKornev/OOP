import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.Test;


/**
 * Test class for Game functionality.
 */
class GameTest {
    private Game game;

    @Test
    void resetRoundClearsHands() {
        Game game = new Game();

        game.getPlayer().takeCard(new Card(Suit.HEARTS, Rank.ACE));
        game.getDealer().takeCard(new Card(Suit.DIAMONDS, Rank.TEN));

        game.resetRound();

        assertEquals(0, game.getPlayer().getHand().getCards().size());
        assertEquals(0, game.getDealer().getHand().getCards().size());
        assertEquals(0, game.getPlayer().getHand().calculateTotal());
        assertEquals(0, game.getDealer().getHand().calculateTotal());
        assertFalse(game.getPlayer().isBusted());
        assertFalse(game.getDealer().isBusted());
        assertFalse(game.getPlayer().hasBlackjack());
        assertFalse(game.getDealer().hasBlackjack());
    }

    @Test
    void dealInitialCardsDealsTwoCardsEach() {
        Game game = new Game();
        game.dealInitialCards();

        assertEquals(2, game.getPlayer().getHand().getCards().size());
        assertEquals(2, game.getDealer().getHand().getCards().size());

        assertTrue(game.getPlayer().getHand().calculateTotal() > 0);
        assertTrue(game.getDealer().getHand().calculateTotal() > 0);

        assertFalse(game.getPlayer().isBusted());
        assertFalse(game.getDealer().isBusted());
    }

    @Test
    void playerBusts() {
        Game game = new Game();

        game.getPlayer().takeCard(new Card(Suit.HEARTS, Rank.TEN));
        game.getPlayer().takeCard(new Card(Suit.DIAMONDS, Rank.TEN));
        game.getPlayer().takeCard(new Card(Suit.CLUBS, Rank.TWO));

        assertTrue(game.getPlayer().isBusted());
        assertEquals(22, game.getPlayer().getHand().calculateTotal());
        assertFalse(game.getPlayer().hasBlackjack());

        assertFalse(game.getDealer().isBusted());
        assertEquals(0, game.getDealer().getHand().calculateTotal());
    }

    @Test
    void gameInitialization() {
        Game game = new Game();
        assertNotNull(game.getPlayer());
        assertNotNull(game.getDealer());
        assertNotNull(game.getDeck());

        assertEquals(0, game.getPlayer().getHand().getCards().size());
        assertEquals(0, game.getDealer().getHand().getCards().size());
        assertFalse(game.getPlayer().isBusted());
        assertFalse(game.getDealer().isBusted());
        assertFalse(game.getPlayer().hasBlackjack());
        assertFalse(game.getDealer().hasBlackjack());
    }

    @Test
    void playerBlackjack() {
        List<Card> cards = List.of(
                new Card(Suit.HEARTS, Rank.ACE),
                new Card(Suit.DIAMONDS, Rank.FIVE),
                new Card(Suit.SPADES, Rank.KING),
                new Card(Suit.CLUBS, Rank.SEVEN)
        );
        Deck mockDeck = new Deck(cards);

        game = new Game(mockDeck);
        game.dealInitialCards();

        assertTrue(game.getPlayer().hasBlackjack());
        assertEquals(21, game.getPlayer().getHand().calculateTotal());
        assertFalse(game.getPlayer().isBusted());

        assertFalse(game.getDealer().hasBlackjack());
        assertEquals(12, game.getDealer().getHand().calculateTotal());
        assertFalse(game.getDealer().isBusted());
    }

    @Test
    void gameWithFixedDeckPlayerWins() {
        List<Card> fixedCards = List.of(
                new Card(Suit.HEARTS, Rank.TEN),
                new Card(Suit.SPADES, Rank.SEVEN),
                new Card(Suit.DIAMONDS, Rank.EIGHT),
                new Card(Suit.CLUBS, Rank.SIX),
                new Card(Suit.HEARTS, Rank.FOUR)
        );

        Deck fixedDeck = new Deck(fixedCards);

        game = new Game(fixedDeck, new Scanner("s\n"));
        String result = game.playRound();

        assertEquals("player_win", result);
        assertFalse(game.getPlayer().isBusted());
        assertFalse(game.getDealer().isBusted());
        assertTrue(game.getPlayer().getHand().calculateTotal()
                > game.getDealer().getHand().calculateTotal());

        assertEquals(18, game.getPlayer().getHand().calculateTotal());
        assertEquals(17, game.getDealer().getHand().calculateTotal());

        assertFalse(game.getPlayer().hasBlackjack());
        assertFalse(game.getDealer().hasBlackjack());
    }

    @Test
    void gameFlowWithPlayerHitting() {
        List<Card> cards = List.of(
                new Card(Suit.HEARTS, Rank.ACE),
                new Card(Suit.SPADES, Rank.KING),
                new Card(Suit.DIAMONDS, Rank.THREE),
                new Card(Suit.CLUBS, Rank.QUEEN),
                new Card(Suit.HEARTS, Rank.FOUR)
        );
        Deck deck = new Deck(cards);

        String input = "h\ns\n";
        Scanner scanner = new Scanner(input);

        Game game = new Game(deck, scanner);
        String result = game.playRound();

        assertEquals("dealer_win", result);
        assertEquals(18, game.getPlayer().getHand().calculateTotal());
        assertEquals(20, game.getDealer().getHand().calculateTotal());
        assertFalse(game.getPlayer().isBusted());
        assertFalse(game.getPlayer().hasBlackjack());
        assertFalse(game.getDealer().isBusted());
        assertFalse(game.getDealer().hasBlackjack());

        assertEquals(3, game.getPlayer().getHand().getCards().size());
        assertEquals(2, game.getDealer().getHand().getCards().size());
    }

    @Test
    void testPlayerWinByPoints() {
        List<Card> cards = List.of(
                new Card(Suit.HEARTS, Rank.TEN),
                new Card(Suit.SPADES, Rank.SEVEN),
                new Card(Suit.DIAMONDS, Rank.EIGHT),
                new Card(Suit.CLUBS, Rank.KING)
        );
        Deck deck = new Deck(cards);

        Scanner scanner = new Scanner("s\n");
        Game game = new Game(deck, scanner);
        String result = game.playRound();

        assertEquals("player_win", result);
        assertEquals(18, game.getPlayer().getHand().calculateTotal());
        assertEquals(17, game.getDealer().getHand().calculateTotal());
        assertFalse(game.getPlayer().isBusted());
        assertFalse(game.getPlayer().hasBlackjack());
        assertFalse(game.getDealer().isBusted());
        assertFalse(game.getDealer().hasBlackjack());
    }

    @Test
    void gameFlowWithPlayerStanding() {
        List<Card> cards = List.of(
                new Card(Suit.HEARTS, Rank.TEN),
                new Card(Suit.SPADES, Rank.KING),
                new Card(Suit.DIAMONDS, Rank.SEVEN),
                new Card(Suit.CLUBS, Rank.QUEEN)
        );
        Deck deck = new Deck(cards);

        String input = "s\n";
        Scanner scanner = new Scanner(input);

        Game game = new Game(deck, scanner);
        String result = game.playRound();

        assertEquals("dealer_win", result);
        assertEquals(17, game.getPlayer().getHand().calculateTotal());
        assertEquals(20, game.getDealer().getHand().calculateTotal());
        assertFalse(game.getPlayer().isBusted());
        assertFalse(game.getPlayer().hasBlackjack());
        assertFalse(game.getDealer().isBusted());
        assertFalse(game.getDealer().hasBlackjack());

        assertEquals(2, game.getPlayer().getHand().getCards().size());
        assertEquals(2, game.getDealer().getHand().getCards().size());
    }

    @Test
    void gameFlowWithPlayerBusting() {
        List<Card> cards = List.of(
                new Card(Suit.HEARTS, Rank.TEN),
                new Card(Suit.SPADES, Rank.KING),
                new Card(Suit.DIAMONDS, Rank.SEVEN),
                new Card(Suit.CLUBS, Rank.QUEEN),
                new Card(Suit.HEARTS, Rank.FIVE)
        );
        Deck deck = new Deck(cards);

        String input = "h\n";
        Scanner scanner = new Scanner(input);

        Game game = new Game(deck, scanner);
        String result = game.playRound();

        assertEquals("dealer_win", result);
        assertTrue(game.getPlayer().isBusted());
        assertEquals(22, game.getPlayer().getHand().calculateTotal());
        assertFalse(game.getPlayer().hasBlackjack());
        assertFalse(game.getDealer().isBusted());
        assertEquals(20, game.getDealer().getHand().calculateTotal());
        assertFalse(game.getDealer().hasBlackjack());

        assertEquals(3, game.getPlayer().getHand().getCards().size());
        assertEquals(2, game.getDealer().getHand().getCards().size());
    }

    @Test
    void testTieByPoints() {
        List<Card> cards = List.of(
                new Card(Suit.HEARTS, Rank.TEN),
                new Card(Suit.SPADES, Rank.KING),
                new Card(Suit.DIAMONDS, Rank.QUEEN),
                new Card(Suit.CLUBS, Rank.KING)
        );
        Deck deck = new Deck(cards);

        Game game = new Game(deck, new Scanner("s\n"));
        String result = game.playRound();

        assertEquals("tie", result);
        assertEquals(20, game.getPlayer().getHand().calculateTotal());
        assertEquals(20, game.getDealer().getHand().calculateTotal());
        assertFalse(game.getPlayer().isBusted());
        assertFalse(game.getPlayer().hasBlackjack());
        assertFalse(game.getDealer().isBusted());
        assertFalse(game.getDealer().hasBlackjack());

        // Verify card counts
        assertEquals(2, game.getPlayer().getHand().getCards().size());
    }

        @Test
    void gameFlowWithDealerBusting() {
        List<Card> cards = List.of(
                new Card(Suit.HEARTS, Rank.TEN),
                new Card(Suit.SPADES, Rank.TEN),
                new Card(Suit.DIAMONDS, Rank.SIX),
                new Card(Suit.CLUBS, Rank.SIX),
                new Card(Suit.HEARTS, Rank.KING)
        );
        Deck deck = new Deck(cards);

        String input = "s\n";
        Scanner scanner = new Scanner(input);

        Game game = new Game(deck, scanner);
        String result = game.playRound();

        assertEquals("player_win", result);
        assertTrue(game.getDealer().isBusted());
        assertEquals(26, game.getDealer().getHand().calculateTotal());
        assertFalse(game.getPlayer().isBusted());
        assertEquals(16, game.getPlayer().getHand().calculateTotal());
        assertFalse(game.getPlayer().hasBlackjack());

        assertEquals(2, game.getPlayer().getHand().getCards().size());
        assertEquals(3, game.getDealer().getHand().getCards().size());
    }

    @Test
    void playRoundWithPlayerBlackjack() {
        List<Card> cards = List.of(
                new Card(Suit.HEARTS, Rank.ACE),
                new Card(Suit.DIAMONDS, Rank.KING),
                new Card(Suit.SPADES, Rank.TEN),
                new Card(Suit.CLUBS, Rank.SEVEN)
        );
        Deck deck = new Deck(cards);

        Game game = new Game(deck);
        String result = game.playRound();

        assertEquals("player_blackjack", result);
        assertTrue(game.getPlayer().hasBlackjack());
        assertFalse(game.getDealer().hasBlackjack());
        assertEquals(21, game.getPlayer().getHand().calculateTotal());
        assertEquals(17, game.getDealer().getHand().calculateTotal());
        assertFalse(game.getPlayer().isBusted());
        assertFalse(game.getDealer().isBusted());

        assertEquals(2, game.getPlayer().getHand().getCards().size());
        assertEquals(2, game.getDealer().getHand().getCards().size());
    }

    @Test
    void testDealerWinByPoints() {
        List<Card> cards = List.of(
                new Card(Suit.HEARTS, Rank.TEN),
                new Card(Suit.DIAMONDS, Rank.KING),
                new Card(Suit.SPADES, Rank.SEVEN),
                new Card(Suit.CLUBS, Rank.QUEEN)
        );
        Deck deck = new Deck(cards);

        Game game = new Game(deck, new Scanner("s\n"));
        String result = game.playRound();

        assertEquals("dealer_win", result);
        assertEquals(17, game.getPlayer().getHand().calculateTotal());
        assertEquals(20, game.getDealer().getHand().calculateTotal());
        assertFalse(game.getPlayer().isBusted());
        assertFalse(game.getPlayer().hasBlackjack());
        assertFalse(game.getDealer().isBusted());
        assertFalse(game.getDealer().hasBlackjack());

        // Verify card counts
        assertEquals(2, game.getPlayer().getHand().getCards().size());
        assertEquals(2, game.getDealer().getHand().getCards().size());
    }

    @Test
    void playRoundWithDealerBlackjack() {
        List<Card> cards = List.of(
                new Card(Suit.HEARTS, Rank.TEN),
                new Card(Suit.DIAMONDS, Rank.ACE),
                new Card(Suit.CLUBS, Rank.SEVEN),
                new Card(Suit.SPADES, Rank.KING)
        );
        Deck deck = new Deck(cards);

        Game game = new Game(deck);
        String result = game.playRound();

        assertEquals("dealer_blackjack", result);
        assertTrue(game.getDealer().hasBlackjack());
        assertFalse(game.getPlayer().hasBlackjack());
        assertEquals(17, game.getPlayer().getHand().calculateTotal());
        assertEquals(21, game.getDealer().getHand().calculateTotal());
        assertFalse(game.getPlayer().isBusted());
        assertFalse(game.getDealer().isBusted());

        assertEquals(2, game.getPlayer().getHand().getCards().size());
        assertEquals(2, game.getDealer().getHand().getCards().size());
    }

    @Test
    void playRoundWithBothBlackjack() {
        List<Card> cards = List.of(
                new Card(Suit.HEARTS, Rank.ACE),
                new Card(Suit.SPADES, Rank.ACE),
                new Card(Suit.DIAMONDS, Rank.KING),
                new Card(Suit.CLUBS, Rank.KING)
        );
        Deck deck = new Deck(cards);

        Game game = new Game(deck, new Scanner("s\n"));
        String result = game.playRound();

        assertEquals("tie", result);
        assertTrue(game.getPlayer().hasBlackjack());
        assertTrue(game.getDealer().hasBlackjack());
        assertEquals(21, game.getPlayer().getHand().calculateTotal());
        assertEquals(21, game.getDealer().getHand().calculateTotal());
        assertFalse(game.getPlayer().isBusted());
        assertFalse(game.getDealer().isBusted());

        assertEquals(2, game.getPlayer().getHand().getCards().size());
        assertEquals(2, game.getDealer().getHand().getCards().size());
    }

    @Test
    void playRoundWithPlayerBust() {
        List<Card> cards = List.of(
                new Card(Suit.HEARTS, Rank.TEN),
                new Card(Suit.DIAMONDS, Rank.SEVEN),
                new Card(Suit.SPADES, Rank.FIVE),
                new Card(Suit.CLUBS, Rank.TWO),
                new Card(Suit.HEARTS, Rank.KING)
        );
        Deck deck = new Deck(cards);

        String input = "h\n";
        Scanner scanner = new Scanner(input);

        Game game = new Game(deck, scanner);
        String result = game.playRound();

        assertEquals("dealer_win", result);
        assertTrue(game.getPlayer().isBusted());
        assertEquals(25, game.getPlayer().getHand().calculateTotal());
        assertFalse(game.getPlayer().hasBlackjack());
        assertFalse(game.getDealer().isBusted());
        assertEquals(9, game.getDealer().getHand().calculateTotal());
        assertFalse(game.getDealer().hasBlackjack());

        assertEquals(3, game.getPlayer().getHand().getCards().size());
        assertEquals(2, game.getDealer().getHand().getCards().size());
    }

    @Test
    void playRoundWithDealerBust() {
        List<Card> cards = List.of(
                new Card(Suit.HEARTS, Rank.TEN),
                new Card(Suit.DIAMONDS, Rank.SIX),
                new Card(Suit.SPADES, Rank.TEN),
                new Card(Suit.CLUBS, Rank.SIX),
                new Card(Suit.HEARTS, Rank.KING)
        );
        Deck deck = new Deck(cards);

        String input = "s\n";
        Scanner scanner = new Scanner(input);

        Game game = new Game(deck, scanner);
        String result = game.playRound();

        assertEquals("player_win", result);
        assertTrue(game.getDealer().isBusted());
        assertEquals(22, game.getDealer().getHand().calculateTotal());
        assertFalse(game.getPlayer().isBusted());
        assertEquals(20, game.getPlayer().getHand().calculateTotal());
        assertFalse(game.getPlayer().hasBlackjack());

        // Verify card counts
        assertEquals(2, game.getPlayer().getHand().getCards().size());
        assertEquals(3, game.getDealer().getHand().getCards().size());
    }
}