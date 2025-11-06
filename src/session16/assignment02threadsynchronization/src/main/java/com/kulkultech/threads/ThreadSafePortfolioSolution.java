/**
 * Thread-Safe Portfolio - SOLUTION
 * 
 * This file contains the solution to make all tests pass.
 */

package session16.assignment02threadsynchronization.src.main.java.com.kulkultech.threads;

import java.util.HashMap;
import java.util.Map;

public class ThreadSafePortfolioSolution {
    private final Map<String, Integer> stockHoldings = new HashMap<>();
    private final Object lock = new Object();
    private double totalValue = 0.0;
    
    public synchronized void addStock(String symbol, int shares) {
        Integer currentShares = stockHoldings.getOrDefault(symbol, 0);
        stockHoldings.put(symbol, currentShares + shares);
        System.out.println("Added " + shares + " shares of " + symbol);
    }
    
    public void updatePortfolioValue(Map<String, Double> currentPrices) {
        synchronized (lock) {
            double newTotalValue = 0.0;
            for (Map.Entry<String, Integer> entry : stockHoldings.entrySet()) {
                String symbol = entry.getKey();
                int shares = entry.getValue();
                double price = currentPrices.getOrDefault(symbol, 0.0);
                newTotalValue += shares * price;
            }
            this.totalValue = newTotalValue;
            System.out.println("Portfolio value updated: $" + String.format("%.2f", totalValue));
        }
    }
    
    public synchronized double getTotalValue() {
        return totalValue;
    }
    
    public synchronized Map<String, Integer> getHoldings() {
        return new HashMap<>(stockHoldings); // Defensive copy
    }
    
    public synchronized int getStockCount(String symbol) {
        return stockHoldings.getOrDefault(symbol, 0);
    }
}
