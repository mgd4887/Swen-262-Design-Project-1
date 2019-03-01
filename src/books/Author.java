package books;

import user.Name;

/**
 * Class representing the author of a book.
 *
 * @author Zachary Cook
 */
public class Author {
    private Name name;

    /**
     * Creates an author.
     *
     * @param name the name of the author.
     */
    public Author(Name name) {
        this.name = name;
    }

    /**
     * Creates an author.
     *
     * @param firstName the first name of the author.
     * @param lastName the last name of the author.
     */
    public Author(String firstName,String lastName) {
        this(new Name(firstName,lastName));
    }
}
