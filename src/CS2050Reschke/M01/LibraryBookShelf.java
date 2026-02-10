package CS2050Reschke.M01;

public class LibraryBookShelf {



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

class Library {

    private String name;
    private int numberOfShelves;
    private int shelfCapacity;
    private int currentSlot = 0;
    private int currentShelf = 0;
    private boolean isFull = false;

    private Book[][] bookShelf;


    /**
     * Constructs a library with name, shelves, and shelf capacity on creation.
     *
     * @param libraryName     Name of the library
     * @param numberOfShelves Number of shelves in the library (row)
     * @param shelfCapacity   Number of slots per shelf (column)
     */
    Library(String libraryName, int numberOfShelves, int shelfCapacity) {
        setName(libraryName);
        setNumberOfShelves(numberOfShelves);
        setShelfCapacity(shelfCapacity);
        setBookShelf();
    }


    /**
     * Adds a book to the library based on the currentSlot variable
     * handles currentShelf and currentSlot logic
     * if book is null print an error message.
     * if library is full print full message.
     *
     * @param book Book object to add
     */
    public boolean addBook(Book book) {
        // Originally I used a nested for loop for this but it would loop through
        // values that had a book creating an inefficient system
        // Refactored to this system so we can use the currentSlot to assign a book

        if (book == null) {
            System.out.println("Invalid book.");
            return false;
        }

        if (isFull) {
            System.out.println("Library is full");
            return false;
        }

        bookShelf[currentShelf][currentSlot] = book;

        System.out.println("Added " + book
                + " at shelf " + (currentShelf + 1)
                + ", slot " + (currentSlot + 1));

        currentSlot++;
        // if currentSlot is beyond index bounds then increment the shelf
        // because shelfCapacity == 4 and currentSlot is (0 - shelfCapacity - 1) aka 3
        // When index value reaches 4 it is safe to say the shelf is full
        if (currentSlot >= shelfCapacity) {
            currentSlot = 0;
            currentShelf++;
        }

        // same logic as above but for shelves
        if (currentShelf >= numberOfShelves) {
            isFull = true;
        }

        return true;
    }

    /**
     * Displays the number of books stored per shelf.
     */
    public void displayCountPerShelf() {
        for (int i = 0; i < bookShelf.length; i++) {

            System.out.print("Shelf " + (i + 1) + " has ");
            int count = 0;
            for (int j = 0; j < bookShelf[i].length; j++) {
                if (bookShelf[i][j] != null) {
                    count++;
                }
            }
            System.out.print(count + " books\n");
        }
    }

    /**
     * Displays the books in the library along with the shelf and index location.
     */
    public void printAllBooks() {
        System.out.println("------------------------------------------------------------");
        System.out.println("All books in " + getName());
        System.out.println("Shelf   Slot   Book Details");
        System.out.println("------------------------------------------------------------");

        for (int i = 0; i < bookShelf.length; i++) {
            for (int j = 0; j < bookShelf[i].length; j++) {
                if (bookShelf[i][j] != null) {
                    System.out.println((i + 1) + "    " + (j + 1) + " " + bookShelf[i][j]);
                }
            }
        }
        int totalBooks = currentShelf * shelfCapacity + currentSlot;

        System.out.println("(" + totalBooks + " of " + (shelfCapacity * numberOfShelves) + " slots filled)");
        System.out.println("------------------------------------------------------------");
    }


