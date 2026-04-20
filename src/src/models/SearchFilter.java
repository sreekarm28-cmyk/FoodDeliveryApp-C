package models;

// ── SearchFilter — used by Strategy pattern ───────────────────────────────────
public class SearchFilter {
    private String keyword;
    private String location;
    private String cuisineType;
    private double minRating;

    public SearchFilter() {}

    public SearchFilter keyword(String keyword)       { this.keyword = keyword;         return this; }
    public SearchFilter location(String location)     { this.location = location;       return this; }
    public SearchFilter cuisineType(String cuisine)   { this.cuisineType = cuisine;     return this; }
    public SearchFilter minRating(double minRating)   { this.minRating = minRating;     return this; }

    public String getKeyword()      { return keyword; }
    public String getLocation()     { return location; }
    public String getCuisineType()  { return cuisineType; }
    public double getMinRating()    { return minRating; }

    // applyFilter() — as specified in Class Diagram
    // Returns true if the given restaurant matches all set criteria
    public boolean applyFilter(models.Restaurant restaurant) {
        if (keyword != null && !restaurant.getName().toLowerCase().contains(keyword.toLowerCase()))
            return false;
        if (location != null && !restaurant.getLocation().toLowerCase().contains(location.toLowerCase()))
            return false;
        if (cuisineType != null && !restaurant.getCuisineType().toLowerCase().contains(cuisineType.toLowerCase()))
            return false;
        if (restaurant.getAverageRating() < minRating)
            return false;
        return true;
    }
}
