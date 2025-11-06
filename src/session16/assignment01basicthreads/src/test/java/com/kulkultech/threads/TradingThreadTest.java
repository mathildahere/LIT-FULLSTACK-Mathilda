package session16.assignment01basicthreads.src.test.java.com.kulkultech.threads;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

/**
 * Trading Thread Tests
 * 
 * Switch between implementation and solution based on test.solution system property
 */
public class TradingThreadTest {
    
    private static final boolean USE_SOLUTION = 
        Boolean.parseBoolean(System.getProperty("test.solution", "false"));
    
    private TradingThread createThread(String symbol, double price) {
        if (USE_SOLUTION) {
            // Create solution instance but cast to base type for testing
            TradingThreadSolution solution = new TradingThreadSolution(symbol, price);
            return solution;
        } else {
            return new TradingThread(symbol, price);
        }
    }
    
    @Test
    public void testThreadCreation() {
        TradingThread thread = createThread("AAPL", 150.25);
        
        assertNotNull(thread, "Thread should be created");
        assertEquals("AAPL", thread.getStockSymbol());
        assertEquals(150.25, thread.getPrice(), 0.01);
        assertFalse(thread.isProcessed(), "Thread should not be processed initially");
    }
    
    @Test
    public void testThreadExecution() throws InterruptedException {
        TradingThread thread = createThread("GOOGL", 2850.00);
        
        assertFalse(thread.isProcessed(), "Should not be processed before start");
        
        thread.start();
        thread.join(1000); // Wait up to 1 second for completion
        
        assertTrue(thread.isProcessed(), "Thread should be processed after execution");
        assertFalse(thread.isAlive(), "Thread should be terminated after completion");
    }
    
    @Test
    public void testMultipleThreads() throws InterruptedException {
        TradingThread appleTrade = createThread("AAPL", 150.25);
        TradingThread googleTrade = createThread("GOOGL", 2850.00);
        TradingThread microsoftTrade = createThread("MSFT", 310.50);
        
        // Start all threads
        appleTrade.start();
        googleTrade.start();
        microsoftTrade.start();
        
        // Wait for all to complete
        appleTrade.join(1000);
        googleTrade.join(1000);
        microsoftTrade.join(1000);
        
        // Verify all completed
        assertTrue(appleTrade.isProcessed(), "Apple trade should be processed");
        assertTrue(googleTrade.isProcessed(), "Google trade should be processed");
        assertTrue(microsoftTrade.isProcessed(), "Microsoft trade should be processed");
    }
    
    @Test
    public void testThreadName() {
        TradingThread thread = createThread("AAPL", 150.25);
        
        // The thread name should be set (either default or custom)
        String threadName = thread.getName();
        assertNotNull(threadName, "Thread should have a name");
        assertFalse(threadName.isEmpty(), "Thread name should not be empty");
    }
    
    @Test
    public void testThreadState() throws InterruptedException {
        TradingThread thread = createThread("TSLA", 250.75);
        
        assertEquals(Thread.State.NEW, thread.getState(), 
            "Thread should be in NEW state before start");
        
        thread.start();
        
        // Thread should transition from NEW to RUNNABLE/TIMED_WAITING/TERMINATED
        // TIMED_WAITING occurs when thread.sleep() is called
        // In CI environments, threads might complete very quickly
        // So we check multiple valid states
        Thread.State state = thread.getState();
        assertTrue(state == Thread.State.RUNNABLE || 
                   state == Thread.State.TIMED_WAITING || 
                   state == Thread.State.TERMINATED,
            "Thread should be running, waiting, or terminated after start, but was: " + state);
        
        // Wait for thread to complete (with timeout)
        thread.join(1000);
        
        // Verify final state
        assertEquals(Thread.State.TERMINATED, thread.getState(),
            "Thread should be terminated after completion");
        
        // Verify it was processed
        assertTrue(thread.isProcessed(), "Thread should have been processed");
    }
}
