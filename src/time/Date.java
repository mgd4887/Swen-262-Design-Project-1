package time;

/**
 * Class representing a date.
 *
 * @author Zachary Cook
 */
public class Date extends Time {
    protected int day;
    protected int month;
    protected int year;

    /**
     * Creates a data object.
     *
     * @param month the month of the date.
     * @param day the day of the date.
     * @param year the year of the date.
     * @param hours the hours of the date.
     * @param minutes the minutes of the date.
     * @param seconds the seconds of the date.
     */
    public Date(int month,int day,int year,int hours,int minutes,int seconds) {
        super(hours,minutes,seconds);

        this.day = day;
        this.month = month;
        this.year = year;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return this.month + "/" + this.day + "/" + this.year + " " + super.toString();
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
        if (!(obj instanceof Date)) {
            return false;
        }

        // Cast the object and return if the hashcodes are the same.
        Date date = (Date) obj;
        return this.hashCode() == date.hashCode();
    }

    /**
     * tells if a given date is after this date
     * @param dueDate the date to check to see if it is after this date
     * @return if hte date is after this one
     */
    public boolean after(Date dueDate) {
        if (this.year > dueDate.year){
            return true;
        }
        if (this.year < dueDate.year){
            return false;
        }
        if (this.month > dueDate.month){
            return true;
        }
        if (this.month < dueDate.month){
            return false;
        }
        if (this.day > dueDate.day){
            return true;
        }
        if (this.day < dueDate.day){
            return false;
        }
        if (this.hours > dueDate.hours){
            return true;
        }
        if (this.hours < dueDate.hours){
            return false;
        }
        if (this.minutes > dueDate.minutes){
            return true;
        }
        if (this.minutes < dueDate.minutes){
            return false;
        }
        if (this.seconds > dueDate.seconds){
            return true;
        }
        if (this.seconds < dueDate.seconds){
            return false;
        }
        return false;
    }

    /**
     * gets the difference in the number of days between two dates
     * @param otherDate the date before this one to get he number of days between
     * @return how many days are between two dates
     */
    public int differenceInDays(Date otherDate) {
        int difference = 0;
        difference += (this.year - otherDate.year) * 365;

        int[] monthToDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        for (int i = otherDate.month; i < this.month; i++){
            difference += monthToDays[i];
        }

        difference += this.day += otherDate.day;

        return difference;
    }
}
