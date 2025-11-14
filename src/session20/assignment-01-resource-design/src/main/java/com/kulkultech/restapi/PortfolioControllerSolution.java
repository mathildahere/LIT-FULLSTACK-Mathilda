/**
 * Portfolio Controller - SOLUTION
 */

package com.kulkultech.restapi;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/portfolios")
public class PortfolioControllerSolution {
    
    private Map<Long, Portfolio> portfolios = new HashMap<>();
    private Long nextId = 1L;
    
    @GetMapping
    public List<Portfolio> getAllPortfolios() {
        return new ArrayList<>(portfolios.values());
    }
    
    @GetMapping("/{id}")
    public Portfolio getPortfolio(@PathVariable Long id) {
        return portfolios.get(id);
    }
    
    @PostMapping
    public Portfolio createPortfolio(@RequestBody Portfolio portfolio) {
        portfolio.setId(nextId++);
        portfolios.put(portfolio.getId(), portfolio);
        return portfolio;
    }
    
    @PutMapping("/{id}")
    public Portfolio updatePortfolio(@PathVariable Long id, @RequestBody Portfolio portfolio) {
        portfolio.setId(id);
        portfolios.put(id, portfolio);
        return portfolio;
    }
    
    @DeleteMapping("/{id}")
    public void deletePortfolio(@PathVariable Long id) {
        portfolios.remove(id);
    }
}

