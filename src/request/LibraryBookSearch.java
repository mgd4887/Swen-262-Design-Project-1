package request;

import books.Author;
import books.Book;
import books.Books;
import response.Response;
import system.Services;
import user.Name;

import java.util.ArrayList;

/**
 * Request for searching books that are owned by library and can
 * be loaned to visitors.
 *
 * @author Joey Zhen
 * @author Zachary Cook
 */
public class LibraryBookSearch extends Request {
    /**
     * Creates a request.
     *
     * @param services the services to use for the request.
     */
    public LibraryBookSearch(Services services) {
        super(services);
    }

    /**
     * Returns the name of the request.
     *
     * @return the name of the request.
     */
    @Override
    public String getName() {
        return "info";
    }

    /**
     * Returns a response for the request.
     *
     * @param arguments the argument parser.
     * @return the response of the request.
     */
    @Override
    public Response handleRequest(Arguments arguments) {
        // Get the title or return an error if it is missing.
        if (!arguments.hasNext()) {
            return this.sendMissingParametersResponse("title,{authors}");
        }
        String title = arguments.getNextString();

        // Get the authors, if any.
        if (!arguments.hasNext()) {
            return this.sendMissingParametersResponse("{authors}");
        }
        String authorsString = arguments.getNextString();

        // Get the ISBN, if any.
        String isbn = "*";
        if (arguments.hasNext()) {
            isbn = arguments.getNextString();
        }

        // Get the publisher, if any.
        String publisher = "*";
        if (arguments.hasNext()) {
            publisher = arguments.getNextString();
        }

        // Get the sort order, if any.
        String sortOrder = "title";
        if (arguments.hasNext()) {
            sortOrder = arguments.getNextString();
        }

        // Validate the sort order.
        Books.SortingMethod sortingMethod;
        if (sortOrder.toLowerCase().equals("title")) {
            sortingMethod = Books.SortingMethod.TITLE;
        } else if (sortOrder.toLowerCase().equals("publish-date")) {
            sortingMethod = Books.SortingMethod.PUBLISH_DATE;
        } else if (sortOrder.toLowerCase().equals("book-status")) {
            sortingMethod = Books.SortingMethod.BOOK_STATUS;
        } else {
            return this.sendResponse("invalid-sort-order");
        }

        // Search and sort the books.
        Books foundBooks = this.services.getBookInventory().getBooks(title,authorsString,isbn,publisher);
        foundBooks.sort(sortingMethod);

        // Build the return string.
        String results = Integer.toString(foundBooks.size());
        for (Book book : foundBooks) {
            // Create the list of authors.
            String authorsList = "";
            for (Author author : book.getAuthors()) {
                if (!authorsList.equals("")) {
                    authorsList += ",";
                }

                authorsList += author.getName();
            }

            // Add the result.
            results += "\n" + (book.getNumCopies() - book.getNumCopiesCheckedOut()) + "," + book.getId() + "," + book.getISBN() + ",\"" + book.getName() + "\",{" + authorsList + "}," + book.getPublishedDate().formatDate() + ",";
        }

        // Return the result.
        return this.sendResponse(results);
    }
}