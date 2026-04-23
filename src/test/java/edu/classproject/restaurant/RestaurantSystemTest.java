package edu.classproject.restaurant;

import org.junit.jupiter.api.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ============================================================
 *  RestaurantSystemTest.java
 *
 *  Unit tests covering:
 *    1. MenuItem  — creation, validation, behaviour
 *    2. Restaurant — menu management, status transitions
 *    3. Repository — CRUD and filtering
 *    4. Service   — business logic, duplicate handling,
 *                   approve/reject workflows, cuisine search
 * ============================================================
 */
class RestaurantSystemTest {

    private Restaurant restaurant;
    private MenuItem item1;
    private MenuItem item2;
    private RestaurantRepository repo;
    private RestaurantService service;

    @BeforeEach
    void setup() {
        restaurant = new Restaurant("R1", "Food Hub", "Bangalore",
                "Indian", "9AM-10PM", "1234567890");

        item1 = new MenuItem("I1", "Burger", 100, "FastFood", "Veg Burger");
        item2 = new MenuItem("I2", "Pizza",  200, "FastFood", "Cheese Pizza");

        repo    = new InMemoryRestaurantRepository();
        service = new InMemoryRestaurantService(repo);
    }

    // ───────────── MENU ITEM TESTS ─────────────

    @Test
    void testMenuItemCreation() {
        assertEquals("Burger", item1.getName());
        assertTrue(item1.isAvailable());
    }

    @Test
    void testMenuItemInvalidPrice() {
        assertThrows(IllegalArgumentException.class, () ->
                new MenuItem("I3", "Bad", -10, "Test", "Invalid"));
    }

    @Test
    void testToggleAvailability() {
        item1.toggleAvailability();
        assertFalse(item1.isAvailable());
    }

    @Test
    void testToggleAvailabilityTwiceRestoresState() {
        item1.toggleAvailability();
        item1.toggleAvailability();
        assertTrue(item1.isAvailable());
    }

    @Test
    void testUpdatePrice() {
        item1.updatePrice(150);
        assertEquals(150, item1.getPrice());
    }

    @Test
    void testUpdatePriceNegative() {
        assertThrows(IllegalArgumentException.class, () ->
                item1.updatePrice(-5));
    }

    @Test
    void testMenuItemEmptyIdThrows() {
        assertThrows(IllegalArgumentException.class, () ->
                new MenuItem("", "Name", 50, "Cat", "Desc"));
    }

    // ───────────── RESTAURANT TESTS ─────────────

    @Test
    void testAddMenuItem() {
        restaurant.addMenuItem(item1);
        assertEquals(1, restaurant.getMenuItems().size());
    }

    @Test
    void testAddNullMenuItemThrows() {
        assertThrows(IllegalArgumentException.class, () ->
                restaurant.addMenuItem(null));
    }

    @Test
    void testRemoveMenuItem() {
        restaurant.addMenuItem(item1);
        restaurant.removeMenuItem("I1");
        assertEquals(0, restaurant.getMenuItems().size());
    }

    @Test
    void testFindMenuItem() {
        restaurant.addMenuItem(item1);
        assertNotNull(restaurant.findMenuItemById("I1"));
    }

    @Test
    void testFindMenuItemNotFound() {
        assertNull(restaurant.findMenuItemById("NONE"));
    }

    @Test
    void testApproveRestaurant() {
        restaurant.approve();
        assertEquals("APPROVED", restaurant.getStatus());
    }

    @Test
    void testRejectRestaurant() {
        restaurant.reject("Bad hygiene");
        assertEquals("REJECTED", restaurant.getStatus());
    }

    @Test
    void testRejectionReasonStored() {
        restaurant.reject("Bad hygiene");
        assertEquals("Bad hygiene", restaurant.getRejectionReason());
    }

    @Test
    void testRejectionReasonClearedOnApprove() {
        restaurant.reject("Bad hygiene");
        restaurant.approve();
        assertNull(restaurant.getRejectionReason());
    }

