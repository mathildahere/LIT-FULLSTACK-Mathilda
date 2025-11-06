/**
 * Order Queue - Thread Coordination with Wait/Notify
 * 
 * Challenge: Implement a producer-consumer pattern using wait/notify
 * 
 * Your task: Complete the addOrder and processOrder methods to use wait/notify
 * for thread coordination.
 * 
 * Concepts covered:
 * - wait() and notify() / notifyAll()
 * - Producer-Consumer pattern
 * - Thread coordination
 * - Synchronized blocks
 */

package session16.assignment03threadcoordination.src.main.java.com.kulkultech.threads;

import java.util.LinkedList;
import java.util.Queue;

public class OrderQueue {
    private final Queue<Order> orders = new LinkedList<>();
    private final int MAX_CAPACITY = 100;
    private final Object lock = new Object();
    
    /**
     * Producer: Add order to queue
     * TODO: Implement wait/notify pattern
     * - If queue is full, wait until space is available
     * - After adding, notify consumers
     */
    public void addOrder(Order order) throws InterruptedException {
        synchronized (lock) {
            // TODO: Wait if queue is full
            // Hint: while (orders.size() >= MAX_CAPACITY) { lock.wait(); }
            while (orders.size() >= MAX_CAPACITY) {
                lock.wait();
            }

            // TODO: Add order to queue
            orders.add(order);
            System.out.println("New Order Added -> " + order);

            // TODO: Notify waiting consumers
            // Hint: lock.notifyAll();
            lock.notifyAll();
        }
    }
    
    /**
     * Consumer: Process order from queue
     * TODO: Implement wait/notify pattern
     * - If queue is empty, wait until an order is available
     * - After processing, notify producers
     */
    public Order processOrder() throws InterruptedException {
        synchronized (lock) {
            // TODO: Wait if queue is empty
            // Hint: while (orders.isEmpty()) { lock.wait(); }
            while (orders.isEmpty()) {
                lock.wait();
            }

            // TODO: Remove and return order from queue
            // Hint: Order order = orders.poll();
            Order order = orders.poll();
            System.out.println("Processing Order → " + order);
            
            // TODO: Notify waiting producers
            // Hint: lock.notifyAll();
            lock.notifyAll();
            
            return order; // TODO: Return the actual order
        }
    }
    
    public int getQueueSize() {
        synchronized (lock) {
            return orders.size();
        }
    }
}

class Order {
    private String symbol;
    private int quantity;
    private String type;
    
    public Order(String symbol, int quantity, String type) {
        this.symbol = symbol;
        this.quantity = quantity;
        this.type = type;
    }
    
    public String getSymbol() { return symbol; }
    public int getQuantity() { return quantity; }
    public String getType() { return type; }
    
    @Override
    public String toString() {
        return "Order{symbol='" + symbol + "', quantity=" + quantity + ", type='" + type + "'}";
    }
}

