package user.visit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import time.Date;
import time.Time;
import user.Visitor;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Visit} class.
 *
 * @author Zachary Cook
 */
public class VisitTest {
    private Visitor visitor;
    private Date start;
    private Time end;

    /**
     * Sets up the unit test.
     */
    @BeforeEach
    public void setup() {
        // Create the visitor.
        this.visitor = new Visitor("0000000001","John","Doe","","1234567890");

        // Create the times.
        this.start = new Date(1,2,2019,4,5,6);
        this.end = new Time(6,21,7);
    }

    /**
     * Tests that the construct works without failing.
     */
    @Test
    public void test_constructor() {
        new Visit(1,this.visitor,this.start,this.end);
        new Visit(1,this.visitor,this.start);
    }

    /**
     * Tests the {@link Visit#getId()} method.
     */
    @Test
    public void test_getId() {
        // Create the component under testing.
        Visit CuT = new Visit(1,this.visitor,this.start);

        // Run the assertions.
        assertEquals(CuT.getId(),1,"Id is incorrect.");
    }

    /**
     * Tests the {@link Visit#getVisitor()} method.
     */
    @Test
    public void test_getVisitor() {
        // Create the component under testing.
        Visit CuT = new Visit(1,this.visitor,this.start);

        // Run the assertions.
        assertEquals(CuT.getVisitor(),this.visitor,"Visitor is incorrect.");
    }

    /**
     * Tests the {@link Visit#getDate()} method.
     */
    @Test
    public void test_getDate() {
        // Create the component under testing.
        Visit CuT = new Visit(1,this.visitor,this.start);

        // Run the assertions.
        assertEquals(CuT.getDate(),this.start,"Date is incorrect.");
    }

    /**
     * Tests the {@link Visit#getTimeOfDeparture()} method.
     */
    @Test
    public void test_getTimeOfDeparture() {
        // Create the component under testing.
        Visit CuT1 = new Visit(1,this.visitor,this.start);
        Visit CuT2 = new Visit(1,this.visitor,this.start,this.end);

        // Run the assertions.
        assertNull(CuT1.getTimeOfDeparture(),"Time is incorrect.");
        assertEquals(CuT2.getTimeOfDeparture(),this.end,"Time is incorrect.");
    }

    /**
     * Tests the {@link Visit#hasEnded()} method.
     */
    @Test
    public void test_hasEnded() {
        // Create the component under testing.
        Visit CuT1 = new Visit(1,this.visitor,this.start);
        Visit CuT2 = new Visit(1,this.visitor,this.start,this.end);

        // Run the assertions.
        assertFalse(CuT1.hasEnded(),"Visit has ended.");
        assertTrue(CuT2.hasEnded(),"Visit hasn't ended.");
    }

    /**
     * Tests the {@link Visit#endVisit(Time)} method.
     */
    @Test
    public void test_endVisit() {
        // Create the component under testing.
        Visit CuT = new Visit(1,this.visitor,this.start);

        // Run the assertions.
        assertFalse(CuT.hasEnded(),"Visit has ended.");
        CuT.endVisit(this.end);
        assertTrue(CuT.hasEnded(),"Visit hasn't ended.");
    }

    /**
     * Tests the {@link Visit#toString()} method.
     */
    @Test
    public void test_toString() {
        // Create the component under testing.
        Visit CuT1 = new Visit(1,this.visitor,this.start);
        Visit CuT2 = new Visit(1,this.visitor,this.start,this.end);

        // Run the assertions.
        assertEquals(CuT1.toString(),this.visitor.toString() + " @ " + this.start.toString(),"Visit string is incorrect.");
        assertEquals(CuT2.toString(),this.visitor.toString() + " @ " + this.start.toString() + " - " + this.end.toString(),"Visit string is incorrect.");
    }

    /**
     * Tests the {@link Visit#equals(Object)} method.
     */
    @Test
    public void test_equals() {
        // Create the component under testing.
        Visit CuT1 = new Visit(1,this.visitor,this.start);
        Visit CuT2 = new Visit(1,this.visitor,this.start,this.end);
        Visit CuT3 = new Visit(1,this.visitor,this.start,this.end);

        // Run the assertions.
        assertNotEquals(CuT1,CuT2,"Visits are equal.");
        assertEquals(CuT2,CuT3,"Visits aren't equal.");
    }
}
