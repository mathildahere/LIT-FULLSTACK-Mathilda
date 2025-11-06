/**
 * Order Queue - SOLUTION
 */

package session16.assignment03threadcoordination.src.main.java.com.kulkultech.threads;

import java.util.LinkedList;
import java.util.Queue;

public class OrderQueueSolution {
    private final Queue<Order> orders = new LinkedList<>();
    private final int MAX_CAPACITY = 100;
    private final Object lock = new Object();
    
    public void addOrder(Order order) throws InterruptedException {
        synchronized (lock) {
            while (orders.size() >= MAX_CAPACITY) {
                lock.wait();
            }
            
            orders.add(order);
            lock.notifyAll();
        }
    }
    
    public Order processOrder() throws InterruptedException {
        synchronized (lock) {
            while (orders.isEmpty()) {
                lock.wait();
            }
            
            Order order = orders.poll();
            lock.notifyAll();
            return order;
        }
    }
    
    public int getQueueSize() {
        synchronized (lock) {
            return orders.size();
        }
    }
}

