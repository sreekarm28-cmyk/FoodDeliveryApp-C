package edu.classproject.restaurant;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ============================================================
 *  InMemoryRestaurantService.java
 *
 *  Concrete implementation of RestaurantService.
 *  Contains business logic and delegates data access
 *  to the RestaurantRepository.
 *
 *  SOLID Principles Applied:
 *
 *  S — Single Responsibility Principle
 *      Handles business rules only (validation, workflows).
 *      Does NOT manage storage — that is the repository's job.
 *
 *  O — Open/Closed Principle
 *      New business rules can be added by extending or creating
 *      new service implementations without modifying this class.
 *
 *  D — Dependency Inversion Principle
 *      Depends on RestaurantRepository interface, not any
 *      concrete class. Repository is injected via constructor.
 * ============================================================
 */
public class InMemoryRestaurantService implements RestaurantService {

    private final RestaurantRepository repository;

    // Constructor Injection — satisfies Dependency Inversion Principle
    public InMemoryRestaurantService(RestaurantRepository repository) {
        this.repository = repository;
    }

    // ── Basic CRUD ────────────────────────────────────────────────

    @Override
    public void register(Restaurant restaurant) {
        if (restaurant == null)
            throw new IllegalArgumentException("Restaurant cannot be null");

        if (repository.findById(restaurant.getRestaurantId()) != null) {
            System.out.println("[Service] Restaurant already exists: "
                + restaurant.getRestaurantId());
            return;
        }

        repository.register(restaurant);
        System.out.println("[Service] Registered: " + restaurant.getName());
    }

    @Override
    public Restaurant findById(String restaurantId) {
        return repository.findById(restaurantId);
    }

    @Override
    public List<Restaurant> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Restaurant> findAllApproved() {
        return repository.findAllApproved();
    }

    @Override
    public List<Restaurant> findAllPending() {
        return repository.findAllPending();
    }

    @Override
    public void update(Restaurant restaurant) {
        repository.update(restaurant);
    }

    @Override
    public void remove(String restaurantId) {
        repository.remove(restaurantId);
    }

    // ── Business Logic Methods (Service layer exclusive) ──────────

    /**
     * Business rule: Only PENDING restaurants can be approved.
     * Fetches, validates, changes state, then persists.
     */
    @Override
    public void approveRestaurant(String restaurantId) {
        Restaurant restaurant = repository.findById(restaurantId);
        if (restaurant == null)
            throw new IllegalArgumentException(
                "Restaurant not found: " + restaurantId);

        if ("APPROVED".equals(restaurant.getStatus())) {
            System.out.println("[Service] Already approved: " + restaurantId);
            return;
        }

        restaurant.approve();
        repository.update(restaurant);
    }

    /**
     * Business rule: Reject with a mandatory reason.
     * Fetches, validates, changes state, then persists.
     */
    @Override
    public void rejectRestaurant(String restaurantId, String reason) {
        Restaurant restaurant = repository.findById(restaurantId);
        if (restaurant == null)
            throw new IllegalArgumentException(
                "Restaurant not found: " + restaurantId);
        if (reason == null || reason.trim().isEmpty())
            throw new IllegalArgumentException("Rejection reason cannot be empty");

        restaurant.reject(reason);
        repository.update(restaurant);
    }

    /**
     * Business rule: Search restaurants by cuisine type.
     * Case-insensitive matching.
     */
    @Override
    public List<Restaurant> searchByCuisine(String cuisineType) {
        if (cuisineType == null || cuisineType.trim().isEmpty())
            throw new IllegalArgumentException("Cuisine type cannot be empty");

        return repository.findAll().stream()
            .filter(r -> r.getCuisineType()
                .equalsIgnoreCase(cuisineType.trim()))
            .collect(Collectors.toList());
    }
}
