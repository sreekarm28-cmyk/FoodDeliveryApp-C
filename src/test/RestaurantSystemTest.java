package test;

import factory.UserFactory;
import models.*;
import observer.*;
import service.*;
import strategy.*;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

/**
 * ══════════════════════════════════════════════════════════
 *  Restaurant Registry and Menu System — JUnit Test Suite
 *  Group 4ACE | OOAD Course 2024/2025
 *
 *  14 Test Cases covering:
 *   TC01 — Factory creates Customer correctly
 *   TC02 — Factory creates RestaurantOwner correctly
 *   TC03 — Factory creates Admin correctly
 *   TC04 — Factory throws error for unknown type
 *   TC05 — Singleton returns same instance
 *   TC06 — Restaurant starts with PENDING status
 *   TC07 — Admin approval changes status to APPROVED
 *   TC08 — Admin rejection changes status to REJECTED
 *   TC09 — MenuItem toggleAvailability works correctly
 *   TC10 — MenuItem updatePrice works correctly
 *   TC11 — Customer submitReview adds review to restaurant
 *   TC12 — Average rating calculated correctly
 *   TC13 — Observer notified when restaurant is approved
 *   TC14 — Keyword search returns correct results
 * ══════════════════════════════════════════════════════════
 */
public class RestaurantSystemTest {

    // Shared objects reused across tests
    private RestaurantOwner owner;
    private Restaurant restaurant;
    private Customer customer;
    private Admin admin;

    @Before
    public void setUp() {
        // Runs before EVERY test — fresh objects each time
        owner = (RestaurantOwner) UserFactory.createUser(
            "OWNER", "O001", "Raj", "raj@mail.com", "pass", "LIC-001");

        restaurant = owner.registerRestaurant(
            "R001", "Spice Garden", "Koramangala", "South Indian", "9am-11pm");

        customer = (Customer) UserFactory.createUser(
            "CUSTOMER", "C001", "Alice", "alice@mail.com", "pass", "MG Road");

        admin = (Admin) UserFactory.createUser(
            "ADMIN", "A001", "Admin", "admin@mail.com", "pass", null);
    }

    // ─────────────────────────────────────────────────────────────────
    // TC01 — Factory creates Customer correctly
    // ─────────────────────────────────────────────────────────────────
    @Test
    public void TC01_factoryCreatesCustomer() {
        System.out.println("\n[TC01] Factory creates Customer correctly");

        User user = UserFactory.createUser("CUSTOMER", "C002", "Bob",
                                           "bob@mail.com", "pass", "BTM Layout");

        assertNotNull("User should not be null", user);
        assertTrue("Should be instance of Customer", user instanceof Customer);
        assertEquals("Role should be CUSTOMER", "CUSTOMER", user.getRole());
        assertEquals("Name should match", "Bob", user.getName());
        assertEquals("Email should match", "bob@mail.com", user.getEmail());

        System.out.println("  PASS — Customer created: " + user);
    }

    // ─────────────────────────────────────────────────────────────────
    // TC02 — Factory creates RestaurantOwner correctly
    // ─────────────────────────────────────────────────────────────────
    @Test
    public void TC02_factoryCreatesOwner() {
        System.out.println("\n[TC02] Factory creates RestaurantOwner correctly");

        User user = UserFactory.createUser("OWNER", "O002", "Priya",
                                           "priya@mail.com", "pass", "LIC-002");

        assertNotNull("Owner should not be null", user);
        assertTrue("Should be instance of RestaurantOwner", user instanceof RestaurantOwner);
        assertEquals("Role should be OWNER", "OWNER", user.getRole());
        assertEquals("Name should match", "Priya", user.getName());

        System.out.println("  PASS — Owner created: " + user);
    }

    // ─────────────────────────────────────────────────────────────────
    // TC03 — Factory creates Admin correctly
    // ─────────────────────────────────────────────────────────────────
    @Test
    public void TC03_factoryCreatesAdmin() {
        System.out.println("\n[TC03] Factory creates Admin correctly");

        User user = UserFactory.createUser("ADMIN", "A002", "SuperAdmin",
                                           "super@mail.com", "pass", null);

        assertNotNull("Admin should not be null", user);
        assertTrue("Should be instance of Admin", user instanceof Admin);
        assertEquals("Role should be ADMIN", "ADMIN", user.getRole());

        System.out.println("  PASS — Admin created: " + user);
    }

    // ─────────────────────────────────────────────────────────────────
    // TC04 — Factory throws exception for unknown user type
    // ─────────────────────────────────────────────────────────────────
    @Test(expected = IllegalArgumentException.class)
    public void TC04_factoryThrowsForUnknownType() {
        System.out.println("\n[TC04] Factory throws error for unknown type");

        // This should throw IllegalArgumentException
        UserFactory.createUser("MANAGER", "M001", "Test", "t@mail.com", "pass", null);

        System.out.println("  PASS — Exception thrown as expected");
    }

