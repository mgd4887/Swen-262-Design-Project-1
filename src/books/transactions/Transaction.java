package books.transactions;

import books.Book;
import time.Date;
import user.Visitor;

import java.util.Objects;

/**
 * Stores the transaction of a book.
 *
 * @author Michael Dolan
 * @author Zachary Cook
 */
public class Transaction {
    private int id;
    private Visitor visitor;
    private Book book;
    private Date checkedOut;
    private Date dueDate;
    private boolean lateFeePaid;

    /**
     * Creates a transaction.
     *
     * @param id the id of the transaction.
     * @param visitor the visitor involved in the transaction.
     * @param book the book involved in the transaction.
     * @param checkedOut the date the book was checked out.
     * @param dueDate the due date of the book.
     */
    public Transaction(int id,Visitor visitor,Book book,Date checkedOut,Date dueDate) {
        this.id = id;
        this.visitor = visitor;
        this.book = book;
        this.checkedOut = checkedOut;
        this.dueDate = dueDate;
        this.lateFeePaid = false;
    }

    /**
     * Returns the id of the transaction.
     *
     * @return the id.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Returns the visitor of the transaction.
     *
     * @return the visitor.
     */
    public Visitor getVisitor() {
        return this.visitor;
    }

    /**
     * Returns the book of the transaction.
     *
     * @return the book.
     */
    public Book getBook() {
        return this.book;
    }

    /**
     * Returns the checked out date.
     *
     * @return the checked out date.
     */
    public Date getCheckedOut() {
        return checkedOut;
    }

    /**
     * Returns the due date.
     *
     * @return the due date.
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * Returns if the late fee was paid.
     *
     * @return if the late fee was paid.
     */
    public boolean getLateFeedPaid() {
        return this.lateFeePaid;
    }

    /**
     * Sets the late fee as paid.
     */
    public void setLateFeeAsPaid() {
        this.lateFeePaid = true;
    }

    /**
     * Returns the late fee for the transaction.
     *
     * @param currentDate the current date to determine the late fee.
     * @return the late fee in dollars.
     */
    public double calculateFee(Date currentDate){
        double fee = 0;

        // Calculate the fee if the current date is after the due date.
        if (currentDate.after(dueDate) && !this.getLateFeedPaid()) {
            // Add the base fee.
            fee += 10;

            // Add the additional fee.
            int daysPastDueDate = currentDate.differenceInDays(dueDate);
            int weeksPastDueDate = daysPastDueDate / 7;
            fee += weeksPastDueDate * 2;

            // Clamp the fee to 30 dollars.
            if (fee > 30){
                fee = 30;
            }
        }

        // Return the late fee.
        return fee;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return this.getVisitor().toString() + ": " + this.getId() + " " + this.getBook().getISBN() + " " + this.getCheckedOut() + " - " + this.getDueDate();
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, visitor, book, checkedOut, dueDate);
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param o the reference object with which to compare.
     *
     * @return true if this object is the same as the obj argument;
     * false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return id == that.id &&
                Objects.equals(visitor, that.visitor) &&
                Objects.equals(book, that.book) &&
                Objects.equals(checkedOut, that.checkedOut) &&
                Objects.equals(dueDate, that.dueDate);
    }
}
