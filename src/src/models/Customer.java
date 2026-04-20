package models;

// ── Customer ─────────────────────────────────────────────────────────────────
public class Customer extends User {
    private String customerId; // explicit field as per Class Diagram
    private String address;

    public Customer(String userId, String name, String email, String password, String address) {
        super(userId, name, email, password, "CUSTOMER");
        this.customerId = userId;
        this.address = address;
    }

    public String getCustomerId() { return customerId; }

    public void searchRestaurant(String query) {
        System.out.println("[Customer] Searching for: " + query);
    }

    public void viewMenu(Restaurant restaurant) {
        System.out.println("[Customer] Viewing menu of: " + restaurant.getName());
        restaurant.getMenuItems().forEach(item ->
            System.out.println("   - " + item));
    }

    public void submitReview(Restaurant restaurant, int rating, String comment) {
        Review review = new Review("R" + System.currentTimeMillis(), this, restaurant, rating, comment);
        restaurant.addReview(review);
        System.out.println("[Customer] Review submitted for " + restaurant.getName() + " | Rating: " + rating);
    }

    public String getAddress() { return address; }
}
