package user;

import java.io.Serializable;

/**
 * Class representing an unformatted name. This is used for authors
 * since they may include extra parts or be unknown.
 *
 * @author Zachary Cook
 */
public class UnformattedName extends Name implements Serializable {
    private String name;

    /**
     * Creates a name class.
     *
     * @param name the name.
     */
    public UnformattedName(String name) {
        super(name,"");
        this.name = name;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return this.name;
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
        if (!(obj instanceof UnformattedName)) {
            return false;
        }

        // Cast the object and return if the hashcodes are the same.
        UnformattedName name = (UnformattedName) obj;
        return this.hashCode() == name.hashCode();
    }
}
