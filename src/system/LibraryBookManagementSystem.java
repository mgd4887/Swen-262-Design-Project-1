package system;

import request.Arguments;
import time.Date;

/**
 * Class representing the library book management system. This acts
 * as the "facade" of the system for interacting with the system.
 *
 * @author Zachary Cook
 */
public class LibraryBookManagementSystem {
    private Services services;
    private Date currentDate;

    /**
     * Creates the library book management system.
     *
     * @param services the services to use.
     */
    public LibraryBookManagementSystem(Services services, Date date) {
        this.services = services;
        this.currentDate = date;
    }

    /**
     * Creates the library book management system.
     */
    public LibraryBookManagementSystem() {
        this(new Services(),new Date(1,1,2019,8,0,0));
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
     * Advances the system time.
     *
     * @param days the amount of days to advance.
     * @param hours the amount of hours to advance.
     */
    public void advanceTime(int days,int hours) {
        // Determine the new hours.
        int rawNewHours = this.currentDate.getHours() + hours;
        int newHours = rawNewHours % 24;
        days += (rawNewHours / 24);

        // Set the new date.
        this.currentDate = new Date(this.currentDate.getMonth(),this.currentDate.getDay() + days,this.currentDate.getYear(),newHours,0,0);
    }

    /**
     * Returns the current date.
     *
     * @return the current date.
     */
    public Date getDate() {
        return this.currentDate;
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
