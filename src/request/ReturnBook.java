package request;

import books.Book;
import books.transactions.Transaction;
import response.Response;
import system.Services;
import time.Date;
import user.Visitor;

import java.util.ArrayList;

/**
 * Request for returning the book by a user.
 *
 * @author Joey Zhen
 * @author Zachary Cook
 */
public class ReturnBook extends Request {
    /**
     * Creates a request.
     *
     * @param services the services to use for the request.
     */
    public ReturnBook(Services services) {
        super(services);
    }

    /**
     * Returns the name of the request.
     *
     * @return the name of the request.
     */
    @Override
    public String getName() {
        return "return";
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
            return this.sendMissingParametersResponse("visitor-id,id");
        }
        String visitorId = arguments.getNextString();

        // Get the ids.
        if (!arguments.hasNext()) {
            return this.sendMissingParametersResponse("id");
        }
        ArrayList<Integer> booksToReturn = new ArrayList<>();

        for (Integer id : arguments.getRemainingIntegers()) {
            // Return if the id is null.
            if (id == null) {
                return this.sendResponse("id-not-a-number");
            }

            // Add the id.
            booksToReturn.add(id);
        }

        // Get the visitor.
        Visitor visitor = this.services.getVisitorsRegistry().getVisitor(visitorId);
        if (visitor == null) {
            return this.sendResponse("invalid-visitor-id");
        }

        // Get the current date.
        Date currentDate = this.services.getClock().getDate();

        // Get the transactions to that can be returned.
        ArrayList<Transaction> returnableTransactions = new ArrayList<>();
        for (Transaction transaction : this.services.getTransactionHistory().getTransactionsByVisitor(visitor)) {
            if (!transaction.getReturned()) {
                returnableTransactions.add(transaction);
            }
        }

        // Determine the transactions to end.
        ArrayList<Transaction> transactionsToReturn = new ArrayList<>();
        String invalidIdsToReturn = "";
        for (int id : booksToReturn) {
            // Get the transaction.
            Transaction transaction = null;
            for (Transaction otherTransaction : returnableTransactions) {
                if (otherTransaction.getBook().getId() - 1 == id) {
                    transaction = otherTransaction;
                    break;
                }
            }

            // Add the transaction if it exists, or the id if it doesn't.
            if (transaction == null) {
                invalidIdsToReturn += "," + id;
            } else {
                returnableTransactions.remove(transaction);
                transactionsToReturn.add(transaction);
            }
        }

        // Throw an error for invalid ids.
        if (invalidIdsToReturn.length() != 0) {
            return this.sendResponse("invalid-book-id" + invalidIdsToReturn);
        }

        // Return the books.
        int lateFee = 0;
        String booksIdString = "";
        for (Transaction transaction : transactionsToReturn) {
            transaction.getBook().returnCopy();
            transaction.setReturned(currentDate);

            booksIdString += "," + (transaction.getBook().getId() - 1);
            lateFee += transaction.calculateFee(currentDate);
        }

        // Return a success if there is a late fee.
        if (lateFee > 0) {
            return this.sendResponse("overdue," + lateFee + booksIdString);
        }

        // Return a success message.
        return this.sendResponse("success");
    }
}