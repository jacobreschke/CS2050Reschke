package CS2050Reschke.M02.LibraryApp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class LibraryBookShelf {



    public static void main(String[] args) {


        // test PrintBook
        System.out.println("Unit Test Book Class");
        PrintBook unitTestPrintBook = new PrintBook("Unmasking AI", "Joy Buolamwini", 2023);
        System.out.println("getTitle(): " + unitTestPrintBook.getTitle());
        System.out.println("getAuthor(): " + unitTestPrintBook.getAuthor());
        System.out.println("getYear(): " + unitTestPrintBook.getYear());
        System.out.println("toString(): " + unitTestPrintBook);
        System.out.println("getLoanDays(): " + unitTestPrintBook.getLoanDays());
        System.out.println("getDailyLateFee(): $" + unitTestPrintBook.getDailyLateFee());
        System.out.println("calculateLateFee(5): $" + unitTestPrintBook.calculateLateFee(5));
        System.out.println();

        // test Ebook
        EBook unitTestEBook = new EBook("Deep Learning", "Ian Goodfellow", 2016);
        System.out.println("getTitle(): " + unitTestEBook.getTitle());
        System.out.println("getAuthor(): " + unitTestEBook.getAuthor());
        System.out.println("getYear(): " + unitTestEBook.getYear());
        System.out.println("toString(): " + unitTestEBook);
        System.out.println("getLoanDays(): " + unitTestEBook.getLoanDays());
        System.out.println("getDailyLateFee(): $" + unitTestEBook.getDailyLateFee());
        System.out.println("calculateLateFee(5): $" + unitTestEBook.calculateLateFee(5));
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
        library.addBook(null); // should fail
        library.addBook(new PrintBook("Unmasking AI", "Joy Buolamwini", 2023));
        library.addBook(new EBook("Hello World", "Hannah Fry", 2018));
        library.addBook(new PrintBook("Race After Technology", "Ruha Benjamin", 2019));
        library.addBook(new EBook("Deep Learning", "Ian Goodfellow", 2016));

        library.displayCountPerShelf();
        library.printAllBooks();
        library.displayOldest();

        // Row 1
        library.addBook(new PrintBook("Algorithms to Live By", "Brian Christian", 2016));
        library.addBook(new EBook("Weapons of Math Destruction", "Cathy O'Neil", 2016));
        library.addBook(new PrintBook("The Mythical Man-Month", "Fred Brooks", 1975));
        library.addBook(new EBook("Refactoring", "Martin Fowler", 1999));

        // Row 2
        library.addBook(new PrintBook("The Pragmatic Programmer", "Andrew Hunt & David Thomas", 1999));
        library.addBook(new EBook("Peopleware", "Tom DeMarco & Tim Lister", 1987));
        library.addBook(new PrintBook("Computer Lib / Dream Machines", "Ted Nelson", 1975));
        library.addBook(new EBook("Clean Code", "Robert Martin", 2008));

        library.displayCountPerShelf();
        library.printAllBooks();
        library.displayOldest();

        System.out.println();
        System.out.println("Test add more books than capacity...");

        // adding beyond capacity and null
        library.addBook(new PrintBook("Extra Title", "Extra Author", 2024));  // Should trigger "full" message
        library.addBook(new EBook("Extra Title2", "Extra Author2", 2024));    // Should trigger "full" message
        library.addBook(null);

        library.displayCountPerShelf();
        library.printAllBooks();
        library.displayOldest();


    }// end main


    // Main using load from csv
/*
    public static void main(String[] args) {

        int numberOfShelves = 3;
        int shelfCapacity = 4;

        Library library = new Library("Test Library", numberOfShelves, shelfCapacity);

        System.out.println("Loading books from CSV...");
        BookLoader.loadFromCsv(library, "books_test.csv");

        library.displayCountPerShelf();
        library.printAllBooks();
        library.displayOldest();
    }
*/

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

abstract class Book {

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
     *
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
     *
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
     *
     * @param year year of the book
     */
    private void setYear(int year) {
        if (year < 0) {
            this.publishYear = 0;
        } else {
            this.publishYear = year;
        }

    }

    public final double calculateLateFee(int daysLate) {
        double lateFee = 0;
        if (daysLate > 0) {
            lateFee = daysLate * getDailyLateFee();
        }
        return lateFee;

    }

    abstract int getLoanDays();

    abstract double getDailyLateFee();

    public abstract String getBookType();


    // toString override
    public String toString() {
        return ('"' + title + '"' + " by " + author + " (" + publishYear + ")");
    }

}

class PrintBook extends Book {

    private String bookType = "Print";
    private int loanDays = 21;
    private double dailyLateFee = .25;

    PrintBook(String title, String author, int publishYear) {
        super(title, author, publishYear);
    }

    @Override
    public String getBookType() {
        return bookType;
    }

    public int getLoanDays() {
        return loanDays;
    }

    public double getDailyLateFee() {
        return dailyLateFee;
    }

    @Override
    public String toString() {
        return super.toString() + " [" + getBookType() + ", " + getLoanDays() + " days, $" + getDailyLateFee() + "/day]";
    }

}

class EBook extends Book {

    private String bookType = "EBook";
    private int loanDays = 14;
    private double dailyLateFee = .10;

    EBook(String title, String author, int publishYear) {
        super(title, author, publishYear);
    }


    public int getLoanDays() {
        return loanDays;
    }

    public double getDailyLateFee() {
        return dailyLateFee;
    }

    @Override
    public String getBookType() {
        return bookType;
    }

    @Override
    public String toString() {
        return super.toString() + " [" + getBookType() + ", " + getLoanDays() + " days, $" + getDailyLateFee() + "/day]";
    }

}

class BookLoader {
    /**
     * Loads books from a CSV file and adds valid entries to the given library.
     * <p>
     * CSV format (no header row): title,author,year,type - type must be P
     * (PrintBook) or E (EBook)
     * <p>
     * Safe input goals for intro: 1) Handle missing file (FileNotFoundException) 2)
     * Validate basic structure (4 fields) 3) Safely parse year
     * (NumberFormatException) 4) Validate year range and type 5) Skip invalid lines
     * without crashing 6) Print a summary report at the end
     */
    public static void loadFromCsv(Library library, String filename) {
        // Defensive check to ensure library object
        if (library == null) {
            System.out.println("LibraryLoader: library is null. Cannot load file.");
            return;
        }
        // ----------------------------
        // Summary counters
        // ----------------------------
        int totalLinesRead = 0;
        int blankLinesSkipped = 0;
        int invalidLinesSkipped = 0;
        int booksAdded = 0;
        int addFailures = 0; // library full or addBook rejected
        try (Scanner fileScan = new Scanner(new File(filename))) {
            while (fileScan.hasNextLine()) {
                String line = fileScan.nextLine();
                totalLinesRead = totalLinesRead + 1;
                boolean shouldProcess = true;
                // 1) Skip blank lines (keep loop structured: no continue/break)
                if (line == null || line.trim().isEmpty()) {
                    shouldProcess = false;
                    blankLinesSkipped = blankLinesSkipped + 1;
                }
                // 2) Parse and add the book if the line is not blank
                if (shouldProcess) {
                    Book parsedBook = parseBookLine(line, totalLinesRead);
                    if (parsedBook == null) {
                        // parseBookLine already printed why it failed
                        invalidLinesSkipped = invalidLinesSkipped + 1;
                    } else {
                        boolean added = library.addBook(parsedBook);
                        if (added) {
                            booksAdded = booksAdded + 1;
                        } else {
                            addFailures = addFailures + 1;
                            System.out.println("Line " + totalLinesRead + ": could not add book (library may be full).");
                        }
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Could not open file: " + filename);
            return; // stop if file cannot be opened
        }
        // ----------------------------
        // Summary report (after reading file)
        // ----------------------------
        System.out.println();
        System.out.println("------------------------------------------------------------");
        System.out.println("File Load Summary: " + filename);
        System.out.println("------------------------------------------------------------");
        System.out.println("Total lines read:            " + totalLinesRead);
        System.out.println("Blank lines skipped:         " + blankLinesSkipped);
        System.out.println("Invalid lines skipped:       " + invalidLinesSkipped);
        System.out.println("Books successfully added:    " + booksAdded);
        System.out.println("Add failures (library full): " + addFailures);
        System.out.println("------------------------------------------------------------");
        System.out.println();
    }

    private static Book parseBookLine(String line, int lineNumber) {
        String[] parts = line.split(",");

        if (parts.length != 4) {
            System.out.println("Line " + lineNumber + ": wrong number of fields (need 4) → " + line);
            return null;
        }
        String titleText = parts[0].trim();
        String authorText = parts[1].trim();
        String yearText = parts[2].trim();
        String typeText = parts[3].trim();

        if (titleText.isEmpty()) {
            System.out.println("Line " + lineNumber + ": missing title");
            return null;
        }

        if (authorText.isEmpty()) {
            System.out.println("Line " + lineNumber + ": missing author");
            return null;
        }

        if (yearText.isEmpty()) {
            System.out.println("Line " + lineNumber + ": missing year");
            return null;
        }

        if (typeText.isEmpty()) {
            System.out.println("Line " + lineNumber + ": missing type");
            return null;
        }

        int year;
        try
        {
            year = Integer.parseInt(yearText);

        } catch (NumberFormatException ex)
        {
            System.out.println("Line " + lineNumber + ": year is not a number → \"" + yearText + "\"");
            return null;
        }

        if (typeText.equalsIgnoreCase("P")) {
            return new PrintBook(titleText, authorText, year);
        } else if (typeText.equalsIgnoreCase("E")) {
            return new EBook(titleText, authorText, year);
        } else {
            System.out.println("Line " + lineNumber + ": unknown book type → " + typeText);
            return null;
        }

    }



    public void writeAllBooksToFile(Book[][] bookShelf, String filename) {
        File outputFile = new File(filename);
        try (PrintWriter writer = new PrintWriter(outputFile)) {
            writer.println("Library Books");
            for (int i = 0; i < bookShelf.length; i++) {
                for (int j = 0; j < bookShelf[i].length; j++) {
                    writer.println(bookShelf[i][j].toString());

                }

            }
            System.out.println("Library contents saved to " + outputFile.getAbsolutePath());
        } catch (IOException exception) {
            System.out.println("Error writing to file: " + filename);
            System.out.println("Reason: " + exception.getMessage());
        }
    }


}





