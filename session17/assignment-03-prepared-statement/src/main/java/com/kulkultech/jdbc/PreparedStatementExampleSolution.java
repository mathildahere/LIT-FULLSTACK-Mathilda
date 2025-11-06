/**
 * PreparedStatement Example - SOLUTION
 */

package com.kulkultech.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PreparedStatementExampleSolution {
    private final String dbUrl;
    private final String username;
    private final String password;
    
    public PreparedStatementExampleSolution(String dbUrl, String username, String password) {
        this.dbUrl = dbUrl;
        this.username = username;
        this.password = password;
    }
    
    public boolean addTrade(int portfolioId, String symbol, int quantity, double price, String tradeType) {
        String sql = "INSERT INTO trades (portfolio_id, stock_symbol, quantity, price, trade_type) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, portfolioId);
            stmt.setString(2, symbol);
            stmt.setInt(3, quantity);
            stmt.setDouble(4, price);
            stmt.setString(5, tradeType);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error adding trade: " + e.getMessage());
            return false;
        }
    }
    
    public List<Trade> findTradesBySymbol(String symbol) {
        String sql = "SELECT id, stock_symbol, quantity, price, trade_type FROM trades WHERE stock_symbol = ?";
        
        List<Trade> trades = new ArrayList<>();
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, symbol);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Trade trade = new Trade(
                        rs.getInt("id"),
                        rs.getString("stock_symbol"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getString("trade_type")
                    );
                    trades.add(trade);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error finding trades: " + e.getMessage());
        }
        
        return trades;
    }
    
    public boolean updateTradePrice(int tradeId, double newPrice) {
        String sql = "UPDATE trades SET price = ? WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDouble(1, newPrice);
            stmt.setInt(2, tradeId);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating trade: " + e.getMessage());
            return false;
        }
    }
    
    public boolean testSqlInjectionSafe(String userInput) {
        String sql = "SELECT id FROM trades WHERE stock_symbol = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, userInput);
            
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Returns true if any row found
            }
            
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            return false;
        }
    }
    
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, username, password);
    }
}

