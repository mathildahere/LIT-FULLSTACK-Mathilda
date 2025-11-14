package com.kulkultech.restapi;

public class PortfolioNotFoundException extends RuntimeException {
    public PortfolioNotFoundException(Long id) {
        super("Portfolio not found with id: " + id);
    }
}

