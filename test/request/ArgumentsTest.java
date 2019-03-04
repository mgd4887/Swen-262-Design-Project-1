package request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Arguments} class.
 */
public class ArgumentsTest {
    /**
     * Tests that the constructor works without failing.
     */
    @Test
    public void test_constructor() {
        new Arguments("Test1,Test2,Test3;");
    }

    /**
     * Tests that the constructor rejects the last character
     * not being a semicolon.
     */
    @Test
    public void test_constructorInvalidIds() {
        // Test with an id that contains letters.
        assertThrows(IllegalArgumentException.class,() -> new Arguments("Test1,Test2,Test3"),
                "Arguments was created.");
    }

    /**
     * Tests the {@link Arguments#getNextString()} and {@link Arguments#hasNext()} methods.
     */
    @Test
    public void test_getNextString() {
        // Create the component under testing.
        Arguments CuT = new Arguments("Test1,Test2,Test3;");

        // Run the assertions.
        assertEquals(CuT.getNextString(),"Test1","String is incorrect.");
        assertTrue(CuT.hasNext(),"Next doesn't exist.");
        assertEquals(CuT.getNextString(),"Test2","String is incorrect.");
        assertTrue(CuT.hasNext(),"Next doesn't exist.");
        assertEquals(CuT.getNextString(),"Test3","String is incorrect.");
        assertFalse(CuT.hasNext(),"Next does exist.");
    }
}
