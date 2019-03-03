package user.visit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import time.Date;
import time.Time;
import user.Name;
import user.Visitor;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This method tests the {@link VisitHistory} class
 *
 * @author Bendrix Bailey
 * @author Zachary Cook
 */
public class VisitHistoryTest {
    private Visitor visitor1;
    private Visitor visitor2;

    /**
     * Sets up the unit test.
     */
    @BeforeEach
    public void setup() {
        this.visitor1 = new Visitor("3402498657", new Name("Wharton", "Selmant"), "538 North ave", "6970430305");
        this.visitor2 = new Visitor("4920184032", new Name("Horace", "Blardo"), "576 rochester ave", "2348765435");
    }

    /**
     * Tests constructor for visitHistory
     */
    @Test
    public void test_constructor(){
        new VisitHistory();
    }

    /**
     * Tests the {@link VisitHistory#addVisit(Visitor,Date)} method.
     */
    @Test
    public void test_addVisit(){
        // Create the component under testing.
        VisitHistory CuT = new VisitHistory();
        CuT.addVisit(this.visitor1,new Date(8,12,2019,18,0,0));
    }

    /**
     * Tests the {@link VisitHistory#finishVisit(Visitor,Time)} method.
     */
    @Test
    public void test_finishVisit(){
        // Create the component under testing.
        VisitHistory CuT = new VisitHistory();
        CuT.addVisit(this.visitor1, new Date(7,17,2016,13,0,0));
        CuT.finishVisit(this.visitor1, new Time(15,45,0));
    }

    /**
     * Tests the {@link VisitHistory#finishAllVisits(Time)} method.
     */
    @Test
    public void test_finishAllVisits(){
        // Create the component under testing.
        VisitHistory CuT = new VisitHistory();
        CuT.addVisit(this.visitor1, new Date(4,5,2016,4,0,0));
        CuT.addVisit(this.visitor2, new Date(4, 5, 2016, 8, 30, 0));

        // Assert the average visit time is 0.
        Assertions.assertEquals(new Time(0), CuT.averageVisitTime(), "Average visit time isn't 0 for no finished visits.");

        // Finish the current visits.
        CuT.finishAllVisits(new Time(12, 30, 0));

        // Assert the average visit time.
        Assertions.assertEquals(new Time(22500), CuT.averageVisitTime(), "Incorrect average visit time");
    }

    /**
     * Tests the {@link VisitHistory#averageVisitTime()} method.
     */
    @Test
    public void test_averageVisitTime(){
        // Create the component under testing.
        VisitHistory CuT = new VisitHistory();
        CuT.addVisit(this.visitor1, new Date(4,5,2016,4,0,0));
        CuT.finishVisit(this.visitor1, new Time(6,45,0));

        // Assert the average visit time.
        Assertions.assertEquals(new Time(9900), CuT.averageVisitTime(), "Average visit time is incorrect");

        // Add a second visitor.
        CuT.addVisit(this.visitor2, new Date(4, 5, 2016, 8, 30, 0));
        CuT.finishVisit(this.visitor2, new Time(12, 30, 0));

        // Assert the average visit time.
        Assertions.assertEquals(new Time(12150), CuT.averageVisitTime(), "Incorrect average visit time");

        // Add but don't finish the first visitor, then assert the average visit time doesn't change.
        CuT.addVisit(this.visitor1, new Date(4, 5, 2016, 8, 30, 0));
        Assertions.assertEquals(new Time(12150), CuT.averageVisitTime(), "Average visit time changed");
    }
}