    // ─────────────────────────────────────────────────────────────────
    // TC05 — Singleton returns the same instance every time
    // ─────────────────────────────────────────────────────────────────
    @Test
    public void TC05_singletonReturnsSameInstance() {
        System.out.println("\n[TC05] Singleton returns same instance");

        RestaurantRegistry instance1 = RestaurantRegistry.getInstance();
        RestaurantRegistry instance2 = RestaurantRegistry.getInstance();
        RestaurantRegistry instance3 = RestaurantRegistry.getInstance();

        assertSame("Instance 1 and 2 must be same object", instance1, instance2);
        assertSame("Instance 2 and 3 must be same object", instance2, instance3);
        assertTrue("Same memory reference", instance1 == instance3);

        System.out.println("  PASS — All three calls returned same instance: " + instance1.hashCode());
    }

    // ─────────────────────────────────────────────────────────────────
    // TC06 — New restaurant starts with PENDING status
    // ─────────────────────────────────────────────────────────────────
    @Test
    public void TC06_newRestaurantIsPending() {
        System.out.println("\n[TC06] New restaurant starts with PENDING status");

        assertEquals("Status should be PENDING on creation",
            "PENDING", restaurant.getStatus());

        System.out.println("  PASS — Restaurant status: " + restaurant.getStatus());
    }

    // ─────────────────────────────────────────────────────────────────
    // TC07 — Admin approval changes status to APPROVED
    // ─────────────────────────────────────────────────────────────────
    @Test
    public void TC07_adminApprovesRestaurant() {
        System.out.println("\n[TC07] Admin approval changes status to APPROVED");

        assertEquals("Initially PENDING", "PENDING", restaurant.getStatus());

        admin.approveRestaurant(restaurant);

        assertEquals("After approval should be APPROVED",
            "APPROVED", restaurant.getStatus());

        System.out.println("  PASS — Status changed to: " + restaurant.getStatus());
    }

    // ─────────────────────────────────────────────────────────────────
    // TC08 — Admin rejection changes status to REJECTED
    // ─────────────────────────────────────────────────────────────────
    @Test
    public void TC08_adminRejectsRestaurant() {
        System.out.println("\n[TC08] Admin rejection changes status to REJECTED");

        admin.rejectRestaurant(restaurant, "Incomplete documents");

        assertEquals("After rejection should be REJECTED",
            "REJECTED", restaurant.getStatus());

        System.out.println("  PASS — Status changed to: " + restaurant.getStatus());
    }

    // ─────────────────────────────────────────────────────────────────
    // TC09 — MenuItem toggleAvailability switches correctly
    // ─────────────────────────────────────────────────────────────────
    @Test
    public void TC09_menuItemToggleAvailability() {
        System.out.println("\n[TC09] MenuItem toggleAvailability works correctly");

        MenuItem item = new MenuItem("M001", "Dosa", 80.0, "Breakfast", "Crispy dosa");

        assertTrue("Should be available by default", item.isAvailable());

        item.toggleAvailability();
        assertFalse("Should be unavailable after first toggle", item.isAvailable());

        item.toggleAvailability();
        assertTrue("Should be available again after second toggle", item.isAvailable());

        System.out.println("  PASS — Toggle works: default=true, after 1st=false, after 2nd=true");
    }

    // ─────────────────────────────────────────────────────────────────
    // TC10 — MenuItem updatePrice updates correctly
    // ─────────────────────────────────────────────────────────────────
    @Test
    public void TC10_menuItemUpdatePrice() {
        System.out.println("\n[TC10] MenuItem updatePrice works correctly");

        MenuItem item = new MenuItem("M002", "Coffee", 40.0, "Beverages", "Filter coffee");

        assertEquals("Initial price should be 40.0", 40.0, item.getPrice(), 0.001);

        item.updatePrice(55.0);
        assertEquals("Price should be updated to 55.0", 55.0, item.getPrice(), 0.001);

        item.updatePrice(60.0);
        assertEquals("Price should be updated to 60.0", 60.0, item.getPrice(), 0.001);

        System.out.println("  PASS — Price updated correctly: 40 -> 55 -> 60");
    }

