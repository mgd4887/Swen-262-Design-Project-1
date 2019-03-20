package user.connection;

/**
 * Class representing an active connection.
 *
 * @author Zachary Cook
 */
public class Connection {
    private int id;
    private User user;

    /**
     * Creates a connection.
     *
     * @param id the id of the connection.
     */
    public Connection(int id) {
        this.id = id;
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
