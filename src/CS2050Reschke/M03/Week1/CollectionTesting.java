package CS2050Reschke.M03.Week1;

import java.util.*;

public class CollectionTesting {




    public void main(String[] args) {

        // =========================
        // ArrayList (Dynamic Array)
        // =========================
        System.out.println("ArrayList Demo:");

        ArrayList<String> fruits = new ArrayList<>();

        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Orange");

        System.out.println("First fruit: " + fruits.get(0));

        for (String fruit : fruits) {
            System.out.println(fruit);
        }

        // =========================
        // LinkedList
        // =========================
        System.out.println("\nLinkedList Demo:");

        LinkedList<String> queue = new LinkedList<>();

        queue.add("Task 1");
        queue.add("Task 2");
        queue.add("Task 3");

        System.out.println("First task: " + queue.getFirst());

        queue.removeFirst();

        System.out.println("Queue after removing first: " + queue);

        // =========================
        // HashSet (No duplicates)
        // =========================
        System.out.println("\nHashSet Demo:");

        HashSet<String> uniqueNames = new HashSet<>();

        uniqueNames.add("Jacob");
        uniqueNames.add("Sarah");
        uniqueNames.add("Jacob"); // duplicate ignored

        System.out.println(uniqueNames);

        // =========================
        // HashMap (Key-Value pairs)
        // =========================
        System.out.println("\nHashMap Demo:");

        HashMap<String, Integer> scores = new HashMap<>();

        scores.put("Jacob", 95);
        scores.put("Sarah", 88);
        scores.put("Mike", 91);

        System.out.println("Jacob's score: " + scores.get("Jacob"));

        for (String name : scores.keySet()) {
            System.out.println(name + " -> " + scores.get(name));
        }

        // =========================
        // Iterator Example
        // =========================
        System.out.println("\nIterator Demo:");

        Iterator<String> iterator = fruits.iterator();

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }


    }


}