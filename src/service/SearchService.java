package service;

import models.Restaurant;
import models.SearchFilter;
import strategy.SearchStrategy;
import java.util.List;

/**
 * SearchService acts as the CONTEXT in the Strategy pattern.
 * It holds a reference to a SearchStrategy and delegates to it.
 */
public class SearchService {

    private SearchStrategy strategy;

    public SearchService(SearchStrategy strategy) {
        this.strategy = strategy;
    }

    // Allow swapping strategy at runtime
    public void setStrategy(SearchStrategy strategy) {
        this.strategy = strategy;
        System.out.println("[SearchService] Strategy switched to: "
            + strategy.getClass().getSimpleName());
    }

    public List<Restaurant> search(List<Restaurant> allRestaurants, SearchFilter filter) {
        System.out.println("[SearchService] Running search with: "
            + strategy.getClass().getSimpleName());
        List<Restaurant> results = strategy.search(allRestaurants, filter);
        System.out.println("[SearchService] Found " + results.size() + " result(s).");
        return results;
    }
}
