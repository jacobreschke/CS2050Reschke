package CS2050Reschke.M02.Week1;

public class InsertionSort {

    public static void main(String[] args) {
        int[] array = new int[6];

        for (int i = 0; i < array.length; i++) {

        }

        printArray(array);
        insertionSort(array);
        printArray(array);
    }

    public static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {

            int current = arr[i];      // Value we want to insert
            int j = i - 1;             // Start comparing with previous element

            // Shift elements that are greater than current to the right
            while (j >= 0 && arr[j] > current) {
                arr[j + 1] = arr[j];
                j--;
            }

            // Insert current value into correct position
            arr[j + 1] = current;
        }
    }

    public static void printArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }


}
