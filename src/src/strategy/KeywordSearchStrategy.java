package strategy;

import models.Restaurant;
import models.SearchFilter;
import java.util.*;
import java.util.stream.Collectors;

// ── Strategy A: Search by name keyword ───────────────────────────────────────
public class KeywordSearchStrategy implements SearchStrategy {
    @Override
    public List<Restaurant> search(List<Restaurant> restaurants, SearchFilter filter) {
        if (filter.getKeyword() == null) return restaurants;
        String kw = filter.getKeyword().toLowerCase();
        return restaurants.stream()
            .filter(r -> r.getName().toLowerCase().contains(kw))
            .collect(Collectors.toList());
    }
}

// ── Strategy B: Search by location ───────────────────────────────────────────
class LocationSearchStrategy implements SearchStrategy {
    @Override
    public List<Restaurant> search(List<Restaurant> restaurants, SearchFilter filter) {
        if (filter.getLocation() == null) return restaurants;
        String loc = filter.getLocation().toLowerCase();
        return restaurants.stream()
            .filter(r -> r.getLocation().toLowerCase().contains(loc))
            .collect(Collectors.toList());
    }
}

// ── Strategy C: Search by cuisine type ───────────────────────────────────────
class CuisineSearchStrategy implements SearchStrategy {
    @Override
    public List<Restaurant> search(List<Restaurant> restaurants, SearchFilter filter) {
        if (filter.getCuisineType() == null) return restaurants;
        String cuisine = filter.getCuisineType().toLowerCase();
        return restaurants.stream()
            .filter(r -> r.getCuisineType().toLowerCase().contains(cuisine))
            .collect(Collectors.toList());
    }
}

// ── Strategy D: Search by minimum rating ─────────────────────────────────────
class RatingSearchStrategy implements SearchStrategy {
    @Override
    public List<Restaurant> search(List<Restaurant> restaurants, SearchFilter filter) {
        return restaurants.stream()
            .filter(r -> r.getAverageRating() >= filter.getMinRating())
            .collect(Collectors.toList());
    }
}

// ── Strategy E: Combined filter (applies all criteria) ───────────────────────
class CombinedSearchStrategy implements SearchStrategy {
    @Override
    public List<Restaurant> search(List<Restaurant> restaurants, SearchFilter filter) {
        return restaurants.stream().filter(r -> {
            boolean match = true;
            if (filter.getKeyword() != null)
                match &= r.getName().toLowerCase().contains(filter.getKeyword().toLowerCase());
            if (filter.getLocation() != null)
                match &= r.getLocation().toLowerCase().contains(filter.getLocation().toLowerCase());
            if (filter.getCuisineType() != null)
                match &= r.getCuisineType().toLowerCase().contains(filter.getCuisineType().toLowerCase());
            match &= r.getAverageRating() >= filter.getMinRating();
            return match;
        }).collect(Collectors.toList());
    }
}
