package edu.classproject.restaurant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ============================================================
 *  Restaurant.java
 *
 *  SOLID Principles Applied:
 *
 *  S — Single Responsibility Principle
 *      This class ONLY holds restaurant data and manages its
 *      own menu items. It does NOT handle storage, searching,
 *      or user management. Those are separate concerns.
 *
 *  O — Open/Closed Principle
 *      Core fields (restaurantId, name) are final — cannot be
 *      changed after creation. Class can be extended without
 *      modifying existing logic.
 *
 *  L — Liskov Substitution Principle
 *      Any subclass of Restaurant can replace Restaurant
 *      without breaking the system.
 *
 *  I — Interface Segregation Principle
 *      Restaurant does not implement any fat interface.
 *      It only exposes what it needs to.
 *
 *  D — Dependency Inversion Principle
 *      Restaurant does NOT depend on any repository or
 *      storage class. Storage depends on Restaurant,
 *      not the other way around.
 * ============================================================
 */
public class Restaurant {

    // Immutable fields — set once at creation, never changed
    private final String restaurantId;
    private final String name;

    // Mutable fields — can be updated by owner
    private String location;
    private String contactInformation;
    private String cuisineType;
    private String operatingHours;

    // Status: PENDING → APPROVED or REJECTED
    private String status;

    // Menu items belonging to this restaurant
    private final List<MenuItem> menuItems = new ArrayList<>();

    // ── Constructor ───────────────────────────────────────────────
    public Restaurant(String restaurantId, String name, String location,
                      String cuisineType, String operatingHours,
                      String contactInformation) {
        if (restaurantId == null || restaurantId.isEmpty())
            throw new IllegalArgumentException("Restaurant ID cannot be empty");
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("Restaurant name cannot be empty");

        this.restaurantId       = restaurantId;
        this.name               = name;
        this.location           = location;
        this.cuisineType        = cuisineType;
        this.operatingHours     = operatingHours;
        this.contactInformation = contactInformation;
        this.status             = "PENDING";
    }

    // ── Menu Management ───────────────────────────────────────────

    public void addMenuItem(MenuItem item) {
        if (item == null)
            throw new IllegalArgumentException("MenuItem cannot be null");
        menuItems.add(item);
        System.out.println("[Restaurant] Item added: '"
            + item.getName() + "' to '" + this.name + "'");
    }

    public void removeMenuItem(String itemId) {
        boolean removed = menuItems.removeIf(
            item -> item.getItemId().equals(itemId));
        if (removed)
            System.out.println("[Restaurant] Item removed: " + itemId);
        else
            System.out.println("[Restaurant] Item not found: " + itemId);
    }

    public MenuItem findMenuItemById(String itemId) {
        return menuItems.stream()
            .filter(item -> item.getItemId().equals(itemId))
            .findFirst()
            .orElse(null);
    }

    // Unmodifiable list — protects internal state
    public List<MenuItem> getMenuItems() {
        return Collections.unmodifiableList(menuItems);
    }

    // ── Status Methods ────────────────────────────────────────────

    public void approve() {
        this.status = "APPROVED";
        System.out.println("[Restaurant] '" + name + "' APPROVED.");
    }

    public void reject(String reason) {
        this.status = "REJECTED";
        System.out.println("[Restaurant] '" + name
            + "' REJECTED. Reason: " + reason);
    }

    // ── Getters ───────────────────────────────────────────────────

    public String getRestaurantId()       { return restaurantId; }
    public String getName()               { return name; }
    public String getLocation()           { return location; }
    public String getCuisineType()        { return cuisineType; }
    public String getOperatingHours()     { return operatingHours; }
    public String getContactInformation() { return contactInformation; }
    public String getStatus()             { return status; }

    // ── Setters (only mutable fields) ─────────────────────────────

    public void setLocation(String location)             { this.location = location; }
    public void setContactInformation(String contact)    { this.contactInformation = contact; }
    public void setCuisineType(String cuisineType)       { this.cuisineType = cuisineType; }
    public void setOperatingHours(String operatingHours) { this.operatingHours = operatingHours; }

    @Override
    public String toString() {
        return String.format(
            "Restaurant{id='%s', name='%s', location='%s', "
            + "cuisine='%s', status='%s', menuItems=%d}",
            restaurantId, name, location,
            cuisineType, status, menuItems.size());
    }
}
