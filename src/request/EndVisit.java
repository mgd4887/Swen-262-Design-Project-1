package request;

import response.Response;
import system.Clock;
import system.Services;
import time.Date;
import user.Visitor;
import user.visit.Visit;

/**
 * Request for getting the current date and time.
 *
 * @author Joey Zhen
 * @author Zachary Cook
 */
public class EndVisit extends Request {
    /**
     * Creates a request.
     *
     * @param services the services to use for the request.
     */
    public EndVisit(Services services) {
        super(services);
    }

    /**
     * Returns the name of the request.
     *
     * @return the name of the request.
     */
    @Override
    public String getName() {
        return "depart";
    }

    /**
     * Returns a response for the request.
     *
     * @param arguments the argument parser.
     * @return the response of the request.
     */
    @Override
    public Response handleRequest(Arguments arguments) {
        // Get the id or return an error if it is missing.
        if (!arguments.hasNext()) {
            return this.sendMissingParametersResponse("visitor-id");
        }
        String visitorId = arguments.getNextString();

        // Return an error if the id isn't registered.
        Visitor visitor = this.services.getVisitorsRegistry().getVisitor(visitorId);
        if (visitor == null) {
            return this.sendResponse("invalid-id");
        }

        // Get the current time.
        Clock clock = this.services.getClock();
        Date date = clock.getDate();
        String formattedTime = date.formatTime();

        // End the current visit and return an error if there is no visit..
        Visit visit = this.services.getVisitHistory().finishVisit(visitor,date);
        if (visit == null) {
            return this.sendResponse("invalid-id");
        }

        // Format the time difference.
        int visitDuration = visit.getTimeOfDeparture().getSeconds() - visit.getDate().getSeconds();
        String formattedHours = Integer.toString(visitDuration/3600);
        String formattedMinutes = Integer.toString((visitDuration/60) % 60);
        String formattedSeconds = Integer.toString(visitDuration % 60);
        if (formattedHours.length() == 1) {
            formattedHours = "0" + formattedHours;
        }
        if (formattedMinutes.length() == 1) {
            formattedMinutes = "0" + formattedMinutes;
        }
        if (formattedSeconds.length() == 1) {
            formattedSeconds = "0" + formattedSeconds;
        }

        // Return the response.
        return this.sendResponse(visitorId + "," + formattedTime + "," + formattedHours + ":" + formattedMinutes + ":" + formattedSeconds);
    }
}