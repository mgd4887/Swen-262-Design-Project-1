package books;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Contains a list of books. This is used for sorting.
 *
 * @author Zachary Cook
 */
public class Books extends ArrayList<Book> implements Serializable {
    /**
     * The sorting methods.
     */
    public enum SortingMethod {
        TITLE,
        PUBLISH_DATE,
        BOOK_STATUS
    }

    /**
     * Creates a list of books.
     */
    public Books() {
        super();
    }

    /**
     * Creates a list of books.
     *
     * @param list the list to copy.
     */
    public Books(List<Book> list) {
        super(list);
    }
    /**
     * Sorts the books by a certain method.
     *
     * @param method the method to sort.
     */
    public void sort(SortingMethod method) {
        // Sort by title.
        if (method == SortingMethod.TITLE) {
            this.sort(new Comparator<Book>() {
                @Override
                public int compare(Book o1, Book o2) {
                    return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
                }
            });
        }

        // Sort by published date.
        if (method == SortingMethod.PUBLISH_DATE) {
            this.sort(new Comparator<Book>() {
                @Override
                public int compare(Book o1, Book o2) {
                    return o2.getPublishedDate().getTimestamp() - o1.getPublishedDate().getTimestamp();
                }
            });
        }

        // Sort by book status.
        if (method == SortingMethod.BOOK_STATUS) {
            this.sort(new Comparator<Book>() {
                @Override
                public int compare(Book o1, Book o2) {
                    return (o2.getNumCopies() - o2.getNumCopiesCheckedOut()) - (o1.getNumCopies() - o1.getNumCopiesCheckedOut());
                }
            });
        }
    }
}
