package CS2050Reschke.M01;

public class TestLibraryApp {


    static class TestLibrary {

        int numberOfShelves = 0;
        int shelfCapacity = 0;
        TestBook[][] shelves;


        TestLibrary() {

        }

        TestLibrary(int numberOfShelves, int shelfCapacity) {
            this.numberOfShelves = numberOfShelves;
            this.shelfCapacity = shelfCapacity;
            shelves = new TestBook[numberOfShelves][shelfCapacity];
        }

        void addBook(String title, String author, int year) {
            TestBook book = new TestBook(title, author, year);

            for (int i = 0; i < shelves.length; i++) {
                for (int j = 0; j < shelves[i].length; i++){
                    if (shelves[i][j] != null) {
                        shelves[i][j] = book;
                    }
                }
            }

        }

    }


    static class TestBook {

        private final String title;
        private final String author;
        private final int publishYear;

        TestBook(String title, String author, int publishYear) {
            this.title = title;
            this.author = author;
            this.publishYear = publishYear;
        }

        String getTitle() {
            return this.title;
        }

        String getAuthor() {
            return this.author;
        }

        int getYear() {
            return this.publishYear;
        }

        public String stringOfBookDetails() {
            return ('"' + title + '"' + " by " + author + " (" + publishYear + ")");
        }


    }

    public static void main(String[] args) {

        int numberOfShelves = 3;
        int shelfCapacity = 4;

        TestLibrary library = new TestLibrary(numberOfShelves, shelfCapacity);

        String bookTitle = "TestTitle";
        String bookAuthor = "TestAuthor";
        int testYear = 1994;

        library.addBook(bookTitle, bookAuthor, testYear);




    }


}
