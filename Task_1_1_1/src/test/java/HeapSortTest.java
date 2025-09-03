import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * Test class for HeapSort implementation.
 */
public class HeapSortTest {

    @Test
    public void testRandomArray() {
        int[] arr = {5, 3, 8, 1, 2};
        int[] expected = {1, 2, 3, 5, 8};
        HeapSort.sort(arr);
        assertArrayEquals(expected, arr);
    }

    @Test
    public void testAlreadySorted() {
        int[] arr = {1, 2, 3, 4, 5};
        HeapSort.sort(arr);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);
    }

    @Test
    public void testReverseSorted() {
        int[] arr = {5, 4, 3, 2, 1};
        HeapSort.sort(arr);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr);
    }

    @Test
    public void testDuplicateElements() {
        int[] arr = {3, 1, 2, 3, 1};
        HeapSort.sort(arr);
        assertArrayEquals(new int[]{1, 1, 2, 3, 3}, arr);
    }
}