package request;

import response.Response;
import system.Services;

/**
 * Request for setting the fines paid by a user as paid.
 *
 * @author Joey Zhen
 * @author Zachary Cook
 */
public class PayFine extends Request {
    /**
     * Creates a request.
     *
     * @param services the services to use for the request.
     */
    public PayFine(Services services) {
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
        // TODO: Unimplemented
        return null;
    }
}