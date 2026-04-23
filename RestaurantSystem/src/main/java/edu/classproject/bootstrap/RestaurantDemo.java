package edu.classproject.bootstrap;

import edu.classproject.restaurant.*;

/**
 * ============================================================
 *  RestaurantDemo.java  —  Entry Point / Driver Class
 *
 *  Demonstrates the full Restaurant System workflow:
 *  registration, approval, rejection, menu management,
 *  search by cuisine, update, and removal.
 *
 *  Uses Dependency Injection to wire Repository → Service.
 * ============================================================
 */
public class RestaurantDemo {

    public static void main(String[] args) {

        // ── Dependency Injection ──────────────────────────────────
        RestaurantRepository repo = new InMemoryRestaurantRepository();
        RestaurantService service = new InMemoryRestaurantService(repo);

        System.out.println("======================================");
        System.out.println("       RESTAURANT SYSTEM DEMO         ");
        System.out.println("======================================");

        // ── 1. Register Restaurants ───────────────────────────────
        Restaurant r1 = new Restaurant(
                "R001", "Spice Garden",
                "Bangalore", "Indian",
                "9AM-10PM", "1234567890");

        Restaurant r2 = new Restaurant(
                "R002", "Pizza Palace",
                "Chennai", "Italian",
                "11AM-11PM", "9876543210");

        Restaurant r3 = new Restaurant(
                "R003", "Curry House",
                "Hyderabad", "Indian",
                "10AM-9PM", "9988776655");

        service.register(r1);
        service.register(r2);
        service.register(r3);

        System.out.println("\n── All Restaurants ──");
        service.findAll().forEach(System.out::println);

        // ── 2. Approve / Reject via Service (business logic) ─────
        System.out.println("\n── Approving / Rejecting ──");
        service.approveRestaurant("R001");
        service.approveRestaurant("R003");
        service.rejectRestaurant("R002", "Incomplete documents");

        System.out.println("\n── Approved Restaurants ──");
        service.findAllApproved().forEach(r ->
                System.out.println("  " + r.getName() + " [" + r.getStatus() + "]"));

        System.out.println("\n── Pending Restaurants ──");
        service.findAllPending().forEach(r ->
                System.out.println("  " + r.getName() + " [" + r.getStatus() + "]"));

        // ── 3. Rejection Reason ───────────────────────────────────
        System.out.println("\n── Rejection Reason for R002 ──");
        Restaurant rejected = service.findById("R002");
        System.out.println("  Reason: " + rejected.getRejectionReason());

        // ── 4. Search by Cuisine ──────────────────────────────────
        System.out.println("\n── Search by Cuisine: Indian ──");
        service.searchByCuisine("Indian").forEach(r ->
                System.out.println("  " + r.getName() + ", " + r.getLocation()));

        // ── 5. Menu Management ────────────────────────────────────
        MenuItem dosa  = new MenuItem("M1", "Dosa",  50,  "Food", "South Indian");
        MenuItem pizza = new MenuItem("M2", "Pizza", 200,  "Food", "Italian");
        MenuItem idli  = new MenuItem("M3", "Idli",  30,  "Food", "South Indian");

        r1.addMenuItem(dosa);
        r1.addMenuItem(pizza);
        r1.addMenuItem(idli);

        System.out.println("\n── Menu of " + r1.getName() + " ──");
        r1.getMenuItems().forEach(System.out::println);

        // Toggle availability and update price
        dosa.toggleAvailability();
        pizza.updatePrice(180);

        System.out.println("\n── Updated Menu of " + r1.getName() + " ──");
        r1.getMenuItems().forEach(System.out::println);

        // Remove a menu item
        r1.removeMenuItem("M3");
        System.out.println("\n── After removing Idli ──");
        r1.getMenuItems().forEach(System.out::println);

        // ── 6. Update Restaurant Details ─────────────────────────
        r1.setLocation("Hyderabad");
        service.update(r1);

        System.out.println("\n── Updated Restaurant ──");
        System.out.println(service.findById("R001"));

        // ── 7. Remove Restaurant ──────────────────────────────────
        service.remove("R002");

        System.out.println("\n── After Removing R002 ──");
        service.findAll().forEach(System.out::println);

        System.out.println("\n======================================");
        System.out.println("           DEMO COMPLETE              ");
        System.out.println("======================================");
    }
}
