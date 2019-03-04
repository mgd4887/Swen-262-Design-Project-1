package request;

import books.Book;
import books.transactions.Transaction;
import response.Response;
import system.Services;
import time.Date;
import user.Visitor;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Request for getting books borrowed by a visitor.
 *
 * @author Joey Zhen
 * @author Zachary Cook
 */
public class FindBorrowedBooks extends Request {
    /**
     * Creates a request.
     *
     * @param services the services to use for the request.
     */
    public FindBorrowedBooks(Services services) {
        super(services);
    }

    /**
     * Returns the name of the request.
     *
     * @return the name of the request.
     */
    @Override
    public String getName() {
        return "borrowed";
    }

    /**
     * Returns a response for the request.
     *
     * @param arguments the argument parser.
     * @return the response of the request.
     */
    @Override
    public Response handleRequest(Arguments arguments) {
        // Get the visitor id or return an error if it is missing.
        if (!arguments.hasNext()) {
            return this.sendMissingParametersResponse("visitor-id");
        }
        String visitorId = arguments.getNextString();

        // Get the visitor.
        Visitor visitor = this.services.getVisitorsRegistry().getVisitor(visitorId);
        if (visitor == null) {
            return this.sendResponse("invalid-visitor-id");
        }

        // Determine the amount of unreturned books.
        ArrayList<Transaction> unreturnedBooks = new ArrayList<>();
        for (Transaction transaction : this.services.getTransactionHistory().getTransactionsByVisitor(visitor)) {
            if (!transaction.getReturned()) {
                unreturnedBooks.add(transaction);
            }
        }

        // Format the result.
        String result = Integer.toString(unreturnedBooks.size());
        for (Transaction transaction : unreturnedBooks) {
            Book book = transaction.getBook();
            result += "\n" + book.getId() + "," + book.getISBN() + "," + book.getName() + "," + transaction.getCheckedOut().formatDate();
        }

        // Return the response.
        return this.sendResponse(result);
    }
}