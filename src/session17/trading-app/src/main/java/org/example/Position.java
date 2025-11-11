package org.example;

public class Position {
    private String symbol;
    private int shares;
    private double averagePrice;
    private double currentPrice;

    public Position(String symbol, int shares, double averagePrice, double currentPrice) {
        this.symbol = symbol;
        this.shares = shares;
        this.averagePrice = averagePrice;
        this.currentPrice = currentPrice;
    }

    public String getSymbol() { return symbol; }
    public int getShares() { return shares; }
    public double getAveragePrice() { return averagePrice; }
    public double getCurrentPrice() { return currentPrice; }

    public double getCurrentValue() {
        return shares * currentPrice;
    }

    public double getProfitLoss() {
        return shares * (currentPrice - averagePrice);
    }
}

