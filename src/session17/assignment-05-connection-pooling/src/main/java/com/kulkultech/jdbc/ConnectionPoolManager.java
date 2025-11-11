package com.kulkultech.jdbc; /**
 * Connection Pool Manager - Connection Pooling with HikariCP
 * 
 * Challenge: Implement connection pooling for efficient database connections
 * 
 * Your task: Use HikariCP to create and manage a connection pool
 * 
 * Concepts covered:
 * - HikariDataSource
 * - Connection pool configuration
 * - Pool size management
 * - Connection lifecycle
 */

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPoolManager {
    private HikariDataSource dataSource;
    
    /**
     * Create connection pool with HikariCP
     */
    public void createConnectionPool(String dbUrl, String username, String password, int maxPoolSize) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dbUrl);
        config.setUsername(username);
        config.setPassword(password);
        config.setMaximumPoolSize(maxPoolSize);
        config.setMinimumIdle(2);
        config.setConnectionTimeout(15000);
        config.setIdleTimeout(300000);
        config.setMaxLifetime(900000);
        this.dataSource = new HikariDataSource(config);
    }
    
    /**
     * Get connection from pool
     */
    public Connection getConnection() throws SQLException {
        if (dataSource == null) {
            throw new SQLException("Connection pool not initialized");
        }
        return dataSource.getConnection();
    }
    
    /**
     * Get DataSource
     */
    public DataSource getDataSource() {
        return dataSource;
    }
    
    /**
     * Close connection pool
     */
    public void closePool() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
    
    /**
     * Get pool statistics
     */
    public String getPoolStats() {
        if (dataSource == null) {
            return "Pool not initialized";
        }

        try {
            return "Pool stats: " + dataSource.getHikariPoolMXBean().getActiveConnections() +
                    " active, " + dataSource.getHikariPoolMXBean().getIdleConnections() + " idle, " +
                    "total: " + dataSource.getHikariPoolMXBean().getTotalConnections();
        } catch (Exception e) {
            return "Error getting pool stats: " + e.getMessage();
        }
    }
}

