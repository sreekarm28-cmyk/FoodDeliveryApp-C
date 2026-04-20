package models;

import java.util.*;

// ── Restaurant ────────────────────────────────────────────────────────────────
public class Restaurant {
    private String restaurantId;
    private String name;
    private String location;
    private String contactInformation; // from problem statement: "contact information"
    private String cuisineType;
    private String operatingHours;
    private String status; // PENDING, APPROVED, REJECTED
    private RestaurantOwner owner;
    private List<MenuItem> menuItems = new ArrayList<>();
    private List<Review> reviews = new ArrayList<>();

    // Observer list — notified on events (e.g. review added, status change)
    private List<observer.RestaurantObserver> observers = new ArrayList<>();

    public Restaurant(String id, String name, String location,
                      String cuisineType, String operatingHours, RestaurantOwner owner) {
        this.restaurantId = id;
        this.name = name;
        this.location = location;
        this.cuisineType = cuisineType;
        this.operatingHours = operatingHours;
        this.contactInformation = "N/A";
        this.status = "PENDING";
        this.owner = owner;
    }

    // ── Observer wiring ──────────────────────────────────────────────────────
    public void addObserver(observer.RestaurantObserver o)    { observers.add(o); }
    public void removeObserver(observer.RestaurantObserver o) { observers.remove(o); }

    private void notifyObservers(String event) {
        for (observer.RestaurantObserver o : observers) {
            o.onRestaurantEvent(this, event);
        }
    }

    // ── Core methods ─────────────────────────────────────────────────────────
    public Map<String, Object> getDetails() {
        Map<String, Object> details = new LinkedHashMap<>();
        details.put("ID",             restaurantId);
        details.put("Name",           name);
        details.put("Location",       location);
        details.put("Contact",        contactInformation);
        details.put("Cuisine",        cuisineType);
        details.put("Hours",          operatingHours);
        details.put("Status",         status);
        details.put("Avg Rating",     String.format("%.1f", getAverageRating()));
        return details;
    }

    public void updateStatus(String newStatus) {
        this.status = newStatus;
        notifyObservers("STATUS_CHANGED:" + newStatus);
    }

    public void addMenuItem(MenuItem item) {
        menuItems.add(item);
        notifyObservers("MENU_UPDATED");
    }

    public void removeMenuItem(String itemId) {
        menuItems.removeIf(item -> item.getItemId().equals(itemId));
        notifyObservers("MENU_UPDATED");
    }

    public void addReview(Review review) {
        reviews.add(review);
        notifyObservers("NEW_REVIEW");
    }

    public double getAverageRating() {
        return reviews.stream().mapToInt(Review::getRating).average().orElse(0.0);
    }

    // ── Getters & Setters ────────────────────────────────────────────────────
    public String getRestaurantId()   { return restaurantId; }
    public String getName()           { return name; }
    public String getLocation()       { return location; }
    public String getCuisineType()    { return cuisineType; }
    public String getOperatingHours() { return operatingHours; }
    public String getStatus()         { return status; }
    public List<MenuItem> getMenuItems() { return menuItems; }
    public List<Review> getReviews()  { return reviews; }

    public void setLocation(String location)          { this.location = location; }
    public void setOperatingHours(String hours)       { this.operatingHours = hours; }
    public void setContactInformation(String contact) { this.contactInformation = contact; }
    public String getContactInformation()             { return contactInformation; }

    @Override
    public String toString() {
        return String.format("%-20s | %-15s | %-12s | Rating: %.1f | %s",
            name, location, cuisineType, getAverageRating(), status);
    }
}
