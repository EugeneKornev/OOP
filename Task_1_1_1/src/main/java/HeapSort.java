
/** Class implementing the Heap Sort algorithm*/
public class HeapSort {

    /**
     * Private constructor to prevent instantiation
     */
    private HeapSort() {
    }

    /**
     * Sorts an array using heap sort algorithm
     * @param arr the array to be sorted
     */
    public static void sort(int[] arr) {
        if (arr == null || arr.length <= 1) return;

        int n = arr.length;

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        for (int i = n - 1; i > 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            heapify(arr, i, 0);
        }
    }

    /**
     * Transforms a subtree into a heap
     * @param arr the array
     * @param n heap size
     * @param i root index
     */
    private static void heapify(int[] arr, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }

        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }

        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            heapify(arr, n, largest);
        }
    }
}