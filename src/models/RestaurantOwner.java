package models;

// ── RestaurantOwner ───────────────────────────────────────────────────────────
public class RestaurantOwner extends User {
    private String businessLicense;
    private Restaurant restaurant;

    public RestaurantOwner(String userId, String name, String email, String password, String businessLicense) {
        super(userId, name, email, password, "OWNER");
        this.businessLicense = businessLicense;
    }

    public Restaurant registerRestaurant(String id, String name, String location,
                                          String cuisineType, String operatingHours) {
        this.restaurant = new Restaurant(id, name, location, cuisineType, operatingHours, this);
        System.out.println("[Owner] Restaurant registered (pending approval): " + name);
        return this.restaurant;
    }

    public void updateDetails(String location, String operatingHours) {
        if (restaurant != null) {
            restaurant.setLocation(location);
            restaurant.setOperatingHours(operatingHours);
            System.out.println("[Owner] Restaurant details updated.");
        }
    }

    public void addMenuItem(String itemId, String name, double price,
                             String category, String description) {
        if (restaurant != null) {
            MenuItem item = new MenuItem(itemId, name, price, category, description);
            restaurant.addMenuItem(item);
            System.out.println("[Owner] Menu item added: " + name);
        }
    }

    public void removeMenuItem(String itemId) {
        if (restaurant != null) {
            restaurant.removeMenuItem(itemId);
            System.out.println("[Owner] Menu item removed: " + itemId);
        }
    }

    // manageMenu() — as specified in Class Diagram (high-level menu management)
    public void manageMenu() {
        if (restaurant != null) {
            System.out.println("[Owner] Managing menu for: " + restaurant.getName());
            System.out.println("  Current items: " + restaurant.getMenuItems().size());
            restaurant.getMenuItems().forEach(item -> System.out.println("   - " + item));
        }
    }

    public Restaurant getRestaurant() { return restaurant; }
    public String getBusinessLicense() { return businessLicense; }
}
