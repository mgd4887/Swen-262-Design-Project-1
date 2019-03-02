package user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Visitor} class.
 *
 * @author Zachary Cook
 */
public class VisitorTest {
    /**
     * Tests that the construct works without failing.
     */
    @Test
    public void test_constructor() {
        new Visitor("0000000001","John","Doe","","1234567890");
    }

    /**
     * Tests that the constructor throws an error for
     * invalid ids.
     */
    @Test
    public void test_constructorInvalidIds() {
        // Test with an id that contains letters.
        assertThrows(IllegalArgumentException.class,() -> new Visitor("a000000001","John","Doe","","1234567890"),
                "Letters in the id were accepted.");

        // Test with an that is too short.
        assertThrows(IllegalArgumentException.class,() -> new Visitor("0000001","John","Doe","","1234567890"),
                "Short id was accepted.");

        // Test with an that is too long.
        assertThrows(IllegalArgumentException.class,() -> new Visitor("0000000000001","John","Doe","","1234567890"),
                "Long id was accepted.");
    }

    /**
     * Tests that the constructor throws an error for
     * invalid phone numbers.
     */
    @Test
    public void test_constructorInvalidPhoneNumbers() {
        // Test with a phone number that contains letters.
        assertThrows(IllegalArgumentException.class,() -> new Visitor("0000000001","John","Doe","","a1234567890"),
                "Letters in the phone number were accepted.");
    }

    /**
     * Tests the {@link Visitor#getId()} method.
     */
    @Test
    public void test_getId() {
        // Create the component under testing.
        Visitor CuT = new Visitor("0000000001","John","Doe","","1234567890");

        // Run the assertions.
        assertEquals(CuT.getId(),"0000000001","Id is incorrect.");
    }

    /**
     * Tests the {@link Visitor#getName()} method.
     */
    @Test
    public void test_getName() {
        // Create the component under testing.
        Visitor CuT = new Visitor("0000000001","John","Doe","","1234567890");

        // Run the assertions.
        assertEquals(CuT.getName(),"John Doe","Name is incorrect.");
    }

    /**
     * Tests the {@link Visitor#getAddress()} method.
     */
    @Test
    public void test_getAddress() {
        // Create the component under testing.
        Visitor CuT = new Visitor("0000000001","John","Doe","Test Address","1234567890");

        // Run the assertions.
        assertEquals(CuT.getAddress(),"Test Address","Address is incorrect.");
    }

    /**
     * Tests the {@link Visitor#getPhoneNumber()} method.
     */
    @Test
    public void test_getPhoneNumber() {
        // Create the component under testing.
        Visitor CuT = new Visitor("0000000001","John","Doe","Test Address","1234567890");

        // Run the assertions.
        assertEquals(CuT.getPhoneNumber(),"1234567890","Phone number is incorrect.");
    }

    /**
     * Tests the {@link Visitor#toString()} method.
     */
    @Test
    public void test_toString() {
        // Create the component under testing.
        Visitor CuT = new Visitor("0000000001","John","Doe","","1234567890");

        // Run the assertions.
        assertEquals(CuT.toString(),"John Doe (0000000001)","Name is incorrect.");
    }

    /**
     * Tests the {@link Visitor#equals(Object)} method.
     */
    @Test
    public void test_equals() {
        // Create the components under testing.
        Visitor CuT1 = new Visitor("0000000001","John","Doe","","1234567890");
        Visitor CuT2 = new Visitor("0000000001","John","Doe","","1234567890");
        Visitor CuT3 = new Visitor("0000000002","John","Doe","","1234567890");

        // Run the assertions.
        assertEquals(CuT1,CuT2,"Visitors aren't equal.");
        assertNotEquals(CuT1,CuT3,"Visitors are equal.");
    }
}
