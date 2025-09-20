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
    }

    @Test
    void dealInitialCardsDealsTwoCardsEach() {
        Game game = new Game();
        game.dealInitialCards();

        assertEquals(2, game.getPlayer().getHand().getCards().size());
        assertEquals(2, game.getDealer().getHand().getCards().size());
    }

    @Test
    void playerBusts() {
        Game game = new Game();

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
        assertFalse(game.getDealer().hasBlackjack());
    }

    @Test
    void gameWithFixedDeckPlayerWins() {
        List<Card> fixedCards = List.of(
                new Card(Suit.HEARTS, Rank.TEN),
                new Card(Suit.SPADES, Rank.SEVEN),
                new Card(Suit.DIAMONDS, Rank.EIGHT),
                new Card(Suit.CLUBS, Rank.SIX),
                new Card(Suit.HEARTS, Rank.TWO)
        );

        Deck fixedDeck = new Deck(fixedCards);

        game = new Game(fixedDeck);
        game.dealInitialCards();

        assertFalse(game.getPlayer().isBusted());
        assertFalse(game.getDealer().isBusted());
        assertTrue(game.getPlayer().getHand().calculateTotal()
                > game.getDealer().getHand().calculateTotal());
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

        String input = "h\ns\nn\n";
        Scanner scanner = new Scanner(input);

        Game game = new Game(deck, scanner);
        game.start();

        assertTrue(game.getPlayer().getHand().calculateTotal() > 0);
        assertTrue(game.getDealer().getHand().calculateTotal() > 0);
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

        String input = "s\nn\n";
        Scanner scanner = new Scanner(input);

        Game game = new Game(deck, scanner);
        game.start();

        assertTrue(game.getPlayer().getHand().calculateTotal() > 0);
        assertTrue(game.getDealer().getHand().calculateTotal() > 0);
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

        String input = "h\nn\n";
        Scanner scanner = new Scanner(input);

        Game game = new Game(deck, scanner);
        game.start();

        assertTrue(game.getPlayer().isBusted());
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

        String input = "s\nn\n";
        Scanner scanner = new Scanner(input);

        Game game = new Game(deck, scanner);
        game.start();

        assertTrue(game.getDealer().isBusted());
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
        game.playRound();

        assertTrue(game.getPlayer().hasBlackjack());
        assertFalse(game.getDealer().hasBlackjack());
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
        game.playRound();

        assertTrue(game.getDealer().hasBlackjack());
        assertFalse(game.getPlayer().hasBlackjack());
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
        game.playRound();

        assertTrue(game.getPlayer().isBusted());
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
        game.playRound();

        assertTrue(game.getDealer().isBusted());
    }
}