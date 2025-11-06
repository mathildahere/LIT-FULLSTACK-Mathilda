package com.kulkultech.jdbc;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;


/**
 * Database Connection Tests
 * Uses H2 in-memory database for testing
 */
public class DatabaseConnectionTest {
    
    private static final boolean USE_SOLUTION = 
        Boolean.parseBoolean(System.getProperty("test.solution", "false"));
    
    private static final String H2_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";
    
    private DatabaseConnection createConnection() {
        if (USE_SOLUTION) {
            return new DatabaseConnectionWrapper(true);
        } else {
            return new DatabaseConnectionWrapper(false);
        }
    }
    
    private static class DatabaseConnectionWrapper extends DatabaseConnection {
        private final DatabaseConnectionSolution solution;
        private final boolean useSolution;
        
        public DatabaseConnectionWrapper(boolean useSolution) {
            super(H2_URL, USERNAME, PASSWORD);
            this.useSolution = useSolution;
            this.solution = useSolution ? new DatabaseConnectionSolution(H2_URL, USERNAME, PASSWORD) : null;
        }
        
        @Override
        public Connection getConnection() throws SQLException {
            if (useSolution && solution != null) {
                return solution.getConnection();
            } else {
                return super.getConnection();
            }
        }
        
        @Override
        public boolean testConnection() {
            if (useSolution && solution != null) {
                return solution.testConnection();
            } else {
                return super.testConnection();
            }
        }
        
        @Override
        public String getDatabaseProductName() throws SQLException {
            if (useSolution && solution != null) {
                return solution.getDatabaseProductName();
            } else {
                return super.getDatabaseProductName();
            }
        }
    }
    
    @Test
    public void testGetConnection() throws SQLException {
        DatabaseConnection db = createConnection();
        
        Connection conn = db.getConnection();
        assertNotNull(conn, "Connection should not be null");
        assertFalse(conn.isClosed(), "Connection should be open");
        
        conn.close();
    }
    
    @Test
    public void testConnectionValidity() throws SQLException {
        DatabaseConnection db = createConnection();
        
        assertTrue(db.testConnection(), "Connection should be valid");
    }
    
    @Test
    public void testDatabaseProductName() throws SQLException {
        DatabaseConnection db = createConnection();
        
        String productName = db.getDatabaseProductName();
        assertNotNull(productName, "Product name should not be null");
        assertEquals("H2", productName, "Should be H2 database");
    }
    
    @Test
    public void testTryWithResources() throws SQLException {
        DatabaseConnection db = createConnection();
        
        try (Connection conn = db.getConnection()) {
            assertNotNull(conn);
            assertFalse(conn.isClosed());
        } // Connection should be automatically closed here
        
        // Connection should be closed after try-with-resources
        Connection conn2 = db.getConnection();
        assertFalse(conn2.isClosed()); // New connection should be open
        conn2.close();
    }
}

