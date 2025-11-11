# How to Run This Assignment

## Quick Start

### Method 1: Run Tests (Recommended)

```bash
# Step 1: Navigate to this assignment directory
cd session-17-jdbc/assignment-01-database-connection

# Step 2: Run tests (will fail initially - that's expected!)
mvn test

# Step 3: Test solution mode (should pass)
mvn test -Dtest.solution=true
```

### Method 2: Run from Project Root

```bash
# From project root, run this specific assignment
mvn test -pl session-17-jdbc/assignment-01-database-connection
```

## What You'll See

Initially, all tests will **FAIL** because `DatabaseConnection` is not fully implemented.

### Step 1: Red 🔴
```bash
cd session-17-jdbc/assignment-01-database-connection
mvn test
# Output: Tests failing ❌
```

### Step 2: Green ✅
Implement the class in `src/main/java/com/kulkultech/jdbc/DatabaseConnection.java`:
- Implement `getConnection()` method
- Implement `testConnection()` method
- Implement `getDatabaseProductName()` method

### Step 3: Test Again
```bash
mvn test
# Output: Tests passing ✅
```

### Step 4: Verify Solution
```bash
# Test with solution mode
mvn test -Dtest.solution=true
# Output: All tests passing ✅
```

## QA Checklist

- [ ] Tests fail when using `DatabaseConnection.java` (problem file)
- [ ] Tests pass when using `DatabaseConnectionSolution.java` (solution file)
- [ ] Connections are properly closed using try-with-resources
- [ ] Connection validation works correctly

## Note on Database

This assignment uses **H2 in-memory database** for testing, so no external database setup is required. The tests will automatically create and manage the database.

## Need Help?

Compare with the solution:
```bash
cat src/main/java/com/kulkultech/jdbc/DatabaseConnectionSolution.java
```

Happy coding! 🎯

