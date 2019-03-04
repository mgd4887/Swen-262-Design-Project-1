package user.visit;

import time.Date;
import time.Time;
import user.Visitor;
import user.visit.Visit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.function.Predicate;

/**
 * This class stores all visit history, and allows history for each user and each day to be fetched.
 *
 * @author Bendrix Bailey
 * @author Zachary Cook
 */

public class VisitHistory implements Serializable {
    private ArrayList<Visit> finishedVisits;
    private ArrayList<Visit> unfinishedVisits;
    private int visitId;

    /**
     * Creates the visit history.
     */
    public VisitHistory(){
        this.finishedVisits = new ArrayList<>();
        this.unfinishedVisits = new ArrayList<>();
        this.visitId = 0;
    }

    /**
     * Returns if the visitor has an open visit.
     *
     * @param visitor the visitor to check.
     */
    public boolean hasOpenVisit(Visitor visitor) {
        // Return true if an unfinished visit is found.
        for (Visit visit : this.unfinishedVisits) {
            if (visit.getVisitor().equals(visitor)) {
                return true;
            }
        }

        // Return false (visit not found).
        return false;
    }

    /**
     * This method adds a new incomplete visit to the unfinished visits list
     *
     * @param visitor the visitor who is visiting.
     * @param visitDate the date of the visit, includes start time.
     */
    public Visit addVisit(Visitor visitor, Date visitDate){
        // Create the visit.
        this.visitId = this.finishedVisits.size() + this.unfinishedVisits.size();
        Visit visit = new Visit(visitId, visitor, visitDate);

        // Register the visit as unfinished and return the visit.
        this.unfinishedVisits.add(visit);
        return visit;
    }

    /**
     * This method ends a certain visitors visit, and moves that visit from the
     * unfinishedVisits list to the finishedVisits list.
     *
     * @param visitor the visitor of the ended visit
     * @param visitEndTime the time of the ended visit
     * @return the visit, if any.
     */
    public Visit finishVisit(Visitor visitor, Time visitEndTime){
        // Get the unfinished visit.
        Visit visitToRemove = null;
        for (Visit visit : this.unfinishedVisits) {
            if (visit.getVisitor().equals(visitor)) {
                visit.endVisit(visitEndTime);
                visitToRemove = visit;
                break;
            }
        }

        // Remove the visit, if any.
        if (visitToRemove != null) {
            this.unfinishedVisits.remove(visitToRemove);
            this.finishedVisits.add(visitToRemove);
        }

        // Return null (no visit).
        return visitToRemove;
    }

    /**
     * Finishes all of the unfinished visits.
     *
     * @param visitEndTime the time of the ended visit
     */
    public void finishAllVisits(Time visitEndTime) {
        // End the visits.
        for (Visit visit : this.unfinishedVisits) {
            visit.endVisit(visitEndTime);
        }

        // Move all the visits.
        this.finishedVisits.addAll(this.unfinishedVisits);
        this.unfinishedVisits.clear();
    }

    /**
     * This method calculates the average time for every completed visit to the library.
     *
     * @return returns the average time as a new time object
     */
    public Time averageVisitTime(){
        // Return 0 if there are no finished visits.
        if (this.finishedVisits.size() == 0) {
            return new Time(0);
        }

        // Calculate the total seconds of the visits.
        int totalSeconds = 0;
        for (Visit visit : this.finishedVisits) {
            totalSeconds += visit.getTimeOfDeparture().getSeconds() - visit.getDate().getSeconds();
        }

        // Determine and return the average.
        return new Time(totalSeconds/this.finishedVisits.size());
    }
}
