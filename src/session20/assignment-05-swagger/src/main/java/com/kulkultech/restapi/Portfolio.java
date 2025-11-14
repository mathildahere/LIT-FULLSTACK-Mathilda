package com.kulkultech.restapi;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Portfolio entity")
public class Portfolio {
    @Schema(description = "Portfolio unique identifier", example = "1")
    private Long id;
    
    @Schema(description = "Portfolio name", example = "Tech Portfolio")
    private String name;
    
    @Schema(description = "Portfolio balance", example = "50000.00")
    private Double balance;
    
    public Portfolio() {}
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public Double getBalance() { return balance; }
    public void setBalance(Double balance) { this.balance = balance; }
}

