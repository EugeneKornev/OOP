import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

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
        Deck mockDeck = new Deck(0);
        mockDeck.getCards().addAll(cards);

        game = new Game(mockDeck);
        game.dealInitialCards();

        assertTrue(game.getPlayer().hasBlackjack());
        assertFalse(game.getDealer().hasBlackjack());
    }

    @Test
    void gameWithFixedDeckPlayerWins() {
        // Create a fixed deck where player wins
        List<Card> fixedCards = List.of(
                new Card(Suit.HEARTS, Rank.TEN),
                new Card(Suit.SPADES, Rank.SEVEN),
                new Card(Suit.DIAMONDS, Rank.EIGHT),
                new Card(Suit.CLUBS, Rank.SIX),
                new Card(Suit.HEARTS, Rank.TWO)
        );

        Deck fixedDeck = new Deck(0);
        fixedDeck.getCards().addAll(fixedCards);

        game = new Game(fixedDeck);
        game.dealInitialCards();

        // Проверки состояния игры
        assertFalse(game.getPlayer().isBusted());
        assertFalse(game.getDealer().isBusted());
        assertTrue(game.getPlayer().getHand().calculateTotal()
                > game.getDealer().getHand().calculateTotal());
    }

}