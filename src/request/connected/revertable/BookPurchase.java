package request.connected.revertable;

import books.Author;
import books.Book;
import request.Arguments;
import request.Parameter;
import request.Request;
import response.Response;
import system.Services;
import time.Date;
import user.connection.Connection;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Request for purchasing new books.
 *
 * @author Joey Zhen
 * @author Zachary Cook
 */
public class BookPurchase extends Request {
    /**
     * Creates a request.
     *
     * @param services the services to use for the request.
     * @param connection the connection to use.
     * @param arguments the arguments to use.
     */
    public BookPurchase(Services services,Connection connection,Arguments arguments) {
        super(services,connection,arguments);
    }

    /**
     * Returns the name of the request.
     *
     * @return the name of the request.
     */
    @Override
    public String getName() {
        return "buy";
    }

    /**
     * Returns a list of the required parameters.
     *
     * @return a list of the required parameters.
     */
    @Override
    public ArrayList<Parameter> getRequiredParameters() {
        // Create the required parameters.
        ArrayList<Parameter> requiredParameters = new ArrayList<>();
        requiredParameters.add(new Parameter("quantity",Parameter.ParameterType.INTEGER));
        requiredParameters.add(new Parameter("id", Parameter.ParameterType.REMAINING_INTEGERS));

        // Return the required parameters.
        return requiredParameters;
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

        // Get the quantity.
        int quantity = arguments.getNextInteger();

        // Get the ids.
        if (!arguments.hasNext()) {
            return this.sendMissingParametersResponse("id");
        }
        HashMap<Integer,Integer> amountToBuy = new HashMap<>();

        for (Integer id : arguments.getRemainingIntegers()) {
            // Add the id.
            if (amountToBuy.containsKey(id)) {
                amountToBuy.put(id,amountToBuy.get(id) + quantity);
            } else {
                amountToBuy.put(id,quantity);
            }
        }

        // Add the books.
        Date currentDate = services.getClock().getDate();
        for (int id : amountToBuy.keySet()) {
            Book book = services.getBookStore().getBook(id);

            for (int i = 0; i < amountToBuy.get(id); i++) {
                services.getBookInventory().registerBook(book);
                services.getBookInventory().getBook(book.getISBN()).addCopy();
                services.getPurchaseHistory().registerPurchase(book,currentDate);
            }
        }

        // Build the return string.
        String results = Integer.toString(amountToBuy.keySet().size());
        for (int id : amountToBuy.keySet()) {
            Book book = services.getBookStore().getBook(id);

            // Create the list of authors.
            String authorsList = "";
            for (Author author : book.getAuthors()) {
                if (!authorsList.equals("")) {
                    authorsList += ",";
                }

                authorsList += author.getName();
            }

            // Add the result.
            results += "\n" + book.getISBN() + "," + book.getName() + ",{" + authorsList + "}," + book.getPublishedDate().formatDate() + "," + amountToBuy.get(id) + ",";
        }

        // Return the result.
        return this.sendResponse(results);
    }
}