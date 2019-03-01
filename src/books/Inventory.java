package books;

import java.util.HashMap;

public class Inventory {
    private HashMap<Integer, Book> books;

    public Inventory() {
        this.books = new HashMap<>();
    }

    void addBook(Book book){
        books.put(book.hashCode(), book);
    }
}
