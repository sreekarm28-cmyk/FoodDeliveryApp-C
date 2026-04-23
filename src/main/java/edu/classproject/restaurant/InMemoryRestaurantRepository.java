package edu.classproject.restaurant;

import java.util.*;

/**
 * ============================================================
 *  InMemoryRestaurantRepository.java
 *
 *  Concrete implementation of RestaurantRepository.
 *  Uses a LinkedHashMap for insertion-ordered in-memory storage.
 *
 *  SOLID Principles Applied:
 *
 *  S — Single Responsibility Principle
 *      Only responsible for storing and retrieving Restaurant
 *      objects. No business logic here.
 *
 *  O — Open/Closed Principle
 *      New storage backends (DatabaseRestaurantRepository, etc.)
 *      can be added without changing this class.
 *
 *  D — Dependency Inversion Principle
 *      Implements the RestaurantRepository abstraction.
 *      The service layer depends on the interface, not this class.
 * ============================================================
 */
public class InMemoryRestaurantRepository implements RestaurantRepository {

    private final Map<String, Restaurant> storage = new LinkedHashMap<>();

    @Override
    public void register(Restaurant restaurant) {
        storage.put(restaurant.getRestaurantId(), restaurant);
    }

    @Override
    public Restaurant findById(String restaurantId) {
        return storage.get(restaurantId);
    }

    @Override
    public List<Restaurant> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public List<Restaurant> findAllApproved() {
        List<Restaurant> list = new ArrayList<>();
        for (Restaurant r : storage.values()) {
            if ("APPROVED".equals(r.getStatus())) {
                list.add(r);
            }
        }
        return list;
    }

    @Override
    public List<Restaurant> findAllPending() {
        List<Restaurant> list = new ArrayList<>();
        for (Restaurant r : storage.values()) {
            if ("PENDING".equals(r.getStatus())) {
                list.add(r);
            }
        }
        return list;
    }

    @Override
    public void update(Restaurant restaurant) {
        storage.put(restaurant.getRestaurantId(), restaurant);
    }

    @Override
    public void remove(String restaurantId) {
        storage.remove(restaurantId);
    }
}
