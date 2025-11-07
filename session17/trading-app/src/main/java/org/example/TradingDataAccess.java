package org.example;

import java.sql.*;
import java.util.*;

public class TradingDataAccess {
    private final TradingDatabase db;

    public TradingDataAccess(TradingDatabase db) {
        this.db = db;
    }

    public List<Portfolio> getUserPortfolios(int userId) {
        String sql = "SELECT id, name, created_at FROM portfolios WHERE user_id = ?";
        List<Portfolio> portfolios = new ArrayList<>();

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Portfolio portfolio = new Portfolio(
                        rs.getInt("id"),
                        userId,
                        rs.getString("name"),
                        rs.getTimestamp("created_at")
                );
                portfolios.add(portfolio);
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving portfolios: " + e.getMessage());
        }

        return portfolios;
    }

    public Map<String, Double> getPortfolioDiversification(int portfolioId) {
        String sql = """
        SELECT sec.name AS sector, SUM(pos.shares * pos.current_price) AS sector_value
        FROM positions pos
        JOIN stocks s ON pos.stock_symbol = s.symbol
        JOIN sectors sec ON s.sector_id = sec.id
        WHERE pos.portfolio_id = ?
        GROUP BY sec.name
    """;

        Map<String, Double> diversification = new HashMap<>();

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, portfolioId);
            ResultSet rs = stmt.executeQuery();

            double totalValue = 0.0;
            while (rs.next()) {
                double sectorValue = rs.getDouble("sector_value");
                diversification.put(rs.getString("sector"), sectorValue);
                totalValue += sectorValue;
            }

            // Convert absolute values to percentage
            for (Map.Entry<String, Double> entry : diversification.entrySet()) {
                diversification.put(entry.getKey(), entry.getValue() / totalValue * 100);
            }

        } catch (SQLException e) {
            System.err.println("Error calculating diversification: " + e.getMessage());
        }

        return diversification;
    }

    public void generateDailyPnLReport(int portfolioId) {
        String sql = """
        SELECT pos.shares, pos.average_price, pos.current_price
        FROM positions pos
        WHERE pos.portfolio_id = ?
    """;

        double realized = 0.0;   // For simplicity, we assume all P/L is unrealized
        double unrealized = 0.0;

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, portfolioId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int shares = rs.getInt("shares");
                double avg = rs.getDouble("average_price");
                double curr = rs.getDouble("current_price");

                unrealized += shares * (curr - avg);
            }

            System.out.println("Daily P/L Report for Portfolio " + portfolioId);
            System.out.println("Realized: " + realized);
            System.out.println("Unrealized: " + unrealized);
            System.out.println("Total: " + (realized + unrealized));

        } catch (SQLException e) {
            System.err.println("Error generating daily P/L: " + e.getMessage());
        }
    }

    public boolean updateMultipleStockPrices(Map<String, Double> priceUpdates) {
        String sql = "UPDATE positions SET current_price = ?, updated_at = CURRENT_TIMESTAMP WHERE stock_symbol = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false);

            for (Map.Entry<String, Double> entry : priceUpdates.entrySet()) {
                stmt.setDouble(1, entry.getValue());
                stmt.setString(2, entry.getKey());
                stmt.addBatch();
            }

            int[] results = stmt.executeBatch();
            conn.commit();

            return Arrays.stream(results).allMatch(r -> r > 0);

        } catch (SQLException e) {
            System.err.println("Error updating stock prices: " + e.getMessage());
            return false;
        }
    }


    // Create - Add new trade
    public boolean addTrade(int portfolioId, String symbol, int quantity,
                            double price, String tradeType) {
        String sql = "INSERT INTO trades (portfolio_id, stock_symbol, quantity, price, trade_type) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = db.getConnection();
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

    public boolean executeTrade(int portfolioId, String symbol, int quantity,
                                double price, String tradeType) {

        String insertTradeSQL = "INSERT INTO trades (portfolio_id, stock_symbol, quantity, price, trade_type) VALUES (?, ?, ?, ?, ?)";
        String selectPositionSQL = "SELECT shares, average_price FROM positions WHERE portfolio_id = ? AND stock_symbol = ?";
        String updatePositionSQL = "UPDATE positions SET shares = ?, average_price = ?, updated_at = CURRENT_TIMESTAMP WHERE portfolio_id = ? AND stock_symbol = ?";
        String insertPositionSQL = "INSERT INTO positions (portfolio_id, stock_symbol, shares, average_price, current_price) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = db.getConnection()) {
            conn.setAutoCommit(false); // Start transaction

            // Step 1: Read current position
            int currentShares = 0;
            double currentAvgPrice = 0.0;
            boolean positionExists = false;

            try (PreparedStatement stmt = conn.prepareStatement(selectPositionSQL)) {
                stmt.setInt(1, portfolioId);
                stmt.setString(2, symbol);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    positionExists = true;
                    currentShares = rs.getInt("shares");
                    currentAvgPrice = rs.getDouble("average_price");
                }
            }

            int updatedShares = currentShares;
            double updatedAvgPrice = currentAvgPrice;

            if (tradeType.equalsIgnoreCase("BUY")) {
                updatedShares += quantity;
                updatedAvgPrice = ((currentShares * currentAvgPrice) + (quantity * price)) / updatedShares;
            } else if (tradeType.equalsIgnoreCase("SELL")) {
                if (quantity > currentShares) {
                    conn.rollback();
                    System.err.println("Not enough shares to sell. Transaction cancelled.");
                    return false;
                }
                updatedShares -= quantity;
                // avg price unchanged for SELL
            }

            // Step 2: Update or insert position
            if (positionExists) {
                try (PreparedStatement stmt = conn.prepareStatement(updatePositionSQL)) {
                    stmt.setInt(1, updatedShares);
                    stmt.setDouble(2, updatedAvgPrice);
                    stmt.setInt(3, portfolioId);
                    stmt.setString(4, symbol);
                    stmt.executeUpdate();
                }
            } else {
                try (PreparedStatement stmt = conn.prepareStatement(insertPositionSQL)) {
                    stmt.setInt(1, portfolioId);
                    stmt.setString(2, symbol);
                    stmt.setInt(3, updatedShares);
                    stmt.setDouble(4, updatedAvgPrice);
                    stmt.setDouble(5, price); // initial current price
                    stmt.executeUpdate();
                }
            }

            // Step 3: Insert trade record
            try (PreparedStatement stmt = conn.prepareStatement(insertTradeSQL)) {
                stmt.setInt(1, portfolioId);
                stmt.setString(2, symbol);
                stmt.setInt(3, quantity);
                stmt.setDouble(4, price);
                stmt.setString(5, tradeType);
                stmt.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            System.err.println("Trade failed: " + e.getMessage());
            return false;
        }
    }

    // Read - Get portfolio positions
    public List<Position> getPortfolioPositions(int portfolioId) {
        String sql = "SELECT p.stock_symbol, p.shares, p.average_price, p.current_price "
                + "FROM positions p WHERE p.portfolio_id = ?";

        List<Position> positions = new ArrayList<>();

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, portfolioId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Position position = new Position(
                        rs.getString("stock_symbol"),
                        rs.getInt("shares"),
                        rs.getDouble("average_price"),
                        rs.getDouble("current_price")
                );
                positions.add(position);
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving positions: " + e.getMessage());
        }

        return positions;
    }

    // Update - Update stock prices
    public boolean updateStockPrices(Map<String, Double> priceUpdates) {
        String sql = "UPDATE positions SET current_price = ?, updated_at = CURRENT_TIMESTAMP WHERE stock_symbol = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false); // Start transaction

            for (Map.Entry<String, Double> entry : priceUpdates.entrySet()) {
                stmt.setDouble(1, entry.getValue());
                stmt.setString(2, entry.getKey());
                stmt.addBatch();
            }

            int[] results = stmt.executeBatch();
            conn.commit(); // Commit transaction

            return Arrays.stream(results).allMatch(result -> result > 0);

        } catch (SQLException e) {
            System.err.println("Error updating prices: " + e.getMessage());
            return false;
        }
    }

    // Delete - Remove position
    public boolean removePosition(int portfolioId, String symbol) {
        String sql = "DELETE FROM positions WHERE portfolio_id = ? AND stock_symbol = ?";

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, portfolioId);
            stmt.setString(2, symbol);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error removing position: " + e.getMessage());
            return false;
        }
    }
}

