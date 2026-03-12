package CS2050Reschke.M03.ClassLabs;/*
 * Stack Example
 * Add comments to explain the code
 */

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class StackTest {
    public static void main(String[] args) {

        // Create an ArrayList<> using the stack class
        Stack<Integer> stack = new Stack<>();

        // push adds elements to the array on top of the stack
        System.out.println("Pushing elements: 10, 20, 30");
        stack.push(10);
        stack.push(20);
        stack.push(30);

        // print 10, 20, 30
        System.out.println("Stack after pushing: " + stack);

        // pop will remove the top element of the array
        int popped = stack.pop();
        System.out.println("Popped element: " + popped);
        System.out.println("Stack after popping: " + stack);

        // Takes the size of the array and subtracts 1 to match indexing and assigns to topElement
        int topElement = stack.peek();
        System.out.println("Top element: " + topElement);

        // Checks if the stack is empty
        System.out.println("Is stack empty? " + stack.isEmpty());


        popped = stack.pop();
        System.out.println("Popped element: " + popped);
        System.out.println("Stack after popping: " + stack);

        popped = stack.pop();
        System.out.println("Popped element: " + popped);
        System.out.println("Stack after popping: " + stack);


        System.out.println("Popped element: " + popped);

        if (!stack.isEmpty()) {
            popped = stack.pop();
            System.out.println("Stack after popping: " + stack);
        }

        Stack<String> stringArr = new Stack<>();
        stringArr.push("String1");
        stringArr.push("String2");
        stringArr.push("String3");


    }
}

class Stack<E> {

    private ArrayList<E> items;

    public Stack() {
        items = new ArrayList<>();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void push(E item) {
        items.add(item);
    }

    public E pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty.");
        }

        int topIndex = items.size() - 1;
        return items.remove(topIndex);
    }

    public E peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty.");
        }

        int topIndex = items.size() - 1;
        return items.get(topIndex);
    }

    public int size() {
        return items.size();
    }

    @Override
    public String toString() {
        if (items.isEmpty()) {
            return "<<empty stack>>";
        }
        return "bottom -> " + items + " <- top";
    }

}