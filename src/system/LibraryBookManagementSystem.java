package system;

import request.*;
import response.Response;

import java.util.HashMap;

/**
 * Class representing the library book management system. This acts
 * as the "facade" of the system for interacting with the system.
 *
 * @author Zachary Cook
 */
public class LibraryBookManagementSystem {
    // The default file location for saving the services.
    public static String SERVICES_SAVE_LOCATION = "services_save";

    protected Services services;
    private HashMap<String,Request> requests;

    /**
     * Creates the library book management system.
     *
     * @param services the services to use.
     */
    public LibraryBookManagementSystem(Services services) {
        // Store the services.
        this.services = services;

        // Create the response classes.
        this.requests = new HashMap<>();
        this.requests.put("register",new RegisterVisitor(this.services));
        this.requests.put("arrive",new BeginVisit(this.services));
        this.requests.put("depart",new EndVisit(this.services));
        this.requests.put("info",new LibraryBookSearch(this.services));
        this.requests.put("borrow",new BorrowBook(this.services));
        this.requests.put("borrowed",new FindBorrowedBooks(this.services));
        this.requests.put("return",new ReturnBook(this.services));
        this.requests.put("pay",new PayFine(this.services));
        this.requests.put("search",new BookStoreSearch(this.services));
        this.requests.put("buy",new BookPurchase(this.services));
        this.requests.put("advance",new AdvanceTime(this.services));
        this.requests.put("datetime",new CurrentDateTime(this.services));
        this.requests.put("report",new LibraryStatisticsReport(this.services));
    }

    /**
     * Creates the library book management system.
     */
    public LibraryBookManagementSystem() {
        this(new Services());
    }

    /**
     * Returns the services used. This is intended to use
     * with behavior testing for requests.
     *
     * @return the services used.
     */
    protected Services getServices() {
        return this.services;
    }

    /**
     * Performs a request and returns a response as
     * a string.
     *
     * @param request the request to make.
     * @return the response.
     */
    public String performRequest(String request) {
        // Create the argument parser.
        Arguments argumentParser;
        try {
            argumentParser = new Arguments(request);
        } catch (IllegalArgumentException exception) {
            // Handle incomplete arguments (no ending semicolon).
            return "partial-request;";
        }

        // Create the request.
        if (!argumentParser.hasNext()) {
            return "partial-request;";
        }
        Request requestObject = this.requests.get(argumentParser.getNextString());
        if (requestObject == null) {
            return "invalid-request;";
        }

        // Run the request and get a response.
        Response response = requestObject.handleRequest(argumentParser);
        String responseString = response.getResponse();

        // Return the response.
        return responseString;
    }


}
