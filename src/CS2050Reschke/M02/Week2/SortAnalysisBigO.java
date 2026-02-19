package CS2050Reschke.M02.Week2;

import java.util.Random;

public class SortAnalysisBigO
{
    public static void main(String[] args)
    {
        // We will test 3 different input sizes (matches the worksheet)
        int[] sizes =
                { 10, 100, 1000 };

        // Random with a fixed seed so the "random" numbers repeat every run.
        // This makes results consistent and easier to compare / grade.
        Random rand = new Random(42);

        // We will generate a few random arrays and average the comparison counts.
        int trials = 3;

        System.out.println("=== Selection vs Insertion Sort Comparison Counts ===");

        // Loop through each array size n
        for (int n : sizes)
        {
            System.out.printf("\nArray Size n = %d\n", n);

            // ====================================================
            // 1) REVERSED ARRAY (often worst-case for insertion sort)
            // ====================================================
            int[] reversed = new int[n];

            // Fill reversed so it goes: n, n-1, n-2, ..., 1
            for (int i = 0; i < n; i++)
                reversed[i] = n - i;

            // IMPORTANT: clone() makes a COPY of the array.
            // Sorting changes the array, so we copy it so both algorithms
            // get the same starting data.
            int selRev = selectionSortCount(reversed.clone());
            int insRev = insertionSortCount(reversed.clone());

            System.out.println("Reversed Array:");
            System.out.printf("  Selection: %d comparisons\n", selRev);
            System.out.printf("  Insertion: %d comparisons\n", insRev);

            // ====================================================
            // 2) SORTED ARRAY (best-case for insertion sort)
            // ====================================================
            int[] sorted = new int[n];

            // Fill sorted so it goes: 0, 1, 2, ..., n-1
            for (int i = 0; i < n; i++)
                sorted[i] = i;

            // clone() again so each algorithm sorts its own copy
            int selSorted = selectionSortCount(sorted.clone());
            int insSorted = insertionSortCount(sorted.clone());

            System.out.println("Sorted Array:");
            System.out.printf("  Selection: %d comparisons\n", selSorted);
            System.out.printf("  Insertion: %d comparisons\n", insSorted);

            // ====================================================
            // 3) RANDOM ARRAY (approximate "average case")
            // ====================================================
            int selRandomTotal = 0;
            int insRandomTotal = 0;

            // Do a few trials and average, because random can vary
            for (int t = 0; t < trials; t++)
            {
                int[] randomArray = new int[n];

                // Fill with random values (0 to n*10 - 1)
                for (int i = 0; i < n; i++)
                    randomArray[i] = rand.nextInt(n * 10);

                // clone() so selection and insertion each get the same random data
                selRandomTotal += selectionSortCount(randomArray.clone());
                insRandomTotal += insertionSortCount(randomArray.clone());
            }

            System.out.println("Random Array (Average of " + trials + " runs):");
            System.out.printf("  Selection: %d comparisons\n", selRandomTotal / trials);
            System.out.printf("  Insertion: %d comparisons\n", insRandomTotal / trials);
        }
    }

    // ----------------------------------------------------
    // Selection Sort – Counts VALUE comparisons
    // We count how many times we compare two values like:
    // a[j] < a[minIndex]
    // ----------------------------------------------------
    public static int selectionSortCount(int[] a)
    {
        int comparisons = 0;

        // i marks the start of the "unsorted" region
        for (int i = 0; i < a.length - 1; i++)
        {
            int minIndex = i; // assume the smallest value is at i

            // Scan the rest of the array to find the true smallest value
            for (int j = i + 1; j < a.length; j++)
            {
                comparisons++; // we are about to compare a[j] and a[minIndex]

                if (a[j] < a[minIndex])
                    minIndex = j; // found a new smallest value
            }

            // Swap the smallest value found into position i
            if (minIndex != i)
            {
                int temp = a[minIndex];
                a[minIndex] = a[i];
                a[i] = temp;
            }
        }

        return comparisons;
    }

    // ----------------------------------------------------
    // Insertion Sort – Counts VALUE comparisons
    // We count comparisons like:
    // a[j] > key
    // ----------------------------------------------------
    public static int insertionSortCount(int[] a)
    {
        int comparisons = 0;

        // i is the index of the element we are inserting into the sorted left side
        for (int i = 1; i < a.length; i++)
        {
            int key = a[i]; // the value we want to insert
            int j = i - 1; // start checking to the left of i

            // While we are still in bounds AND the left value is bigger than key,
            // shift that left value one space to the right.
            while (j >= 0 && a[j] > key)
            {
                comparisons++; // we compared a[j] > key and it was TRUE
                a[j + 1] = a[j]; // shift right
                j--;
            }

            // If j >= 0, the loop stopped because (a[j] > key) was FALSE.
            // That last comparison still happened, so count it once.
            if (j >= 0)
                comparisons++;

            // Insert key into the correct spot
            a[j + 1] = key;
        }

        return comparisons;
    }
}