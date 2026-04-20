package models;

// ── MenuItem ──────────────────────────────────────────────────────────────────
public class MenuItem {
    private String itemId;
    private String name;
    private double price;
    private String category;
    private String description;
    private boolean isAvailable;

    public MenuItem(String itemId, String name, double price, String category, String description) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.category = category;
        this.description = description;
        this.isAvailable = true; // available by default
    }

    public void toggleAvailability() {
        this.isAvailable = !this.isAvailable;
        System.out.println("[MenuItem] " + name + " is now " + (isAvailable ? "AVAILABLE" : "UNAVAILABLE"));
    }

    public void updatePrice(double newPrice) {
        System.out.printf("[MenuItem] Price updated: %s  ₹%.2f → ₹%.2f%n", name, this.price, newPrice);
        this.price = newPrice;
    }

    public String getItemId()     { return itemId; }
    public String getName()       { return name; }
    public double getPrice()      { return price; }
    public String getCategory()   { return category; }
    public String getDescription(){ return description; }
    public boolean isAvailable()  { return isAvailable; }

    @Override
    public String toString() {
        return String.format("%-20s ₹%-8.2f [%s] %s", name, price, category,
            isAvailable ? "✔ Available" : "✘ Unavailable");
    }
}
