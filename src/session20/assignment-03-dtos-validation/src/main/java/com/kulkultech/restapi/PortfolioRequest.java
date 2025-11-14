package com.kulkultech.restapi;

import jakarta.validation.constraints.*;

// SOLVED: Add validation annotations
// @NotBlank for name (with message "Name is required")
// @Size(min = 3, max = 100) for name
// @NotNull for balance (with message "Balance is required")
// @DecimalMin(value = "0.0", message = "Balance must be positive") for balance

public class PortfolioRequest {
    // SOLVED: Add @NotBlank and @Size annotations
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 100)
    private String name;
    
    // SOLVED: Add @NotNull and @DecimalMin annotations
    @NotNull(message = "Balance is required")
    @DecimalMin(value = "0.0", message = "Balance must be positive")
    private Double balance;
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public Double getBalance() { return balance; }
    public void setBalance(Double balance) { this.balance = balance; }
}

