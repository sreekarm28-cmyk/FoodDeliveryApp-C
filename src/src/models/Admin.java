package models;

import service.RestaurantRegistry;

// ── Admin ─────────────────────────────────────────────────────────────────────
public class Admin extends User {
    private String adminId;

    public Admin(String userId, String name, String email, String password) {
        super(userId, name, email, password, "ADMIN");
        this.adminId = userId;
    }

    public void approveRestaurant(Restaurant restaurant) {
        restaurant.updateStatus("APPROVED");
        System.out.println("[Admin] Approved restaurant: " + restaurant.getName());
    }

    public void rejectRestaurant(Restaurant restaurant, String reason) {
        restaurant.updateStatus("REJECTED");
        System.out.println("[Admin] Rejected restaurant: " + restaurant.getName() + " | Reason: " + reason);
    }

    public void removeData(String restaurantId, RestaurantRegistry registry) {
        registry.removeRestaurant(restaurantId);
        System.out.println("[Admin] Removed restaurant ID: " + restaurantId);
    }

    public void manageUsers() {
        System.out.println("[Admin] Managing users...");
    }
}
