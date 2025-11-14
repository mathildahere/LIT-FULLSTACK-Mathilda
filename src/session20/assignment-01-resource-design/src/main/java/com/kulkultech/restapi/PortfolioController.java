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

// TODO: Add @RestController annotation
// TODO: Add @RequestMapping("/api/portfolios") - use plural noun, base path
@RestController
@RequestMapping("/api/portfolios")
public class PortfolioController {
    
    private Map<Long, Portfolio> portfolios = new HashMap<>();
    private Long nextId = 1L;
    
    // TODO: Add @GetMapping to handle GET /api/portfolios
    // This should return all portfolios (collection)
    @GetMapping
    public List<Portfolio> getAllPortfolios() {
        // TODO: Return list of all portfolios
        return new ArrayList<>(portfolios.values());
    }
    
    // TODO: Add @GetMapping("/{id}") to handle GET /api/portfolios/{id}
    // TODO: Add @PathVariable Long id parameter
    // This should return a specific portfolio (single resource)
    @GetMapping("/{id}")
    public Portfolio getPortfolio(@PathVariable Long id) {
        // TODO: Return portfolio by ID or null
        return portfolios.get(id);
    }
    
    // TODO: Add @PostMapping to handle POST /api/portfolios
    // TODO: Add @RequestBody Portfolio portfolio parameter
    // This should create a new portfolio
    @PostMapping
    public Portfolio createPortfolio(@RequestBody Portfolio portfolio) {
        // TODO: Set ID, save, and return created portfolio
        portfolio.setId(nextId++);
        portfolios.put(portfolio.getId(), portfolio);
        return portfolio;
    }
    
    // TODO: Add @PutMapping("/{id}") to handle PUT /api/portfolios/{id}
    // TODO: Add @PathVariable Long id and @RequestBody Portfolio portfolio
    // This should update an entire portfolio (idempotent)
    @PutMapping("/{id}")
    public Portfolio updatePortfolio(@PathVariable Long id, @RequestBody Portfolio portfolio) {
        // TODO: Update portfolio and return updated portfolio
        portfolio.setId(id);
        portfolios.put(id, portfolio);
        return portfolio;
    }
    
    // TODO: Add @DeleteMapping("/{id}") to handle DELETE /api/portfolios/{id}
    // TODO: Add @PathVariable Long id parameter
    // This should delete a portfolio (idempotent)
    @DeleteMapping("/{id}")
    public void deletePortfolio(@PathVariable Long id) {
        // TODO: Remove portfolio from map
        portfolios.remove(id);
    }
}

