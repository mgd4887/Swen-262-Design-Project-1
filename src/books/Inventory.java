package books;

import sun.plugin.javascript.navig.Array;

import java.util.ArrayList;
import java.util.HashMap;

public class Inventory {
    private HashMap<Integer, Book> books;

    public Inventory() {
        this.books = new HashMap<>();
    }

    public Book getBook(int ISBN){
        return books.get(ISBN);
    }

    public ArrayList<Book> getBooks(String name){
        ArrayList<Book> output = new ArrayList<>();
        for (Book book: books.values()){
            if (book.getName().equals(name)){
                output.add(book);
            }
        }
        return output;
    }

    public ArrayList<Book> getBooks(Author author){
        ArrayList<Book> output = new ArrayList<>();
        for (Book book: books.values()){
            if (book.getAuthor().equals(author)){
                output.add(book);
            }
        }
        return output;
    }

    public ArrayList<Book> getBooks(Publisher publisher){
        ArrayList<Book> output = new ArrayList<>();
        for (Book book: books.values()){
            if (book.getPublisher().equals(publisher)){
                output.add(book);
            }
        }
        return output;
    }

}
