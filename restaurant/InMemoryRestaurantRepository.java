package edu.classproject.restaurant;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * ============================================================
 *  InMemoryRestaurantRepository.java
 *
 *  This is the IMPLEMENTATION of RestaurantRepository.
 *  It stores all restaurants in memory using a HashMap.
 *
 *  Other teams connect to your module like this:
 *
 *      RestaurantRepository repo =
 *          new InMemoryRestaurantRepository();
 *
 *  SOLID Principles Applied:
 *
 *  S — Single Responsibility Principle
 *      This class ONLY handles storing and retrieving
 *      restaurants from memory. Nothing else.
 *      - Approval logic is in Restaurant.java
 *      - Menu logic is in Restaurant.java + MenuItem.java
 *      - Demo logic is in RestaurantDemo.java
 *
 *  O — Open/Closed Principle
 *      If tomorrow you want to store in a database instead,
 *      you create DatabaseRestaurantRepository and implement
 *      RestaurantRepository. This class is NOT modified.
 *      All other modules keep working because they depend
 *      on the interface, not this class.
 *
 *  L — Liskov Substitution Principle
 *      This class fully implements RestaurantRepository.
 *      It can be replaced by any other implementation
 *      without breaking any code that uses the interface.
 *
 *  D — Dependency Inversion Principle
 *      This class depends on the Restaurant abstraction,
 *      not on any specific user or service class.
 * ============================================================
 */
public class InMemoryRestaurantRepository implements RestaurantRepository {

    // Internal storage — restaurantId → Restaurant
    private final Map<String, Restaurant> storage = new LinkedHashMap<>();

    // ── Register ──────────────────────────────────────────────────

    @Override
    public void register(Restaurant restaurant) {
        if (restaurant == null)
            throw new IllegalArgumentException("Restaurant cannot be null");
        if (storage.containsKey(restaurant.getRestaurantId())) {
            System.out.println("[Repository] Restaurant already exists: "
                + restaurant.getRestaurantId());
            return;
        }
        storage.put(restaurant.getRestaurantId(), restaurant);
        System.out.println("[Repository] Registered: '"
            + restaurant.getName() + "' | Status: " + restaurant.getStatus());
    }

    // ── Find ──────────────────────────────────────────────────────

    @Override
    public Restaurant findById(String restaurantId) {
        if (restaurantId == null) return null;
        return storage.get(restaurantId);
    }

    @Override
    public List<Restaurant> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public List<Restaurant> findAllApproved() {
        List<Restaurant> approved = new ArrayList<>();
        for (Restaurant r : storage.values()) {
            if ("APPROVED".equals(r.getStatus()))
                approved.add(r);
        }
        return approved;
    }

    @Override
    public List<Restaurant> findAllPending() {
        List<Restaurant> pending = new ArrayList<>();
        for (Restaurant r : storage.values()) {
            if ("PENDING".equals(r.getStatus()))
                pending.add(r);
        }
        return pending;
    }

    // ── Update ────────────────────────────────────────────────────

    @Override
    public void update(Restaurant restaurant) {
        if (!storage.containsKey(restaurant.getRestaurantId())) {
            System.out.println("[Repository] Cannot update — not found: "
                + restaurant.getRestaurantId());
            return;
        }
        storage.put(restaurant.getRestaurantId(), restaurant);
        System.out.println("[Repository] Updated: '" + restaurant.getName() + "'");
    }

    // ── Remove ────────────────────────────────────────────────────

    @Override
    public void remove(String restaurantId) {
        Restaurant removed = storage.remove(restaurantId);
        if (removed != null)
            System.out.println("[Repository] Removed: '" + removed.getName() + "'");
        else
            System.out.println("[Repository] Not found: " + restaurantId);
    }

    // ── Helper ────────────────────────────────────────────────────

    public int count() {
        return storage.size();
    }
}
