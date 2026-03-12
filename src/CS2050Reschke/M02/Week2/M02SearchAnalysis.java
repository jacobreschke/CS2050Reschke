package CS2050Reschke.M02.Week2;

public class M02SearchAnalysis
{
	public static void main(String[] args)
	{
		int[] sizes =
		{ 10, 100, 1000, 10000 };

		for (int n : sizes)
		{
			int[] data = new int[n];
			for (int i = 0; i < n; i++)
			{
				data[i] = i + 1; // sorted: 1..n
			}

			System.out.println("\n=== Array size " + n + " ===");

			// -----------------------------
			// LINEAR SEARCH TEST CASES
			// -----------------------------
			int linearBestKey = data[0]; // best: first element
			int linearMiddleKey = data[n / 2]; // middle: middle element
			int linearWorstFoundKey = data[n - 1]; // worst (found): last element
			int linearWorstNotFoundKey = n + 10; // worst (not found): absent

			System.out.println("-- Linear Search (Best / Middle / Worst) --");
			runLinearCase(data, "BEST (first element)", linearBestKey);
			runLinearCase(data, "MIDDLE (middle element)", linearMiddleKey);
			runLinearCase(data, "WORST (found at end)", linearWorstFoundKey);
			runLinearCase(data, "WORST (not found)", linearWorstNotFoundKey);

			// -----------------------------
			// BINARY SEARCH TEST CASES
			// -----------------------------
			int binaryBestKey = data[n / 2] - 1; // best: mid element found immediately
			int binaryMiddleKey = data[n / 4]; // middle-ish: usually a few steps
			int binaryWorstFoundKey = data[0]; // often near worst found case (end of range)
			int binaryWorstNotFoundKey = n + 10; // worst (not found): absent

			System.out.println("-- Binary Search (Best / Middle / Worst) --");
			runBinaryCase(data, "BEST (middle element)", binaryBestKey);
			runBinaryCase(data, "MIDDLE (quarter element)", binaryMiddleKey);
			runBinaryCase(data, "WORST (found near end)", binaryWorstFoundKey);
			runBinaryCase(data, "WORST (not found)", binaryWorstNotFoundKey);
		}
	}

	private static void runLinearCase(int[] data, String label, int key)
	{
		int comparisons = linearSearchComparisons(data, key);
		System.out.println("Linear " + label + ": key " + key + " -> comparisons = " + comparisons);
	}

	private static void runBinaryCase(int[] data, String label, int key)
	{
		int comparisons = binarySearchComparisons(data, key);
		System.out.println("Binary " + label + ": key " + key + " -> comparisons = " + comparisons);
	}

	// ---------- Linear Search: return comparisons ----------
	public static int linearSearchComparisons(int[] data, int key)
	{
		int comparisons = 0;

		for (int i = 0; i < data.length; i++)
		{
			comparisons++; // compare data[i] to key
			if (data[i] == key)
			{
				return comparisons;
			}
		}
		return comparisons; // not found
	}


	// ---------- Binary Search: return comparisons ----------
	// Counts each comparison between data[mid] and key as one comparison.
	public static int binarySearchComparisons(int[] data, int key)
	{
		int low = 0;
		int high = data.length - 1;
		int comparisons = 0;

		while (low <= high)
		{
			int mid = (low + high) / 2;

			comparisons++; // compare equality
			if (data[mid] == key)
			{
				return comparisons;
			}

			comparisons++; // compare less-than (only if not equal)
			if (data[mid] < key)
			{
				low = mid + 1;
			} else
			{
				high = mid - 1;
			}
		}

		return comparisons; // not found
	}
}

