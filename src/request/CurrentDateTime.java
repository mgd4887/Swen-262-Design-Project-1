package request;

import response.Response;
import system.Clock;
import system.Services;
import time.Date;
import time.Time;

import java.util.ArrayList;
import java.util.List;

/**
 * Request for getting the current date and time.
 *
 * @author Joey Zhen
 * @author Zachary Cook
 */
public class CurrentDateTime extends Request {
    /**
     * Creates a request.
     *
     * @param services the services to use for the request.
     */
    public CurrentDateTime(Services services) {
        super(services);
    }

    /**
     * Returns the name of the request.
     *
     * @return the name of the request.
     */
    @Override
    public String getName() {
        return "datetime";
    }

    /**
     * Returns a response for the request.
     *
     * @param arguments the argument parser.
     * @return the response of the request.
     */
    @Override
    public Response handleRequest(Arguments arguments) {
        // Get the clock information.
        Clock clock = this.services.getClock();
        Date date = clock.getDate();
        String formattedDate = date.formatDate();
        String formattedTime = date.formatTime();

        // Format the response.
        return this.sendResponse(formattedDate + "," + formattedTime);
    }
}