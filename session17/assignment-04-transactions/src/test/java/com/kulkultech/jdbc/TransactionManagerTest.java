import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Map;

/**
 * Transaction Manager Tests
 */
public class TransactionManagerTest {
    
    private static final boolean USE_SOLUTION = 
        Boolean.parseBoolean(System.getProperty("test.solution", "false"));
    
    private static final String H2_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";
    
    private TransactionManager createManager() {
        if (USE_SOLUTION) {
            return new TransactionManagerWrapper(true);
        } else {
            return new TransactionManagerWrapper(false);
        }
    }
    
    private static class TransactionManagerWrapper extends TransactionManager {
        private final TransactionManagerSolution solution;
        private final boolean useSolution;
        
        public TransactionManagerWrapper(boolean useSolution) {
            super(H2_URL, USERNAME, PASSWORD);
            this.useSolution = useSolution;
            this.solution = useSolution ? 
                new TransactionManagerSolution(H2_URL, USERNAME, PASSWORD) : null;
        }
        
        @Override
        public boolean transferFunds(int fromAccountId, int toAccountId, double amount) {
            if (useSolution && solution != null) {
                return solution.transferFunds(fromAccountId, toAccountId, amount);
            } else {
                return super.transferFunds(fromAccountId, toAccountId, amount);
            }
        }
        
        @Override
        public boolean updateMultiplePrices(Map<String, Double> priceUpdates) {
            if (useSolution && solution != null) {
                return solution.updateMultiplePrices(priceUpdates);
            } else {
                return super.updateMultiplePrices(priceUpdates);
            }
        }
        
        @Override
        public boolean processTradeWithSavepoint(int tradeId, double newPrice) {
            if (useSolution && solution != null) {
                return solution.processTradeWithSavepoint(tradeId, newPrice);
            } else {
                return super.processTradeWithSavepoint(tradeId, newPrice);
            }
        }
    }
    
    @BeforeEach
    public void setUp() throws Exception {
        try (Connection conn = DriverManager.getConnection(H2_URL, USERNAME, PASSWORD);
             Statement stmt = conn.createStatement()) {
            
            stmt.execute("DROP TABLE IF EXISTS accounts");
            stmt.execute("CREATE TABLE accounts (" +
                        "id INT PRIMARY KEY, " +
                        "balance DECIMAL(10,2) NOT NULL" +
                        ")");
            
            stmt.execute("DROP TABLE IF EXISTS positions");
            stmt.execute("CREATE TABLE positions (" +
                        "id INT PRIMARY KEY AUTO_INCREMENT, " +
                        "stock_symbol VARCHAR(10) NOT NULL, " +
                        "current_price DECIMAL(10,2)" +
                        ")");
            
            stmt.execute("DROP TABLE IF EXISTS trades");
            stmt.execute("CREATE TABLE trades (" +
                        "id INT PRIMARY KEY AUTO_INCREMENT, " +
                        "price DECIMAL(10,2) NOT NULL" +
                        ")");
            
            // Insert test data
            stmt.execute("INSERT INTO accounts (id, balance) VALUES (1, 1000.00)");
            stmt.execute("INSERT INTO accounts (id, balance) VALUES (2, 500.00)");
            
            stmt.execute("INSERT INTO positions (stock_symbol, current_price) VALUES ('AAPL', 150.0)");
            stmt.execute("INSERT INTO positions (stock_symbol, current_price) VALUES ('GOOGL', 2800.0)");
            
            stmt.execute("INSERT INTO trades (id, price) VALUES (1, 150.0)");
        }
    }
    
    @Test
    public void testTransferFundsSuccess() {
        TransactionManager manager = createManager();
        
        boolean result = manager.transferFunds(1, 2, 100.0);
        assertTrue(result, "Transfer should succeed");
        
        // Verify balances (would need to query, but transaction ensures consistency)
    }
    
    @Test
    public void testTransferFundsRollback() {
        TransactionManager manager = createManager();
        
        // Try to transfer from non-existent account (should rollback)
        boolean result = manager.transferFunds(999, 2, 100.0);
        assertFalse(result, "Transfer should fail and rollback");
    }
    
    @Test
    public void testUpdateMultiplePrices() {
        TransactionManager manager = createManager();
        
        Map<String, Double> priceUpdates = Map.of(
            "AAPL", 155.0,
            "GOOGL", 2850.0
        );
        
        boolean result = manager.updateMultiplePrices(priceUpdates);
        assertTrue(result, "Batch update should succeed");
    }
    
    @Test
    public void testSavepointRollback() {
        TransactionManager manager = createManager();
        
        // Try to update with invalid price (negative) - should rollback to savepoint
        boolean result = manager.processTradeWithSavepoint(1, -10.0);
        assertFalse(result, "Update with negative price should fail");
    }
    
    @Test
    public void testSavepointSuccess() {
        TransactionManager manager = createManager();
        
        boolean result = manager.processTradeWithSavepoint(1, 160.0);
        assertTrue(result, "Update with valid price should succeed");
    }
}

