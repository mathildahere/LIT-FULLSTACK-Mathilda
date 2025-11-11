# Assignment 03: PreparedStatement

## Learning Objectives
- Understand PreparedStatement vs Statement
- Learn parameterized queries
- Prevent SQL injection attacks
- Use setInt, setString, setDouble methods

## The Challenge

Replace Statement with PreparedStatement for safe, parameterized queries.

### Why PreparedStatement?

- **Security**: Prevents SQL injection
- **Performance**: Pre-compiled, faster execution
- **Type Safety**: Compile-time checking of parameters

## Running Tests

```bash
mvn test                    # Problem file (should fail)
mvn test -Dtest.solution=true  # Solution file (should pass)
```

