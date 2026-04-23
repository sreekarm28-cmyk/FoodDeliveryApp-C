package main;

import factory.UserFactory;
import models.*;
import observer.*;
import service.*;
import strategy.*;

/**
 * ═══════════════════════════════════════════════════════════════
 *  Restaurant Registry and Menu System — Demo Runner
 *  Group 4ACE | OOAD Course 2024/2025
 *
 *  Design Patterns demonstrated:
 *   1. Factory Method  → UserFactory
 *   2. Observer        → OwnerNotificationObserver, AdminMonitorObserver
 *   3. Singleton       → RestaurantRegistry.getInstance()
 *   4. Strategy        → KeywordSearchStrategy, etc. via SearchService
 *
 *  OOP Principles applied:
 *   • Inheritance      → User → Customer / RestaurantOwner / Admin
 *   • Encapsulation    → private fields, public methods
 *   • Polymorphism     → SearchStrategy, RestaurantObserver
 *   • Abstraction      → User (abstract), interfaces
 *   • SRP              → each class has one responsibility
 *   • OCP              → new search strategies without changing SearchService
 * ═══════════════════════════════════════════════════════════════
 */
public class Main {

    public static void main(String[] args) {

        sep("RESTAURANT REGISTRY AND MENU SYSTEM — 4ACE");

        // ─────────────────────────────────────────────────────────────────────
        // 1. FACTORY METHOD — create users without knowing concrete classes
        // ─────────────────────────────────────────────────────────────────────
        sep("1. FACTORY METHOD — Creating Users");

        Customer   alice = (Customer)         UserFactory.createUser("CUSTOMER", "C001", "Alice",   "alice@mail.com",  "pass123", "12 MG Road, Bengaluru");
        RestaurantOwner raj = (RestaurantOwner) UserFactory.createUser("OWNER",   "O001", "Raj",     "raj@mail.com",    "pass456", "LIC-2024-BLR");
        Admin      admin = (Admin)            UserFactory.createUser("ADMIN",   "A001", "Admin",   "admin@mail.com",  "adminpass", null);

        System.out.println("Created: " + alice);
        System.out.println("Created: " + raj);
        System.out.println("Created: " + admin);

        // ─────────────────────────────────────────────────────────────────────
        // 2. SINGLETON — one shared registry for the whole app
        // ─────────────────────────────────────────────────────────────────────
        sep("2. SINGLETON — RestaurantRegistry");

        RestaurantRegistry registry = RestaurantRegistry.getInstance();
        RestaurantRegistry sameRef  = RestaurantRegistry.getInstance(); // same object
        System.out.println("Same instance? " + (registry == sameRef)); // true

        // ─────────────────────────────────────────────────────────────────────
        // 3. OBSERVER — attach observers before registering restaurant
        // ─────────────────────────────────────────────────────────────────────
        sep("3. OBSERVER — Setting Up Notifications");

        Restaurant spiceGarden = raj.registerRestaurant(
            "REST001", "Spice Garden", "Koramangala, Bengaluru", "South Indian", "9am–11pm");

        // Attach observers — will be notified on any restaurant event
        spiceGarden.addObserver(new OwnerNotificationObserver(raj.getName()));
        spiceGarden.addObserver(new AdminMonitorObserver());

        // Register in Singleton registry
        registry.registerRestaurant(spiceGarden);

        // Admin approves — triggers all observers
        sep("Admin Approves Restaurant");
        admin.approveRestaurant(spiceGarden); // fires STATUS_CHANGED:APPROVED

        // ─────────────────────────────────────────────────────────────────────
        // Owner manages menu items
        // ─────────────────────────────────────────────────────────────────────
        sep("Owner Adds Menu Items");
        raj.addMenuItem("M001", "Masala Dosa",    80.0,  "Breakfast", "Crispy dosa with spiced potato filling");
        raj.addMenuItem("M002", "Filter Coffee",  40.0,  "Beverages", "Traditional South Indian filter coffee");
        raj.addMenuItem("M003", "Bisi Bele Bath", 120.0, "Main Course","Spicy rice and lentil dish");
        raj.addMenuItem("M004", "Gulab Jamun",    60.0,  "Desserts",  "Soft milk-solid-based sweet");

        // Toggle availability
        spiceGarden.getMenuItems().get(3).toggleAvailability(); // Gulab Jamun unavailable
        spiceGarden.getMenuItems().get(0).updatePrice(90.0);    // Masala Dosa price update

        // ─────────────────────────────────────────────────────────────────────
        // Add a second restaurant for search demo
        // ─────────────────────────────────────────────────────────────────────
        RestaurantOwner priya = (RestaurantOwner) UserFactory.createUser(
            "OWNER", "O002", "Priya", "priya@mail.com", "pass789", "LIC-2024-BLR-2");

        Restaurant pizzaPalace = priya.registerRestaurant(
            "REST002", "Pizza Palace", "Indiranagar, Bengaluru", "Italian", "11am–12am");
        pizzaPalace.addObserver(new OwnerNotificationObserver(priya.getName()));
        registry.registerRestaurant(pizzaPalace);
        admin.approveRestaurant(pizzaPalace);

        priya.addMenuItem("P001", "Margherita",    350.0, "Pizza",  "Classic tomato and mozzarella");
        priya.addMenuItem("P002", "Pasta Arrabbiata", 280.0, "Pasta", "Spicy tomato pasta");

        // ─────────────────────────────────────────────────────────────────────
        // Customer submits reviews — triggers OBSERVER
        // ─────────────────────────────────────────────────────────────────────
        sep("Customer Submits Reviews");
        alice.submitReview(spiceGarden, 5, "Best dosa in Bengaluru!");
        alice.submitReview(pizzaPalace, 4, "Great pizza, slightly expensive.");

        // ─────────────────────────────────────────────────────────────────────
        // 4. STRATEGY — switch search algorithms at runtime
        // ─────────────────────────────────────────────────────────────────────
        sep("4. STRATEGY — Searching Restaurants");

        java.util.List<Restaurant> all = registry.getAllApproved();
        SearchService searchService = new SearchService(new KeywordSearchStrategy());

        // Strategy A: keyword search
        SearchFilter f1 = new SearchFilter().keyword("spice");
        java.util.List<Restaurant> r1 = searchService.search(all, f1);
        r1.forEach(r -> System.out.println("  → " + r));

        // Switch to cuisine strategy at runtime
        searchService.setStrategy(new strategy.KeywordSearchStrategy()); // reuse as placeholder
        SearchFilter f2 = new SearchFilter().cuisineType("italian");
        // using anonymous inner class to show extensibility:
        searchService.setStrategy((restaurants, filter) ->
            restaurants.stream()
                .filter(r -> r.getCuisineType().equalsIgnoreCase(filter.getCuisineType()))
                .collect(java.util.stream.Collectors.toList())
        );
        java.util.List<Restaurant> r2 = searchService.search(all, f2);
        r2.forEach(r -> System.out.println("  → " + r));

        // Strategy with rating filter (min 4.0)
        searchService.setStrategy((restaurants, filter) ->
            restaurants.stream()
                .filter(r -> r.getAverageRating() >= filter.getMinRating())
                .collect(java.util.stream.Collectors.toList())
        );
        SearchFilter f3 = new SearchFilter().minRating(4.5);
        java.util.List<Restaurant> r3 = searchService.search(all, f3);
        System.out.println("Restaurants rated 4.5+:");
        r3.forEach(r -> System.out.println("  → " + r));

        // ─────────────────────────────────────────────────────────────────────
        // Customer views menu
        // ─────────────────────────────────────────────────────────────────────
        sep("Customer Views Menu");
        alice.viewMenu(spiceGarden);

        // ─────────────────────────────────────────────────────────────────────
        // View restaurant details (from getDetails() method)
        // ─────────────────────────────────────────────────────────────────────
        sep("Restaurant Details");
        spiceGarden.getDetails().forEach((k, v) ->
            System.out.printf("  %-12s: %s%n", k, v));

        sep("DEMO COMPLETE");
    }

    private static void sep(String title) {
        System.out.println("\n" + "═".repeat(60));
        System.out.println("  " + title);
        System.out.println("═".repeat(60));
    }
}
