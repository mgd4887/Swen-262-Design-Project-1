package system;

import time.Date;

import java.io.Serializable;

/**
 * Class representing the "clock" of the system.
 *
 * @author Zachary Cook
 */
public class Clock implements Serializable {
    private Date currentDate;

    /**
     * Creates the clock.
     */
    public Clock(Date date) {
        this.currentDate = date;
    }

    /**
     * Creates a clock.
     */
    public Clock() {
        this(new Date(1,1,2019,8,0,0));
    }

    /**
     * Advances the system time.
     *
     * @param days the amount of days to advance.
     * @param hours the amount of hours to advance.
     */
    public void advanceTime(int days,int hours) {
        // Determine the new hours.
        int rawNewHours = this.currentDate.getHours() + hours;
        int newHours = rawNewHours % 24;
        days += (rawNewHours / 24);

        // Set the new date.
        this.currentDate = new Date(this.currentDate.getMonth(),this.currentDate.getDay() + days,this.currentDate.getYear(),newHours,0,0);
    }

    /**
     * Returns the current date.
     *
     * @return the current date.
     */
    public Date getDate() {
        return this.currentDate;
    }
}
