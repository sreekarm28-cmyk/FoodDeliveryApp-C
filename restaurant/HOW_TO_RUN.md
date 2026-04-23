# Restaurant Registry and Menu Management Module
## Group 4ACE | OOAD Course 2024/2025

---

## Module Overview

This module handles two responsibilities:
1. **Restaurant Registry** — register, approve, reject restaurants
2. **Menu Management** — add, remove, toggle, update menu items

---

## How Other Teams Connect to This Module

Any other module (Search, Review, Order) connects using the interface:

```java
RestaurantRepository repo = new InMemoryRestaurantRepository();

// Get all approved restaurants
List<Restaurant> approved = repo.findAllApproved();

// Find a specific restaurant
Restaurant r = repo.findById("R001");

// Access its menu
List<MenuItem> menu = r.getMenuItems();
```

---

## File Structure

```
src/main/java/edu/classproject/
├── restaurant/
│   ├── Restaurant.java                    ← Core restaurant model
│   ├── MenuItem.java                      ← Menu item model
│   ├── RestaurantRepository.java          ← Interface (connect here)
│   └── InMemoryRestaurantRepository.java  ← Implementation
└── bootstrap/
    └── RestaurantDemo.java                ← Run this to see it working
```

---

## How to Compile and Run

```bash
# Step 1 — Compile
javac -d out $(find src -name "*.java")

# Step 2 — Run
java -cp out edu.classproject.bootstrap.RestaurantDemo
```

---

## SOLID Principles Applied

| Principle | Where |
|---|---|
| S — Single Responsibility | Each class has one job only |
| O — Open/Closed | Add new storage without modifying existing code |
| L — Liskov Substitution | Any Repository impl can replace InMemoryRestaurantRepository |
| I — Interface Segregation | RestaurantRepository only has storage methods |
| D — Dependency Inversion | All modules depend on RestaurantRepository interface, not the class |
