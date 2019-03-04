package request;

import books.Author;
import books.Book;
import books.transactions.Transaction;
import response.Response;
import system.Services;
import time.Date;
import user.UnformattedName;
import user.Visitor;

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
     */
    public BorrowBook(Services services) {
        super(services);
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
     * Returns a response for the request.
     *
     * @param arguments the argument parser.
     * @return the response of the request.
     */
    @Override
    public Response handleRequest(Arguments arguments) {
        // Get the visitor id or return an error if it is missing.
        if (!arguments.hasNext()) {
            return this.sendMissingParametersResponse("visitor-id,{id}");
        }
        String visitorId = arguments.getNextString();

        // Get the ids.
        if (!arguments.hasNext()) {
            return this.sendMissingParametersResponse("{id}");
        }
        HashMap<Integer,Integer> amountToBorrow = new HashMap<>();

        for (String idString : arguments.getNextString().split(",")) {
            // Get the id.
            int id;
            try {
                id = Integer.parseInt(idString);
            } catch (NumberFormatException e) {
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
        Visitor visitor = this.services.getVisitorsRegistry().getVisitor(visitorId);
        if (visitor == null) {
            return this.sendResponse("invalid-visitor-id");
        }

        // Get the current date and due date.
        Date currentDate = this.services.getClock().getDate();
        Date dueDate = new Date(currentDate.getMonth(),currentDate.getDay()+ 7,currentDate.getYear(),0,0,0);

        // Get the books to check out.
        ArrayList<Book> books = new ArrayList<>();
        for (int id : amountToBorrow.keySet()) {
            Book book = this.services.getBookInventory().getBook(this.services.getBookStore().getBook(id).getISBN());

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
        for (Transaction transaction : this.services.getTransactionHistory().getTransactionsByVisitor(visitor)) {
            if (!transaction.getLateFeedPaid()) {
                unretunedBooks += 1;
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
            this.services.getTransactionHistory().registerTransaction(book,visitor,currentDate,dueDate);
        }

        // Return the response.
        return this.sendResponse(dueDate.formatDate());
    }
}