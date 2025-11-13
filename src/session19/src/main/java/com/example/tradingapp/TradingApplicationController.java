package com.example.tradingapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TradingApplicationController {

    @GetMapping("/test")
    public String test() {
        return "Test TradingApp";
    }
}
