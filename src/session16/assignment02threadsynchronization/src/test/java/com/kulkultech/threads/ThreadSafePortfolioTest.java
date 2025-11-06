package session16.assignment02threadsynchronization.src.test.java.com.kulkultech.threads;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Thread-Safe Portfolio Tests
 */
public class ThreadSafePortfolioTest {
    
    private static final boolean USE_SOLUTION = 
        Boolean.parseBoolean(System.getProperty("test.solution", "false"));
    
    private ThreadSafePortfolio createPortfolio() {
        if (USE_SOLUTION) {
            // Use reflection or create wrapper - for simplicity, test directly
            return new ThreadSafePortfolioWrapper(true);
        } else {
            return new ThreadSafePortfolioWrapper(false);
        }
    }
    
    // Wrapper to handle both implementations
    private static class ThreadSafePortfolioWrapper extends ThreadSafePortfolio {
        private final ThreadSafePortfolioSolution solution;
        private final boolean useSolution;
        
        public ThreadSafePortfolioWrapper(boolean useSolution) {
            this.useSolution = useSolution;
            this.solution = useSolution ? new ThreadSafePortfolioSolution() : null;
        }
        
        @Override
        public void addStock(String symbol, int shares) {
            if (useSolution && solution != null) {
                solution.addStock(symbol, shares);
            } else {
                super.addStock(symbol, shares);
            }
        }
        
        @Override
        public void updatePortfolioValue(Map<String, Double> currentPrices) {
            if (useSolution && solution != null) {
                solution.updatePortfolioValue(currentPrices);
            } else {
                super.updatePortfolioValue(currentPrices);
            }
        }
        
        @Override
        public double getTotalValue() {
            if (useSolution && solution != null) {
                return solution.getTotalValue();
            } else {
                return super.getTotalValue();
            }
        }
        
        @Override
        public Map<String, Integer> getHoldings() {
            if (useSolution && solution != null) {
                return solution.getHoldings();
            } else {
                return super.getHoldings();
            }
        }
        
        @Override
        public int getStockCount(String symbol) {
            if (useSolution && solution != null) {
                return solution.getStockCount(symbol);
            } else {
                return super.getStockCount(symbol);
            }
        }
    }
    
    @Test
    public void testSingleThreadAddStock() {
        ThreadSafePortfolio portfolio = createPortfolio();
        
        portfolio.addStock("AAPL", 10);
        portfolio.addStock("AAPL", 5);
        
        assertEquals(15, portfolio.getStockCount("AAPL"));
    }
    
    @Test
    public void testMultipleThreadsAddStock() throws InterruptedException {
        ThreadSafePortfolio portfolio = createPortfolio();
        int numberOfThreads = 10;
        int sharesPerThread = 10;
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        
        for (int i = 0; i < numberOfThreads; i++) {
            executor.submit(() -> {
                try {
                    portfolio.addStock("AAPL", sharesPerThread);
                } finally {
                    latch.countDown();
                }
            });
        }
        
        latch.await(5, TimeUnit.SECONDS);
        executor.shutdown();
        
        // Without synchronization, this might be less than expected due to race conditions
        int expectedShares = numberOfThreads * sharesPerThread;
        assertEquals(expectedShares, portfolio.getStockCount("AAPL"),
            "All shares should be added correctly without race conditions");
    }
    
    @Test
    public void testConcurrentUpdates() throws InterruptedException {
        ThreadSafePortfolio portfolio = createPortfolio();
        Map<String, Double> prices = Map.of("AAPL", 150.0, "GOOGL", 2800.0);
        
        portfolio.addStock("AAPL", 10);
        portfolio.addStock("GOOGL", 5);
        
        int numberOfThreads = 5;
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        
        for (int i = 0; i < numberOfThreads; i++) {
            executor.submit(() -> {
                try {
                    portfolio.updatePortfolioValue(prices);
                } finally {
                    latch.countDown();
                }
            });
        }
        
        latch.await(5, TimeUnit.SECONDS);
        executor.shutdown();
        
        // Total value should be consistent: (10 * 150) + (5 * 2800) = 15500
        double expectedValue = (10 * 150.0) + (5 * 2800.0);
        assertEquals(expectedValue, portfolio.getTotalValue(), 0.01,
            "Portfolio value should be calculated correctly");
    }
    
    @Test
    public void testGetHoldingsReturnsDefensiveCopy() {
        ThreadSafePortfolio portfolio = createPortfolio();
        portfolio.addStock("AAPL", 10);
        
        Map<String, Integer> holdings = portfolio.getHoldings();
        holdings.put("GOOGL", 5); // Try to modify the returned map
        
        // Original portfolio should not be affected
        assertEquals(0, portfolio.getStockCount("GOOGL"),
            "Modifying returned map should not affect original portfolio");
    }
    
    @Test
    public void testThreadSafetyUnderHighLoad() throws InterruptedException {
        ThreadSafePortfolio portfolio = createPortfolio();
        int numberOfThreads = 20;
        int operationsPerThread = 100;
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        
        for (int i = 0; i < numberOfThreads; i++) {
            final int threadId = i;
            executor.submit(() -> {
                try {
                    for (int j = 0; j < operationsPerThread; j++) {
                        portfolio.addStock("STOCK" + (threadId % 5), 1);
                    }
                } finally {
                    latch.countDown();
                }
            });
        }
        
        latch.await(10, TimeUnit.SECONDS);
        executor.shutdown();
        
        // Verify all operations were applied correctly
        int totalExpected = numberOfThreads * operationsPerThread;
        int totalActual = portfolio.getHoldings().values().stream()
            .mapToInt(Integer::intValue).sum();
        
        assertEquals(totalExpected, totalActual,
            "All concurrent operations should be applied correctly");
    }
}

