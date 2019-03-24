package system;

import books.BookStore;
import books.Inventory;
import books.purchases.PurchaseHistory;
import books.transactions.TransactionHistory;
import user.Registry;
import user.connection.ClientConnections;
import user.visit.VisitHistory;

import java.io.Serializable;

/**
 * Class representing a set of services.
 *
 * @author Zachary Cook
 */
public class Services implements Serializable {
    private TransactionHistory transactionHistory;
    private Inventory bookInventory;
    private VisitHistory visitHistory;
    private Registry visitorsRegistry;
    private Clock clock;
    private BookStore bookStore;
    private PurchaseHistory purchaseHistory;
    private ClientConnections clientConnections;

    /**
     * Creates the services.
     */
    public Services() {
        this.transactionHistory = new TransactionHistory();
        this.bookInventory = new Inventory();
        this.visitHistory = new VisitHistory();
        this.visitorsRegistry = new Registry();
        this.clock = new Clock();
        this.bookStore = BookStore.fromFile(BookStore.BOOK_STORE_FILE_LOCATION);
        this.purchaseHistory = new PurchaseHistory();
        this.clientConnections = new ClientConnections();
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

    /**
     * Returns the system clock.
     *
     * @return the system clock.
     */
    public Clock getClock() {
        return this.clock;
    }

    /**
     * Returns the book store.
     *
     * @return the book store.
     */
    public BookStore getBookStore() {
        return this.bookStore;
    }

    /**
     * Returns the purchase history.
     *
     * @return the purchase history.
     */
    public PurchaseHistory getPurchaseHistory() {
        return this.purchaseHistory;
    }

    /**
     * Returns the client connections.
     *
     * @return the client connections.
     */
    public ClientConnections getClientConnections() {
        return this.clientConnections;
    }
}
