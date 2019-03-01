package books;

import java.util.Objects;

public class Book {
    private Author author;
    private Publisher publisher;

    @Override
    public String toString() {
        return "Book{" +
                "author=" + author +
                ", publisher=" + publisher +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(author, book.author) &&
                Objects.equals(publisher, book.publisher) &&
                Objects.equals(name, book.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, publisher, name);
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

    public Book(String name, Author author, Publisher publisher){
        this.name = name;
        this.author = author;
        this.publisher = publisher;
    }
}
