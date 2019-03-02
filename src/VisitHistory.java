import time.Date;
import time.Time;
import user.Visitor;
import user.visit.Visit;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * This class stores all visit history, and allows history for each user and each day to be fetched.
 *
 * Uses a hashset to store each visit, where the key is a visitor name.
 */

public class VisitHistory {
    private HashSet<Visit> finishedVisits;
    private HashSet<Visit> unfinishedVisits;
    private int visitId;

    public VisitHistory(){
        this.finishedVisits = new HashSet<>();
        this.unfinishedVisits = new HashSet<>();
        this.visitId = 0;
    }

    /**
     * This method adds a new incomplete visit to the unfinished visits list.
     * @param visitor the visitor who is visiting
     * @param visitDate the date of the visit, includes start time
     */
    public void addVisit(Visitor visitor, Date visitDate){
        visitId = finishedVisits.size() + unfinishedVisits.size();
        Visit visit = new Visit(visitId, visitor, visitDate);
        unfinishedVisits.add(visit);
    }

    /**
     * This method ends a certain visitors visit, and moves that visit from the
     * unfinishedVisits list to the finishedVisits list
     * @param visitor the visitor of the ended visit
     * @param visitEndTime the time of the ended visit
     */
    public void finishVisit(Visitor visitor, Time visitEndTime){
        for (Visit v : unfinishedVisits){
            if(v.getVisitor().equals(visitor)){
                v.endVisit(visitEndTime);
            }
            unfinishedVisits.remove(v);
            finishedVisits.add(v);
        }

    }

    /**
     * This method calculates the average time for every completed visit to the library.
     * @return returns the average time as a new time object
     */
    public Time averageVisitTime(){
        int totalSeconds = 0;
        if(finishedVisits.size() > 0){
            for(Visit visit : finishedVisits){
                totalSeconds += visit.getTimeOfDeparture().getSeconds() - visit.getDate().getSeconds();
            }
        }

        return new Time(totalSeconds/finishedVisits.size());
    }

}
