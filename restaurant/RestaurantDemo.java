package edu.classproject.bootstrap;

import edu.classproject.restaurant.InMemoryRestaurantRepository;
import edu.classproject.restaurant.MenuItem;
import edu.classproject.restaurant.Restaurant;
import edu.classproject.restaurant.RestaurantRepository;

/**
 * ============================================================
 *  RestaurantDemo.java
 *
 *  This is the entry point — shows the full module working.
 *
 *  It demonstrates:
 *  1. Restaurant Registration
 *  2. Admin Approval / Rejection
 *  3. Menu Management (add, remove, toggle, update price)
 *  4. How other teams connect using RestaurantRepository
 *
 *  SOLID — D (Dependency Inversion):
 *  Notice we declare the variable as RestaurantRepository
 *  (interface), NOT InMemoryRestaurantRepository (class).
 *  This means if storage changes, only this one line changes.
 * ============================================================
 */
public class RestaurantDemo {

    public static void main(String[] args) {

        // ── DIP: Depend on interface, not implementation ───────────
        RestaurantRepository repo = new InMemoryRestaurantRepository();

        sep("MODULE: RESTAURANT REGISTRY AND MENU MANAGEMENT");
        sep("Group 4ACE | OOAD Course 2024/2025");

        // ══════════════════════════════════════════════════════════
        // 1. RESTAURANT REGISTRATION
        // ══════════════════════════════════════════════════════════
        sep("1. RESTAURANT REGISTRATION");

        Restaurant r1 = new Restaurant(
            "R001", "Spice Garden",
            "Koramangala, Bengaluru",
            "South Indian", "9am-11pm",
            "+91-9876543210"
        );

        Restaurant r2 = new Restaurant(
            "R002", "Pizza Palace",
            "Indiranagar, Bengaluru",
            "Italian", "11am-12am",
            "+91-9123456789"
        );

        Restaurant r3 = new Restaurant(
            "R003", "Dragon Wok",
            "HSR Layout, Bengaluru",
            "Chinese", "12pm-10pm",
            "+91-9988776655"
        );

        repo.register(r1);
        repo.register(r2);
        repo.register(r3);

        System.out.println("\nAll registered restaurants:");
        repo.findAll().forEach(r ->
            System.out.println("  -> " + r));

        // ══════════════════════════════════════════════════════════
        // 2. ADMIN APPROVAL / REJECTION
        // ══════════════════════════════════════════════════════════
        sep("2. ADMIN APPROVAL AND REJECTION");

        System.out.println("Pending restaurants: "
            + repo.findAllPending().size());

        // Approve R001 and R002
        r1.approve();
        r2.approve();

        // Reject R003
        r3.reject("Incomplete documentation submitted");

        System.out.println("\nApproved restaurants:");
        repo.findAllApproved().forEach(r ->
            System.out.println("  -> " + r.getName()
                + " | " + r.getStatus()));

        // ══════════════════════════════════════════════════════════
        // 3. MENU MANAGEMENT — Add Items
        // ══════════════════════════════════════════════════════════
        sep("3. MENU MANAGEMENT — Adding Items");

        // Spice Garden menu
        r1.addMenuItem(new MenuItem("M001", "Masala Dosa",    80.0,  "Breakfast",   "Crispy dosa with potato filling"));
        r1.addMenuItem(new MenuItem("M002", "Filter Coffee",  40.0,  "Beverages",   "Traditional South Indian coffee"));
        r1.addMenuItem(new MenuItem("M003", "Bisi Bele Bath", 120.0, "Main Course", "Spicy rice and lentil dish"));
        r1.addMenuItem(new MenuItem("M004", "Gulab Jamun",    60.0,  "Desserts",    "Soft milk-solid sweet"));

        // Pizza Palace menu
        r2.addMenuItem(new MenuItem("P001", "Margherita",        350.0, "Pizza", "Classic tomato and mozzarella"));
        r2.addMenuItem(new MenuItem("P002", "Pasta Arrabbiata",  280.0, "Pasta", "Spicy tomato pasta"));
        r2.addMenuItem(new MenuItem("P003", "Garlic Bread",      120.0, "Sides", "Toasted garlic bread"));

        System.out.println("\nSpice Garden menu (" + r1.getMenuItems().size() + " items):");
        r1.getMenuItems().forEach(item ->
            System.out.println("  -> " + item));

        // ══════════════════════════════════════════════════════════
        // 4. MENU MANAGEMENT — Toggle Availability
        // ══════════════════════════════════════════════════════════
        sep("4. MENU MANAGEMENT — Toggle Availability");

        MenuItem gulab = r1.findMenuItemById("M004");
        if (gulab != null) {
            System.out.println("Before toggle: " + gulab.getName()
                + " available=" + gulab.isAvailable());
            gulab.toggleAvailability();
            System.out.println("After toggle:  " + gulab.getName()
                + " available=" + gulab.isAvailable());
        }

        // ══════════════════════════════════════════════════════════
        // 5. MENU MANAGEMENT — Update Price
        // ══════════════════════════════════════════════════════════
        sep("5. MENU MANAGEMENT — Update Price");

        MenuItem dosa = r1.findMenuItemById("M001");
        if (dosa != null) {
            dosa.updatePrice(95.0);
        }

        MenuItem margherita = r2.findMenuItemById("P001");
        if (margherita != null) {
            margherita.updatePrice(380.0);
        }

        // ══════════════════════════════════════════════════════════
        // 6. MENU MANAGEMENT — Remove Item
        // ══════════════════════════════════════════════════════════
        sep("6. MENU MANAGEMENT — Remove Item");

        System.out.println("Pizza Palace items before removal: "
            + r2.getMenuItems().size());
        r2.removeMenuItem("P003"); // remove Garlic Bread
        System.out.println("Pizza Palace items after removal:  "
            + r2.getMenuItems().size());

        // ══════════════════════════════════════════════════════════
        // 7. HOW OTHER TEAMS CONNECT TO THIS MODULE
        // ══════════════════════════════════════════════════════════
        sep("7. HOW OTHER TEAMS USE THIS MODULE");

        System.out.println("Other teams (Search, Review, Order) connect like this:");
        System.out.println();
        System.out.println("  RestaurantRepository repo = new InMemoryRestaurantRepository();");
        System.out.println("  List<Restaurant> approved = repo.findAllApproved();");
        System.out.println();
        System.out.println("Available approved restaurants for other modules:");
        repo.findAllApproved().forEach(r -> {
            System.out.println("  Restaurant: " + r.getName()
                + " | Menu items: " + r.getMenuItems().size());
            r.getMenuItems().stream()
                .filter(MenuItem::isAvailable)
                .forEach(item -> System.out.println("      - "
                    + item.getName() + " | Rs." + item.getPrice()));
        });

        sep("MODULE DEMO COMPLETE");
    }

    private static void sep(String title) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("  " + title);
        System.out.println("=".repeat(60));
    }
}
