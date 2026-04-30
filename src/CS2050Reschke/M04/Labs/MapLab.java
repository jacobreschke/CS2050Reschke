package CS2050Reschke.M04.Labs;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class MapLab {


    public static void main(String[] args) {


        // creates the linked list and map
        Map<String, Book> bookMapByTitle = populateData();


        // 4. loop through all keys
        printAllBooksInMapByKey(bookMapByTitle);
        // 5. loop through all values
        printAllBooksInMapByValue(bookMapByTitle);
        // 6. Loop through key value pairs
        printAllBooksInMapByKeyValuePair(bookMapByTitle);



        // Key to search
        String searchTitle = "Hello World".toLowerCase();

        // 1. Check if empty
        printMapSearchByTitle(bookMapByTitle.isEmpty(), "The map is empty", "The map is not empty");

        // 2. Get the number of entries
        System.out.println("The map contains " + bookMapByTitle.size() + " entries");

        // if book is found then print found else print not found
        printMapSearchByTitle(bookMapByTitle.containsKey(searchTitle), "Found Book " + bookMapByTitle.get(searchTitle), "Book not found: " + searchTitle);

        // 3. remove a book by title
        System.out.println("Removing book: " + searchTitle);
        bookMapByTitle.remove(searchTitle);


    }

    private static Map<String, Book> populateData() {
        LinkedList<Book> bookInventory = new LinkedList<>();

        bookInventory.add(new Book("Unmasking AI", "Dr. Joy Buolamwini", 2023));
        bookInventory.add(new Book("Hello World", "Hannah Fry", 2018));
        bookInventory.add(new Book("The Mathematics of Love", "Hannah Fry", 2015));
        bookInventory.add(new Book("Weapons of Math Destruction", "Cathy O’Neil", 2016));
        bookInventory.add(new Book("Race After Technology", "Ruha Benjamin", 2019));

        // Creates the map to hold the books by title
        Map<String, Book> bookMapByTitle = new HashMap<>();

        for (Book currentBook: bookInventory) {
            bookMapByTitle.put(currentBook.getTitle().toLowerCase(), currentBook);
        }
        return bookMapByTitle;
    }

    private static void printAllBooksInMapByKeyValuePair(Map<String, Book> bookMapByTitle) {

        System.out.println("-----Printing all books in map by key value pair-----");
        for (Map.Entry<String, Book> entry : bookMapByTitle.entrySet()) {
            String title = entry.getKey();
            Book book = entry.getValue();

            System.out.println(title + " " + book);
        }
        System.out.println("-----------------------------------------------------");
    }


    private static void printAllBooksInMapByKey(Map<String, Book> bookMapByTitle) {
        System.out.println("-----Printing all books in map by key-----");
        for (String bookTitle: bookMapByTitle.keySet()) {
            System.out.println(bookTitle);
        }
        System.out.println("-------------------------------------------");
    }

    private static void printAllBooksInMapByValue(Map<String, Book> bookMapByTitle) {
        System.out.println("-----Printing all books in map by value-----");
        for (Book book: bookMapByTitle.values()) {
            System.out.println(book);
        }
        System.out.println("--------------------------------------------");
    }

    private static void printMapSearchByTitle(boolean bookMapByTitle, String bookMapByTitle1, String searchTitle) {
        if (bookMapByTitle) {
            System.out.println(bookMapByTitle1);
        } else {
            System.out.println(searchTitle);
        }
    }


}
