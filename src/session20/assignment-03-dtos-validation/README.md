# Assignment 03: DTOs and Request Validation

## Learning Objectives
- Understand DTO (Data Transfer Object) pattern
- Learn to use @Valid for request validation
- Understand Bean Validation annotations (@NotNull, @Size, @Min, etc.)
- Practice separating API contracts from entities
- Learn validation error handling

## The Challenge

Implement DTOs for request/response and add validation to ensure data integrity.

### Key Concepts

1. **DTO Pattern**: 
   - Separate API contracts from internal entities
   - Hide sensitive fields
   - Control what data is exposed
   - Version API independently

2. **Bean Validation**: 
   - @NotNull: Field cannot be null
   - @Size: String length constraints
   - @Min/@Max: Numeric range constraints
   - @NotBlank: String cannot be blank

3. **@Valid**: 
   - Triggers validation on request body
   - Returns 400 if validation fails
   - Must be used with @RequestBody

### The Solution Pattern

```java
public class PortfolioRequest {
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 100)
    private String name;
    
    @NotNull
    @DecimalMin(value = "0.0")
    private Double balance;
}

@PostMapping
public PortfolioResponse createPortfolio(@Valid @RequestBody PortfolioRequest request) {
    // Convert DTO to entity, save, convert back to response DTO
}
```

## Running Tests

```bash
mvn test                    # Problem file (should fail)
mvn test -Dtest.solution=true  # Solution file (should pass)
```

## Test Cases

- ✅ Validation works with @Valid
- ✅ Invalid data returns 400
- ✅ DTOs separate request/response from entities
- ✅ Validation messages are clear

## Key Takeaways

- DTOs protect internal entities
- @Valid triggers validation automatically
- Bean Validation provides declarative validation
- Validation errors return 400 Bad Request

