/**
 * Thread-Safe Portfolio - Thread Synchronization
 * 
 * Challenge: Make this portfolio class thread-safe using synchronization
 * 
 * Your task: Add synchronization to prevent race conditions when multiple threads
 * access the portfolio simultaneously.
 * 
 * Concepts covered:
 * - synchronized methods
 * - synchronized blocks
 * - race conditions
 * - thread safety
 */

package session16.assignment02threadsynchronization.src.main.java.com.kulkultech.threads;

import java.util.HashMap;
import java.util.Map;

public class ThreadSafePortfolio {
    private final Map<String, Integer> stockHoldings = new HashMap<>();
    private double totalValue = 0.0;

    // Lock object for synchronizing
    private final Object lock = new Object();
    
    // TODO: Make this method thread-safe using synchronized keyword
    public void addStock(String symbol, int shares) {
        // TODO: Add synchronization to prevent race conditions
        // Hint: Use synchronized method or synchronized block
        // Current implementation has a race condition - multiple threads
        // could read, modify, and write simultaneously

        synchronized (lock) {
            Integer currentShares = stockHoldings.getOrDefault(symbol, 0);
            stockHoldings.put(symbol, currentShares + shares);
            System.out.println("Added " + shares + " shares of " + symbol);
        }
    }
    
    // TODO: Make this method thread-safe
    public void updatePortfolioValue(Map<String, Double> currentPrices) {
        // TODO: Add synchronization for this complex operation
        // Hint: Use synchronized block with a lock object

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
    
    // TODO: Make this method thread-safe
    public double getTotalValue() {
        // TODO: Add synchronization to ensure consistent reads
        synchronized (lock) {
            return totalValue;
        }
    }
    
    // TODO: Make this method thread-safe
    public Map<String, Integer> getHoldings() {
        // TODO: Return a defensive copy to prevent external modification
        // Hint: return new HashMap<>(stockHoldings);
        synchronized (lock) {
            return new HashMap<>(stockHoldings);
        }
    }
    
    public int getStockCount(String symbol) {
        synchronized (lock) {
            return stockHoldings.getOrDefault(symbol, 0);
        }
    }
}

