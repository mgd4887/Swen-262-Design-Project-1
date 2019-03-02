
import org.junit.jupiter.api.Test;
import time.Date;
import time.Time;
import user.Name;
import user.Visitor;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This method tests the {@Link VisitHistory} class
 *
 * @author Bendrix Bailey
 */
public class TestVisitHistory {

    /**
     * Tests constructor for visitHistory
     */
    @Test
    public void test_constructor(){
        new VisitHistory();
    }

    @Test
    public void test_addVisit(){
        VisitHistory visitHistory = new VisitHistory();
        Visitor v1 = new Visitor("3402498657", new Name("Wharton", "Selmant"), "538 North ave", "6970430305");
        visitHistory.addVisit(v1, new Date(8,12,2019,18,0,0));
    }

    @Test
    public void test_finishVisit(){
        VisitHistory visitHistory = new VisitHistory();
        Visitor v1 = new Visitor("9032854932", new Name("Ronnie", "Bronson"), "412 Shark drive", "0945321854");
        visitHistory.addVisit(v1, new Date(7,17,2016,13,0,0));
        visitHistory.finishVisit(v1, new Time(15,45,0));
    }


    @Test
    public void test_averageVisitTime(){
        VisitHistory visitHistory = new VisitHistory();
        Visitor v1 = new Visitor("3203021457", new Name("Johnny", "Bravo"), "43 loophole road", "5092332214");
        visitHistory.addVisit(v1, new Date(4,5,2016,4,0,0));
        visitHistory.finishVisit(v1, new Time(6,45,0));

        assertEquals(new Time(9900), visitHistory.averageVisitTime(), "Average visit time is incorrect");

        Visitor v2 = new Visitor("4920184032", new Name("Horace", "Blardo"), "576 rochester ave", "2348765435");
        visitHistory.addVisit(v2, new Date(4, 5, 2016, 8, 30, 0));
        visitHistory.finishVisit(v2, new Time(12, 30, 0));

        assertEquals(new Time(12150), visitHistory.averageVisitTime(), "Incorrect average visit time");
    }
}
