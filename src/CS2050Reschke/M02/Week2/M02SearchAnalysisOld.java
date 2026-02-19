package CS2050Reschke.M02.Week2;

public class M02SearchAnalysisOld
{

	public static void main(String[] args)
	{
		// Array sizes to test
		int[] sizes =
		{ 10, 100, 1000, 10000, 100000 };

		for (int n : sizes)
		{
			// Build a sorted array [1, 2, 3, ..., n]
			int[] data = new int[n];
			for (int i = 0; i < n; i++)
			{
				data[i] = i + 1;
			}

			System.out.println("\n=== Array size " + n + " ===");

			// Pick keys for linear test cases
			int linearFirstKey = data[0]; // first element
			int linearMiddleKey = data[(n / 2)]; // middle element
			int linearLastKey = data[n - 1]; // last element
			int linearAbsentKey = n + 10; // guaranteed not present

			// Pick keys for binary test cases
			int binaryFirstKey = data[0]; // first element
			int binaryMiddleKey = data[(n / 2) - 1]; // middle element
			int binaryLastKey = data[n - 1]; // last element
			int binaryAbsentKey = n + 10; // guaranteed not present

			// Run Linear Search
			System.out.println("-- Linear Search --");
			linearSearch(data, linearFirstKey);
			linearSearch(data, linearMiddleKey);
			linearSearch(data, linearLastKey);
			linearSearch(data, linearAbsentKey);

			// Run Binary Search
			System.out.println("-- Binary Search --");
			binarySearch(data, binaryFirstKey);
			binarySearch(data, binaryMiddleKey);
			binarySearch(data, binaryLastKey);
			binarySearch(data, binaryAbsentKey);
		}
	}

	// ---------- Linear Search with comparison counting ----------
	public static int linearSearch(int[] data, int key)
	{
		int comparisons = 0;
        int iterations = 0;
		for (int i = 0; i < data.length; i++)
		{
			comparisons++;
            iterations++;
			if (data[i] == key)
			{
				System.out.println(
						"Linear: key " + key + " found at index " + i + " after " + comparisons + " comparisons and " + iterations + " iterations");
				return i;
			}
		}
		System.out.println("Linear: key " + key + " not found after " + comparisons + " comparisons " + iterations + " iterations");
		return -1;
	}

	// ---------- Binary Search with comparison counting ----------
	public static int binarySearch(int[] data, int key)
	{
        // define low and high
		int low = 0;
		int high = data.length - 1;
        // Initialize comparisons
		int comparisons = 0;
        int iterations = 0;

		while (low <= high)
		{
			int mid = (low + high) / 2;
			comparisons++;
            iterations++;
			if (data[mid] == key)
			{
				System.out.println(
						"Binary: key " + key + " found at index " + mid + " after " + comparisons + " comparisons and " + iterations + " iterations");
				return mid;
			} else if (data[mid] < key)
			{
				comparisons++;
				low = mid + 1;
			} else
			{
				comparisons++;
				high = mid - 1;
			}
		}
		System.out.println("Binary: key " + key + " not found after " + comparisons + " comparisons and " + iterations + " iterations");
		return -1;
	}

}