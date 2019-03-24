package request.connected.revertable;


import request.Arguments;
import request.Parameter;
import request.connected.AccountRequest;
import response.Response;
import system.Clock;
import system.Services;
import time.Date;
import user.Visitor;
import user.connection.Connection;
import user.connection.User;

import java.util.ArrayList;

/**
 * Request for a visitor beginning a visit.
 *
 * @author Joey Zhen
 * @author Zachary Cook
 */
public class BeginVisit extends AccountRequest {
    /**
     * Creates a request.
     *
     * @param services the services to use for the request.
     * @param connection the connection to use.
     * @param arguments the arguments to use.
     */
    public BeginVisit(Services services,Connection connection,Arguments arguments) {
        super(services,connection,arguments,User.PermissionLevel.VISITOR);
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
     * Returns a list of the required parameters.
     *
     * @return a list of the required parameters.
     */
    @Override
    public ArrayList<Parameter> getRequiredParameters() {
        return new ArrayList<>();
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
        Connection connection = this.getConnection();

        // Get the visitor.
        Visitor visitor = connection.getUser().getVisitor();
        if (arguments.hasNext()) {
            String visitorId = arguments.getNextString();
            visitor = services.getVisitorsRegistry().getVisitor(visitorId);

            if (visitor == null) {
                return this.sendResponse("invalid-id");
            }
        }

        // Get the current time.
        Clock clock = services.getClock();
        Date date = clock.getDate();
        String formattedDate = date.formatDate();
        String formattedTime = date.formatTime();

        // Return an error if the library is closed.
        if (clock.getTimeState() == Clock.TimeState.CLOSED) {
            return this.sendResponse("closed");
        }

        // Return an error if there is an open visit.
        if (services.getVisitHistory().hasOpenVisit(visitor)) {
            return this.sendResponse("duplicate");
        }

        // Create the visit.
        services.getVisitHistory().addVisit(visitor,date);
        return this.sendResponse(visitor.getId() + "," + formattedDate + "," + formattedTime);
    }
}