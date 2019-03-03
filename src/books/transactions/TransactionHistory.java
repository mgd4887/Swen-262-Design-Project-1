package books.transactions;

import books.Book;
import time.Date;
import user.Visitor;
import user.visit.Visit;

import java.util.ArrayList;
import java.util.Collection;
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

    /**
     * Gets an arraylist of transactions by a specific visitor
     * @param visitor the visitor whose transactions you want to find
     * @return an arraylist of transactions by the given visitor
     */
    public ArrayList<Transaction> getTransactionsByVisitor(Visitor visitor){
        ArrayList<Transaction> output = new ArrayList<>();
        Collection<Transaction> transactions = this.transactions.values();
        for(Transaction transaction: transactions){
            if (transaction.getVisitor().equals(visitor)){
                output.add(transaction);
            }
        }
        return output;
    }

    /**
     * Gets an arraylist of transactions by a the date the book was checked out
     * @param checkedOut the date on which the transaction took place
     * @return an arraylist of transactions where the book was checked out on the given date
     */
    public ArrayList<Transaction> getTransactionsCheckedOutOn(Date checkedOut){
        ArrayList<Transaction> output = new ArrayList<>();
        Collection<Transaction> transactions = this.transactions.values();
        for(Transaction transaction: transactions){
            if (transaction.getCheckedOut().equals(checkedOut)){
                output.add(transaction);
            }
        }
        return output;
    }

    /**
     * Gets an arraylist of transactions by a the date the book is due back
     * @param dueDate the date on which the book is due back
     * @return an arraylist of transactions where the book is due on the given date
     */
    public ArrayList<Transaction> getTransactionsDueOn(Date dueDate){
        ArrayList<Transaction> output = new ArrayList<>();
        Collection<Transaction> transactions = this.transactions.values();
        for(Transaction transaction: transactions){
            if (transaction.getDueDate().equals(dueDate)){
                output.add(transaction);
            }
        }
        return output;
    }

}
