package CS2050Reschke.M02;

public class SelectionSortTesting {

    public static void main(String[] args) {

        int[] arr = {9, 4, 3, 5, 1};

        // Loop through full array
        // find smallest index
        // swap smallest with index 0


        System.out.print("Pre Swap: ");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.print("\n");

        for (int i = 0; i < arr.length - 1; i++) {
            int currentMin = arr[i];
            int currentMinIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < currentMin) {
                    currentMin = arr[j];
                    currentMinIndex = j;
                }
            }
            int tempNum = arr[i];
            arr[i] = arr[currentMinIndex];
            arr[currentMinIndex] = tempNum;
        }

        System.out.print("Post Swap: ");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }


    }
}
