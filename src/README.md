# Restaurant Registry and Menu System
## Group 4ACE — OOAD Course 2024/2025

### Project Structure
```
src/
├── models/          → Core domain classes (from Class Diagram)
│   ├── User.java           (abstract base)
│   ├── Customer.java
│   ├── RestaurantOwner.java
│   ├── Admin.java
│   ├── Restaurant.java
│   ├── MenuItem.java
│   ├── Review.java
│   └── SearchFilter.java
├── factory/         → PATTERN 1: Factory Method
│   └── UserFactory.java
├── observer/        → PATTERN 2: Observer
│   ├── RestaurantObserver.java    (interface)
│   ├── OwnerNotificationObserver.java
│   └── AdminMonitorObserver.java
├── service/         → PATTERN 3: Singleton + Strategy Context
│   ├── RestaurantRegistry.java    (Singleton)
│   └── SearchService.java         (Strategy context)
├── strategy/        → PATTERN 4: Strategy
│   ├── SearchStrategy.java        (interface)
│   └── KeywordSearchStrategy.java (+ Location, Cuisine, Rating, Combined)
└── main/
    └── Main.java    → Demo runner
```

### Design Patterns Used

| Pattern        | Class(es)                             | Why Used |
|----------------|---------------------------------------|----------|
| Factory Method | `UserFactory`                         | Centralise User creation; decouple caller from concrete types |
| Observer       | `RestaurantObserver`, `OwnerNotificationObserver`, `AdminMonitorObserver` | Notify owner/admin on restaurant events without tight coupling |
| Singleton      | `RestaurantRegistry`                  | One shared in-memory store for all restaurants |
| Strategy       | `SearchStrategy`, `SearchService`     | Swap search algorithms at runtime without changing caller code |

### OOP Principles Applied
- **Inheritance** — `User` → `Customer`, `RestaurantOwner`, `Admin`
- **Encapsulation** — private fields, public methods throughout
- **Polymorphism** — `SearchStrategy` and `RestaurantObserver` interfaces
- **Abstraction** — `User` (abstract class), interface contracts
- **SRP** — each class has one clear responsibility
- **OCP** — new search strategies can be added without modifying `SearchService`

### How to Compile & Run
```bash
# Compile
javac -d out $(find src -name "*.java")

# Run
java -cp out main.Main
```
