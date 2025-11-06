/**
 * Transaction Manager - SOLUTION
 */
import java.sql.*;
import java.util.Map;

public class TransactionManagerSolution {
    private final String dbUrl;
    private final String username;
    private final String password;
    
    public TransactionManagerSolution(String dbUrl, String username, String password) {
        this.dbUrl = dbUrl;
        this.username = username;
        this.password = password;
    }
    
    public boolean transferFunds(int fromAccountId, int toAccountId, double amount) {
        Connection conn = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);
            
            // Subtract from source account
            try (PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE accounts SET balance = balance - ? WHERE id = ?")) {
                stmt.setDouble(1, amount);
                stmt.setInt(2, fromAccountId);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected == 0) {
                    conn.rollback();
                    return false;
                }
            }
            
            // Add to destination account
            try (PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE accounts SET balance = balance + ? WHERE id = ?")) {
                stmt.setDouble(1, amount);
                stmt.setInt(2, toAccountId);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected == 0) {
                    conn.rollback();
                    return false;
                }
            }
            
            conn.commit();
            return true;
            
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    System.err.println("Rollback failed: " + rollbackEx.getMessage());
                }
            }
            System.err.println("Transfer failed: " + e.getMessage());
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Error closing connection: " + e.getMessage());
                }
            }
        }
    }
    
    public boolean updateMultiplePrices(Map<String, Double> priceUpdates) {
        String sql = "UPDATE positions SET current_price = ? WHERE stock_symbol = ?";
        
        Connection conn = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);
            
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                for (Map.Entry<String, Double> entry : priceUpdates.entrySet()) {
                    stmt.setDouble(1, entry.getValue());
                    stmt.setString(2, entry.getKey());
                    stmt.addBatch();
                }
                
                int[] results = stmt.executeBatch();
                
                // Check if all updates succeeded
                for (int result : results) {
                    if (result == Statement.EXECUTE_FAILED) {
                        conn.rollback();
                        return false;
                    }
                }
                
                conn.commit();
                return true;
            }
            
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    System.err.println("Rollback failed: " + rollbackEx.getMessage());
                }
            }
            System.err.println("Batch update failed: " + e.getMessage());
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Error closing connection: " + e.getMessage());
                }
            }
        }
    }
    
    public boolean processTradeWithSavepoint(int tradeId, double newPrice) {
        Connection conn = null;
        Savepoint savepoint = null;
        
        try {
            conn = getConnection();
            conn.setAutoCommit(false);
            
            // Create savepoint
            savepoint = conn.setSavepoint("before_update");
            
            // Update trade price
            try (PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE trades SET price = ? WHERE id = ?")) {
                stmt.setDouble(1, newPrice);
                stmt.setInt(2, tradeId);
                int rowsAffected = stmt.executeUpdate();
                
                if (rowsAffected == 0) {
                    conn.rollback(savepoint);
                    return false;
                }
            }
            
            // Validation: price must be positive
            if (newPrice <= 0) {
                conn.rollback(savepoint);
                return false;
            }
            
            conn.commit();
            return true;
            
        } catch (SQLException e) {
            if (conn != null && savepoint != null) {
                try {
                    conn.rollback(savepoint);
                } catch (SQLException rollbackEx) {
                    System.err.println("Rollback to savepoint failed: " + rollbackEx.getMessage());
                }
            }
            System.err.println("Process trade failed: " + e.getMessage());
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.err.println("Error closing connection: " + e.getMessage());
                }
            }
        }
    }
    
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, username, password);
    }
}

