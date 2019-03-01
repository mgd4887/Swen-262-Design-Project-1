package books;

import org.junit.Before;
import org.junit.Test;
import books.Author;
import user.Name;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BookTest {

    Book CuT;

    @Before
    public void setUp() throws Exception {

        Name authorName = new Name("authorName", "authorLast");
        Author author = new Author(authorName);
        Publisher publisher = new Publisher();
        int ISBN = 1;
        Date publishedDate = new Date();
        int pageCount = 1;
        int numCopies = 2;
        int numCopiesCheckedOut = 1;
        Date purchasedDate = new Date();
        String name = "testbook";


        this.CuT = new Book(author, publisher, ISBN, publishedDate, pageCount, numCopies, numCopiesCheckedOut, purchasedDate, name);
    }

    @Test
    public void getISBN() {
        assertEquals(CuT.getISBN(), 1);
    }

    @Test
    public void getPublishedDate() {
        assertEquals(CuT.getPublishedDate(), new Date());
    }

    @Test
    public void getPageCount() {
        assertEquals(CuT.getPageCount(), 1);
    }

    @Test
    public void getNumCopies() {
        assertEquals(CuT.getNumCopies(), 2);
    }

    @Test
    public void getPurchasedDate() {
        assertEquals(CuT.getPublishedDate(), new Date());
    }

    @Test
    public void getNumCopiesCheckedOut() {
        assertEquals(CuT.getNumCopiesCheckedOut(), 1);
    }

    @Test
    public void getAuthor() {
        assertEquals(CuT.getAuthor(), new Author(new Name("authorName", "authorLast")));
    }

    @Test
    public void getPublisher() {
        assertEquals(CuT.getPublisher(), new Publisher());
    }

    @Test
    public void getName() {
        assertEquals(CuT.getName(), "testbook");
    }

    @Test
    public void equals() {
        assertTrue(CuT.equals(CuT));
    }
}