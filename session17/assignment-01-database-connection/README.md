# Assignment 01: Database Connection

## Learning Objectives
- Understand how to establish JDBC connections using `DriverManager`
- Learn connection string format and properties
- Practice using try-with-resources for proper resource management
- Understand connection validation and metadata access

## The Challenge

Create a `DatabaseConnection` class that manages database connections using JDBC. The class should:
1. Establish connections using `DriverManager.getConnection()`
2. Test connection validity
3. Retrieve database metadata

### What You'll Learn

1. **DriverManager.getConnection()**: 
   - Creates a connection to the database
   - Requires: JDBC URL, username, and password
   - Format: `jdbc:mysql://host:port/database` or `jdbc:h2:mem:testdb`

2. **Connection Properties**:
   - URL: Database location and connection string
   - Username: Database user credentials
   - Password: Database password

3. **Try-with-Resources**:
   - Automatically closes resources (connections, statements, etc.)
   - Prevents resource leaks
   - Syntax: `try (Connection conn = getConnection()) { ... }`

4. **Connection Validation**:
   - `connection.isValid(timeout)` - checks if connection is still valid
   - Useful for connection health checks

5. **Database Metadata**:
   - `connection.getMetaData()` - provides database information
   - `getDatabaseProductName()` - returns database product name

### The Solution Pattern

```java
public Connection getConnection() throws SQLException {
    return DriverManager.getConnection(dbUrl, username, password);
}

public boolean testConnection() {
    try (Connection conn = getConnection()) {
        return conn.isValid(5);
    } catch (SQLException e) {
        return false;
    }
}
```

### Why Proper Connection Management?

- **Resource Leaks**: Unclosed connections consume database resources
- **Connection Limits**: Databases have maximum connection limits
- **Performance**: Proper connection management improves application performance
- **Best Practice**: Always use try-with-resources for JDBC resources

## Running the Tests

### Step 1: Run Tests (Will Fail Initially)
```bash
mvn test
```

### Step 2: Implement Solution
Complete the `DatabaseConnection.java` file to make tests pass.

### Step 3: Verify Solution
```bash
# Test your implementation
mvn test

# Test with solution (should pass)
mvn test -Dtest.solution=true
```

## Test Cases

Your implementation should:
- ✅ Create database connections using DriverManager
- ✅ Validate connections using `isValid()`
- ✅ Retrieve database product name from metadata
- ✅ Properly close connections using try-with-resources

## Key Takeaways

- Always use try-with-resources for JDBC resources
- Connections must be explicitly closed (or use try-with-resources)
- Connection validation helps detect connection issues early
- Database metadata provides useful information about the database

## Resources

- [JDBC Tutorial](https://docs.oracle.com/javase/tutorial/jdbc/)
- [DriverManager API](https://docs.oracle.com/javase/11/docs/api/java/sql/DriverManager.html)
- [Connection API](https://docs.oracle.com/javase/11/docs/api/java/sql/Connection.html)

