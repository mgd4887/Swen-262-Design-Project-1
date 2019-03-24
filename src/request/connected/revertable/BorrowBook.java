package request.connected.revertable;

import books.Book;
import books.Books;
import books.transactions.Transaction;
import request.Arguments;
import request.Parameter;
import request.Request;
import response.Response;
import system.Services;
import time.Date;
import user.Visitor;
import user.connection.Connection;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Request for starting a loan of a book to a visitor.
 *
 * @author Joey Zhen
 * @author Zachary Cook
 */
public class BorrowBook extends Request {
    /**
     * Creates a request.
     *
     * @param services the services to use for the request.
     * @param connection the connection to use.
     * @param arguments the arguments to use.
     */
    public BorrowBook(Services services,Connection connection,Arguments arguments) {
        super(services,connection,arguments);
    }

    /**
     * Returns the name of the request.
     *
     * @return the name of the request.
     */
    @Override
    public String getName() {
        return "borrow";
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
        requiredParameters.add(new Parameter("{id}",Parameter.ParameterType.LIST_OF_INTEGERS));

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
        HashMap<Integer,Integer> amountToBorrow = new HashMap<>();

        for (Integer id : arguments.getNextListAsIntegers()) {
            // Get the id.
            if (id == null) {
                return this.sendResponse("id-not-a-number");
            }

            // Add the id.
            if (amountToBorrow.containsKey(id)) {
                amountToBorrow.put(id,amountToBorrow.get(id) + 1);
            } else {
                amountToBorrow.put(id,1);
            }
        }

        // Get the visitor.
        Visitor visitor = services.getVisitorsRegistry().getVisitor(visitorId);
        if (visitor == null) {
            return this.sendResponse("invalid-visitor-id");
        }

        // Get the current date and due date.
        Date currentDate = services.getClock().getDate();
        Date dueDate = new Date(currentDate.getMonth(),currentDate.getDay()+ 7,currentDate.getYear(),0,0,0);

        // Get the books to check out.
        Books books = new Books();
        for (int id : amountToBorrow.keySet()) {
            Book book = services.getBookInventory().getBook(services.getBookStore().getBook(id).getISBN());

            // Return an error if the book is invalid.
            if (book == null) {
                return this.sendResponse("invalid-book-id");
            }

            // Determine if there is enough copies and throw an error if there isn't.
            if ((book.getNumCopies() - book.getNumCopiesCheckedOut()) < amountToBorrow.get(id)) {
                return this.sendResponse("book-limit-exceeded");
            }

            // Add the book.
            for (int i = 0; i < amountToBorrow.get(id); i++) {
                books.add(book);
            }
        }

        // Determine the amount of unreturned books and unpaid fees.
        int unretunedBooks = 0;
        int unpaidBalance = 0;
        for (Transaction transaction : services.getTransactionHistory().getTransactionsByVisitor(visitor)) {
            if (!transaction.getReturned()) {
                unretunedBooks += 1;
            }
            if (!transaction.getLateFeedPaid()) {
                unpaidBalance += transaction.calculateFee(currentDate);
            }
        }

        // Return an error if there is an unpaid balance.
        if (unpaidBalance > 0) {
            return this.sendResponse("outstanding-fine," + unpaidBalance);
        }

        // Return an error if there is 5 or more books borrowed.
        if (unretunedBooks >= 5) {
            return this.sendResponse("book-limit-exceeded");
        }

        // Borrow the books.
        for (Book book : books) {
            book.borrowCopy();
            services.getTransactionHistory().registerTransaction(book,visitor,currentDate,dueDate);
        }

        // Return the response.
        return this.sendResponse(dueDate.formatDate());
    }
}