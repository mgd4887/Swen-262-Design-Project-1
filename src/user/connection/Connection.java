package user.connection;

import request.Request;

import java.util.ArrayList;

/**
 * Class representing an active connection.
 *
 * @author Zachary Cook
 */
public class Connection {
    private int id;
    private User user;
    private ArrayList<Request> pastRequests;
    private ArrayList<Request> undoneRequests;

    /**
     * Creates a connection.
     *
     * @param id the id of the connection.
     */
    public Connection(int id) {
        this.id = id;
        this.pastRequests = new ArrayList<>();
        this.undoneRequests = new ArrayList<>();
    }

    /**
     * Returns the id of the connection.
     *
     * @return the id of the connection.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Returns the user of the connection. It
     * may be null.
     *
     * @return the user of the connection.
     */
    public User getUser() {
        return this.user;
    }

    /**
     * Associate the user with the connection.
     *
     * @param user the user to associate.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Returns the next request to undo.
     *
     * @return the next request to undo.
     */
    public Request getRequestToUndo() {
        // Return null if there are no requests.
        if (this.pastRequests.size() == 0) {
            return null;
        }

        // Return the request.
        return this.pastRequests.get(this.pastRequests.size() - 1);
    }

    /**
     * Returns the next request to redo.
     *
     * @return the next request to redo.
     */
    public Request getRequestToRedo() {
        // Return null if there are no requests.
        if (this.undoneRequests.size() == 0) {
            return null;
        }

        // Return the request.
        return this.undoneRequests.get(this.undoneRequests.size() - 1);
    }

    /**
     * Adds a request that was done.
     *
     * @param request the request that was done.
     */
    public void addCompletedRequest(Request request) {
        this.undoneRequests.clear();
        this.pastRequests.add(request);
    }

    /**
     * Moves the last undone requests to the requests to redo.
     */
    public void lastRequestUndone() {
        // Return if there is no command to undo.
        if (this.pastRequests.size() == 0) {
            return;
        }

        // Move the request.
        this.undoneRequests.add(this.pastRequests.remove(this.pastRequests.size() - 1));
    }

    /**
     * Moves the last redone requests to the requests to undo.
     */
    public void lastRequestRedone() {
        // Return if there is no command to undo.
        if (this.undoneRequests.size() == 0) {
            return;
        }

        // Move the request.
        this.pastRequests.add(this.undoneRequests.remove(this.undoneRequests.size() - 1));
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "Connection " + this.getId();
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param obj the reference object with which to compare.
     *
     * @return true if this object is the same as the obj argument;
     * false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        // Return false if the class isn't the same.
        if (!(obj instanceof Connection)) {
            return false;
        }

        // Cast the object and return if the hashcodes are the same.
        Connection connection = (Connection) obj;
        return this.hashCode() == connection.hashCode();
    }
}
