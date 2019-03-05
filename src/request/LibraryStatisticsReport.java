package request;

import books.Book;
import books.transactions.Transaction;
import response.Response;
import system.Services;
import time.Date;
import time.Time;
import user.Visitor;
import user.visit.Visit;

/**
 * Request for getting a statistics report of the library.
 *
 * @author Joey Zhen
 * @author Zachary Cook
 */
public class LibraryStatisticsReport extends Request {
    /**
     * Creates a request.
     *
     * @param services the services to use for the request.
     */
    public LibraryStatisticsReport(Services services) {
        super(services);
    }

    /**
     * Returns the name of the request.
     *
     * @return the name of the request.
     */
    @Override
    public String getName() {
        return "report";
    }

    /**
     * Returns a response for the request.
     *
     * @param arguments the argument parser.
     * @return the response of the request.
     */
    @Override
    public Response handleRequest(Arguments arguments) {
        // Get the day scope, if any.
        int dayLimit = Integer.MAX_VALUE;
        if (arguments.hasNext()) {
            try {
                dayLimit = Integer.parseInt(arguments.getNextString());
            } catch (NumberFormatException e) {
                return this.sendResponse("day-not-a-number");
            }
        }

        // Get the current date.
        Date currentDate = this.services.getClock().getDate();

        // Get the average visit length.
        int averageVisitLength = 0;
        int visits = 0;
        for (Visit visit : this.services.getVisitHistory().getFinishedVisits()) {
            if (visit.getDate().differenceInDays(currentDate) < dayLimit) {
                averageVisitLength = ((averageVisitLength * visits) + (visit.getTimeOfDeparture().getSeconds() - visit.getDate().getSeconds())) / (visits + 1);
                visits += 1;
            }
        }

        // Get the purchased books.
        int bookCount = 0;
        int purchasedBooks = 0;
        for (Book book : this.services.getBookInventory().getBooks()) {
            bookCount += book.getNumCopies();
            if (book.getPurchasedDate().differenceInDays(currentDate) < dayLimit) {
                purchasedBooks += book.getNumCopies();
            }
        }

        // Get the fines.
        int finesCollected = 0;
        int finesOutstanding = 0;

        for (Visitor visitor : this.services.getVisitorsRegistry().getVisitors()) {
            for (Transaction transaction : this.services.getTransactionHistory().getTransactionsByVisitor(visitor)) {
                if (transaction.getCheckedOut().differenceInDays(currentDate) < dayLimit || !transaction.getReturned() || transaction.getReturnedDate().differenceInDays(currentDate) < dayLimit) {
                    // Add the fines.
                    int finesPaid = transaction.getPartialLateFeePaid();
                    int finesOwed = (int) transaction.calculateFee(currentDate) - finesPaid;

                    finesCollected += finesPaid;
                    finesOutstanding += finesOwed;
                }
            }
        }

        // Format the average visit time.
        Time averageVisitTime  = new Time(0,0,0);
        averageVisitTime = averageVisitTime.advance(0,0,averageVisitLength);

        // Create the result.
        String result = currentDate.formatDate() + ","
            + "\n Number of Books: " + bookCount
            + "\n Number of Visitors: " + this.services.getVisitorsRegistry().getVisitors().size()
            + "\n Average Length of Visit: " + averageVisitTime.formatTime()
            + "\n Number of Books Purchased: " + purchasedBooks
            + "\n Fines Collected: " + finesCollected
            + "\n Fines Outstanding: " + finesOutstanding + "\n";

        // Return the result.
        return this.sendResponse(result);
    }
}