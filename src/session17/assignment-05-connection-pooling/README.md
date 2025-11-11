# Assignment 05: Connection Pooling

## Learning Objectives
- Understand connection pooling benefits
- Configure HikariCP connection pool
- Manage pool size and lifecycle
- Monitor pool statistics

## The Challenge

Implement connection pooling using HikariCP for efficient connection management.

### Benefits

- **Performance**: Reuse connections instead of creating new ones
- **Resource Management**: Limit concurrent connections
- **Scalability**: Handle high load efficiently

## Running Tests

```bash
mvn test                    # Problem file (should fail)
mvn test -Dtest.solution=true  # Solution file (should pass)
```

