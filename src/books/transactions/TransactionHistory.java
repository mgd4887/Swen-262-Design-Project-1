package books.transactions;

import time.Date;
import user.Visitor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Class for storing transactions.
 *
 * @author Michael Dolan
 * @author Zachary Cook
 */
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

    /**
     * Returns the transaction for the given id.
     *
     * @param id the id of the transaction.
     * @return the transaction for the given id.
     */
    public Transaction getTransaction(int id){
        return transactions.get(id);
    }

    /**
     * Gets an ArrayList of transactions by a specific visitor.
     *
     * @param visitor the visitor whose transactions you want to find.
     * @return an ArrayList of transactions by the given visitor.
     */
    public ArrayList<Transaction> getTransactionsByVisitor(Visitor visitor){
        // Search for the transactions by the visitor.
        ArrayList<Transaction> output = new ArrayList<>();
        Collection<Transaction> transactions = this.transactions.values();
        for(Transaction transaction: transactions){
            if (transaction.getVisitor().equals(visitor)){
                output.add(transaction);
            }
        }

        // Return the transactions.
        return output;
    }

    /**
     * Gets an ArrayList of transactions by a the date the book was checked out.
     *
     * @param checkedOut the date on which the transaction took place.
     * @return an ArrayList of transactions where the book was checked out on the given date.
     */
    public ArrayList<Transaction> getTransactionsCheckedOutOn(Date checkedOut){
        // Search for the transactions by the date.
        ArrayList<Transaction> output = new ArrayList<>();
        Collection<Transaction> transactions = this.transactions.values();
        for(Transaction transaction: transactions){
            if (transaction.getCheckedOut().equals(checkedOut)){
                output.add(transaction);
            }
        }

        // Return the transactions.
        return output;
    }

    /**
     * Gets an ArrayList of transactions by a the date the book is due back.
     *
     * @param dueDate the date on which the book is due back.
     * @return an ArrayList of transactions where the book is due on the given date.
     */
    public ArrayList<Transaction> getTransactionsDueOn(Date dueDate){
        // Search for the transactions by the date.
        ArrayList<Transaction> output = new ArrayList<>();
        Collection<Transaction> transactions = this.transactions.values();
        for(Transaction transaction: transactions){
            if (transaction.getDueDate().equals(dueDate)){
                output.add(transaction);
            }
        }

        // Return the transactions.
        return output;
    }
}