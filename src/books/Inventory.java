package books;

import time.Date;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Class representing the inventory of books.
 *
 * @author Michael Dolan
 * @author Zachary Cook
 */
public class Inventory implements Serializable {
    private HashMap<Long,Book> books;

    /**
     * Creates the inventory of book.
     */
    public Inventory() {
        this.books = new HashMap<>();
    }

    /**
     * Registers a book in the inventory.
     *
     * @param book the book to register.
     * @param purchaseDate the date the book was purchased.
     */
    public void registerBook(Book book,Date purchaseDate) {
        // If the book doesn't exist, add the book.
        if (!this.books.containsKey(book.getISBN())) {
            this.books.put(book.getISBN(),new Book(book.getAuthors(),book.getPublisher(),book.getISBN(),book.getPublishedDate(),book.getPageCount(),book.getNumCopies(),book.getNumCopiesCheckedOut(),purchaseDate,book.getName(),book.getId()));
        }
    }

    /**
     * Registers a book in the inventory.
     *
     * @param book the book to register.
     */
    public void registerBook(Book book) {
        this.registerBook(book,book.getPurchasedDate());
    }

    /**
     * Returns the book for the ISBN number.
     *
     * @param ISBN the ISBN number of the book.
     *
     * @return the book with the corresponding ISBN number.
     */
    public Book getBook(long ISBN){
        return books.get(ISBN);
    }

    /**
     * Returns all of the books in the inventory.
     *
     * @return all of the books.
     */
    public Collection<Book> getBooks() {
        return this.books.values();
    }

    /**
     * Searches the inventory for books by name.
     *
     * @param name the name to search.
     *
     * @return the list of books found.
     */
    public ArrayList<Book> getBooks(String name){
        // Search the books.
        ArrayList<Book> output = new ArrayList<>();
        for (Book book: books.values()){
            if (book.getName().contains(name)){
                output.add(book);
            }
        }

        // Return the books.
        return output;
    }

    /**
     * Searches the inventory for books by author.
     *
     * @param author the author to search.
     *
     * @return the list of books found.
     */
    public ArrayList<Book> getBooks(Author author){
        // Search the books.
        ArrayList<Book> output = new ArrayList<>();
        for (Book book: books.values()){
            if (book.getAuthors().contains(author)){
                output.add(book);
            }
        }

        // Return the books.
        return output;
    }

    /**
     * Searches the inventory for books by publisher.
     *
     * @param publisher the publisher to search.
     *
     * @return the list of books found.
     */
    public ArrayList<Book> getBooks(Publisher publisher){
        // Search the books.
        ArrayList<Book> output = new ArrayList<>();
        for (Book book: books.values()){
            if (book.getPublisher().equals(publisher)){
                output.add(book);
            }
        }

        // Return the books.
        return output;
    }

}
