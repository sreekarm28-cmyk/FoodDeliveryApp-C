package edu.classproject.restaurant;

/**
 * ============================================================
 *  MenuItem.java
 *
 *  SOLID Principles Applied:
 *
 *  S — Single Responsibility Principle
 *      This class ONLY represents a single menu item.
 *      It manages its own price and availability.
 *      It does NOT know about restaurants, storage, or users.
 *
 *  O — Open/Closed Principle
 *      itemId and name are final — closed for modification.
 *      price and availability are mutable — open for updates.
 *
 *  D — Dependency Inversion Principle
 *      MenuItem has zero dependencies on any other class.
 *      It is a pure data + behaviour class.
 * ============================================================
 */
public class MenuItem {

    // Immutable fields
    private final String itemId;
    private final String name;
    private final String category;
    private final String description;

    // Mutable fields
    private double price;
    private boolean isAvailable;

    // ── Constructor ───────────────────────────────────────────────
    public MenuItem(String itemId, String name, double price,
                    String category, String description) {
        if (itemId == null || itemId.isEmpty())
            throw new IllegalArgumentException("Item ID cannot be empty");
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("Item name cannot be empty");
        if (price < 0)
            throw new IllegalArgumentException("Price cannot be negative");

        this.itemId      = itemId;
        this.name        = name;
        this.price       = price;
        this.category    = category;
        this.description = description;
        this.isAvailable = true; // available by default
    }

    // ── Behaviour Methods ─────────────────────────────────────────

    // SRP: MenuItem is responsible for toggling its own availability
    public void toggleAvailability() {
        this.isAvailable = !this.isAvailable;
        System.out.println("[MenuItem] '" + name + "' is now "
            + (isAvailable ? "AVAILABLE" : "UNAVAILABLE"));
    }

    // SRP: MenuItem is responsible for updating its own price
    public void updatePrice(double newPrice) {
        if (newPrice < 0)
            throw new IllegalArgumentException("Price cannot be negative");
        System.out.printf("[MenuItem] Price updated: '%s'  %.2f -> %.2f%n",
            name, this.price, newPrice);
        this.price = newPrice;
    }

    // ── Getters ───────────────────────────────────────────────────

    public String getItemId()      { return itemId; }
    public String getName()        { return name; }
    public double getPrice()       { return price; }
    public String getCategory()    { return category; }
    public String getDescription() { return description; }
    public boolean isAvailable()   { return isAvailable; }

    @Override
    public String toString() {
        return String.format(
            "MenuItem{id='%s', name='%-20s', price=%.2f, "
            + "category='%s', available=%s}",
            itemId, name, price, category, isAvailable);
    }
}
