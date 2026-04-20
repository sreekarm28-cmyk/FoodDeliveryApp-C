package observer;

import models.Restaurant;

// ── Observer A: Notifies the restaurant owner ─────────────────────────────────
public class OwnerNotificationObserver implements RestaurantObserver {
    private String ownerName;

    public OwnerNotificationObserver(String ownerName) {
        this.ownerName = ownerName;
    }

    @Override
    public void onRestaurantEvent(Restaurant restaurant, String event) {
        if (event.startsWith("STATUS_CHANGED")) {
            String status = event.split(":")[1];
            System.out.println("[🔔 Owner Notification → " + ownerName + "] "
                + "Your restaurant \"" + restaurant.getName()
                + "\" has been " + status + ".");
        } else if (event.equals("NEW_REVIEW")) {
            System.out.println("[🔔 Owner Notification → " + ownerName + "] "
                + "New review received for \"" + restaurant.getName() + "\".");
        }
    }
}
