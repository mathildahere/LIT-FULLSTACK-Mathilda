/**
 * Transaction Manager - JDBC Transaction Management
 * 
 * Challenge: Implement transaction management with commit and rollback
 * 
 * Your task: Use Connection transaction methods to ensure ACID properties
 * 
 * Concepts covered:
 * - setAutoCommit(false)
 * - commit()
 * - rollback()
 * - Savepoints
 * - Transaction isolation levels
 */

import java.sql.*;
import java.util.*;

public class TransactionManager {
    private final String dbUrl;
    private final String username;
    private final String password;
    
    public TransactionManager(String dbUrl, String username, String password) {
        this.dbUrl = dbUrl;
        this.username = username;
        this.password = password;
    }
    
    /**
     * Transfer funds between accounts atomically
     */
    public boolean transferFunds(int fromAccountId, int toAccountId, double amount) {
        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            try (PreparedStatement statement = connection.prepareStatement(
                    "UPDATE accounts SET balance = balance - ? WHERE id = ?")) {
                statement.setDouble(1, amount);
                statement.setInt(2, fromAccountId);
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected == 0) {
                    connection.rollback();
                    return false;
                }
            }

            try (PreparedStatement statement = connection.prepareStatement(
                    "UPDATE accounts SET balance = balance + ? WHERE id = ?")) {
                statement.setDouble(1, amount);
                statement.setInt(2, toAccountId);
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected == 0) {
                    connection.rollback();
                    return false;
                }
            }

            connection.commit();
            return true;
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackEx) {
                    System.err.println("Rollback failed: " + rollbackEx.getMessage());
                }
            }
            System.err.println("Transfer failed: " + e.getMessage());
            return false;
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Error closing connection: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Batch update with transaction
     */
    public boolean updateMultiplePrices(java.util.Map<String, Double> priceUpdates) {
        String sql = "UPDATE positions SET current_price = ? WHERE stock_symbol = ?";

        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                for (Map.Entry<String, Double> entry : priceUpdates.entrySet()) {
                    statement.setDouble(1, entry.getValue());
                    statement.setString(2, entry.getKey());
                    statement.addBatch();
                }

                int[] results = statement.executeBatch();

                // Check if all updates succeeded
                for (int result : results) {
                    if (result == Statement.EXECUTE_FAILED) {
                        connection.rollback();
                        return false;
                    }
                }

                connection.commit();
                return true;
            }

        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackEx) {
                    System.err.println("Rollback failed: " + rollbackEx.getMessage());
                }
            }
            System.err.println("Batch update failed: " + e.getMessage());
            return false;
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Error closing connection: " + e.getMessage());
                }
            }
        }
    }
    
    /**
     * Transaction with savepoint
     */
    public boolean processTradeWithSavepoint(int tradeId, double newPrice) {
        Connection connection = null;
        Savepoint savepoint = null;

        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            savepoint = connection.setSavepoint("before_update");

            try (PreparedStatement statement = connection.prepareStatement(
                    "UPDATE trades SET price = ? WHERE id = ?")) {
                statement.setDouble(1, newPrice);
                statement.setInt(2, tradeId);
                int rowsAffected = statement.executeUpdate();

                if (rowsAffected == 0) {
                    connection.rollback(savepoint);
                    return false;
                }
            }

            if (newPrice <= 0) {
                connection.rollback(savepoint);
                return false;
            }

            connection.commit();
            return true;

        } catch (SQLException e) {
            if (connection != null && savepoint != null) {
                try {
                    connection.rollback(savepoint);
                } catch (SQLException rollbackEx) {
                    System.err.println("Rollback to savepoint failed: " + rollbackEx.getMessage());
                }
            }
            System.err.println("Process trade failed: " + e.getMessage());
            return false;
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
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

