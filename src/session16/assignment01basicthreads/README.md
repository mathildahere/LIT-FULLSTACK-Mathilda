# Assignment 01: Basic Thread Creation

## Learning Objectives
- Understand how to create threads by extending the `Thread` class
- Learn to implement the `run()` method
- Understand thread lifecycle (NEW, RUNNABLE, TERMINATED)
- Practice using `Thread.sleep()` for simulation
- Handle `InterruptedException` properly

## The Challenge

Create a `TradingThread` class that extends `Thread` and processes trading operations concurrently. Each thread should:
1. Process a trade for a specific stock symbol at a given price
2. Simulate processing time
3. Track completion status

### What You'll Learn

1. **Extending Thread Class**: 
   - Create a class that extends `Thread`
   - Override the `run()` method to define what the thread does
   - Call `super()` constructor if needed

2. **Thread Lifecycle**:
   - **NEW**: Thread created but not started
   - **RUNNABLE**: Thread is executing (started)
   - **TERMINATED**: Thread has completed execution

3. **Thread.sleep()**: 
   - Pauses execution for a specified time
   - Must handle `InterruptedException`
   - Always restore interrupt status: `Thread.currentThread().interrupt()`

4. **Starting Threads**:
   - Call `thread.start()` to begin execution (not `run()` directly!)
   - Use `thread.join()` to wait for completion

### The Solution Pattern

```java
public class TradingThread extends Thread {
    private String stockSymbol;
    private double price;
    private boolean processed = false;
    
    public TradingThread(String stockSymbol, double price) {
        super("TradingThread-" + stockSymbol);  // Set thread name
        this.stockSymbol = stockSymbol;
        this.price = price;
    }
    
    @Override
    public void run() {
        System.out.println("Processing trade for " + stockSymbol + " at $" + price);
        
        try {
            Thread.sleep(100);  // Simulate processing
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();  // Restore interrupt status
            return;
        }
        
        processed = true;
        System.out.println("Trade completed for " + stockSymbol);
    }
}
```

### Why Use Threads?

In trading systems, multiple operations happen simultaneously:
- Processing multiple stock trades
- Real-time price updates
- Risk management checks
- Portfolio calculations

Threads allow these operations to run concurrently, improving system responsiveness.

## Running the Tests

### Step 1: Run Tests (Will Fail Initially)
```bash
mvn test
```

### Step 2: Implement Solution
Complete the `TradingThread.java` file to make tests pass.

### Step 3: Verify Solution
```bash
# Test your implementation
mvn test

# Test with solution (should pass)
mvn test -Dtest.solution=true
```

## Test Cases

Your implementation should:
- ✅ Create threads with stock symbol and price
- ✅ Execute the run() method when started
- ✅ Track completion status
- ✅ Handle multiple threads concurrently
- ✅ Properly set thread name

## Key Takeaways

- Always extend `Thread` or implement `Runnable` for concurrent execution
- Override `run()` to define thread behavior
- Use `start()` to begin execution, not `run()` directly
- Handle `InterruptedException` properly
- Use `join()` to wait for thread completion

## Resources

- [Java Thread Documentation](https://docs.oracle.com/javase/11/docs/api/java/lang/Thread.html)
- [Java Concurrency Tutorial](https://docs.oracle.com/javase/tutorial/essential/concurrency/)
