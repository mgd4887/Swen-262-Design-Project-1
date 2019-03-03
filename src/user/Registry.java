package user;

import java.util.ArrayList;

/**
 * Class representing a registry of visitors.
 *
 * @author Zachary Cook
 */
public class Registry {
    private ArrayList<Visitor> visitors;

    /**
     * Creates a registry.
     */
    public Registry() {
        this.visitors = new ArrayList<>();
    }

    /**
     * Returns the next id to register a user.
     */
    public String getNextId() {
        int maxId = 0;

        // Determine the highest existing id.
        for (Visitor visitor : this.visitors) {
            int id = Integer.parseInt(visitor.getId());
            if (id > maxId) {
                maxId = id;
            }
        }

        // Get the next id.
        String nextId = Integer.toString(maxId + 1);

        // Add zeros to the next id.
        int baseIdLength = nextId.length();
        for (int i = 0; i < (10 - baseIdLength); i++) {
            nextId = "0" + nextId;
        }

        // Return the next id.
        return nextId;
    }

    /**
     * Registers a visitor.
     *
     * @param name the name of the visitor.
     * @param address the address of the visitor.
     * @param phoneNumber the phone number of the visitor.
     */
    public void registerVisitor(Name name,String address,String phoneNumber) {
        // Create the visitor.
        String id = this.getNextId();
        Visitor visitor = new Visitor(id,name,address,phoneNumber);

        // Add the visitor.
        this.visitors.add(visitor);
    }

    /**
     * Returns a list of all the visitors.
     *
     * @return all the visitors.
     */
    public ArrayList<Visitor> getVisitors() {
        return new ArrayList<>(this.visitors);
    }

    /**
     * Returns the visitor for the given id.
     *
     * @param id the id of teh visitor.
     * @return the visitor with the id.
     */
    public Visitor getVisitor(String id) {
        // Search for the id.
        for (Visitor visitor : this.visitors) {
            if (visitor.getId().equals(id)) {
                return visitor;
            }
        }

        // Return null (not found).
        return null;
    }

    /**
     * Returns the visitor for the given name.
     *
     * @param name the name of teh visitor.
     * @return the visitor with the name.
     */
    public Visitor getVisitor(Name name) {
        // Search for the name.
        for (Visitor visitor : this.visitors) {
            if (visitor.getName().equals(name.toString())) {
                return visitor;
            }
        }

        // Return null (not found).
        return null;
    }
}
