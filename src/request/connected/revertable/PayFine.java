package request.connected.revertable;

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
     * @param connection the connection to use.
     * @param arguments the arguments to use.
     */
    public PayFine(Services services,Connection connection,Arguments arguments) {
        super(services,connection,arguments);
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
     * Returns a list of the required parameters.
     *
     * @return a list of the required parameters.
     */
    @Override
    public ArrayList<Parameter> getRequiredParameters() {
        // Create the required parameters.
        ArrayList<Parameter> requiredParameters = new ArrayList<>();
        requiredParameters.add(new Parameter("visitor-id",Parameter.ParameterType.STRING));
        requiredParameters.add(new Parameter("amount",Parameter.ParameterType.INTEGER));

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

        // Get the visitor id and amount to pay.
        String visitorId = arguments.getNextString();
        Integer amountToPay = arguments.getNextInteger();
        if (amountToPay == null) {
            return this.sendResponse("amount-not-a-number");
        }

        // Get the visitor.
        Visitor visitor = services.getVisitorsRegistry().getVisitor(visitorId);
        if (visitor == null) {
            return this.sendResponse("invalid-visitor-id");
        }

        // Get the current date and due date.
        Date currentDate = services.getClock().getDate();

        // Determine the unpaid transactions.
        int unpaidBalance = 0;
        ArrayList<Transaction> unpaidUnreturnedTransactions = new ArrayList<>();
        ArrayList<Transaction> unpaidReturnedTransactions = new ArrayList<>();
        for (Transaction transaction : services.getTransactionHistory().getTransactionsByVisitor(visitor)) {
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