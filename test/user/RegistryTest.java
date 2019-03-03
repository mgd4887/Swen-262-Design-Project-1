package user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the {@link Registry} class.
 *
 * @author Zachary Cook
 */
public class RegistryTest {
    /**
     * Test that the constructor works without failing.
     */
    @Test
    public void test_constructor() {
        new Registry();
    }

    /**
     * Tests the {@link Registry#getNextId()} method.
     */
    @Test
    public void test_getNextId() {
        // Create the component under testing.
        Registry CuT = new Registry();

        // Assert the first id is 1.
        assertEquals(CuT.getNextId(),"0000000001","First id is incorrect.");

        // Register 1 user and and assert the id is 2.
        CuT.registerVisitor(new Name("John","Doe"),"Test Address","1234567890");
        assertEquals(CuT.getNextId(),"0000000002","Second id is incorrect.");
        assertEquals(CuT.getVisitor("0000000001").getName(),"John Doe","Name is incorrect.");

        // Register 2 user and and assert the id is 3.
        CuT.registerVisitor(new Name("Jane","Doe"),"Test Address","1234567890");
        assertEquals(CuT.getNextId(),"0000000003","Third id is incorrect.");
        assertEquals(CuT.getVisitor("0000000001").getName(),"John Doe","Name is incorrect.");
        assertEquals(CuT.getVisitor("0000000002").getName(),"Jane Doe","Name is incorrect.");
    }

    /**
     * Tests the {@link Registry#getVisitor(Name)} method.
     */
    @Test
    public void test_getVisitorName() {
        // Create the component under testing.
        Registry CuT = new Registry();
        CuT.registerVisitor(new Name("John","Doe"),"Test Address","1234567890");
        CuT.registerVisitor(new Name("Jane","Doe"),"Test Address","1234567890");

        // Run the assertions.
        assertEquals(CuT.getVisitor(new Name("John","Doe")).getName(),"John Doe","Name is incorrect.");
        assertEquals(CuT.getVisitor(new Name("Jane","Doe")).getName(),"Jane Doe","Name is incorrect.");
    }
}
