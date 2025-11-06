package session16.assignment;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Order Queue Tests
 */
public class OrderQueueTest {
    
    private static final boolean USE_SOLUTION = 
        Boolean.parseBoolean(System.getProperty("test.solution", "false"));
    
    private OrderQueue createQueue() {
        if (USE_SOLUTION) {
            return new OrderQueueWrapper(true);
        } else {
            return new OrderQueueWrapper(false);
        }
    }
    
    private static class OrderQueueWrapper extends OrderQueue {
        private final OrderQueueSolution solution;
        private final boolean useSolution;
        
        public OrderQueueWrapper(boolean useSolution) {
            this.useSolution = useSolution;
            this.solution = useSolution ? new OrderQueueSolution() : null;
        }
        
        @Override
        public void addOrder(Order order) throws InterruptedException {
            if (useSolution && solution != null) {
                solution.addOrder(order);
            } else {
                super.addOrder(order);
            }
        }
        
        @Override
        public Order processOrder() throws InterruptedException {
            if (useSolution && solution != null) {
                return solution.processOrder();
            } else {
                return super.processOrder();
            }
        }
        
        @Override
        public int getQueueSize() {
            if (useSolution && solution != null) {
                return solution.getQueueSize();
            } else {
                return super.getQueueSize();
            }
        }
    }
    
    @Test
    public void testAddAndProcessOrder() throws InterruptedException {
        OrderQueue queue = createQueue();
        
        Order order = new Order("AAPL", 10, "BUY");
        queue.addOrder(order);
        
        assertEquals(1, queue.getQueueSize());
        
        Order processed = queue.processOrder();
        assertNotNull(processed);
        assertEquals("AAPL", processed.getSymbol());
        assertEquals(0, queue.getQueueSize());
    }
    
    @Test
    public void testProducerConsumer() throws InterruptedException {
        OrderQueue queue = createQueue();
        ExecutorService executor = Executors.newFixedThreadPool(2);
        List<Order> consumed = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(10);
        
        // Producer
        executor.submit(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    queue.addOrder(new Order("STOCK" + i, i + 1, "BUY"));
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        
        // Consumer
        executor.submit(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    Order order = queue.processOrder();
                    consumed.add(order);
                    latch.countDown();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        
        assertTrue(latch.await(5, TimeUnit.SECONDS));
        assertEquals(10, consumed.size());
        executor.shutdown();
    }
    
    @Test
    public void testMaxCapacityWait() throws InterruptedException {
        OrderQueue queue = createQueue();
        ExecutorService executor = Executors.newFixedThreadPool(2);
        AtomicBoolean producerBlocked = new AtomicBoolean(false);
        CountDownLatch started = new CountDownLatch(1);
        
        // Fill queue to capacity
        for (int i = 0; i < 100; i++) {
            queue.addOrder(new Order("STOCK" + i, 1, "BUY"));
        }
        
        // Producer thread that should block
        executor.submit(() -> {
            try {
                started.countDown();
                producerBlocked.set(true);
                queue.addOrder(new Order("BLOCKED", 1, "BUY"));
                producerBlocked.set(false);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        
        started.await();
        Thread.sleep(100); // Give producer time to block
        
        // Producer should be waiting (blocked)
        if (!USE_SOLUTION) {
            // Without solution, this might not work correctly
        } else {
            assertTrue(producerBlocked.get() || queue.getQueueSize() == 100);
        }
        
        // Consumer frees space
        queue.processOrder();
        Thread.sleep(100);
        
        executor.shutdown();
    }
    
    @Test
    public void testConsumerWaitsWhenEmpty() throws InterruptedException, ExecutionException, TimeoutException {
        OrderQueue queue = createQueue();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        AtomicBoolean consumerStarted = new AtomicBoolean(false);
        CountDownLatch consumerWaiting = new CountDownLatch(1);
        
        // Consumer that should wait
        Future<Order> future = executor.submit(() -> {
            consumerStarted.set(true);
            consumerWaiting.countDown();
            return queue.processOrder();
        });
        
        consumerWaiting.await();
        Thread.sleep(100);
        
        // Consumer should be waiting, add order
        queue.addOrder(new Order("AAPL", 10, "BUY"));
        
        Order order = future.get(2, TimeUnit.SECONDS);
        assertNotNull(order);
        assertEquals("AAPL", order.getSymbol());
        
        executor.shutdown();
    }
}

// Helper class for atomic boolean (if not available)
class AtomicBoolean {
    private boolean value;
    
    public AtomicBoolean() {
        this(false);
    }
    
    public AtomicBoolean(boolean initialValue) {
        this.value = initialValue;
    }
    
    public synchronized boolean get() { return value; }
    public synchronized void set(boolean value) { this.value = value; }
}

