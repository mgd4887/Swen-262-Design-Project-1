package time;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Date} class.
 *
 * @author Zachary Cook
 */
public class DateTest {
    /**
     * Tests that the construct works without failing.
     */
    @Test
    public void test_constructor() {
        new Date(3,14,2019,5,2,30);
    }

    /**
     * Tests the {@link Date#after(Date)} method.
     */
    @Test
    public void test_after() {
        // Create the components under testing.
        Date CuT = new Date(3,14,2019,5,2,30);

        // Run the assertions for dates being after.
        assertFalse(CuT.after(new Date(3,14,2020,5,2,30)),"Date is not after.");
        assertFalse(CuT.after(new Date(4,14,2019,5,2,30)),"Date is not after.");
        assertFalse(CuT.after(new Date(3,15,2019,5,2,30)),"Date is not after.");
        assertFalse(CuT.after(new Date(3,14,2019,6,2,30)),"Date is not after.");
        assertFalse(CuT.after(new Date(3,14,2019,5,3,30)),"Date is not after.");

        // Run the assertions for dates being the same.
        assertFalse(CuT.after(CuT),"Date is not after.");

        // Run the assertions for dates being before.
        assertTrue(CuT.after(new Date(3,14,2019,5,2,29)),"Date is after.");
        assertTrue(CuT.after(new Date(3,14,2019,5,1,30)),"Date is after.");
        assertTrue(CuT.after(new Date(3,14,2019,4,2,30)),"Date is after.");
        assertTrue(CuT.after(new Date(3,13,2019,5,2,30)),"Date is after.");
        assertTrue(CuT.after(new Date(2,14,2019,5,2,30)),"Date is after.");
        assertTrue(CuT.after(new Date(3,14,2018,5,2,30)),"Date is after.");
    }

    /**
     * Tests the {@link Date#toString()} method.
     */
    @Test
    public void test_toString() {
        // Create the components under testing.
        Date CuT = new Date(3,14,2019,5,2,30);

        // Run the assertions.
        assertEquals(CuT.toString(),"3/14/2019 5:02:30","Date is incorrect.");
    }

    /**
     * Tests the {@link Date#equals(Object)} method.
     */
    @Test
    public void test_equals() {
        // Create the components under testing.
        Date CuT1 = new Date(3,14,2019,5,2,30);
        Date CuT2 = new Date(3,14,2019,5,2,30);

        // Run the assertions.
        assertEquals(CuT1,CuT2,"Dates aren't equal.");
    }
}
