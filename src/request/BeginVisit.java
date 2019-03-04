package request;


import response.Response;
import system.Clock;
import system.Services;
import time.Date;
import user.Visitor;

/**
 * Request for a visitor beginning a visit.
 *
 * @author Joey Zhen
 * @author Zachary Cook
 */
public class BeginVisit extends Request {
    /**
     * Creates a request.
     *
     * @param services the services to use for the request.
     */
    public BeginVisit(Services services) {
        super(services);
    }

    /**
     * Returns the name of the request.
     *
     * @return the name of the request.
     */
    @Override
    public String getName() {
        return "arrive";
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
        String formattedDate = date.formatDate();
        String formattedTime = date.formatTime();

        // Return an error if the library is closed.
        if (date.getHours() < 8 || date.getHours() > 19) {
            return this.sendResponse("closed");
        }

        // Return an error if there is an open visit.
        if (this.services.getVisitHistory().hasOpenVisit(visitor)) {
            return this.sendResponse("duplicate");
        }

        // Create the visit.
        this.services.getVisitHistory().addVisit(visitor,date);
        return this.sendResponse(visitorId + "," + formattedDate + "," + formattedTime);
    }
}