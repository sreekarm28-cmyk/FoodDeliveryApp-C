package factory;

import models.*;

/**
 * ╔══════════════════════════════════════════════════════════╗
 *  DESIGN PATTERN 1 — FACTORY METHOD
 *  Purpose : Centralise User creation. Caller doesn't need
 *            to know which concrete class is being built.
 *  Benefit : Adding a new role (e.g. Moderator) only changes
 *            this file — not the rest of the codebase.
 * ╚══════════════════════════════════════════════════════════╝
 */
public class UserFactory {

    public static User createUser(String type, String userId,
                                  String name, String email,
                                  String password, String extra) {
        return switch (type.toUpperCase()) {

            case "CUSTOMER" ->
                new Customer(userId, name, email, password, extra); // extra = address

            case "OWNER" ->
                new RestaurantOwner(userId, name, email, password, extra); // extra = businessLicense

            case "ADMIN" ->
                new Admin(userId, name, email, password);

            default -> throw new IllegalArgumentException("Unknown user type: " + type);
        };
    }
}
