package edu.classproject.restaurant;

import java.util.List;

/**
 * ============================================================
 *  RestaurantService.java  —  Business Logic Layer Interface
 *
 *  SOLID Principles Applied:
 *
 *  S — Single Responsibility Principle
 *      Handles only business rules: validation, approval
 *      workflows, and search logic. Raw CRUD is delegated
 *      to the Repository layer.
 *
 *  I — Interface Segregation Principle
 *      This interface is intentionally separate from
 *      RestaurantRepository. It adds business-level operations
 *      (approve, reject, searchByCuisine) that go beyond
 *      simple data access.
 *
 *  D — Dependency Inversion Principle
 *      Service depends on the Repository abstraction,
 *      not on any concrete implementation.
 * ============================================================
 */
public interface RestaurantService {

    // ── Basic CRUD (delegates to Repository) ──────────────────────
    void register(Restaurant restaurant);

    Restaurant findById(String restaurantId);

    List<Restaurant> findAll();

    List<Restaurant> findAllApproved();

    List<Restaurant> findAllPending();

    void update(Restaurant restaurant);

    void remove(String restaurantId);

    // ── Business Logic (Service layer exclusive) ──────────────────

    /**
     * Approves a restaurant by ID.
     * Throws IllegalArgumentException if restaurant not found.
     */
    void approveRestaurant(String restaurantId);

    /**
     * Rejects a restaurant by ID with a reason.
     * Throws IllegalArgumentException if restaurant not found.
     */
    void rejectRestaurant(String restaurantId, String reason);

    /**
     * Returns all restaurants matching the given cuisine type.
     * Case-insensitive search.
     */
    List<Restaurant> searchByCuisine(String cuisineType);
}
