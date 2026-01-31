package CS2050Reschke.M01;

public class LibraryBookShelf {


    public static class Library {

        private final String libraryName;
        private final int numberOfShelves;
        private final int shelfCapacity;

        // books can be final here because we are finalizing the size of the array not the contents of the array
        private final Book[][] books;
        private int filledCount = 0;


        /**
         * Constructs a library with name, shelves, and shelf capacity on creation.
         *
         * @param libraryName Name of the library
         * @param numberOfShelves Number of shelves in the library (row)
         * @param shelfCapacity Number of slots per shelf (column)
         */
        Library(String libraryName, int numberOfShelves, int shelfCapacity) {
            this.libraryName = libraryName;
            this.numberOfShelves = numberOfShelves;
            this.shelfCapacity = shelfCapacity;
            books = new Book[numberOfShelves][shelfCapacity];
        }

        /**
         * Adds a book to the first empty(null) index value in the library.
         * if book is null print an error message.
         * if library is full print full message.
         *
         * @param book Book object to add
         */
        public void addBook(Book book) {
            // placed to control adding the book
            boolean placed = false;
            // validBook for null check
            boolean validBook = book != null;

            // if book is null tell user
            if (!validBook) {
                System.out.println("Invalid book.");
            } else {
                // else look through shelf
                for (int i = 0; i < books.length; i++) {
                    for (int j = 0; j < books[i].length; j++) {
                        // find first null index and only add if book hasnt been placed
                        if (!placed && books[i][j] == null) {
                            books[i][j] = book;
                            filledCount++;
                            System.out.println("Added " + books[i][j].stringOfBookDetails() + " at shelf " + (i + 1) + ", slot " + (j + 1));
                            placed = true;
                        }
                    }
                }
            }
            // if the book is not null and we werent able to find a null index in the array
            // tell user the library is full
            if (validBook && !placed) {
                System.out.println("Library is full");
            }
        }

        /**
         * Displays the number of books stored per shelf.
         */
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

        /**
         * Displays the books in the library along with the shelf and index location.
         */
        void printAllBooks() {
            System.out.println("------------------------------------------------------------");
            System.out.println("All books in " + libraryName);
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


        /**
         * Finds the year of the oldest book in the library.
         * @return year of the oldest book, or -1 if library is empty
         */
        private int findOldestBook() {
            // Initialize to -1 meaning the value isnt set yet
            // then changing value to first non null book index year in the code
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

        /**
         * Displays the oldest book/book in library based on year unless its empty.
         */
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


        /**
         * Creates a book with a title, author, and publish year.
         * @param title title of the book
         * @param author the author of the book
         * @param publishYear the year the book was published
         */
        Book(String title, String author, int publishYear) {
            this.title = title;
            this.author = author;
            this.publishYear = publishYear;
        }

        /**
         * Returns the title of the book.
         * @return title
         */
        String getTitle() {
            return title;
        }

        /**
         * Returns the author of the book.
         * @return author
         */
        String getAuthor() {
            return author;
        }

        /**
         * returns the year of the book.
         * @return publish year
         */
        int getYear() {
            return publishYear;
        }

        /**
         * returns a string of the books details.
         * @return formatted string of details
         */
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
        // Also threw in a null book to test my addBook() preventing a null book triggering a full library
        library.addBook(null);
        library.displayCountPerShelf();
        library.printAllBooks();
        library.displayOldest();
    }// end main


}
