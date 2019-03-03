package books.transactions;

import java.util.HashMap;

public class TransactionHistory {

    private final HashMap<Integer, Transaction> transactions;

    /**
     * Creates the inventory of book.
     */
    public TransactionHistory() {
        this.transactions = new HashMap<>();
    }

    /**
     * Registers a transaction in the transaction history.
     *
     * @param transaction the transaction to register.
     */
    public void registerTransaction(Transaction transaction) {
        this.transactions.put(transaction.getId(),transaction);
    }

    public Transaction getTransaction(int id){
        return transactions.get(id);
    }

}
