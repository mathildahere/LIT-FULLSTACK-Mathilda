/**
 * Trading Data Access - SOLUTION
 */

package com.kulkultech.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TradingDataAccessSolution {
    private final String dbUrl;
    private final String username;
    private final String password;

    public TradingDataAccessSolution(String dbUrl, String username, String password) {
        this.dbUrl = dbUrl;
        this.username = username;
        this.password = password;
    }

    public boolean addTrade(int portfolioId, String symbol, int quantity,
                            double price, String tradeType) {
        String sql = "INSERT INTO trades (portfolio_id, stock_symbol, quantity, price, trade_type) " +
                "VALUES (" + portfolioId + ", '" + symbol + "', " + quantity +
                ", " + price + ", '" + tradeType + "')";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            int rowsAffected = stmt.executeUpdate(sql);
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error adding trade: " + e.getMessage());
            return false;
        }
    }

    public List<com.kulkultech.jdbc.Trade> getTrades(int portfolioId) {
        String sql = "SELECT id, stock_symbol, quantity, price, trade_type FROM trades " +
                "WHERE portfolio_id = " + portfolioId;

        List<com.kulkultech.jdbc.Trade> trades = new ArrayList<>();

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

    public boolean updateTradePrice(int tradeId, double newPrice) {
        String sql = "UPDATE trades SET price = " + newPrice +
                " WHERE id = " + tradeId;

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            int rowsAffected = stmt.executeUpdate(sql);
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating trade: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteTrade(int tradeId) {
        String sql = "DELETE FROM trades WHERE id = " + tradeId;

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

