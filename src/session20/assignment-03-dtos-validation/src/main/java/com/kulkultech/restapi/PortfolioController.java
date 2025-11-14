package com.kulkultech.restapi;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/portfolios")
public class PortfolioController {
    
    private Map<Long, PortfolioResponse> portfolios = new HashMap<>();
    private Long nextId = 1L;
    
    // SOLVED: Add @PostMapping
    // SOLVED: Add @Valid annotation before @RequestBody
    // SOLVED: Change parameter type to PortfolioRequest
    // SOLVED: Change return type to PortfolioResponse
    @PostMapping
    public PortfolioResponse createPortfolio(@Valid @RequestBody PortfolioRequest request/* SOLVED: Add @Valid @RequestBody PortfolioRequest request */) {
        // SOLVED: Convert PortfolioRequest to PortfolioResponse
        // SOLVED: Set ID, save, and return
        PortfolioResponse response = new PortfolioResponse();
        response.setId(nextId++);
        response.setName(request.getName());
        response.setBalance(request.getBalance());
        portfolios.put(response.getId(), response);
        return response;
    }
}

