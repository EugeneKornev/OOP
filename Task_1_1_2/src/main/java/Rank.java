/**
 * Enum representing the ranks of playing cards with their corresponding values.
 */
public enum Rank {
    TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10),
    JACK(10), QUEEN(10), KING(10), ACE(11);

    /**
     * The point value of the card rank in Blackjack.
     */
    public int value;

    /**
     * Constructs a Rank enum with the specified value.
     *
     * @param value the point value of the card rank
     */
    Rank(int value) {
        this.value = value;
    }
}