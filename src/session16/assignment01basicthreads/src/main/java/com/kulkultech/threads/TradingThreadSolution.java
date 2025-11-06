/**
 * Trading Thread - SOLUTION
 * 
 * This file contains the solution to make all tests pass.
 * Try not to look at this until you've attempted the challenge!
 */

package session16.assignment01basicthreads.src.main.java.com.kulkultech.threads;

public class TradingThreadSolution extends TradingThread {
    
    public TradingThreadSolution(String stockSymbol, double price) {
        super("TradingThread-" + stockSymbol); // Call protected constructor
        this.stockSymbol = stockSymbol;
        this.price = price;
    }
    
    @Override
    public void run() {
        System.out.println("Processing trade for " + stockSymbol + " at $" + price);
        
        try {
            Thread.sleep(100); // Simulate processing time
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
        
        processed = true;
        System.out.println("Trade completed for " + stockSymbol);
    }
}
