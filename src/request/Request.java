package request;

import response.Response;
import system.Services;

/**
 * Abstract class representing a request.
 *
 * @author Zachary Cook
 */
public abstract class Request {
    protected Services services;

    /**
     * Creates a request.
     *
     * @param services the services to use for the request.
     */
    public Request(Services services) {
        this.services = services;
    }

    /**
     * Sends a general response.
     *
     * @param response the response message, ignoring the final semicolon.
     */

    public Response sendResponse(String response) {
        // Create the response string.
        String responseString = this.getName() + "," + response + ";";

        // Return the response.
        return new Response(responseString);
    }

    /**
     * Sends a missing parameters response.
     *
     * @param missingParameters the missing parameters as "param-1,param-2,param-3"
     */
    public Response sendMissingParametersResponse(String missingParameters) {
        return this.sendResponse("missing-parameters,{" + missingParameters + "}");
    }

    /**
     * Returns the name of the request.
     *
     * @return the name of the request.
     */
    public abstract String getName();

    /**
     * Returns a response for the request.
     *
     * @param arguments the argument parser.
     * @return the response of the request.
     */
    public abstract Response handleRequest(Arguments arguments);
}
