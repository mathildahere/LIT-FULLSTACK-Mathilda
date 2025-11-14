# Assignment 05: API Documentation with Swagger

## Learning Objectives
- Understand Swagger/OpenAPI for API documentation
- Learn to use @Tag, @Operation, @ApiResponse annotations
- Understand @Schema for model documentation
- Practice generating interactive API documentation
- Learn API documentation best practices

## The Challenge

Add Swagger/OpenAPI documentation to your REST API using annotations.

### Key Concepts

1. **Swagger/OpenAPI**: 
   - Industry standard for API documentation
   - Interactive documentation UI
   - Auto-generates from annotations

2. **@Tag**: 
   - Groups related endpoints
   - Appears in Swagger UI sidebar

3. **@Operation**: 
   - Describes endpoint operation
   - Summary and description

4. **@ApiResponse**: 
   - Documents possible responses
   - Status codes and descriptions

5. **@Schema**: 
   - Documents model properties
   - Examples and constraints

### The Solution Pattern

```java
@Tag(name = "Portfolio", description = "Portfolio management APIs")
@RestController
@RequestMapping("/api/portfolios")
public class PortfolioController {
    
    @Operation(summary = "Get portfolio by ID", description = "Returns a portfolio")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Portfolio found"),
        @ApiResponse(responseCode = "404", description = "Portfolio not found")
    })
    @GetMapping("/{id}")
    public Portfolio getPortfolio(@Parameter(description = "Portfolio ID") @PathVariable Long id) {
        // ...
    }
}
```

## Running Tests

```bash
mvn test                    # Problem file (should fail)
mvn test -Dtest.solution=true  # Solution file (should pass)
```

## Test Cases

- ✅ Swagger annotations are present
- ✅ API documentation is generated
- ✅ Endpoints are properly documented
- ✅ Models have schema documentation

## Key Takeaways

- Swagger provides interactive API documentation
- Annotations document endpoints automatically
- @Tag groups related endpoints
- @Operation describes what endpoints do
- Good documentation improves API usability

