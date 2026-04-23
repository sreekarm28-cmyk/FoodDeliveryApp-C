package edu.classproject.restaurant;

import java.util.List;

/**
 * ============================================================
 *  RestaurantRepository.java  —  Data Access Layer Interface
 *
 *  SOLID Principles Applied:
 *
 *  D — Dependency Inversion Principle
 *      High-level modules (Service) depend on this abstraction,
 *      not on concrete storage implementations.
 *
 *  I — Interface Segregation Principle
 *      Only CRUD + query operations live here.
 *      Business logic (approve, reject, search) belongs
 *      in the Service layer, not here.
 * ============================================================
 */
public interface RestaurantRepository {

    void register(Restaurant restaurant);

    Restaurant findById(String restaurantId);

    List<Restaurant> findAll();

    List<Restaurant> findAllApproved();

    List<Restaurant> findAllPending();

    void update(Restaurant restaurant);

    void remove(String restaurantId);
}
