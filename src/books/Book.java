package books;

import java.util.Date;
import java.util.Objects;

public class Book {
    private Author author;
    private Publisher publisher;
    private int ISBN;
    private Date publishedDate;
    private int pageCount;
    private int numCopies;
    private int numCopiesCheckedOut;
    private Date purchasedDate;
    private String name;

    public Book(Author author, Publisher publisher, int ISBN, Date publishedDate, int pageCount, int numCopies, int numCopiesCheckedOut, Date purchasedDate, String name) {
        this.author = author;
        this.publisher = publisher;
        this.ISBN = ISBN;
        this.publishedDate = publishedDate;
        this.pageCount = pageCount;
        this.numCopies = numCopies;
        this.numCopiesCheckedOut = numCopiesCheckedOut;
        this.purchasedDate = purchasedDate;
        this.name = name;
    }

    /**
     * get the book's ISBN
     * @return the  book's ISBN
     */
    public int getISBN() {
        return ISBN;
    }

    /**
     * get the date the book was published
     * @return when the book was published
     */
    public Date getPublishedDate() {
        return publishedDate;
    }

    /**
     * get how many pages are in the book
     * @return how many pages are in the book
     */
    public int getPageCount() {
        return pageCount;
    }

    /**
     * get how many copies of this book the library owns
     * @return how many copies of this book the library owns
     */
    public int getNumCopies() {
        return numCopies;
    }

    /**
     * get when the library purchased this book
     * @return when the book was purchased
     */
    public Date getPurchasedDate() {
        return purchasedDate;
    }

    /**
     * get how many of this book are checked out
     * @return how many of this book are checaked out
     */
    public int getNumCopiesCheckedOut() {
        return numCopiesCheckedOut;
    }

    /**
     * Get the hashcode of this object
     * @return the hashcode of this book
     */
    @Override
    public int hashCode() {
        return Objects.hash(ISBN);
    }

    /**
     * get the author of this book
     * @return the author of this book
     */
    public Author getAuthor() {
        return author;
    }

    /**
     * get the publisher of this book
     * @return the publisher of this book
     */
    public Publisher getPublisher() {
        return publisher;
    }

    /**
     * get the name og this book
     * @return the name of this book
     */
    public String getName() {
        return name;
    }


    /**
     * compares two books to see if they are equal
     * @param o the book to compare to
     * @return if the books are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return ISBN == book.ISBN
    }

    /**
     * return this object as a string
     * @return this object as a string
     */
    @Override
    public String toString() {
        return "Book{" +
                "author=" + author +
                ", publisher=" + publisher +
                ", ISBN=" + ISBN +
                ", publishedDate=" + publishedDate +
                ", pageCount=" + pageCount +
                ", numCopies=" + numCopies +
                ", numCopiesCheckedOut=" + numCopiesCheckedOut +
                ", purchasedDate=" + purchasedDate +
                ", name='" + name + '\'' +
                '}';
    }

}
