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
 * Request for setting the fines paid by a user as paid.
 *
 * @author Joey Zhen
 * @author Zachary Cook
 */
public class PayFine extends Request {
    /**
     * Creates a request.
     *
     * @param services the services to use for the request.
     */
    public PayFine(Services services) {
        super(services);
    }

    /**
     * Returns the name of the request.
     *
     * @return the name of the request.
     */
    @Override
    public String getName() {
        return "pay";
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
            return this.sendMissingParametersResponse("visitor-id,amount");
        }
        String visitorId = arguments.getNextString();

        // Get the amount to pay.
        if (!arguments.hasNext()) {
            return this.sendMissingParametersResponse("amount");
        }
        int amountToPay = 0;

        try {
            amountToPay = Integer.parseInt(arguments.getNextString());
        } catch (NumberFormatException e) {
            return this.sendResponse("amount-not-a-number");
        }

        // Get the visitor.
        Visitor visitor = this.services.getVisitorsRegistry().getVisitor(visitorId);
        if (visitor == null) {
            return this.sendResponse("invalid-visitor-id");
        }

        // Get the current date and due date.
        Date currentDate = this.services.getClock().getDate();
        Date dueDate = new Date(currentDate.getMonth(),currentDate.getDay()+ 7,currentDate.getYear(),0,0,0);

        // Determine the unpaid transactions.
        int unpaidBalance = 0;
        ArrayList<Transaction> unpaidUnreturnedTransactions = new ArrayList<>();
        ArrayList<Transaction> unpaidReturnedTransactions = new ArrayList<>();
        for (Transaction transaction : this.services.getTransactionHistory().getTransactionsByVisitor(visitor)) {
            if (!transaction.getLateFeedPaid()) {
                unpaidBalance += transaction.calculateFee(currentDate) - transaction.getPartialLateFeePaid();

                if (transaction.getReturned()) {
                    unpaidReturnedTransactions.add(transaction);
                } else {
                    unpaidUnreturnedTransactions.add(transaction);
                }
            }
        }

        // Return errors for invalid payments.
        if (amountToPay > unpaidBalance || amountToPay < 0) {
            return this.sendResponse("invalid-amount," + amountToPay + "," + unpaidBalance);
        }

        // Pay for the books.
        int balanceAfterward = unpaidBalance;
        unpaidReturnedTransactions.addAll(unpaidUnreturnedTransactions);
        for (Transaction transaction : unpaidReturnedTransactions) {
            // Calculate the remaining late fee.
            int lateFeeRemainder = ((int) transaction.calculateFee(currentDate)) - transaction.getPartialLateFeePaid();

            // Either pay the partial fee or pay the transaction.
            if (lateFeeRemainder <= amountToPay) {
                transaction.incrementPartialLateFeePaid(lateFeeRemainder);
                balanceAfterward += -lateFeeRemainder;
                amountToPay += -lateFeeRemainder;
                if (transaction.getReturned()) {
                    transaction.setLateFeeAsPaid(currentDate);
                }
            } else {
                transaction.incrementPartialLateFeePaid(amountToPay);
                balanceAfterward += -amountToPay;
                amountToPay = 0;
            }
        }

        // Return the response.
        return this.sendResponse("success," + balanceAfterward);
    }
}