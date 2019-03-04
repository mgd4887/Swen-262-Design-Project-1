package request;

import books.Author;
import books.Book;
import response.Response;
import system.Services;

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
     */
    public BookPurchase(Services services) {
        super(services);
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
     * Returns a response for the request.
     *
     * @param arguments the argument parser.
     * @return the response of the request.
     */
    @Override
    public Response handleRequest(Arguments arguments) {
        // Get the quantity or return an error if it is missing.
        if (!arguments.hasNext()) {
            return this.sendMissingParametersResponse("quantity,id");
        }

        int quantity;
        try {
            quantity = Integer.parseInt(arguments.getNextString());
        } catch (NumberFormatException e) {
            return this.sendResponse("quantity-not-a-number");
        }

        // Get the ids.
        if (!arguments.hasNext()) {
            return this.sendMissingParametersResponse("id");
        }
        HashMap<Integer,Integer> amountToBuy = new HashMap<>();

        while (arguments.hasNext()) {
            // Get the id.
            int id;
            try {
                id = Integer.parseInt(arguments.getNextString());
            } catch (NumberFormatException e) {
                return this.sendResponse("id-not-a-number");
            }

            // Add the id.
            if (amountToBuy.containsKey(id)) {
                amountToBuy.put(id,amountToBuy.get(id) + quantity);
            } else {
                amountToBuy.put(id,quantity);
            }
        }

        // Add the books.
        for (int id : amountToBuy.keySet()) {
            Book book = this.services.getBookStore().getBook(id);

            for (int i = 0; i < amountToBuy.get(id); i++) {
                this.services.getBookInventory().registerBook(book);
                this.services.getBookInventory().getBook(book.getISBN()).addCopy();
            }
        }

        // Build the return string.
        String results = Integer.toString(amountToBuy.keySet().size());
        for (int id : amountToBuy.keySet()) {
            Book book = this.services.getBookStore().getBook(id);

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