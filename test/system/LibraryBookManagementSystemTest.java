package system;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Behavior tests for the {@link LibraryBookManagementSystemTest} class.
 *
 * @author Zachary Cook
 */
public class LibraryBookManagementSystemTest {
    private LibraryBookManagementSystem CuT;

    /**
     * Sets up the unit tests.
     */
    @BeforeEach
    public void setup() {
        // Create the component under testing.
        this.CuT = new LibraryBookManagementSystem();
    }

    /**
     * Asserts that a request returns the intended response.
     *
     * @param request the request to make.
     * @param result the intended result.
     * @param errorMessage the error message if the result doesn't match.
     */
    private void assertRequest(String request,String result,String errorMessage) {
        // Run the request and get the result.
        String actualResult = CuT.performRequest(request);

        // Assert the request.
        assertEquals(actualResult,result,errorMessage);
    }

    /**
     * Asserts that a request returns the intended response.
     *
     * @param request the request to make.
     * @param result the intended result.
     */
    private void assertRequest(String request,String result) {
        this.assertRequest(request,result,"Results don't match.");
    }

    /**
     * Tests that partial requests will fail.
     */
    @Test
    public void test_partialRequest() {
        this.assertRequest("register,John,Doe,Test Address,1234567890","partial-request;");
    }

    /**
     * Tests registering visitors.
     */
    @Test
    public void test_register() {
        // Assert missing parameters.
        this.assertRequest("register;","register,missing-parameters,{first-name,last-name,address,phone-number};");
        this.assertRequest("register,John;","register,missing-parameters,{last-name,address,phone-number};");
        this.assertRequest("register,John,Doe;","register,missing-parameters,{address,phone-number};");
        this.assertRequest("register,John,Doe,Test Address;","register,missing-parameters,{phone-number};");

    }

    /**
     * Tests beginning visits.
     */
    @Test
    public void test_beginVisit() {

    }

    /**
     * Tests ending visits.
     */
    @Test
    public void test_endVisit() {

    }

    /**
     * Tests book searching.
     */
    @Test
    public void test_bookSearch() {

    }

    /**
     * Tests borrowing books.
     */
    @Test
    public void test_bookBorrow() {

    }

    /**
     * Tests finding borrowed books.
     */
    @Test
    public void test_findBorrowedBooks() {

    }

    /**
     * Tests finding returned books.
     */
    @Test
    public void test_findReturnedBooks() {

    }

    /**
     * Tests paying fines.
     */
    @Test
    public void test_payFines() {

    }

    /**
     * Tests searching the book store.
     */
    @Test
    public void test_searchBookStore() {

    }

    /**
     * Tests purchasing books.
     */
    @Test
    public void test_purchaseBook() {

    }

    /**
     * Tests advancing time.
     */
    @Test
    public void test_advanceTime() {

    }

    /**
     * Tests getting the current date and time.
     */
    @Test
    public void test_currentDateAndTime() {

    }

    /**
     * Test getting library statistics.
     */
    @Test
    public void test_libraryStatistics() {

    }
}