    @Test
    void testDefaultStatusIsPending() {
        assertEquals("PENDING", restaurant.getStatus());
    }

    @Test
    void testInvalidRestaurantId() {
        assertThrows(IllegalArgumentException.class, () ->
                new Restaurant("", "Test", "Loc", "Type", "Time", "123"));
    }

    // ───────────── REPOSITORY TESTS ─────────────

    @Test
    void testRegister() {
        repo.register(restaurant);
        assertEquals(1, repo.findAll().size());
    }

    @Test
    void testFindById() {
        repo.register(restaurant);
        assertNotNull(repo.findById("R1"));
    }

    @Test
    void testFindByIdNotFound() {
        assertNull(repo.findById("UNKNOWN"));
    }

    @Test
    void testFindAllApproved() {
        restaurant.approve();
        repo.register(restaurant);
        assertEquals(1, repo.findAllApproved().size());
    }

    @Test
    void testFindAllPending() {
        repo.register(restaurant);
        assertEquals(1, repo.findAllPending().size());
    }

    @Test
    void testUpdate() {
        repo.register(restaurant);
        restaurant.setLocation("Hyderabad");
        repo.update(restaurant);
        assertEquals("Hyderabad", repo.findById("R1").getLocation());
    }

    @Test
    void testRemove() {
        repo.register(restaurant);
        repo.remove("R1");
        assertNull(repo.findById("R1"));
    }

    @Test
    void testFindAllEmpty() {
        assertTrue(repo.findAll().isEmpty());
    }

    // ───────────── SERVICE TESTS ─────────────

    @Test
    void testServiceRegisterNullThrows() {
        assertThrows(IllegalArgumentException.class, () ->
                service.register(null));
    }

    @Test
    void testServiceDuplicateRegisterIgnored() {
        service.register(restaurant);
        service.register(restaurant); // second call should be ignored
        assertEquals(1, service.findAll().size());
    }

    @Test
    void testServiceApproveRestaurant() {
        service.register(restaurant);
        service.approveRestaurant("R1");
        assertEquals("APPROVED", service.findById("R1").getStatus());
    }

    @Test
    void testServiceApproveNotFoundThrows() {
        assertThrows(IllegalArgumentException.class, () ->
                service.approveRestaurant("UNKNOWN"));
    }

    @Test
    void testServiceRejectRestaurant() {
        service.register(restaurant);
        service.rejectRestaurant("R1", "Incomplete docs");
        assertEquals("REJECTED", service.findById("R1").getStatus());
        assertEquals("Incomplete docs", service.findById("R1").getRejectionReason());
    }

    @Test
    void testServiceRejectNotFoundThrows() {
        assertThrows(IllegalArgumentException.class, () ->
                service.rejectRestaurant("UNKNOWN", "reason"));
    }

    @Test
    void testServiceRejectEmptyReasonThrows() {
        service.register(restaurant);
        assertThrows(IllegalArgumentException.class, () ->
                service.rejectRestaurant("R1", ""));
    }

    @Test
    void testServiceSearchByCuisine() {
        Restaurant r2 = new Restaurant("R2", "Curry House",
                "Chennai", "Indian", "10AM-9PM", "111");
        service.register(restaurant); // Indian
        service.register(r2);         // Indian
        List<Restaurant> results = service.searchByCuisine("Indian");
        assertEquals(2, results.size());
    }

    @Test
    void testServiceSearchByCuisineCaseInsensitive() {
        service.register(restaurant); // cuisine = "Indian"
        List<Restaurant> results = service.searchByCuisine("indian");
        assertEquals(1, results.size());
    }

    @Test
    void testServiceSearchByCuisineEmptyThrows() {
        assertThrows(IllegalArgumentException.class, () ->
                service.searchByCuisine(""));
    }

    @Test
    void testServiceRemove() {
        service.register(restaurant);
        service.remove("R1");
        assertNull(service.findById("R1"));
    }
}
