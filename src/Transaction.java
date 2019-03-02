import time.Date;
import time.Time;

public class Transaction {

    int ISBN;
    int id;
    Date checkedOut;
    Date dueDate;

    public Transaction(int ISBN, int id, Date checkedOut, Date dueDate) {
        this.ISBN = ISBN;
        this.id = id;
        this.checkedOut = checkedOut;
        this.dueDate = dueDate;
    }

    public double CalculateFee(Date currentDate){
        double fee = 00.00;
        if (currentDate.after(dueDate)){
            fee += 10.00;
            int difference = currentDate.differenceInDays(dueDate);
            fee += (difference % 7) * 2;
            if (fee > 30.00){
                fee = 30.00;
            }
        }
        return fee;
    }

    public int getISBN() {
        return ISBN;
    }

    public int getId() {
        return id;
    }

    public Date getCheckedOut() {
        return checkedOut;
    }

    public Date getDueDate() {
        return dueDate;
    }
}