    // ─────────────────────────────────────────────────────────────────
    // TC11 — Customer submitReview adds review to restaurant
    // ─────────────────────────────────────────────────────────────────
    @Test
    public void TC11_customerSubmitReview() {
        System.out.println("\n[TC11] Customer submitReview adds review to restaurant");

        assertEquals("No reviews initially", 0, restaurant.getReviews().size());

        customer.submitReview(restaurant, 5, "Amazing food!");

        assertEquals("Should have 1 review after submission", 1, restaurant.getReviews().size());
        assertEquals("Review rating should be 5", 5, restaurant.getReviews().get(0).getRating());
        assertEquals("Review comment should match", "Amazing food!", restaurant.getReviews().get(0).getComment());

        System.out.println("  PASS — Review added. Total reviews: " + restaurant.getReviews().size());
    }

    // ─────────────────────────────────────────────────────────────────
    // TC12 — Average rating calculated correctly across multiple reviews
    // ─────────────────────────────────────────────────────────────────
    @Test
    public void TC12_averageRatingCalculation() {
        System.out.println("\n[TC12] Average rating calculated correctly");

        assertEquals("Average should be 0.0 with no reviews", 0.0, restaurant.getAverageRating(), 0.001);

        customer.submitReview(restaurant, 5, "Excellent!");
        assertEquals("Average should be 5.0", 5.0, restaurant.getAverageRating(), 0.001);

        Customer customer2 = (Customer) UserFactory.createUser(
            "CUSTOMER", "C002", "Bob", "bob@mail.com", "pass", "Indiranagar");
        customer2.submitReview(restaurant, 3, "Good but pricey");

        // Average of 5 and 3 = 4.0
        assertEquals("Average of 5 and 3 should be 4.0", 4.0, restaurant.getAverageRating(), 0.001);

        System.out.println("  PASS — Average rating: " + restaurant.getAverageRating());
    }

    // ─────────────────────────────────────────────────────────────────
    // TC13 — Observer is notified when restaurant status changes
    // ─────────────────────────────────────────────────────────────────
    @Test
    public void TC13_observerNotifiedOnApproval() {
        System.out.println("\n[TC13] Observer notified when restaurant is approved");

        // Custom observer that records events for testing
        List<String> receivedEvents = new ArrayList<>();
        RestaurantObserver testObserver = (r, event) -> receivedEvents.add(event);

        restaurant.addObserver(testObserver);

        assertEquals("No events before approval", 0, receivedEvents.size());

        admin.approveRestaurant(restaurant);

        assertEquals("Should have received 1 event", 1, receivedEvents.size());
        assertTrue("Event should contain STATUS_CHANGED",
            receivedEvents.get(0).contains("STATUS_CHANGED"));
        assertTrue("Event should contain APPROVED",
            receivedEvents.get(0).contains("APPROVED"));

        System.out.println("  PASS — Observer received event: " + receivedEvents.get(0));
    }

    // ─────────────────────────────────────────────────────────────────
    // TC14 — Keyword search returns correct restaurant
    // ─────────────────────────────────────────────────────────────────
    @Test
    public void TC14_keywordSearchReturnsCorrectResult() {
        System.out.println("\n[TC14] Keyword search returns correct results");

        // Build a list of approved restaurants
        RestaurantOwner owner2 = (RestaurantOwner) UserFactory.createUser(
            "OWNER", "O002", "Priya", "priya@mail.com", "pass", "LIC-002");
        Restaurant pizzaPalace = owner2.registerRestaurant(
            "R002", "Pizza Palace", "Indiranagar", "Italian", "11am-12am");

        List<Restaurant> allRestaurants = new ArrayList<>();
        allRestaurants.add(restaurant);   // Spice Garden
        allRestaurants.add(pizzaPalace);  // Pizza Palace

        SearchService searchService = new SearchService(new KeywordSearchStrategy());

        // Search for "spice"
        SearchFilter filter = new SearchFilter().keyword("spice");
        List<Restaurant> results = searchService.search(allRestaurants, filter);

        assertEquals("Should find exactly 1 result", 1, results.size());
        assertEquals("Result should be Spice Garden", "Spice Garden", results.get(0).getName());

        // Search for "pizza"
        SearchFilter filter2 = new SearchFilter().keyword("pizza");
        List<Restaurant> results2 = searchService.search(allRestaurants, filter2);

        assertEquals("Should find exactly 1 result", 1, results2.size());
        assertEquals("Result should be Pizza Palace", "Pizza Palace", results2.get(0).getName());

        // Search for something that doesn't exist
        SearchFilter filter3 = new SearchFilter().keyword("burger");
        List<Restaurant> results3 = searchService.search(allRestaurants, filter3);

        assertEquals("Should find 0 results for burger", 0, results3.size());

        System.out.println("  PASS — Search 'spice' -> " + results.get(0).getName());
        System.out.println("  PASS — Search 'pizza' -> " + results2.get(0).getName());
        System.out.println("  PASS — Search 'burger' -> 0 results");
    }
}
