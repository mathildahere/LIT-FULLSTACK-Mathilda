# Assignment 04: Pagination and Filtering

## Learning Objectives
- Understand pagination for large datasets
- Learn to use Pageable and Page interfaces
- Understand @RequestParam for query parameters
- Practice implementing filtering and sorting
- Learn paginated response structure

## The Challenge

Implement pagination and filtering for a Portfolio list endpoint.

### Key Concepts

1. **Pagination**: 
   - Pageable interface for pagination parameters
   - Page interface for paginated results
   - Prevents loading all data at once

2. **@RequestParam**: 
   - Extracts query parameters from URL
   - Can have default values
   - Optional parameters

3. **Filtering**: 
   - Use query parameters for filters
   - Example: ?name=Tech&minBalance=10000

4. **Sorting**: 
   - Sort parameter in Pageable
   - Can sort by multiple fields

### The Solution Pattern

```java
@GetMapping
public Page<Portfolio> getPortfolios(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size,
    @RequestParam(defaultValue = "id") String sortBy) {
    
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
    return portfolioRepository.findAll(pageable);
}
```

## Running Tests

```bash
mvn test                    # Problem file (should fail)
mvn test -Dtest.solution=true  # Solution file (should pass)
```

## Test Cases

- ✅ Pagination works with page and size
- ✅ Sorting works correctly
- ✅ Filtering by query parameters works
- ✅ Returns Page object with metadata

## Key Takeaways

- Pagination improves performance for large datasets
- Pageable provides page, size, and sort parameters
- Page contains data and pagination metadata
- Query parameters enable flexible filtering

