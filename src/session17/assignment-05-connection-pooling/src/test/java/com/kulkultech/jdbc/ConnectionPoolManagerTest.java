package com.kulkultech.jdbc;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Connection Pool Manager Tests
 */
public class ConnectionPoolManagerTest {
    
    private static final boolean USE_SOLUTION = 
        Boolean.parseBoolean(System.getProperty("test.solution", "false"));
    
    private static final String H2_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";
    
    private ConnectionPoolManager createManager() {
        if (USE_SOLUTION) {
            return new ConnectionPoolManagerWrapper(true);
        } else {
            return new ConnectionPoolManagerWrapper(false);
        }
    }
    
    private static class ConnectionPoolManagerWrapper extends ConnectionPoolManager {
        private final ConnectionPoolManagerSolution solution;
        private final boolean useSolution;
        
        public ConnectionPoolManagerWrapper(boolean useSolution) {
            this.useSolution = useSolution;
            this.solution = useSolution ? new ConnectionPoolManagerSolution() : null;
        }
        
        @Override
        public void createConnectionPool(String dbUrl, String username, String password, int maxPoolSize) {
            if (useSolution && solution != null) {
                solution.createConnectionPool(dbUrl, username, password, maxPoolSize);
            } else {
                super.createConnectionPool(dbUrl, username, password, maxPoolSize);
            }
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
        public javax.sql.DataSource getDataSource() {
            if (useSolution && solution != null) {
                return solution.getDataSource();
            } else {
                return super.getDataSource();
            }
        }
        
        @Override
        public void closePool() {
            if (useSolution && solution != null) {
                solution.closePool();
            } else {
                super.closePool();
            }
        }
        
        @Override
        public String getPoolStats() {
            if (useSolution && solution != null) {
                return solution.getPoolStats();
            } else {
                return super.getPoolStats();
            }
        }
    }
    
    private ConnectionPoolManager manager;
    
    @BeforeEach
    public void setUp() {
        manager = createManager();
        manager.createConnectionPool(H2_URL, USERNAME, PASSWORD, 5);
    }
    
    @AfterEach
    public void tearDown() {
        if (manager != null) {
            manager.closePool();
        }
    }
    
    @Test
    public void testGetConnection() throws SQLException {
        Connection conn = manager.getConnection();
        assertNotNull(conn, "Connection should not be null");
        assertFalse(conn.isClosed(), "Connection should be open");
        conn.close();
    }
    
    @Test
    public void testMultipleConnections() throws SQLException {
        Connection conn1 = manager.getConnection();
        Connection conn2 = manager.getConnection();
        
        assertNotNull(conn1);
        assertNotNull(conn2);
        assertNotSame(conn1, conn2, "Should get different connections from pool");
        
        conn1.close();
        conn2.close();
    }
    
    @Test
    public void testConnectionReuse() throws SQLException {
        Connection conn1 = manager.getConnection();
        conn1.close();
        
        Connection conn2 = manager.getConnection();
        assertNotNull(conn2, "Should reuse connection from pool");
        conn2.close();
    }
    
    @Test
    public void testPoolStats() {
        String stats = manager.getPoolStats();
        assertNotNull(stats);
        assertFalse(stats.isEmpty(), "Pool stats should not be empty");
    }
    
    @Test
    public void testClosePool() {
        manager.closePool();
        
        assertThrows(SQLException.class, () -> {
            manager.getConnection();
        }, "Should not be able to get connection after pool is closed");
    }
}

