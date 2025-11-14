package com.kulkultech.restapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/portfolios")
public class PortfolioControllerSolution {
    
    private Map<Long, Portfolio> portfolios = new HashMap<>();
    private Long nextId = 1L;
    
    @GetMapping("/{id}")
    public Portfolio getPortfolio(@PathVariable Long id) {
        Portfolio portfolio = portfolios.get(id);
        if (portfolio == null) {
            throw new PortfolioNotFoundException(id);
        }
        return portfolio;
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Portfolio createPortfolio(@RequestBody Portfolio portfolio) {
        portfolio.setId(nextId++);
        portfolios.put(portfolio.getId(), portfolio);
        return portfolio;
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePortfolio(@PathVariable Long id) {
        if (!portfolios.containsKey(id)) {
            throw new PortfolioNotFoundException(id);
        }
        portfolios.remove(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

