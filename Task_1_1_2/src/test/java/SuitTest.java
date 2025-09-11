import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;


/**
 * Test class for Suit functionality.
 */
public class SuitTest {

    @Test
    public void testSuitValues() {
        assertEquals(4, Suit.values().length);
        assertArrayEquals(new Suit[]{Suit.HEARTS, Suit.DIAMONDS, Suit.CLUBS,
                Suit.SPADES}, Suit.values());
    }
}