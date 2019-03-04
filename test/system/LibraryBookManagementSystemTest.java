package system;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import user.Name;
import user.Visitor;
import user.visit.Visit;

import static org.junit.jupiter.api.Assertions.*;

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

        // Assert registering visitors.
        this.assertRequest("register,John,Doe,Test Address,1234567890;","register,0000000001,2019/01/01 08:00:00;");
        this.assertRequest("register,Jane,Doe,Test Address,1234567890;","register,0000000002,2019/01/01 08:00:00;");
        this.assertRequest("register,Jane,Doe,Test Address 2,1234567890;","register,0000000003,2019/01/01 08:00:00;");
        assertEquals(CuT.getServices().getVisitorsRegistry().getVisitor("0000000001").getName(),"John Doe","Wrong visitor stored.");
        assertEquals(CuT.getServices().getVisitorsRegistry().getVisitor("0000000002").getName(),"Jane Doe","Wrong visitor stored.");
        assertEquals(CuT.getServices().getVisitorsRegistry().getVisitor("0000000003").getName(),"Jane Doe","Wrong visitor stored.");

        // Assert an error for a duplicate visitor.
        this.assertRequest("register,John,Doe,Test Address,1234567890;","register,duplicate;");
        assertNull(CuT.getServices().getVisitorsRegistry().getVisitor("0000000004"),"Duplicate user stored.");
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
        // Assert not giving a set of days.
        this.assertRequest("advance;","advance,missing-parameters,{number-of-days};");

        // Assert advancing an incorrect amount days.
        this.assertRequest("advance,-2;","advance,invalid-number-of-days,-2;");
        this.assertRequest("advance,28;","advance,invalid-number-of-days,28;");
        this.assertRequest("advance,test;","advance,invalid-number-of-days,test;");
        this.assertRequest("datetime;","datetime,2019/01/01,08:00:00;","Time mutated.");

        // Assert advancing an incorrect amount of hours.
        this.assertRequest("advance,0,-2;","advance,invalid-number-of-hours,-2;");
        this.assertRequest("advance,0,28;","advance,invalid-number-of-hours,28;");
        this.assertRequest("advance,0,test;","advance,invalid-number-of-hours,test;");
        this.assertRequest("datetime;","datetime,2019/01/01,08:00:00;","Time mutated.");

        // Assert advancing into the next day.
        this.assertRequest("advance,1;","advance,success;");
        this.assertRequest("datetime;","datetime,2019/01/02,08:00:00;","Time not advanced.");

        // Assert advancing only days.
        this.assertRequest("advance,2,0;","advance,success;");
        this.assertRequest("datetime;","datetime,2019/01/04,08:00:00;","Time not advanced.");

        // Assert advancing only hours.
        this.assertRequest("advance,0,2;","advance,success;");
        this.assertRequest("datetime;","datetime,2019/01/04,10:00:00;","Time not advanced.");

        // Assert advancing both.
        this.assertRequest("advance,2,2;","advance,success;");
        this.assertRequest("datetime;","datetime,2019/01/06,12:00:00;","Time not advanced.");

        // Assert advancing into the next day only using hours.
        this.assertRequest("advance,0,20;","advance,success;");
        this.assertRequest("datetime;","datetime,2019/01/07,08:00:00;","Time not advanced.");

        // Test a visitor having his visit ended by the time being past 19:00.
        Visitor visitor = CuT.getServices().getVisitorsRegistry().registerVisitor(new Name("John","Doe"),"Test Address","1234567890");
        Visit visit = CuT.getServices().getVisitHistory().addVisit(visitor,CuT.getServices().getClock().getDate());

        // Assert the visit has not ended.
        this.assertRequest("advance,0,2;","advance,success;");
        this.assertRequest("datetime;","datetime,2019/01/07,10:00:00;","Time not advanced.");
        assertFalse(visit.hasEnded(),"Visit ended.");

        // Finish the visit
        this.assertRequest("advance,0,10;","advance,success;");
        this.assertRequest("datetime;","datetime,2019/01/07,20:00:00;","Time not advanced.");
        assertTrue(visit.hasEnded(),"Visit not ended.");

        // Reset the time to the next day.
        this.assertRequest("advance,0,12;","advance,success;");
        this.assertRequest("datetime;","datetime,2019/01/08,08:00:00;","Time not advanced.");

        // Test a visitor having his visit ended by the time being set to the next day.
        visit = CuT.getServices().getVisitHistory().addVisit(visitor,CuT.getServices().getClock().getDate());
        this.assertRequest("advance,1,0;","advance,success;");
        this.assertRequest("datetime;","datetime,2019/01/09,08:00:00;","Time not advanced.");
        assertTrue(visit.hasEnded(),"Visit not ended.");
    }

    /**
     * Tests getting the current date and time.
     */
    @Test
    public void test_currentDateAndTime() {
        this.assertRequest("datetime;","datetime,2019/01/01,08:00:00;","Initial time is incorrect.");
    }

    /**
     * Test getting library statistics.
     */
    @Test
    public void test_libraryStatistics() {

    }
}
