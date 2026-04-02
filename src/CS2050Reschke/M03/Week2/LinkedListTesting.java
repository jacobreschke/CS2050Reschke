package CS2050Reschke.M03.Week2;

public class LinkedListTesting {

    public static void main(String[] args) {
        Node head = new Node(2);
        head.next = new Node(4);
        head.next.next = new Node(8);
        head.next.next.next = new Node(10);


        int index = 2;
        int newValue = 6;

        Node newNode = new Node(newValue);
        Node current = head;

        while (current != null) {
            for (int i = 0; i < index; i++) {
                current = current.next;
            }

        }
    }

}

class Node {
    int value;
    Node next;

    Node(int value) {
        this.value = value;
        this.next = null;
    }


}
