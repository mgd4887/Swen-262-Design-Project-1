package request;

import books.BookStore;

import java.util.Arrays;
import java.util.List;

/**
 * Class representing a collection of arguments.
 *
 * @author Zachary Cook
 */
public class Arguments {
    private int pointer = 0;
    private List<String> paramters;

    /**
     * Creates the arguments collection.
     *
     * @param arguments a string of comma-separated values for parameters
     *                  and ending with a semicolon.
     */
    public Arguments(String arguments) {
        // Throw an error if the last character isn't a semicolon.
        if (!arguments.subSequence(arguments.length() - 1,arguments.length()).equals(";")) {
            throw new IllegalArgumentException("Last character must be a semicolon.");
        }

        // Remove the last character.
        arguments = arguments.substring(0,arguments.length() - 1);

        // Store the parameters.
        this.pointer = 0;
        this.paramters = BookStore.parseCSV(arguments);
    }

    /**
     * Returns if there is another string.
     *
     * @return if there is another string.
     */
    public boolean hasNext() {
        return this.pointer < this.paramters.size();
    }

    /**
     * Returns the next string.
     *
     * @return the next string.
     */
    public String getNextString() {
        // Return null if there is no next String.
        if (!this.hasNext()) {
            return null;
        }

        // Increment the pointer and return the next string.
        this.pointer += 1;
        return this.paramters.get(this.pointer - 1);
    }
}
