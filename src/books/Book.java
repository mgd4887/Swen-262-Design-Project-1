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
    private int numCpiesCheckedOut;
    private Date purchasedDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return ISBN == book.ISBN &&
                pageCount == book.pageCount &&
                numCopies == book.numCopies &&
                numCpiesCheckedOut == book.numCpiesCheckedOut &&
                Objects.equals(author, book.author) &&
                Objects.equals(publisher, book.publisher) &&
                Objects.equals(publishedDate, book.publishedDate) &&
                Objects.equals(purchasedDate, book.purchasedDate) &&
                Objects.equals(name, book.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, publisher, ISBN, publishedDate, pageCount, numCopies, numCpiesCheckedOut, purchasedDate, name);
    }

    @Override
    public String toString() {
        return "Book{" +
                "author=" + author +
                ", publisher=" + publisher +
                ", ISBN=" + ISBN +
                ", publishedDate=" + publishedDate +
                ", pageCount=" + pageCount +
                ", numCopies=" + numCopies +
                ", numCpiesCheckedOut=" + numCpiesCheckedOut +
                ", purchasedDate=" + purchasedDate +
                ", name='" + name + '\'' +
                '}';
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public Book(Author author, Publisher publisher, int ISBN, Date publishedDate, int pageCount, int numCopies, int numCpiesCheckedOut, Date purchasedDate, String name) {
        this.author = author;
        this.publisher = publisher;
        this.ISBN = ISBN;
        this.publishedDate = publishedDate;
        this.pageCount = pageCount;
        this.numCopies = numCopies;
        this.numCpiesCheckedOut = numCpiesCheckedOut;
        this.purchasedDate = purchasedDate;
        this.name = name;
    }

}
