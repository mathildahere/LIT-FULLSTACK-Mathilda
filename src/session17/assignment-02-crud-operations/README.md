# Assignment 02: CRUD Operations

## Learning Objectives
- Understand CREATE, READ, UPDATE, DELETE operations in JDBC
- Learn to use Statement.executeUpdate() and executeQuery()
- Practice processing ResultSet
- Handle SQL exceptions properly

## The Challenge

Implement CRUD operations for a trading system using JDBC Statement.

### Key Concepts

1. **CREATE (INSERT)**: Use `executeUpdate()` to insert records
2. **READ (SELECT)**: Use `executeQuery()` and process ResultSet
3. **UPDATE**: Use `executeUpdate()` to modify records
4. **DELETE**: Use `executeUpdate()` to remove records

## Running Tests

```bash
mvn test                    # Problem file (should fail)
mvn test -Dtest.solution=true  # Solution file (should pass)
```

## Test Cases

- ✅ Add trade (INSERT)
- ✅ Retrieve trades (SELECT)
- ✅ Update trade price (UPDATE)
- ✅ Delete trade (DELETE)
- ✅ Complete CRUD cycle

