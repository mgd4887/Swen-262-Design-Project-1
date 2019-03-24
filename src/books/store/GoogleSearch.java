package books.store;

import books.Author;
import books.Book;
import books.Books;
import books.Publisher;
import time.Date;

import java.util.ArrayList;

public class GoogleSearch implements StoreSearchService {
    private Books localStoreBooks;
    private Books sharedBooks;

    /**
     * Creates the book store.
     *
     * @param books the main list of books.
     */
    public GoogleSearch(Books books) {
        this.localStoreBooks = new Books();
        this.sharedBooks = books;
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
     * @return the id of the book.
     */
    public int addBook(long isbn,String name,ArrayList<Author> authors,Publisher publisher,Date date,int pageCount) {
        // Return if the book exists.
        for (Book book : this.localStoreBooks) {
            if (book.getISBN() == isbn) {
                return book.getId();
            }
        }

        // Add the book.
        Book book = new Book(authors,publisher,isbn,date,pageCount,0,0,name,sharedBooks.size() + 1);
        this.localStoreBooks.add(book);
        this.sharedBooks.add(book);

        // Return the id.
        return book.getId();
    }

    /**
     * Returns the books for the given search.
     *
     * @param title the title of the book. To ignore this, leave it empty or use "*".
     * @param authors the authors of the book. To ignore this, leave it empty or use "*".
     * @param isbn the authors of the book. To ignore this, leave it empty or use "*".
     * @param publisher the publisher of the book. To ignore this, leave it empty or use "*".
     * @return the filtered books.
     */
    public Books getBooks(String title,String authors,String isbn,String publisher) {
        // Run the Google Query.
        // TODO

        // Register the books and store the ids.
        Books foundBooks = new Books();
        // TODO

        // Return the found books.
        return foundBooks;
    }
}
