package com.kulkultech.restapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "Portfolio", description = "Portfolio management APIs")
@RestController
@RequestMapping("/api/portfolios")
public class PortfolioControllerSolution {
    
    private Map<Long, Portfolio> portfolios = new HashMap<>();
    private Long nextId = 1L;
    
    @Operation(summary = "Get portfolio by ID", description = "Returns a portfolio by its unique identifier")
    @ApiResponse(responseCode = "200", description = "Portfolio found")
    @ApiResponse(responseCode = "404", description = "Portfolio not found")
    @GetMapping("/{id}")
    public Portfolio getPortfolio(
            @Parameter(description = "Portfolio ID", required = true)
            @PathVariable Long id) {
        return portfolios.get(id);
    }
    
    @Operation(summary = "Create new portfolio", description = "Creates a new portfolio with the provided details")
    @ApiResponse(responseCode = "201", description = "Portfolio created successfully")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Portfolio createPortfolio(@RequestBody Portfolio portfolio) {
        portfolio.setId(nextId++);
        portfolios.put(portfolio.getId(), portfolio);
        return portfolio;
    }
}

