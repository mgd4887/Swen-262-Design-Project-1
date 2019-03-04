package time;

/**
 * Base class representing a time. This includes the
 * hours, minutes, and seconds of a day.
 *
 * @author Zachary Cook
 * @author Bendrix Bailey
 */
public class Time {
    protected int hours;
    protected int minutes;
    protected int seconds;

    /**
     * Creates a time object.
     *
     * @param hours the hours of the time.
     * @param minutes the minutes of the time.
     * @param seconds the seconds of the time.
     */
    public Time(int hours,int minutes,int seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    /**
     * Creates a time object.
     *
     * @param totalSeconds the total seconds of the time.
     */
    public Time(int totalSeconds) {
        this(totalSeconds / 3600,(totalSeconds / 60) % 60,totalSeconds % 60);
    }

    /**
     * Returns the hours of the time.
     *
     * @return the hours of the time.
     */
    public int getHours() {
        return this.hours;
    }

    /**
     * Returns the total time in seconds.
     *
     * @return returns time in seconds as an int
     */
    public int getSeconds(){
        return (this.hours * 3600) + (this.minutes * 60) + this.seconds;
    }

    /**
     * Formats the time as HH:MM:SS.
     *
     * @return the formatted date.
     */
    public String formatTime() {
        // Determine the partial strings.
        String seconds = String.valueOf(this.seconds);
        if (seconds.length() == 1) {
            seconds = "0" + seconds;
        }

        String minutes = String.valueOf(this.minutes);
        if (minutes.length() == 1) {
            minutes = "0" + minutes;
        }

        String hours = String.valueOf(this.hours);
        if (hours.length() == 1) {
            hours = "0" + hours;
        }

        // Create the new string.
        return hours + ":" + minutes + ":" + seconds;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        // Determine the partial strings.
        String seconds = String.valueOf(this.seconds);
        if (seconds.length() == 1) {
            seconds = "0" + seconds;
        }

        String minutes = String.valueOf(this.minutes);
        if (minutes.length() == 1) {
            minutes = "0" + minutes;
        }

        // Create the new string.
        return this.hours + ":" + minutes + ":" + seconds;
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
        if (!(obj instanceof Time)) {
            return false;
        }

        // Cast the object and return if the hashcodes are the same.
        Time time = (Time) obj;
        return this.hashCode() == time.hashCode();
    }
}
