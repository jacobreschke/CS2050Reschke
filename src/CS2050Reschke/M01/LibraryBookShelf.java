package CS2050Reschke.M01;

public class LibraryBookShelf {


    public static class Library {

        String libraryName;

        // make const?
        int numberOfShelves;
        int shelfCapacity;


        Book[][] books;
        int filledCount = 0;


        Library(String libraryName, int numberOfShelves, int shelfCapacity) {
            this.libraryName = libraryName;
            this.numberOfShelves = numberOfShelves;
            this.shelfCapacity = shelfCapacity;
            books = new Book[numberOfShelves][shelfCapacity];
        }


        public void addBook(Book book) {
            boolean placed = false;

            if (book == null) {
                System.out.println("Invalid book.");
            } else {
                for (int i = 0; i < books.length; i++) {
                    for (int j = 0; j < books[i].length; j++) {
                        if (!placed && books[i][j] == null) {
                            books[i][j] = book;
                            filledCount++;
                            // Output = Added "Unmasking AI" by Joy Buolamwini (2023) at shelf 1, slot 1
                            System.out.println("Added " + books[i][j].stringOfBookDetails() + " at shelf " + (i + 1) + ", slot " + (j + 1));
                            placed = true;
                        }
                    }
                }
            }
            if (!placed) {
                System.out.println("Library is full");
            }
        }


        void displayCountPerShelf() {
            for (int i = 0; i < books.length; i++) {

                System.out.print("Shelf " + (i + 1) + " has ");
                int count = 0;
                for (int j = 0; j < books[i].length; j++) {
                    if (books[i][j] != null) {
                        count++;
                    }
                }
                System.out.print(count + " books\n");
            }
        }

        void printAllBooks() {
            System.out.println("------------------------------------------------------------");
            System.out.println("All books in Test Library");
            System.out.println("Shelf   Slot   Book Details");
            System.out.println("------------------------------------------------------------");

            for (int i = 0; i < books.length; i++) {
                for (int j = 0; j < books[i].length; j++) {
                    if (books[i][j] != null) {
                        System.out.println((i + 1) + "    " + (j + 1) + " " + books[i][j].stringOfBookDetails());
                    }
                }
            }
            System.out.println("(" + filledCount + " of " + (shelfCapacity * numberOfShelves) + " slots filled)");
            System.out.println("------------------------------------------------------------");
        }


        private int findOldestBook() {
            // Initialize to -1 then changing value to first non null book index year later in the code
            // Did this to get rid of a dummy year and to make sure the loop tests
            // against an actual data value
            int oldestBookYear = -1;

            for (int i = 0; i < books.length; i++) {
                for (int j = 0; j < books[i].length; j++) {
                    if (books[i][j] != null) {

                        if (oldestBookYear == -1) {
                            // set the initial value of oldestBookYear to the first non null value
                            oldestBookYear = books[i][j].getYear();
                        }


                        if (books[i][j].getYear() < oldestBookYear) {
                            // comparing book index year to the current oldest year
                            oldestBookYear = books[i][j].getYear();

                        }

                    }
                }
            }
            return oldestBookYear;
        }

        public void displayOldest() {
            int oldestBookYear = findOldestBook();

            if (filledCount == 0) {
                System.out.println("Display Oldest: Library is empty.");
            } else {


                System.out.println("Oldest books in " + libraryName);
                System.out.println(oldestBookYear);

                // Loop to display the oldest books that contain a year matching the oldestBookYear
                for (int i = 0; i < books.length; i++) {
                    for (int j = 0; j < books[i].length; j++) {
                        if (books[i][j] != null && books[i][j].getYear() == oldestBookYear) {
                            System.out.println(books[i][j].stringOfBookDetails());
                        }
                    }
                }


            }


        }
    }

    public static class Book {

        private final String title;
        private final String author;
        private final int publishYear;


        Book(String title, String author, int publishYear) {
            this.title = title;
            this.author = author;
            this.publishYear = publishYear;
        }

        String getTitle() {
            return title;
        }

        String getAuthor() {
            return author;
        }

        int getYear() {
            return publishYear;
        }

        public String stringOfBookDetails() {
            return ('"' + title + '"' + " by " + author + " (" + publishYear + ")");
        }

    }


    public static void main(String[] args) {
        // --- unit test checks for Book ---
        System.out.println("Unit Test Book Class");
        Book unitTestBook = new Book("Unmasking AI", "Joy Buolamwini", 2023);
        System.out.println("getTitle():   " + unitTestBook.getTitle());
        System.out.println("getAuthor():  " + unitTestBook.getAuthor());
        System.out.println("getYear():    " + unitTestBook.getYear());
        System.out.println("stringOfBookDetails():   " + unitTestBook.stringOfBookDetails());
        System.out.println();
        System.out.println("Setting up Test Library");
        int numberOfShelves = 3;
        int shelfCapacity = 4;
        System.out.println("Shelves (rows): " + numberOfShelves);
        System.out.println("Slots per shelf (columns): " + shelfCapacity);
        System.out.println("Total capacity: " + (numberOfShelves * shelfCapacity));
        System.out.println();
        Library library = new Library("Test Library", numberOfShelves, shelfCapacity);
        library.displayCountPerShelf();
        library.printAllBooks();
        library.displayOldest();
        // Row 0
        library.addBook(null);
        library.addBook(new Book("Unmasking AI", "Joy Buolamwini", 2023));
        library.addBook(new Book("Hello World", "Hannah Fry", 2018));
        library.addBook(new Book("Race After Technology", "Ruha Benjamin", 2019));
        library.addBook(new Book("Deep Learning", "Ian Goodfellow", 2016));
        library.displayCountPerShelf();
        library.printAllBooks();
        library.displayOldest();
        // Row 1
        library.addBook(new Book("Algorithms to Live By", "Brian Christian", 2016));
        library.addBook(new Book("Weapons of Math Destruction", "Cathy O'Neil", 2016));
        library.addBook(new Book("The Mythical Man-Month", "Fred Brooks", 1975));
        library.addBook(new Book("Refactoring", "Martin Fowler", 1999));


        // Row 2
        library.addBook(new Book("The Pragmatic Programmer", "Andrew Hunt & David Thomas", 1999));
        library.addBook(new Book("Peopleware", "Tom DeMarco & Tim Lister", 1987));
        library.addBook(new Book("Computer Lib / Dream Machines", "Ted Nelson", 1975));
        library.displayCountPerShelf();
        library.printAllBooks();
        library.displayOldest();
        System.out.println();
        System.out.println("Test add more books than capacity...");
        // This will fill the 12th slot not the out of bounds index so I added an extra addBook to test full capacity
        library.addBook(new Book("Extra Title", "Extra Author", 2024)); // should trigger "full" message
        library.addBook(new Book("Extra Title2", "Extra Author2", 2024)); // should trigger "full" message
        library.displayCountPerShelf();
        library.printAllBooks();
        library.displayOldest();
    }// end main


}
