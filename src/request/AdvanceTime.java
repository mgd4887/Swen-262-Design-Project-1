package request;

import response.Response;
import system.Services;
import time.Date;
import time.Time;

/**
 * Request for advancing the current time.
 *
 * @author Joey Zhen
 * @author Zachary Cook
 */
public class AdvanceTime extends Request {
    /**
     * Creates a request.
     *
     * @param services the services to use for the request.
     */
    public AdvanceTime(Services services) {
        super(services);
    }

    /**
     * Returns the name of the request.
     *
     * @return the name of the request.
     */
    @Override
    public String getName() {
        return "advance";
    }

    /**
     * Returns a response for the request.
     *
     * @param arguments the argument parser.
     * @return the response of the request.
     */
    @Override
    public Response handleRequest(Arguments arguments) {
        // Return an error if the number of days is missing.
        if (!arguments.hasNext()) {
            return this.sendMissingParametersResponse("number-of-days");
        }

        // Get the number of hours and days to advance.
        String daysToAdvanceString = arguments.getNextString();
        int daysToAdvance = 0;
        String hoursToAdvanceString = "";
        int hoursToAdvance = 0;

        // Get the days to advance.
        try {
            daysToAdvance = Integer.parseInt(daysToAdvanceString);
        } catch (NumberFormatException e) {
            return this.sendResponse("invalid-number-of-days," + daysToAdvanceString);
        }

        // Get the hours to advance.
        if (arguments.hasNext()) {
            hoursToAdvanceString = arguments.getNextString();
            try {
                hoursToAdvance = Integer.parseInt(hoursToAdvanceString);
            } catch (NumberFormatException e) {
                return this.sendResponse("invalid-number-of-hours," + hoursToAdvanceString);
            }
        }

        // Return an error if the days or hours are invalid.
        if (daysToAdvance < 0 || daysToAdvance > 7) {
            return this.sendResponse("invalid-number-of-days," + daysToAdvanceString);
        }
        if (hoursToAdvance < 0 || hoursToAdvance > 23) {
            return this.sendResponse("invalid-number-of-hours," + hoursToAdvanceString);
        }

        // Advance the time.
        Date beginningTime = this.services.getClock().getDate();
        this.services.getClock().advanceTime(daysToAdvance,hoursToAdvance);

        // End visits if it is past closing or the day has changed.
        Date endingTime = this.services.getClock().getDate();
        if (beginningTime.getDay() != endingTime.getDay() || endingTime.getHours() >= 19) {
            this.services.getVisitHistory().finishAllVisits(new Time(19,0,0));
        }

        // Return success.
        return new Response("advance,success;");
    }
}