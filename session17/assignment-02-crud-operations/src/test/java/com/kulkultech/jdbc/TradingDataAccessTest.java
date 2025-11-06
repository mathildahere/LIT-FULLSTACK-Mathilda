package com.kulkultech.jdbc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

/**
 * Trading Data Access Tests
 * Uses H2 in-memory database for testing
 */
public class TradingDataAccessTest {

    private static final boolean USE_SOLUTION =
            Boolean.parseBoolean(System.getProperty("test.solution", "false"));

    private static final String H2_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";

    private TradingDataAccess createDataAccess() {
        if (USE_SOLUTION) {
            return new TradingDataAccessWrapper(true);
        } else {
            return new TradingDataAccessWrapper(false);
        }
    }

    private static class TradingDataAccessWrapper extends TradingDataAccess {
        private final TradingDataAccessSolution solution;
        private final boolean useSolution;

        public TradingDataAccessWrapper(boolean useSolution) {
            super(H2_URL, USERNAME, PASSWORD);
            this.useSolution = useSolution;
            this.solution = useSolution ?
                    new TradingDataAccessSolution(H2_URL, USERNAME, PASSWORD) : null;
        }

        @Override
        public boolean addTrade(int portfolioId, String symbol, int quantity,
                                double price, String tradeType) {
            if (useSolution && solution != null) {
                return solution.addTrade(portfolioId, symbol, quantity, price, tradeType);
            } else {
                return super.addTrade(portfolioId, symbol, quantity, price, tradeType);
            }
        }

        @Override
        public List<com.kulkultech.jdbc.Trade> getTrades(int portfolioId) {
            if (useSolution && solution != null) {
                return solution.getTrades(portfolioId);
            } else {
                return super.getTrades(portfolioId);
            }
        }

        @Override
        public boolean updateTradePrice(int tradeId, double newPrice) {
            if (useSolution && solution != null) {
                return solution.updateTradePrice(tradeId, newPrice);
            } else {
                return super.updateTradePrice(tradeId, newPrice);
            }
        }

        @Override
        public boolean deleteTrade(int tradeId) {
            if (useSolution && solution != null) {
                return solution.deleteTrade(tradeId);
            } else {
                return super.deleteTrade(tradeId);
            }
        }
    }

    @BeforeEach
    public void setUp() throws Exception {
        // Create table for testing
        try (Connection conn = DriverManager.getConnection(H2_URL, USERNAME, PASSWORD);
             Statement stmt = conn.createStatement()) {

            stmt.execute("DROP TABLE IF EXISTS trades");
            stmt.execute("CREATE TABLE trades (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "portfolio_id INT NOT NULL, " +
                    "stock_symbol VARCHAR(10) NOT NULL, " +
                    "quantity INT NOT NULL, " +
                    "price DECIMAL(10,2) NOT NULL, " +
                    "trade_type VARCHAR(10) NOT NULL" +
                    ")");
        }
    }

    @Test
    public void testAddTrade() {
        TradingDataAccess dao = createDataAccess();

        boolean result = dao.addTrade(1, "AAPL", 10, 150.0, "BUY");
        assertTrue(result, "Trade should be added successfully");
    }

    @Test
    public void testGetTrades() {
        TradingDataAccess dao = createDataAccess();

        dao.addTrade(1, "AAPL", 10, 150.0, "BUY");
        dao.addTrade(1, "GOOGL", 5, 2800.0, "BUY");

        List<com.kulkultech.jdbc.Trade> trades = dao.getTrades(1);
        assertEquals(2, trades.size(), "Should retrieve 2 trades");

        com.kulkultech.jdbc.Trade firstTrade = trades.get(0);
        assertEquals("AAPL", firstTrade.getSymbol());
        assertEquals(10, firstTrade.getQuantity());
    }

    @Test
    public void testUpdateTradePrice() {
        TradingDataAccess dao = createDataAccess();

        dao.addTrade(1, "AAPL", 10, 150.0, "BUY");
        List<com.kulkultech.jdbc.Trade> trades = dao.getTrades(1);
        int tradeId = trades.get(0).getId();

        boolean updated = dao.updateTradePrice(tradeId, 155.0);
        assertTrue(updated, "Trade price should be updated");

        List<Trade> updatedTrades = dao.getTrades(1);
        assertEquals(155.0, updatedTrades.get(0).getPrice(), 0.01);
    }

    @Test
    public void testDeleteTrade() {
        TradingDataAccess dao = createDataAccess();

        dao.addTrade(1, "AAPL", 10, 150.0, "BUY");
        List<com.kulkultech.jdbc.Trade> trades = dao.getTrades(1);
        int tradeId = trades.get(0).getId();

        boolean deleted = dao.deleteTrade(tradeId);
        assertTrue(deleted, "Trade should be deleted");

        List<Trade> remainingTrades = dao.getTrades(1);
        assertEquals(0, remainingTrades.size(), "No trades should remain");
    }

    @Test
    public void testCompleteCRUD() {
        TradingDataAccess dao = createDataAccess();

        // CREATE
        assertTrue(dao.addTrade(1, "MSFT", 20, 310.0, "BUY"));

        // READ
        List<com.kulkultech.jdbc.Trade> trades = dao.getTrades(1);
        assertEquals(1, trades.size());
        int tradeId = trades.get(0).getId();

        // UPDATE
        assertTrue(dao.updateTradePrice(tradeId, 315.0));
        trades = dao.getTrades(1);
        assertEquals(315.0, trades.get(0).getPrice(), 0.01);

        // DELETE
        assertTrue(dao.deleteTrade(tradeId));
        trades = dao.getTrades(1);
        assertEquals(0, trades.size());
    }
}

