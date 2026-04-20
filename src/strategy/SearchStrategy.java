package strategy;

import models.Restaurant;
import models.SearchFilter;
import java.util.List;

/**
 * ╔══════════════════════════════════════════════════════════╗
 *  DESIGN PATTERN 4 — STRATEGY
 *  Purpose : Encapsulate different search algorithms behind
 *            a common interface. The caller (SearchService)
 *            picks a strategy at runtime without knowing how
 *            it works internally.
 *  Benefit : Adding a new search type (e.g. proximity-based)
 *            requires no changes to existing code — just a
 *            new Strategy class.
 * ╚══════════════════════════════════════════════════════════╝
 */
public interface SearchStrategy {
    List<Restaurant> search(List<Restaurant> restaurants, SearchFilter filter);
}
