package CS2050Reschke.M03.ClassLabs;

import java.util.ArrayList;
import java.util.NoSuchElementException;


public class QueueTesting {

    public static void main(String[] args) {
        CustomerQueue<Customer> queue = new CustomerQueue<>();

        // Create test customers
        Customer customer1 = new Customer("Name1", "Problem1");
        Customer customer2 = new Customer("Name2", "Problem2");
        Customer customer3 = new Customer("Name3", "Problem3");

        // Test 1: isEmpty()
        System.out.println("Testing empty queue");
        System.out.println(queue.isEmpty());
        System.out.println();

        // Test 2: queue()
        System.out.println("adding customers");
        queue.queue(customer1);
        queue.queue(customer2);
        queue.queue(customer3);

        System.out.println("Current queue:");
        queue.displayQueue();
        System.out.println();

        // Test 3: peek()
        System.out.println("checking first element");
        System.out.println(queue.peek());
        System.out.println();

        // Test 4: dequeue()
        System.out.println("Removing first element");
        System.out.println("Removed: " + queue.dequeue());
        System.out.println();

        System.out.println("Queue after one dequeue:");
        queue.displayQueue();
        System.out.println();

        // Test 5: queue() after removing 0th index
        System.out.println("Test 5: Peek again:");
        System.out.println(queue.peek());
        System.out.println();

        // Test 6: dequeue() the remainder
        System.out.println("dequeue remaining customers:");
        System.out.println("Removed: " + queue.dequeue());
        System.out.println("Removed: " + queue.dequeue());
        System.out.println();

        // Test 7
        System.out.println("is empty?");
        System.out.println(queue.isEmpty()); // true
        System.out.println();

        // Test 8: peek() empty queue
        System.out.println("peek on empty queue:");
        try {
            System.out.println(queue.peek());
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
        System.out.println();

        // Test 9 dequeue() an empty queue
        System.out.println("dequeue on empty queue:");
        try {
            System.out.println(queue.dequeue());
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
    }
}


class CustomerQueue<E> {
    ArrayList<E> queue;

    CustomerQueue() {
        queue = new ArrayList<>();
    }

    public void queue(E item) {
        queue.add(item);
    }

    public E dequeue() {
        return queue.removeFirst();
    }

    public E peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty.");
        }
        int bottomIndex = 0;
        return queue.get(bottomIndex);
    }


    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public void displayQueue() {
        for (E item : queue ) {
            System.out.println(item.toString());
        }
    }

}


class Customer {
    String name;
    String issueType;


    Customer(String name, String issueType) {
        this.name = name;
        this.issueType = issueType;
    }

    @Override
    public String toString() {
        return "Customer Name: " + name + " Issue: " + issueType;
    }
}