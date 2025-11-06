# Assignment 04: Transactions

## Learning Objectives
- Understand ACID properties
- Implement transaction management with commit/rollback
- Use savepoints for partial rollback
- Handle transaction errors properly

## The Challenge

Implement transaction management to ensure data consistency.

### ACID Properties

- **Atomicity**: All or nothing
- **Consistency**: Valid state transitions
- **Isolation**: Concurrent transactions don't interfere
- **Durability**: Committed changes persist

## Running Tests

```bash
mvn test                    # Problem file (should fail)
mvn test -Dtest.solution=true  # Solution file (should pass)
```

