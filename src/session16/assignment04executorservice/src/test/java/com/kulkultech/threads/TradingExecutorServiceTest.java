package session16.assignment04executorservice.src.test.java.com.kulkultech.threads;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Trading Executor Service Tests
 */
public class TradingExecutorServiceTest {
    
    private static final boolean USE_SOLUTION = 
        Boolean.parseBoolean(System.getProperty("test.solution", "false"));
    
    private TradingExecutorService createService(int poolSize) {
        if (USE_SOLUTION) {
            return new TradingExecutorServiceWrapper(poolSize, true);
        } else {
            return new TradingExecutorServiceWrapper(poolSize, false);
        }
    }
    
    private static class TradingExecutorServiceWrapper extends TradingExecutorService {
        private final TradingExecutorServiceSolution solution;
        private final boolean useSolution;
        
        public TradingExecutorServiceWrapper(int poolSize, boolean useSolution) {
            super(poolSize);
            this.useSolution = useSolution;
            this.solution = useSolution ? new TradingExecutorServiceSolution(poolSize) : null;
        }
        
        @Override
        public Future<Boolean> executeTradeAsync(Trade trade) {
            if (useSolution && solution != null) {
                return solution.executeTradeAsync(trade);
            } else {
                return super.executeTradeAsync(trade);
            }
        }
        
        @Override
        public List<Future<Boolean>> executeTradesAsync(List<Trade> trades) {
            if (useSolution && solution != null) {
                return solution.executeTradesAsync(trades);
            } else {
                return super.executeTradesAsync(trades);
            }
        }
        
        @Override
        public void shutdown() throws InterruptedException {
            if (useSolution && solution != null) {
                solution.shutdown();
            } else {
                super.shutdown();
            }
        }
        
        @Override
        public ExecutorService getExecutor() {
            if (useSolution && solution != null) {
                return solution.getExecutor();
            } else {
                return super.getExecutor();
            }
        }
    }
    
    @Test
    public void testSingleTradeExecution() throws InterruptedException, ExecutionException, TimeoutException {
        TradingExecutorService service = createService(5);
        
        Trade trade = new Trade("AAPL", 10, 150.0);
        Future<Boolean> future = service.executeTradeAsync(trade);
        
        assertNotNull(future);
        Boolean result = future.get(2, TimeUnit.SECONDS);
        assertTrue(result);
        
        service.shutdown();
    }
    
    @Test
    public void testMultipleTradesConcurrent() throws InterruptedException, ExecutionException, TimeoutException {
        TradingExecutorService service = createService(5);
        
        List<Trade> trades = List.of(
            new Trade("AAPL", 10, 150.0),
            new Trade("GOOGL", 5, 2800.0),
            new Trade("MSFT", 20, 310.0)
        );
        
        List<Future<Boolean>> futures = service.executeTradesAsync(trades);
        
        assertEquals(3, futures.size());
        
        for (Future<Boolean> future : futures) {
            assertTrue(future.get(2, TimeUnit.SECONDS));
        }
        
        service.shutdown();
    }
    
    @Test
    public void testThreadPoolSize() {
        TradingExecutorService service = createService(10);
        
        assertNotNull(service.getExecutor());
        
        try {
            service.shutdown();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    @Test
    public void testShutdownGracefully() throws InterruptedException {
        TradingExecutorService service = createService(5);
        
        service.executeTradeAsync(new Trade("AAPL", 10, 150.0));
        
        service.shutdown();
        
        assertTrue(service.getExecutor().isShutdown());
    }
}

