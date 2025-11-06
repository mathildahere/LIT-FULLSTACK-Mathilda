/**
 * Trading Thread - Basic Thread Creation
 * 
 * Challenge: Create a thread that processes trading operations
 * 
 * Your task: Complete this class to extend Thread and implement the run() method
 * to make the tests pass.
 * 
 * Concepts covered:
 * - Extending Thread class
 * - Implementing run() method
 * - Thread lifecycle (start, run, termination)
 * - Thread.sleep() for simulation
 */

package session16.assignment01basicthreads.src.main.java.com.kulkultech.threads;

public class TradingThread extends Thread {
    protected String stockSymbol;
    protected double price;
    protected boolean processed = false;
    
    public TradingThread(String stockSymbol, double price) {
        // TODO: Call super constructor and initialize fields
        // Hint: Set thread name to something meaningful like "TradingThread-" + stockSymbol
        // Use: super("TradingThread-" + stockSymbol);
        super("TradingThread-" + stockSymbol);
        this.stockSymbol = stockSymbol;
        this.price = price;
    }
    
    // Constructor that allows setting thread name (used by solution)
    protected TradingThread(String name) {
        super(name);
    }
    
    @Override
    public void run() {
        // TODO: Implement the run method
        // 1. Print a message: "Processing trade for " + stockSymbol + " at $" + price
        // 2. Simulate processing time by sleeping for 100 milliseconds
        //    Hint: Use Thread.sleep(100) and handle InterruptedException
        // 3. Set processed = true
        // 4. Print a message: "Trade completed for " + stockSymbol
        System.out.println("Processing trade for " + stockSymbol + " at $" + price);

        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            return;
        }

        processed = true;
        System.out.println("Trade completed for  " + stockSymbol);
    }
    
    public boolean isProcessed() {
        return processed;
    }
    
    public String getStockSymbol() {
        return stockSymbol;
    }
    
    public double getPrice() {
        return price;
    }
}
