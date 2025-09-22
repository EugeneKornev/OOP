import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


/**
 * Test class for Rank functionality.
 */
public class RankTest {

    @Test
    public void testRankValues() {
        assertEquals(13, Rank.values().length);
    }

    @Test
    public void testRankValuesCorrect() {
        assertEquals(2, Rank.TWO.value);
        assertEquals(10, Rank.TEN.value);
        assertEquals(10, Rank.JACK.value);
        assertEquals(10, Rank.QUEEN.value);
        assertEquals(10, Rank.KING.value);
        assertEquals(11, Rank.ACE.value);
    }
}