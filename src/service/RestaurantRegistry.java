package service;

import models.Restaurant;
import java.util.*;

/**
 * ╔══════════════════════════════════════════════════════════╗
 *  DESIGN PATTERN 3 — SINGLETON
 *  Purpose : One and only one RestaurantRegistry exists for
 *            the entire application lifetime. All components
 *            share the same registry (no duplicate data).
 *  Benefit : Prevents data fragmentation; acts as the central
 *            in-memory "database" for restaurants.
 * ╚══════════════════════════════════════════════════════════╝
 */
public class RestaurantRegistry {

    // The single instance — volatile for thread safety
    private static volatile RestaurantRegistry instance;

    // Internal store: restaurantId → Restaurant
    private final Map<String, Restaurant> restaurants = new LinkedHashMap<>();

    // Private constructor — prevents external instantiation
    private RestaurantRegistry() {}

    // Double-checked locking for thread-safe lazy initialization
    public static RestaurantRegistry getInstance() {
        if (instance == null) {
            synchronized (RestaurantRegistry.class) {
                if (instance == null) {
                    instance = new RestaurantRegistry();
                    System.out.println("[Singleton] RestaurantRegistry instance created.");
                }
            }
        }
        return instance;
    }

    public void registerRestaurant(Restaurant restaurant) {
        restaurants.put(restaurant.getRestaurantId(), restaurant);
        System.out.println("[Registry] Restaurant added: " + restaurant.getName());
    }

    public Optional<Restaurant> findById(String id) {
        return Optional.ofNullable(restaurants.get(id));
    }

    public void removeRestaurant(String id) {
        restaurants.remove(id);
    }

    public List<Restaurant> getAllApproved() {
        List<Restaurant> result = new ArrayList<>();
        for (Restaurant r : restaurants.values()) {
            if ("APPROVED".equals(r.getStatus())) result.add(r);
        }
        return result;
    }

    public List<Restaurant> getAll() {
        return new ArrayList<>(restaurants.values());
    }
}
