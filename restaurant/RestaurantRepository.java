package edu.classproject.restaurant;

import java.util.List;

/**
 * ============================================================
 *  RestaurantRepository.java  —  INTERFACE
 *
 *  THIS IS THE CONNECTION POINT FOR OTHER TEAMS.
 *
 *  Any other module (Search, Review, Order) that needs
 *  restaurant data simply uses this interface:
 *
 *      RestaurantRepository repo = new InMemoryRestaurantRepository();
 *      List<Restaurant> all = repo.findAll();
 *
 *  They never need to know HOW restaurants are stored.
 *  They just call these methods.
 *
 *  SOLID Principles Applied:
 *
 *  S — Single Responsibility Principle
 *      This interface only defines restaurant storage operations.
 *      Nothing else.
 *
 *  O — Open/Closed Principle
 *      New storage implementations (DatabaseRestaurantRepository,
 *      FileRestaurantRepository) can be added without changing
 *      any existing code that uses this interface.
 *
 *  L — Liskov Substitution Principle
 *      Any class implementing this interface can replace
 *      InMemoryRestaurantRepository without breaking anything.
 *      Example: DatabaseRestaurantRepository can replace
 *      InMemoryRestaurantRepository seamlessly.
 *
 *  I — Interface Segregation Principle
 *      This interface only has methods related to restaurant
 *      storage. It is NOT bloated with unrelated methods.
 *      If search needs extra methods, that is a separate
 *      interface in the search module.
 *
 *  D — Dependency Inversion Principle  ← MOST IMPORTANT
 *      Other modules depend on THIS INTERFACE (abstraction),
 *      NOT on InMemoryRestaurantRepository (concrete class).
 *      This means:
 *        - Search module imports RestaurantRepository
 *        - NOT InMemoryRestaurantRepository
 *      So if storage changes, Search module is unaffected.
 * ============================================================
 */
public interface RestaurantRepository {

    // ── Registration ──────────────────────────────────────────────
    /**
     * Register a new restaurant. Status will be PENDING.
     * @param restaurant the restaurant to register
     */
    void register(Restaurant restaurant);

    // ── Retrieval ─────────────────────────────────────────────────
    /**
     * Find a restaurant by its unique ID.
     * @param restaurantId the ID to search
     * @return Restaurant if found, null if not found
     */
    Restaurant findById(String restaurantId);

    /**
     * Get all restaurants regardless of status.
     * @return list of all restaurants
     */
    List<Restaurant> findAll();

    /**
     * Get only APPROVED restaurants.
     * Used by Search module, Review module, Order module.
     * @return list of approved restaurants
     */
    List<Restaurant> findAllApproved();

    /**
     * Get only PENDING restaurants waiting for approval.
     * @return list of pending restaurants
     */
    List<Restaurant> findAllPending();

    // ── Update ────────────────────────────────────────────────────
    /**
     * Update restaurant details.
     * @param restaurant the updated restaurant object
     */
    void update(Restaurant restaurant);

    // ── Removal ───────────────────────────────────────────────────
    /**
     * Remove a restaurant from the registry.
     * @param restaurantId the ID of restaurant to remove
     */
    void remove(String restaurantId);
}
