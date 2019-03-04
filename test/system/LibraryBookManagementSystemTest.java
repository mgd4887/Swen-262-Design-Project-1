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
        // Assert missing parameters.
        this.assertRequest("arrive;","arrive,missing-parameters,{visitor-id};");

        // Assert an unregistered visitor.
        this.assertRequest("arrive,0000000001;","arrive,invalid-id;");

        // Assert someone arriving when closed.
        this.assertRequest("register,John,Doe,Test Address,1234567890;","register,0000000001,2019/01/01 08:00:00;");
        this.assertRequest("advance,0,12;","advance,success;");
        this.assertRequest("datetime;","datetime,2019/01/01,20:00:00;","Time not advanced.");
        this.assertRequest("arrive,0000000001;","arrive,closed;");
        this.assertRequest("advance,0,10;","advance,success;");
        this.assertRequest("datetime;","datetime,2019/01/02,06:00:00;","Time not advanced.");
        this.assertRequest("arrive,0000000001;","arrive,closed;");

        // Assert someone arriving when open.
        this.assertRequest("advance,0,2;","advance,success;");
        this.assertRequest("datetime;","datetime,2019/01/02,08:00:00;","Time not advanced.");
        this.assertRequest("arrive,0000000001;","arrive,0000000001,2019/01/02,08:00:00;");

        // Assert someone arriving when already arrived.
        this.assertRequest("arrive,0000000001;","arrive,duplicate;");
    }

    /**
     * Tests ending visits.
     */
    @Test
    public void test_endVisit() {
        // Assert missing parameters.
        this.assertRequest("depart;","depart,missing-parameters,{visitor-id};");

        // Assert an unregistered visitor.
        this.assertRequest("depart,0000000001;","depart,invalid-id;");

        // Assert someone arriving.
        this.assertRequest("register,John,Doe,Test Address,1234567890;","register,0000000001,2019/01/01 08:00:00;");
        this.assertRequest("arrive,0000000001;","arrive,0000000001,2019/01/01,08:00:00;");

        // Assert someone departing when already arrived.
        this.assertRequest("advance,0,2;","advance,success;");
        this.assertRequest("datetime;","datetime,2019/01/01,10:00:00;","Time not advanced.");
        this.assertRequest("depart,0000000001;","depart,0000000001,10:00:00,02:00:00;");

        // Assert someone leaving when not arrived.
        this.assertRequest("depart,0000000001;","depart,invalid-id;");
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
        // Assert missing parameters.
        this.assertRequest("search;","search,missing-parameters,{title};");

        // Perform a search with only the title.
        this.assertRequest("search,Harry Potter;","search,8\n" +
                "13,9781783296033,Harry Potter,{Jody Revenson},2015/09/25,\n" +
                "16,9781781107041,Harry Potter and the Cursed Child – Parts One and Two (Special Rehearsal Edition),{J.K. Rowling, John Tiffany, Jack Thorne},2016/07/31,\n" +
                "9,9781408855713,Harry Potter and the Deathly Hallows,{J. K. Rowling},2014/01/01,\n" +
                "15,9780545582971,Harry Potter and the Order of the Phoenix,{J. K. Rowling},2013/08/27,\n" +
                "10,9781781100516,Harry Potter and the Prisoner of Azkaban,{J.K. Rowling},1999/07/08,\n" +
                "11,9781781100486,Harry Potter and the Sorcerer's Stone,{J.K. Rowling},2015/12/08,\n" +
                "12,9781338029994,Harry Potter Coloring Book,{Inc. Scholastic},2015/11/10,\n" +
                "14,9780062101891,Harry Potter Page to Screen,{Bob McCabe},2011/10/25,;");

        // Perform a search with the title and authors.
        this.assertRequest("search,Harry Potter,{J.K. Rowling};","search,3\n" +
                "16,9781781107041,Harry Potter and the Cursed Child – Parts One and Two (Special Rehearsal Edition),{J.K. Rowling, John Tiffany, Jack Thorne},2016/07/31,\n" +
                "10,9781781100516,Harry Potter and the Prisoner of Azkaban,{J.K. Rowling},1999/07/08,\n" +
                "11,9781781100486,Harry Potter and the Sorcerer's Stone,{J.K. Rowling},2015/12/08,;");

        // Perform a search with the title, authors, and ISBN.
        this.assertRequest("search,Harry Potter,{J.K. Rowling},9781781107041;","search,1\n" +
                "16,9781781107041,Harry Potter and the Cursed Child – Parts One and Two (Special Rehearsal Edition),{J.K. Rowling, John Tiffany, Jack Thorne},2016/07/31,;");

        // Perform a search with the title, authors, and publisher.
        this.assertRequest("search,Harry Potter,{J.K. Rowling},*,Pottermore;","search,3\n" +
                "16,9781781107041,Harry Potter and the Cursed Child – Parts One and Two (Special Rehearsal Edition),{J.K. Rowling, John Tiffany, Jack Thorne},2016/07/31,\n" +
                "10,9781781100516,Harry Potter and the Prisoner of Azkaban,{J.K. Rowling},1999/07/08,\n" +
                "11,9781781100486,Harry Potter and the Sorcerer's Stone,{J.K. Rowling},2015/12/08,;");

        // Perform a search with only the title and search order.
        this.assertRequest("search,Harry Potter,*,*,*,title;","search,8\n" +
                "13,9781783296033,Harry Potter,{Jody Revenson},2015/09/25,\n" +
                "16,9781781107041,Harry Potter and the Cursed Child – Parts One and Two (Special Rehearsal Edition),{J.K. Rowling, John Tiffany, Jack Thorne},2016/07/31,\n" +
                "9,9781408855713,Harry Potter and the Deathly Hallows,{J. K. Rowling},2014/01/01,\n" +
                "15,9780545582971,Harry Potter and the Order of the Phoenix,{J. K. Rowling},2013/08/27,\n" +
                "10,9781781100516,Harry Potter and the Prisoner of Azkaban,{J.K. Rowling},1999/07/08,\n" +
                "11,9781781100486,Harry Potter and the Sorcerer's Stone,{J.K. Rowling},2015/12/08,\n" +
                "12,9781338029994,Harry Potter Coloring Book,{Inc. Scholastic},2015/11/10,\n" +
                "14,9780062101891,Harry Potter Page to Screen,{Bob McCabe},2011/10/25,;");
        this.assertRequest("search,Harry Potter,*,*,*,publish-date;","search,8\n" +
                "16,9781781107041,Harry Potter and the Cursed Child – Parts One and Two (Special Rehearsal Edition),{J.K. Rowling, John Tiffany, Jack Thorne},2016/07/31,\n" +
                "11,9781781100486,Harry Potter and the Sorcerer's Stone,{J.K. Rowling},2015/12/08,\n" +
                "12,9781338029994,Harry Potter Coloring Book,{Inc. Scholastic},2015/11/10,\n" +
                "13,9781783296033,Harry Potter,{Jody Revenson},2015/09/25,\n" +
                "9,9781408855713,Harry Potter and the Deathly Hallows,{J. K. Rowling},2014/01/01,\n" +
                "15,9780545582971,Harry Potter and the Order of the Phoenix,{J. K. Rowling},2013/08/27,\n" +
                "14,9780062101891,Harry Potter Page to Screen,{Bob McCabe},2011/10/25,\n" +
                "10,9781781100516,Harry Potter and the Prisoner of Azkaban,{J.K. Rowling},1999/07/08,;");
    }

    /**
     * Tests purchasing books.
     */
    @Test
    public void test_purchaseBook() {
        // Assert missing parameters.
        this.assertRequest("buy;","buy,missing-parameters,{quantity,id};");
        this.assertRequest("buy,3;","buy,missing-parameters,{id};");

        // Test a purchase.
        this.assertRequest("buy,3,1,1,2,3,4;","buy,4\n" +
                "9780936070278,Galloway's Book on Running,{Jeff Galloway},2002/01/01,6,\n" +
                "9781840894622,Running Shoes,{Frederick Lipp},2007/09/01,3,\n" +
                "9780736045100,Fitness Running,{Richard L. Brown, Joe Henderson},2003/01/01,3,\n" +
                "9780375896798,The Running Dream,{Wendelin Van Draanen},2011/01/11,3,;");
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
