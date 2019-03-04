package system;

import books.Inventory;
import books.transactions.TransactionHistory;
import user.Registry;
import user.visit.VisitHistory;

/**
 * Class representing a set of services.
 *
 * @author Zachary Cook
 */
public class Services {
    private TransactionHistory transactionHistory;
    private Inventory bookInventory;
    private VisitHistory visitHistory;
    private Registry visitorsRegistry;

    /**
     * Creates the services.
     */
    public Services() {
        this.transactionHistory = new TransactionHistory();
        this.bookInventory = new Inventory();
        this.visitHistory = new VisitHistory();
        this.visitorsRegistry = new Registry();
    }

    /**
     * Returns the transaction history object.
     *
     * @return the transaction history object.
     */
    public TransactionHistory getTransactionHistory() {
        return this.transactionHistory;
    }

    /**
     * Returns the book inventory.
     *
     * @return the book inventory.
     */
    public Inventory getBookInventory() {
        return bookInventory;
    }

    /**
     * Returns the visit history.
     *
     * @return the visit history.
     */
    public VisitHistory getVisitHistory() {
        return visitHistory;
    }

    /**
     * Returns the visitor registry.
     *
     * @return the visitor registry.
     */
    public Registry getVisitorsRegistry() {
        return visitorsRegistry;
    }
}
