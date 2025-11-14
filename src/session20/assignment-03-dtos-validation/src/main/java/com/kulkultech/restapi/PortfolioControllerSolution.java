package com.kulkultech.restapi;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/portfolios")
public class PortfolioControllerSolution {
    
    private Map<Long, PortfolioResponse> portfolios = new HashMap<>();
    private Long nextId = 1L;
    
    @PostMapping
    public PortfolioResponse createPortfolio(@Valid @RequestBody PortfolioRequestSolution request) {
        PortfolioResponse response = new PortfolioResponse();
        response.setId(nextId++);
        response.setName(request.getName());
        response.setBalance(request.getBalance());
        portfolios.put(response.getId(), response);
        return response;
    }
}

