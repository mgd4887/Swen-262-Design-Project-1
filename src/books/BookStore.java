package books;

import time.Date;
import user.Name;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class representing a  "Book Store". This includes the books
 * that can be bought by the library.
 *
 * @author Zachary Cook
 */
public class BookStore implements Serializable {
    // The default file location of the book store file.
    public static String BOOK_STORE_FILE_LOCATION = "books.txt";

    private ArrayList<Book> books;

    /**
     * Creates the book store.
     */
    public BookStore() {
        this.books = new ArrayList<>();
    }

    /**
     * Adds a book to the book store.
     *
     * @param isbn the id of the book.
     * @param name the name of the book.
     * @param authors the authors of the book.
     * @param publisher the publisher of the book.
     * @param date the date the book was published.
     * @param pageCount the page count of the book.
     */
    public void addBook(long isbn,String name,ArrayList<Author> authors,Publisher publisher,Date date,int pageCount) {
        this.books.add(new Book(authors,publisher,isbn,date,pageCount,0,0,new Date(0,0,0,0,0,0),name,books.size() + 1));
    }

    /**
     * Adds a book from a CSV line.
     *
     * @param csvLine the line to parse.
     */
    public void addBookFromCSV(String csvLine) {
        // Split the string.
        ArrayList<String> entries = parseCSV(csvLine);

        // Parse the ISBN.
        long isbn;
        try {
            isbn = Long.parseLong(entries.get(0));
        } catch (NumberFormatException e) {
            System.out.println("ISBN number invalid (" + entries.get(0) + "); ignoring: " + csvLine);
            return;
        }

        // Parse the book name.
        String bookName = entries.get(1);

        // Parse the authors.
        String authorString = entries.get(2);
        ArrayList<Author> authors = new ArrayList<>();
        for (String authorName : Arrays.asList(authorString.split(","))) {
            authors.add(new Author(new Name(authorName)));
        }

        // Parse the publisher.
        String publisher = entries.get(3);

        // Parse the published date.
        String dateString = entries.get(4);
        int year = 0;
        int month = 1;
        int day = 1;
        try {
            if (dateString.length() > 0) {
                year = Integer.parseInt(dateString.substring(0,4));
            }
            if (dateString.length() > 4) {
                month = Integer.parseInt(dateString.substring(5, 7));
            }
            if (dateString.length() > 7) {
                day = Integer.parseInt(dateString.substring(8, 10));
            }
        } catch (NumberFormatException e) {
            System.out.println("Page count invalid (" + entries.get(4) + ") ; ignoring: " + csvLine);
            return;
        }

        // Parse the page count.
        int pageCount;
        try {
            pageCount = Integer.parseInt(entries.get(5));
        } catch (NumberFormatException e) {
            System.out.println("Page count invalid (" + entries.get(5) + "); ignoring: " + csvLine);
            return;
        }

        // Add the book.
        this.addBook(isbn,bookName,authors,new Publisher(publisher),new Date(month,day,year,0,0,0),pageCount);
    }

    /**
     * Returns a list of all the books.
     *
     * @return the books as a list.
     */
    public Books getBooks() {
        return new Books(this.books);
    }

    /**
     * Returns the book for a given id.
     *
     * @param id the id to return.
     * @return the book with the id.
     */
    public Book getBook(int id) {
        return this.books.get(id);
    }

    /**
     * Reads and parses a CSV line.
     *
     * @param csvLine the line to parse.
     * @return the parsed CSV line as a CSV.
     */
    public static ArrayList<String> parseCSV(String csvLine) {
        ArrayList<String> parsedLine = new ArrayList<>();
        String currentString = "";
        boolean inBreak = false;

        // Add the lines.
        for (int i = 0; i < csvLine.length(); i++) {
            // Get the character.
            Character character = csvLine.charAt(i);

            // Interpret the character.
            if (inBreak) {
                if (character == '}' || character == '\"') {
                    inBreak = false;
                } else {
                    currentString += character;
                }
            } else {
                if (character == '{' || character == '\"') {
                    inBreak = true;
                } else if (character == ',') {
                    parsedLine.add(currentString);
                    currentString = "";
                } else {
                    currentString += character;
                }
            }
        }

        // Push the last string.
        parsedLine.add(currentString);

        // Return the parse CSV lined.
        return parsedLine;
    }

    /**
     * Creates a bookstore from the CSV file of books.
     *
     * @param fileLocation the location of the file.
     */
    public static BookStore fromFile(String fileLocation) {
        // Create the bookstore.
        BookStore bookStore = new BookStore();

        // Read the files by each line.
        BufferedReader reader;
        try {
            // Create the reader.
            reader = new BufferedReader(new FileReader(fileLocation));

            // Keep reading lines until the lines have ended.
            String line = reader.readLine();
            while (line != null) {
                // Parse the book and add read the next line.
                bookStore.addBookFromCSV(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Warning: The books in the book store (" +  BOOK_STORE_FILE_LOCATION + ") is unreadable.");
            // e.printStackTrace();
        }

        // Return the bookstore.
        return bookStore;
    }

}
