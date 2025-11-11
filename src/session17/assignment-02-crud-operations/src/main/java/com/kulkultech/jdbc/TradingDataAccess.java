/**
 * Trading Data Access - CRUD Operations
 *
 * Challenge: Implement Create, Read, Update, Delete operations using JDBC
 *
 * Your task: Complete CRUD methods using Statement and ResultSet
 *
 * Concepts covered:
 * - CREATE: INSERT statements
 * - READ: SELECT statements and ResultSet processing
 * - UPDATE: UPDATE statements
 * - DELETE: DELETE statements
 * - Statement.executeUpdate() and executeQuery()
 */

package com.kulkultech.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TradingDataAccess {
    private final String dbUrl;
    private final String username;
    private final String password;

    public TradingDataAccess(String dbUrl, String username, String password) {
        this.dbUrl = dbUrl;
        this.username = username;
        this.password = password;
    }

    /**
     * CREATE: Insert a new trade
     * TODO: Use Statement.executeUpdate() to insert a trade
     */
    public boolean addTrade(int portfolioId, String symbol, int quantity,
                            double price, String tradeType) {
        String sql = "INSERT INTO trades (portfolio_id, stock_symbol, quantity, price, trade_type) " +
                "VALUES (" + portfolioId + ", '" + symbol + "', " + quantity +
                ", " + price + ", '" + tradeType + "')";

        // TODO: Get connection, create statement, execute update
        // Hint: try (Connection conn = getConnection();
        //            Statement stmt = conn.createStatement()) {
        //          int rowsAffected = stmt.executeUpdate(sql);
        //          return rowsAffected > 0;
        //       }
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            int rowsAffected = stmt.executeUpdate(sql);
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error adding trade: " + e.getMessage());
            return false;
        }
    }

    /**
     * READ: Get all trades for a portfolio
     * TODO: Use Statement.executeQuery() to retrieve trades
     */
    public List<com.kulkultech.jdbc.Trade> getTrades(int portfolioId) {
        String sql = "SELECT id, stock_symbol, quantity, price, trade_type FROM trades " +
                "WHERE portfolio_id = " + portfolioId;

        List<com.kulkultech.jdbc.Trade> trades = new ArrayList<>();

        // TODO: Get connection, create statement, execute query
        // TODO: Process ResultSet and create Trade objects
        // Hint: while (rs.next()) {
        //          Trade trade = new Trade(
        //              rs.getInt("id"),
        //              rs.getString("stock_symbol"),
        //              rs.getInt("quantity"),
        //              rs.getDouble("price"),
        //              rs.getString("trade_type")
        //          );
        //          trades.add(trade);
        //       }
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                com.kulkultech.jdbc.Trade trade = new com.kulkultech.jdbc.Trade(
                        rs.getInt("id"),
                        rs.getString("stock_symbol"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getString("trade_type")
                );
                trades.add(trade);
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving trades: " + e.getMessage());
        }

        return trades;
    }

    /**
     * UPDATE: Update trade price
     * TODO: Use Statement.executeUpdate() to update a trade
     */
    public boolean updateTradePrice(int tradeId, double newPrice) {
        String sql = "UPDATE trades SET price = " + newPrice +
                " WHERE id = " + tradeId;

        // TODO: Execute update statement
        // Hint: Similar to addTrade but with UPDATE SQL

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            int rowsAffected = stmt.executeUpdate(sql);
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating trade: " + e.getMessage());
            return false;
        }
    }

    /**
     * DELETE: Remove a trade
     * TODO: Use Statement.executeUpdate() to delete a trade
     */
    public boolean deleteTrade(int tradeId) {
        String sql = "DELETE FROM trades WHERE id = " + tradeId;

        // TODO: Execute delete statement
        // Hint: Similar to addTrade but with DELETE SQL

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            int rowsAffected = stmt.executeUpdate(sql);
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting trade: " + e.getMessage());
            return false;
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, username, password);
    }
}

class Trade {
    private int id;
    private String symbol;
    private int quantity;
    private double price;
    private String tradeType;

    public Trade(int id, String symbol, int quantity, double price, String tradeType) {
        this.id = id;
        this.symbol = symbol;
        this.quantity = quantity;
        this.price = price;
        this.tradeType = tradeType;
    }

    // Getters
    public int getId() { return id; }
    public String getSymbol() { return symbol; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public String getTradeType() { return tradeType; }
}

