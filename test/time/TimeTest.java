package time;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the {@link Time} class.
 *
 * @author Zachary Cook
 */
public class TimeTest {
    /**
     * Tests that the construct works without failing.
     */
    @Test
    public void test_constructor() {
        new Time(5,2,30);
        new Time((5 * 3600) + (2 * 60) + 30);
    }

    /**
     * Tests the seconds converter of time.
     */
    @Test
    public void test_getSeconds(){
        Time secondTime = new Time(560);
        Time regularTime = new Time(2, 3, 15);

        assertEquals(560, secondTime.getSeconds(), "Seconds incorrect");
        assertEquals(7395, regularTime.getSeconds(), "Seconds incorrect");
    }

    /**
     * Tests the {@link Time#toString()} method.
     */
    @Test
    public void test_toString() {
        // Create the components under testing.
        Time CuT1 = new Time(5,2,30);
        Time CuT2 = new Time((5 * 3600) + (2 * 60) + 30);

        // Run the assertions.
        assertEquals(CuT1.toString(),"5:02:30","Time incorrect.");
        assertEquals(CuT2.toString(),"5:02:30","Time incorrect.");
    }

    /**
     * Tests the {@link Time#equals(Object)} method.
     */
    @Test
    public void test_equals() {
        // Create the components under testing.
        Time CuT1 = new Time(5,2,30);
        Time CuT2 = new Time((5 * 3600) + (2 * 60) + 30);

        // Run the assertions.
        assertEquals(CuT1,CuT2,"Times aren't equal.");
    }
}

