package time;

/**
 * Class representing a date.
 *
 * @author Zachary Cook
 * @author Michael Dolan
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
     * Determines if the date is after the given date.
     *
     * @param otherDate the date to check.
     * @return if the date is given after the given date.
     */
    public boolean after(Date otherDate) {
        // Return if there is a difference in daysDifference.
        int daysDifference = this.differenceInDays(otherDate);
        if (daysDifference > 0) {
            return true;
        } else if (daysDifference < 0) {
            return false;
        }

        // Return based on the difference in seconds.
        return this.getSeconds() > otherDate.getSeconds();
    }

    /**
     * Gets the difference in the number of days between two dates. The date given
     * as the parameter should be greater than for the difference to be positive.
     *
     * @param otherDate the date before this one to get he number of days between.
     * @return how many days are between two dates.
     */
    public int differenceInDays(Date otherDate) {
        int difference = 0;

        // Add based on the difference in years.
        difference += (this.year - otherDate.year) * 365;

        // Add based on the difference in months.
        // TODO: Doesn't account for leap years (year % 4 is 0 and year % 100 is not 0).
        int[] monthToDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        for (int i = otherDate.month; i < this.month; i++){
            difference += monthToDays[i];
        }

        // Add the difference based on the days.
        difference += this.day - otherDate.day;

        // Return the difference in days.
        return difference;
    }
}
