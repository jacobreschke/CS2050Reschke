package CS2050Reschke.M02;

public class SelectionSortTDD {
    public static void main(String[] args) {
        System.out.println("Testing Selection Sort");

        int[][] testCases = {
                {4, 2, 7, 1, 5}, // Regular case
                {}, // Empty array
                {5}, // Single element
                {1, 2, 3, 4, 5}, // Already sorted
                {9, 7, 5, 3, 1}, // Reverse sorted
                {4, 2, 7, 2, 5} // Array with duplicates
        };

        for (int i = 0; i < testCases.length; i++) {
            System.out.println("Test Case " + (i + 1) + ": Before Sorting:");
            printArray(testCases[i]);
            selectionSort(testCases[i]);
            System.out.println("After Sorting:");
            printArray(testCases[i]);
            System.out.println();
        }
    }


    public static void selectionSort(int[] array) {

        for (int i = 0; i < array.length - 1; i++) {
            int currentMin = array[i];
            int currentMinIndex = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < currentMin) {
                    currentMin = array[j];
                    currentMinIndex = j;
                }
            }
            int tempNum = array[i];
            array[i] = array[currentMinIndex];
            array[currentMinIndex] = tempNum;
        }


    }

    public static void printArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }


}