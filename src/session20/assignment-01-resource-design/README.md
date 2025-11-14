# Assignment 01: RESTful Resource Design

## Learning Objectives
- Understand REST architectural principles
- Learn proper resource naming conventions
- Understand HTTP methods and their semantics
- Practice designing RESTful URLs
- Learn RESTful API best practices

## The Challenge

Design and implement RESTful endpoints following REST principles for a Portfolio API.

### Key Concepts

1. **Resource Naming**: 
   - Use nouns, not verbs
   - Use plural for collections
   - Use lowercase with hyphens
   - Example: `/api/portfolios` not `/api/getPortfolios`

2. **HTTP Methods**: 
   - GET: Retrieve resources (safe, idempotent)
   - POST: Create new resources
   - PUT: Update entire resources (idempotent)
   - DELETE: Remove resources (idempotent)

3. **URL Structure**: 
   - Base path: `/api/portfolios`
   - Resource ID: `/api/portfolios/{id}`
   - Nested resources: `/api/portfolios/{id}/trades`

4. **REST Principles**: 
   - Stateless requests
   - Uniform interface
   - Resource-based URLs

### The Solution Pattern

```java
@RestController
@RequestMapping("/api/portfolios")  // Base path, plural noun
public class PortfolioController {
    
    @GetMapping  // GET /api/portfolios
    public List<Portfolio> getAllPortfolios() { }
    
    @GetMapping("/{id}")  // GET /api/portfolios/123
    public Portfolio getPortfolio(@PathVariable Long id) { }
    
    @PostMapping  // POST /api/portfolios
    public Portfolio createPortfolio(@RequestBody Portfolio portfolio) { }
    
    @PutMapping("/{id}")  // PUT /api/portfolios/123
    public Portfolio updatePortfolio(@PathVariable Long id, 
                                     @RequestBody Portfolio portfolio) { }
    
    @DeleteMapping("/{id}")  // DELETE /api/portfolios/123
    public void deletePortfolio(@PathVariable Long id) { }
}
```

## Running Tests

```bash
mvn test                    # Problem file (should fail)
mvn test -Dtest.solution=true  # Solution file (should pass)
```

## Test Cases

- ✅ Proper resource naming (nouns, plural)
- ✅ Correct HTTP method usage
- ✅ Proper URL structure
- ✅ Path variables for resource IDs

## Key Takeaways

- Use nouns for resources, not verbs
- Use plural for collections
- HTTP methods define operations
- URLs should be hierarchical and intuitive
- Follow REST conventions for consistency

