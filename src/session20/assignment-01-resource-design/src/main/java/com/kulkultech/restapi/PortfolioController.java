/**
 * Portfolio Controller - RESTful Resource Design
 * 
 * Challenge: Design RESTful endpoints following REST principles
 * 
 * Your task: Implement RESTful endpoints with proper resource naming
 * 
 * Concepts covered:
 * - RESTful resource naming (nouns, plural)
 * - HTTP methods (GET, POST, PUT, DELETE)
 * - Proper URL structure
 * - REST architectural principles
 */

package com.kulkultech.restapi;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// SOLVED: Add @RestController annotation
// SOLVED: Add @RequestMapping("/api/portfolios") - use plural noun, base path
@RestController
@RequestMapping("/api/portfolios")
public class PortfolioController {
    
    private Map<Long, Portfolio> portfolios = new HashMap<>();
    private Long nextId = 1L;
    
    // SOLVED: Add @GetMapping to handle GET /api/portfolios
    // This should return all portfolios (collection)
    @GetMapping
    public List<Portfolio> getAllPortfolios() {
        // SOLVED: Return list of all portfolios
        return new ArrayList<>(portfolios.values());
    }
    
    // SOLVED: Add @GetMapping("/{id}") to handle GET /api/portfolios/{id}
    // SOLVED: Add @PathVariable Long id parameter
    // This should return a specific portfolio (single resource)
    @GetMapping("/{id}")
    public Portfolio getPortfolio(@PathVariable Long id) {
        // SOLVED: Return portfolio by ID or null
        return portfolios.get(id);
    }
    
    // SOLVED: Add @PostMapping to handle POST /api/portfolios
    // SOLVED: Add @RequestBody Portfolio portfolio parameter
    // This should create a new portfolio
    @PostMapping
    public Portfolio createPortfolio(@RequestBody Portfolio portfolio) {
        // SOLVED: Set ID, save, and return created portfolio
        portfolio.setId(nextId++);
        portfolios.put(portfolio.getId(), portfolio);
        return portfolio;
    }
    
    // SOLVED: Add @PutMapping("/{id}") to handle PUT /api/portfolios/{id}
    // SOLVED: Add @PathVariable Long id and @RequestBody Portfolio portfolio
    // This should update an entire portfolio (idempotent)
    @PutMapping("/{id}")
    public Portfolio updatePortfolio(@PathVariable Long id, @RequestBody Portfolio portfolio) {
        // SOLVED: Update portfolio and return updated portfolio
        portfolio.setId(id);
        portfolios.put(id, portfolio);
        return portfolio;
    }
    
    // SOLVED: Add @DeleteMapping("/{id}") to handle DELETE /api/portfolios/{id}
    // SOLVED: Add @PathVariable Long id parameter
    // This should delete a portfolio (idempotent)
    @DeleteMapping("/{id}")
    public void deletePortfolio(@PathVariable Long id) {
        // SOLVED: Remove portfolio from map
        portfolios.remove(id);
    }
}

