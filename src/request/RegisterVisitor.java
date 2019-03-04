package request;

import response.Response;
import system.Clock;
import system.Services;
import time.Date;
import user.Name;
import user.Visitor;

/**
 * Request for registering a visitor.
 *
 * @author Joey Zhen
 * @author Zachary Cook
 */
public class RegisterVisitor extends Request {
    /**
     * Creates a request.
     *
     * @param services the services to use for the request.
     */
    public RegisterVisitor(Services services) {
        super(services);
    }

    /**
     * Returns the name of the request.
     *
     * @return the name of the request.
     */
    @Override
    public String getName() {
        return "register";
    }

    /**
     * Returns a response for the request.
     *
     * @param arguments the argument parser.
     * @return the response of the request.
     */
    @Override
    public Response handleRequest(Arguments arguments) {
        // Get the first name or return an error if it is missing.
        if (!arguments.hasNext()) {
            return this.sendMissingParametersResponse("first-name,last-name,address,phone-number");
        }
        String firstName = arguments.getNextString();

        // Get the last name or return an error if it is missing.
        if (!arguments.hasNext()) {
            return this.sendMissingParametersResponse("last-name,address,phone-number");
        }
        String lastName = arguments.getNextString();

        // Get the address or return an error if it is missing.
        if (!arguments.hasNext()) {
            return this.sendMissingParametersResponse("address,phone-number");
        }
        String address = arguments.getNextString();

        // Get the phone number or return an error if it is missing.
        if (!arguments.hasNext()) {
            return this.sendMissingParametersResponse("phone-number");
        }
        String phoneNumber = arguments.getNextString();

        // Check for an existing visitor and return an error if the visitor exists.
        Name visitorName = new Name(firstName,lastName);
        Visitor existingVisitor = this.services.getVisitorsRegistry().getVisitor(visitorName);
        if (existingVisitor != null && existingVisitor.getAddress().equals(address) && existingVisitor.getPhoneNumber().equals(phoneNumber)) {
            return this.sendResponse("duplicate");
        }

        // Get the current time.
        Clock clock = this.services.getClock();
        Date date = clock.getDate();
        String formattedDate = date.formatDate();
        String formattedTime = date.formatTime();

        // Register the visitor.
        Visitor visitor;
        try {
            visitor = this.services.getVisitorsRegistry().registerVisitor(visitorName,address,phoneNumber,date);
        } catch (IllegalArgumentException e) {
            return this.sendResponse("error," + e.getMessage());
        }

        // Return the response.

        return this.sendResponse(visitor.getId() + "," + formattedDate + " " + formattedTime);
    }
}