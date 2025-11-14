package com.kulkultech.restapi;

import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

// SOLVED: Add @RestController and @RequestMapping("/api/portfolios")

@RestController
@RequestMapping("/api/portfolios")
public class PortfolioController {
    
    // SOLVED: Inject PortfolioRepository using constructor injection
    private final PortfolioRepository portfolioRepository;

    public PortfolioController(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }
    
    // SOLVED: Add @GetMapping
    // SOLVED: Add @RequestParam parameters with defaults:
    //   - int page (defaultValue = "0")
    //   - int size (defaultValue = "10")
    //   - String sortBy (defaultValue = "id")
    // SOLVED: Return type should be Page<Portfolio>
    @GetMapping
    public Page<Portfolio> getPortfolios(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size,
                                         @RequestParam(defaultValue = "id") String sortBy/* SOLVED: Add parameters */) {
        // SOLVED: Create Pageable using PageRequest.of(page, size, Sort.by(sortBy))
        // SOLVED: Call repository.findAll(pageable)
        // SOLVED: Return Page result
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return portfolioRepository.findAll(pageable);
    }
}

