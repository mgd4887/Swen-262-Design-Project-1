package system;

import request.Arguments;

/**
 * Class representing the library book management system. This acts
 * as the "facade" of the system for interacting with the system.
 *
 * @author Zachary Cook
 */
public class LibraryBookManagementSystem {
    private Services services;

    /**
     * Creates the library book management system.
     *
     * @param services the services to use.
     */
    public LibraryBookManagementSystem(Services services) {
        this.services = services;
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
        // TODO

        // Run the request and get a response.
        // TODO

        // Return the response.
        return "Unimplemented";
    }
}
