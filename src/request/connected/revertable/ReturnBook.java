package request.connected.revertable;

import books.transactions.Transaction;
import request.Arguments;
import request.Parameter;
import request.connected.AccountRequest;
import response.Response;
import system.Services;
import time.Date;
import user.Visitor;
import user.connection.Connection;
import user.connection.User;

import java.util.ArrayList;

/**
 * Request for returning the book by a user.
 *
 * @author Joey Zhen
 * @author Zachary Cook
 */
public class ReturnBook extends AccountRequest {
    /**
     * Creates a request.
     *
     * @param services the services to use for the request.
     * @param connection the connection to use.
     * @param arguments the arguments to use.
     */
    public ReturnBook(Services services, Connection connection, Arguments arguments) {
        super(services,connection,arguments,User.PermissionLevel.EMPLOYEE);
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
     * Returns a list of the required parameters.
     *
     * @return a list of the required parameters.
     */
    @Override
    public ArrayList<Parameter> getRequiredParameters() {
        // Create the required parameters.
        ArrayList<Parameter> requiredParameters = new ArrayList<>();
        requiredParameters.add(new Parameter("visitor-id",Parameter.ParameterType.STRING));
        requiredParameters.add(new Parameter("id",Parameter.ParameterType.INTEGER));

        // Return the required parameters.
        return requiredParameters;
    }

    /**
     * Returns a response for the request.
     *
     * @return the response of the request.
     */
    @Override
    public Response handleRequest() {
        Arguments arguments = this.getArguments();
        Services services = this.getServices();

        // Get the visitor id.
        String visitorId = arguments.getNextString();

        // Get the ids.
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
        Visitor visitor = services.getVisitorsRegistry().getVisitor(visitorId);
        if (visitor == null) {
            return this.sendResponse("invalid-visitor-id");
        }

        // Get the current date.
        Date currentDate = services.getClock().getDate();

        // Get the transactions to that can be returned.
        ArrayList<Transaction> returnableTransactions = new ArrayList<>();
        for (Transaction transaction : services.getTransactionHistory().getTransactionsByVisitor(visitor)) {
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