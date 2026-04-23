package models;

// Base class — mirrors Class Diagram (User with Customer, RestaurantOwner, Admin subclasses)
public abstract class User {
    protected String userId;
    protected String name;
    protected String email;
    protected String password;
    protected String role;

    public User(String userId, String name, String email, String password, String role) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public boolean login(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }

    public String getUserId()  { return userId; }
    public String getName()    { return name; }
    public String getEmail()   { return email; }
    public String getRole()    { return role; }

    @Override
    public String toString() {
        return "[" + role + "] " + name + " (" + email + ")";
    }
}
