package CS2050Reschke.M01;

public class LibraryBookShelf {


    public static class Library {

        String libraryName;

        int numberOfShelves;
        int shelfCapacity;

        int rowStock;

        Book[][] books;
        int filledCount = 0;


        Library(String libraryName, int numberOfShelves, int shelfCapacity) {
            this.libraryName = libraryName;
            this.numberOfShelves = numberOfShelves;
            this.shelfCapacity = shelfCapacity;
            books = new Book[shelfCapacity][numberOfShelves];
        }


        // TODO: Logic is wrong here
        // Maybe we use the boolean added logic of the Student class
        // then increment the counter each time.
        //
        // increment row and column and run a check on numberOfShelves shelfCapacity

   /*     public void addBook(Book book) {
            int row = 0;
            int column = 0;

            for (int i = 0; i < numberOfShelves; i++) {
                if (row < numberOfShelves) {
                    for (int j = 0; j < books[i].length; j++) {
                        if (column < (books[i].length + 1)) {
                            books[column][row] = book;
                            column++;
                        }
                    }
                    row++;
                }

            }
        }*/


        void displayCountPerShelf() {
            for (int i = 0; i < numberOfShelves; i++) {

                System.out.print("Shelf " + (i + 1) + " has ");
                for (int j = 0; j < books[i].length; j++) {
                    if (books[i][j] != null) {
                        filledCount++;
                    }
                }
                System.out.print(filledCount + " books\n");
            }
        }

        void printAllBooks() {
            System.out.println("------------------------------------------------------------");
            System.out.println("All books in Test Library");
            System.out.println("Shelf   Slot   Book Details");
            System.out.println("------------------------------------------------------------");

            for (int i = 0; i < numberOfShelves; i++) {
                for (int j = 0; j < books[i].length; j++) {
                    if (books[i][j] != null) {
                        System.out.println((i + 1) + "    " + j + " " + books[i][j].stringOfBookDetails());
                    }
                }
            }
            System.out.println("(" + filledCount + " of " + (shelfCapacity * numberOfShelves) + " slots filled)");
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

        // TODO: displayOldest() function
        //library.displayOldest();


        // Row 0

        // TODO: null check on addBook()
        //library.addBook(null);


        library.addBook(new Book("Unmasking AI", "Joy Buolamwini", 2023));
        library.addBook(new Book("Hello World", "Hannah Fry", 2018));
        library.addBook(new Book("Race After Technology", "Ruha Benjamin", 2019));
        library.addBook(new Book("Deep Learning", "Ian Goodfellow", 2016));
        library.displayCountPerShelf();
        library.printAllBooks();
        /*
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
        library.addBook(new Book("Extra Title", "Extra Author", 2024)); // should trigger "full" message
        library.displayCountPerShelf();
        library.printAllBooks();
        library.displayOldest();*/
    }// end main


}
