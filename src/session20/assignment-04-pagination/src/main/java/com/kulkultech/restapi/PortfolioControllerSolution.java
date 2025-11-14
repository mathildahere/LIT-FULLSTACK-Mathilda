package com.kulkultech.restapi;

import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/portfolios")
public class PortfolioControllerSolution {
    
    private final PortfolioRepository portfolioRepository;
    
    public PortfolioControllerSolution(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }
    
    @GetMapping
    public Page<Portfolio> getPortfolios(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return portfolioRepository.findAll(pageable);
    }
}

