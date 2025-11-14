package com.kulkultech.restapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

// SOLVED: Add @Tag(name = "Portfolio", description = "Portfolio management APIs")
// SOLVED: Add @RestController and @RequestMapping("/api/portfolios")

@Tag(name = "Portfolio", description = "Portfolio management APIs")
@RestController
@RequestMapping("/api/portfolios")
public class PortfolioController {
    
    private Map<Long, Portfolio> portfolios = new HashMap<>();
    private Long nextId = 1L;
    
    // SOLVED: Add @Operation(summary = "Get portfolio by ID", description = "Returns a portfolio by its ID")
    // SOLVED: Add @ApiResponse(responseCode = "200", description = "Portfolio found")
    // SOLVED: Add @ApiResponse(responseCode = "404", description = "Portfolio not found")
    // SOLVED: Add @GetMapping("/{id}")
    @Operation(summary = "Get portfolio by ID", description = "Returns a portfolio by its unique identifier")
    @ApiResponse(responseCode = "200", description = "Portfolio found")
    @ApiResponse(responseCode = "404", description = "Portfolio not found")
    @GetMapping("/{id}")
    public Portfolio getPortfolio(
            // SOLVED: Add @Parameter(description = "Portfolio ID", required = true)
            @Parameter(description = "Portfolio ID", required = true)
            @PathVariable Long id) {
        return portfolios.get(id);
    }
    
    // SOLVED: Add @Operation(summary = "Create new portfolio")
    // SOLVED: Add @ApiResponse(responseCode = "201", description = "Portfolio created")
    // SOLVED: Add @PostMapping
    @Operation(summary = "Create new portfolio")
    @ApiResponse(responseCode = "201", description = "Portfolio created")
    @PostMapping
    public Portfolio createPortfolio(@RequestBody Portfolio portfolio) {
        portfolio.setId(nextId++);
        portfolios.put(portfolio.getId(), portfolio);
        return portfolio;
    }
}