    /**
     * Finds the year of the oldest book in the library.
     *
     * @return year of the oldest book, or -1 if library is empty
     */
    private int findOldestBook() {
        // Initialize to -1 meaning the value isnt set yet
        // then changing value to first non null book index year in the code
        // Did this to get rid of a dummy year and to make sure the loop tests
        // against an actual data value
        int oldestBookYear = -1;

        for (int i = 0; i < bookShelf.length; i++) {
            for (int j = 0; j < bookShelf[i].length; j++) {
                if (bookShelf[i][j] != null) {

                    if (oldestBookYear == -1) {
                        // set the initial value of oldestBookYear to the first non null value
                        oldestBookYear = bookShelf[i][j].getYear();
                    }


                    if (bookShelf[i][j].getYear() < oldestBookYear) {
                        // comparing book index year to the current oldest year
                        oldestBookYear = bookShelf[i][j].getYear();

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

        if (currentShelf == 0 && currentSlot == 0) {
            System.out.println("Display Oldest: Library is empty.");
        } else {


            System.out.println("Oldest books in " + getName());
            System.out.println("Earliest publication year: " + oldestBookYear);

            // Loop to display the oldest books that contain a year matching the oldestBookYear
            for (int i = 0; i < bookShelf.length; i++) {
                for (int j = 0; j < bookShelf[i].length; j++) {
                    if (bookShelf[i][j] != null && bookShelf[i][j].getYear() == oldestBookYear) {
                        System.out.println(bookShelf[i][j]);
                    }
                }
            }
        }
    }

    /**
     * returns the name variable of the library.
     *
     * @return Library name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Error checks the library name input and assigns if valid.
     * If invalid it will assign a default value of "Default Library.
     *
     * @param libraryName the intended name of the library
     */
    private void setName(String libraryName) {
        if (libraryName == null || libraryName.isEmpty()) {
            this.name = "Default Library";
        } else {
            this.name = libraryName;
        }
    }

    /**
     * Error checks numberOfShelves input, validates that it is over 0
     * then assigns to object. Default value is 1.
     *
     * @param numberOfShelves the row of shelves
     */
    private void setNumberOfShelves(int numberOfShelves) {
        if (numberOfShelves > 0) {
            this.numberOfShelves = numberOfShelves;
        } else {
            this.numberOfShelves = 1;
        }
    }

    /**
     * Error checks numberOfShelves input, validates that it is over 0
     * then assigns to object. Default value is 1.
     *
     * @param shelfCapacity the space on the shelves
     */
    private void setShelfCapacity(int shelfCapacity) {
        if (shelfCapacity > 0) {
            this.shelfCapacity = shelfCapacity;
        } else {
            this.shelfCapacity = 1;
        }
    }

    /**
     * Assigns the numberOfShelves and shelfCapacity to the bookShelf array
     */
    private void setBookShelf() {
        // We already double checked that numShelves and shelCapacity are valid numbers
        this.bookShelf = new Book[numberOfShelves][shelfCapacity];
    }

}

class Book {

    private String title;
    private String author;
    private int publishYear;


    /**
     * Creates a book with a title, author, and publish year.
     *
     * @param title       title of the book
     * @param author      the author of the book
     * @param publishYear the year the book was published
     */
    Book(String title, String author, int publishYear) {
        setTitle(title);
        setAuthor(author);
        setYear(publishYear);
    }

    /**
     * Returns the title of the book.
     *
     * @return title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Returns the author of the book.
     *
     * @return author
     */
    public String getAuthor() {
        return this.author;
    }

    /**
     * returns the year of the book.
     *
     * @return publish year
     */
    public int getYear() {
        return this.publishYear;
    }

    /**
     * Checks the title input to ensure it is not null or empty
     * then assigns the value to the book. If null/empty assign Default title
     * @param title title of the book
     */
    private void setTitle(String title) {
        if (title == null || title.isEmpty()) {
            this.title = "Default Title";
        } else {
            this.title = title;
        }
    }

    /**
     * Checks if the author input isnt null or empty then
     * assignt the value to the book. If null.empty assign default to title
     * @param author author of the book
     */
    private void setAuthor(String author) {
        if (author == null || author.isEmpty()) {
            this.author = "Default Author";
        } else {
            this.author = author;
        }
    }


    /**
     * Checks if the year input is greater than or equal to 0. If it is
     * then it will assign the year to the book. if less than 0 default value is 0
     * @param year year of the book
     */
    private void setYear(int year) {
        if (year < 0) {
            this.publishYear = 0;
        } else {
            this.publishYear = year;
        }

    }

    /**
     * returns a string of the books details.
     *
     * @return formatted string of details
     */
    public String stringOfBookDetails() {
        return ('"' + title + '"' + " by " + author + " (" + publishYear + ")");
    }

    // toString override
    @Override
    public String toString() {
        return ('"' + title + '"' + " by " + author + " (" + publishYear + ")");
    }

}

