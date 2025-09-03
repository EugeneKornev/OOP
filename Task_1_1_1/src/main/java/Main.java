import java.util.Arrays;

/**
 * Main class demonstrating heap sort functionality.
 */
public class Main {

    /**
     * Private constructor to prevent instantiation.
     */
    private Main() {
        // Utility class - no instantiation needed
    }

    /**
     * Application entry point.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        int[] arr = {5, 3, 8, 1, 2};
        System.out.println("Original array: " + Arrays.toString(arr));
        HeapSort.sort(arr);
        System.out.println("Sorted array: " + Arrays.toString(arr));
    }
}