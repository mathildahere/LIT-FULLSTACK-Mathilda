/**
 * Trading Executor Service - SOLUTION
 */

package session16.assignment;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class TradingExecutorServiceSolution {
    private ExecutorService executor;
    
    public TradingExecutorServiceSolution(int threadPoolSize) {
        this.executor = Executors.newFixedThreadPool(threadPoolSize);
    }
    
    public Future<Boolean> executeTradeAsync(Trade trade) {
        return executor.submit(() -> processTrade(trade));
    }
    
    public List<Future<Boolean>> executeTradesAsync(List<Trade> trades) {
        return trades.stream()
            .map(trade -> executor.submit(() -> processTrade(trade)))
            .collect(Collectors.toList());
    }
    
    private Boolean processTrade(Trade trade) {
        try {
            Thread.sleep(100);
            System.out.println("Processed trade: " + trade.getSymbol() + " - " + trade.getQuantity() + " shares");
            return true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }
    
    public void shutdown() throws InterruptedException {
        executor.shutdown();
        if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
            executor.shutdownNow();
        }
    }
    
    public ExecutorService getExecutor() {
        return executor;
    }
}

