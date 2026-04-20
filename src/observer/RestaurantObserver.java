package observer;

import models.Restaurant;

/**
 * ╔══════════════════════════════════════════════════════════╗
 *  DESIGN PATTERN 2 — OBSERVER
 *  Purpose : Notify interested parties (Owner, Admin) when
 *            a Restaurant fires an event (approval, review,
 *            menu update) without tight coupling.
 *  Benefit : New observers (e.g. email service, analytics)
 *            can be added without touching Restaurant.
 * ╚══════════════════════════════════════════════════════════╝
 */
public interface RestaurantObserver {
    void onRestaurantEvent(Restaurant restaurant, String event);
}
