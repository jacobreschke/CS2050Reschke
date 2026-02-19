package CS2050Reschke.M02.Week1;

import java.util.Arrays;
import java.util.Scanner;

public class ArraysTesting {


    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        int arraySize = 50;
        int[] intArray = new int[arraySize];

        for (int i = 0; i < intArray.length; i++) {
            intArray[i] = (int) (Math.random() * 10);
        }

        for (int i = 0; i < intArray.length; i++) {
            System.out.print(intArray[i] + ", ");
        }

        System.out.println(" ");
        Arrays.sort(intArray);

        for (int i = 0; i < intArray.length; i++) {
            System.out.print(intArray[i] + ", ");
        }

        System.out.println(" ");

        int key = 2;
        int firstArrayIndex = Arrays.binarySearch(intArray, key);


        for (int i = firstArrayIndex; i < intArray.length; i++) {
            if (intArray[i] == key) {
                System.out.println(i);
            } else {
                break;
            }
        }

    }
}