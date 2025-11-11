package com.kulkultech.jdbc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

/**
 * PreparedStatement Example Tests
 */
public class PreparedStatementExampleTest {

    private static final boolean USE_SOLUTION =
            Boolean.parseBoolean(System.getProperty("test.solution", "false"));

    private static final String H2_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";

    private PreparedStatementExample createExample() {
        if (USE_SOLUTION) {
            return new PreparedStatementExampleWrapper(true);
        } else {
            return new PreparedStatementExampleWrapper(false);
        }
    }

    private static class PreparedStatementExampleWrapper extends PreparedStatementExample {
        private final PreparedStatementExampleSolution solution;
        private final boolean useSolution;

        public PreparedStatementExampleWrapper(boolean useSolution) {
            super(H2_URL, USERNAME, PASSWORD);
            this.useSolution = useSolution;
            this.solution = useSolution ?
                    new PreparedStatementExampleSolution(H2_URL, USERNAME, PASSWORD) : null;
        }

        @Override
        public boolean addTrade(int portfolioId, String symbol, int quantity, double price, String tradeType) {
            if (useSolution && solution != null) {
                return solution.addTrade(portfolioId, symbol, quantity, price, tradeType);
            } else {
                return super.addTrade(portfolioId, symbol, quantity, price, tradeType);
            }
        }

        @Override
        public List<Trade> findTradesBySymbol(String symbol) {
            if (useSolution && solution != null) {
                return solution.findTradesBySymbol(symbol);
            } else {
                return super.findTradesBySymbol(symbol);
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
        public boolean testSqlInjectionSafe(String userInput) {
            if (useSolution && solution != null) {
                return solution.testSqlInjectionSafe(userInput);
            } else {
                return super.testSqlInjectionSafe(userInput);
            }
        }
    }

    @BeforeEach
    public void setUp() throws Exception {
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
    public void testAddTradeWithPreparedStatement() {
        PreparedStatementExample example = createExample();

        boolean result = example.addTrade(1, "AAPL", 10, 150.0, "BUY");
        assertTrue(result, "Trade should be added successfully");
    }

    @Test
    public void testFindTradesBySymbol() {
        PreparedStatementExample example = createExample();

        example.addTrade(1, "AAPL", 10, 150.0, "BUY");
        example.addTrade(1, "AAPL", 5, 155.0, "BUY");
        example.addTrade(1, "GOOGL", 3, 2800.0, "BUY");

        List<Trade> trades = example.findTradesBySymbol("AAPL");
        assertEquals(2, trades.size(), "Should find 2 AAPL trades");

        for (Trade trade : trades) {
            assertEquals("AAPL", trade.getSymbol());
        }
    }

    @Test
    public void testUpdateTradePrice() {
        PreparedStatementExample example = createExample();

        example.addTrade(1, "AAPL", 10, 150.0, "BUY");
        List<com.kulkultech.jdbc.Trade> trades = example.findTradesBySymbol("AAPL");
        int tradeId = trades.get(0).getId();

        boolean updated = example.updateTradePrice(tradeId, 160.0);
        assertTrue(updated, "Trade price should be updated");

        trades = example.findTradesBySymbol("AAPL");
        assertEquals(160.0, trades.get(0).getPrice(), 0.01);
    }

    @Test
    public void testSqlInjectionPrevention() {
        PreparedStatementExample example = createExample();

        // Add a legitimate trade
        example.addTrade(1, "AAPL", 10, 150.0, "BUY");

        // Try SQL injection attack (should be safe with PreparedStatement)
        String maliciousInput = "'; DROP TABLE trades; --";

        // This should not throw an exception or drop the table
        assertDoesNotThrow(() -> {
            boolean result = example.testSqlInjectionSafe(maliciousInput);
            assertFalse(result, "Malicious input should not match any trades");
        });

        // Verify table still exists and has data
        List<com.kulkultech.jdbc.Trade> trades = example.findTradesBySymbol("AAPL");
        assertEquals(1, trades.size(), "Table should still exist and have data");
    }

    @Test
    public void testSpecialCharactersInSymbol() {
        PreparedStatementExample example = createExample();

        // Test with symbols containing special characters (must fit in VARCHAR(10))
        // Using shorter symbol that still demonstrates SQL injection safety
        String symbolWithSpecialChars = "TEST'OR'1";

        example.addTrade(1, symbolWithSpecialChars, 10, 150.0, "BUY");

        List<Trade> trades = example.findTradesBySymbol(symbolWithSpecialChars);
        assertEquals(1, trades.size(), "Should handle special characters safely");
    }
}

