/**
 * Database Connection - JDBC Connection Management
 * 
 * Challenge: Create and manage database connections using JDBC
 * 
 * Your task: Implement methods to establish and test database connections
 * 
 * Concepts covered:
 * - DriverManager.getConnection()
 * - Connection properties
 * - Connection validation
 * - Try-with-resources for resource management
 */

package com.kulkultech.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private String dbUrl;
    private String username;
    private String password;
    
    public DatabaseConnection(String dbUrl, String username, String password) {
        // TODO: Initialize connection properties
        this.dbUrl = dbUrl;
        this.username = username;
        this.password = password;
    }
    
    /**
     * Get a database connection
     * TODO: Use DriverManager.getConnection() to create connection
     */
    public Connection getConnection() throws SQLException {
        // TODO: Return a connection using DriverManager
        // Hint: DriverManager.getConnection(dbUrl, username, password)
        return DriverManager.getConnection(dbUrl, username, password);
    }
    
    /**
     * Test if connection is valid
     * TODO: Create connection, check if valid, and close it
     */
    public boolean testConnection() {
        // TODO: Use try-with-resources to get connection
        // TODO: Check connection.isValid(5) with 5 second timeout
        // TODO: Return true if valid, false otherwise
        try (Connection c = getConnection()) {
            return c.isValid(5);
        } catch (SQLException e) {
            return false;
        }
    }
    
    /**
     * Get database metadata
     * TODO: Return database product name
     */
    public String getDatabaseProductName() throws SQLException {
        // TODO: Get connection, retrieve metadata, return product name
        // Hint: connection.getMetaData().getDatabaseProductName()
        try (Connection c = getConnection()) {
            return c.getMetaData().getDatabaseProductName();
        }
    }
}

