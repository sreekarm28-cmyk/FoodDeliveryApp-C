package observer;

import models.Restaurant;

// ── Observer B: Admin activity monitor ───────────────────────────────────────
public class AdminMonitorObserver implements RestaurantObserver {
    @Override
    public void onRestaurantEvent(Restaurant restaurant, String event) {
        System.out.println("[📋 Admin Monitor] Event on \"" + restaurant.getName()
            + "\": " + event + " | Status: " + restaurant.getStatus());
    }
}

// ── Observer C: Audit log (could write to file/DB in production) ──────────────
class AuditLogObserver implements RestaurantObserver {
    @Override
    public void onRestaurantEvent(Restaurant restaurant, String event) {
        System.out.println("[📁 Audit Log] " + java.time.LocalDateTime.now()
            + " | Restaurant: " + restaurant.getRestaurantId()
            + " | Event: " + event);
    }
}
