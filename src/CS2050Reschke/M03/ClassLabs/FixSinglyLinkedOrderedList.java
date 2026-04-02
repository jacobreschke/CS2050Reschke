package CS2050Reschke.M03.ClassLabs;

/**
 * Lab: Fix Singly Linked Ordered List ----------------------------------- The
 * insertNode() and deleteNode() methods contain logic bugs. 1. Predict what
 * each method should do (draw before/after pictures). 2. Use the debugger or
 * print statements to trace previous/current. 3. Fix the code so the list stays
 * in sorted order after insertions and nodes are correctly deleted when found.
 *
 * Add comments above your fixes explaining what was wrong and why.
 */

public class FixSinglyLinkedOrderedList
{

	// Test the Singly Linked List
	public static void main(String[] args)
	{

		SinglyLinkedListFix list = new SinglyLinkedListFix();
		list.printList();

		// Use your unit testing to ensure it handles all cases
		list.insertNode(2);

		// Use your unit testing to ensure it handles all cases
		list.insertNode(4);

		list.printList();	// Use your unit testing to ensure it handles all cases
		list.insertNode(6);


		list.insertNode(5); // Insert in middle
		list.printList(); // Expected: 4 → 5 → 6 → 8 → null

		list.printList();	// Use your unit testing to ensure it handles all cases
		list.insertNode(8);

		list.printList();

		list.deleteNode(2);
		list.printList();

		list.deleteNode(6);
		list.printList();
		list.deleteNode(8);

		list.printList();

	}

}

class SinglyLinkedListFix
{
	NodeFix head;

	public void insertNode(int number)
	{
		NodeFix newNode = new NodeFix(number);
		NodeFix current = head;
		NodeFix previous = null;

		if (head == null) {
			head = newNode;
			return;
		}
		while (current != null && current.data < number)
		{
			previous = current;
			current = current.next;
		}

		if (previous == null)
		{
			newNode.next = head;
			head = newNode;
		} else
		{
			previous.next = newNode;

		}
	}

	public void deleteNode(int number)
	{
		NodeFix current = head;
		NodeFix previous = null;

		while (current != null && current.data != number)
		{
			previous = current;
			current = current.next;
		}

		if (current != null) {
			if (previous == null)
			{
				head = current.next;
			} else
			{
				previous.next = current.next;
			}
		}

	}

	public void printList()
	{
		NodeFix current = head;
		while (current != null)
		{
			System.out.print(current.data + " → ");
			current = current.next;
		}
		System.out.println("null");
	}

	private static class NodeFix
	{
		int data;
		NodeFix next;

		public NodeFix(int data)
		{
			this.data = data;
			this.next = null;
		}
	}
}