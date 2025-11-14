# Assignment 02: HTTP Status Codes and Error Handling

## Learning Objectives
- Understand HTTP status codes and when to use them
- Learn to use @ResponseStatus annotation
- Understand ResponseEntity for custom responses
- Learn @RestControllerAdvice for global exception handling
- Practice standardized error responses

## The Challenge

Implement proper HTTP status codes and error handling for a Portfolio API.

### Key Concepts

1. **HTTP Status Codes**:
   - 200 OK: Successful GET, PUT
   - 201 Created: Successful POST
   - 204 No Content: Successful DELETE
   - 400 Bad Request: Invalid input
   - 404 Not Found: Resource doesn't exist
   - 500 Server Error: Unexpected error

2. **@ResponseStatus**: 
   - Sets HTTP status code for method
   - Can be used on methods or exception classes

3. **ResponseEntity**: 
   - Provides full control over HTTP response
   - Can set status, headers, body

4. **@RestControllerAdvice**: 
   - Global exception handler
   - Handles exceptions across all controllers
   - Returns standardized error responses

### The Solution Pattern

```java
@PostMapping
@ResponseStatus(HttpStatus.CREATED)
public Portfolio createPortfolio(@RequestBody Portfolio portfolio) {
    return portfolioService.save(portfolio);
}

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(PortfolioNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(PortfolioNotFoundException ex) {
        return new ErrorResponse(404, ex.getMessage());
    }
}
```

## Running Tests

```bash
mvn test                    # Problem file (should fail)
mvn test -Dtest.solution=true  # Solution file (should pass)
```

## Test Cases

- ✅ POST returns 201 Created
- ✅ DELETE returns 204 No Content
- ✅ Not found returns 404
- ✅ Global exception handler works

## Key Takeaways

- Use appropriate status codes for each operation
- 201 for created resources
- 204 for successful deletions
- 404 for not found resources
- Global exception handlers provide consistent error responses

