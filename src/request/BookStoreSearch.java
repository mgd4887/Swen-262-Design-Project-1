package request;

import books.Author;
import books.Book;
import books.Books;
import response.Response;
import system.Services;
import user.UnformattedName;

import java.util.ArrayList;

/**
 * Request for searching the book store.
 *
 * @author Joey Zhen
 * @author Zachary Cook
 */
public class BookStoreSearch extends Request {
    /**
     * Creates a request.
     *
     * @param services the services to use for the request.
     */
    public BookStoreSearch(Services services) {
        super(services);
    }

    /**
     * Returns the name of the request.
     *
     * @return the name of the request.
     */
    @Override
    public String getName() {
        return "search";
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
            return this.sendMissingParametersResponse("title");
        }
        String title = arguments.getNextString();

        // Get the authors, if any.
        ArrayList<Author> authors = new ArrayList<>();
        if (arguments.hasNext()) {
            String authorsString = arguments.getNextString();

            // Parse the authors.
            if (!authorsString.equals("*")) {
                for (String authorName : authorsString.split(",")) {
                    authors.add(new Author(new UnformattedName(authorName.trim())));
                }
            }
        }

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
        } else {
            return this.sendResponse("invalid-sort-order");
        }

        // Search for the books.
        Books foundBooks = new Books();
        for (Book book : this.services.getBookStore().getBooks()) {
            if (title.equals("*") || book.getName().toLowerCase().contains(title.toLowerCase())) {
                // Determine if all authors are present.
                boolean authorsPresent = true;
                for (Author author : authors) {
                    if (!book.getAuthors().contains(author)) {
                        authorsPresent = false;
                        break;
                    }
                }

                // Add the book if the rest of the search works.
                if (authorsPresent && (isbn.equals("*") || Long.toString(book.getISBN()).contains(isbn)) && (publisher.equals("*") || book.getPublisher().toString().toLowerCase().contains(publisher.toLowerCase()))) {
                    foundBooks.add(book);
                }
            }
        }

        // Sort the books.
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
            results += "\n" + book.getId() + "," + book.getISBN() + "," + book.getName() + ",{" + authorsList + "}," + book.getPublishedDate().formatDate() + ",";
        }

        // Return the result.
        return this.sendResponse(results);
    }
}