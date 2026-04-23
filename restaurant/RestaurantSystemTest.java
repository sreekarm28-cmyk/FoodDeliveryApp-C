package edu.classproject.restaurant;

import org.junit.jupiter.api.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantSystemTest {

    private Restaurant restaurant;
    private MenuItem item1;
    private MenuItem item2;
    private RestaurantRepository repo;

    @BeforeEach
    void setup() {
        restaurant = new Restaurant("R1", "Food Hub", "Bangalore",
                "Indian", "9AM-10PM", "1234567890");

        item1 = new MenuItem("I1", "Burger", 100, "FastFood", "Veg Burger");
        item2 = new MenuItem("I2", "Pizza", 200, "FastFood", "Cheese Pizza");

        repo = new InMemoryRestaurantRepository();
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
    void testUpdatePrice() {
        item1.updatePrice(150);
        assertEquals(150, item1.getPrice());
    }

    @Test
    void testUpdatePriceNegative() {
        assertThrows(IllegalArgumentException.class, () ->
                item1.updatePrice(-5));
    }

    // ───────────── RESTAURANT TESTS ─────────────

    @Test
    void testAddMenuItem() {
        restaurant.addMenuItem(item1);
        assertEquals(1, restaurant.getMenuItems().size());
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
    void testApproveRestaurant() {
        restaurant.approve();
        assertEquals("APPROVED", restaurant.getStatus());
    }

    @Test
    void testRejectRestaurant() {
        restaurant.reject("Bad");
        assertEquals("REJECTED", restaurant.getStatus());
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
}