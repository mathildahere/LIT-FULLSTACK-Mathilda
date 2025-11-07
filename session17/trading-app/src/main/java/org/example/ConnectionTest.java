package org.example;

import java.util.*;

public class ConnectionTest {
    public static void main(String[] args) {
        TradingDataAccess dao = new TradingDataAccess(new TradingDatabase());

        // Test trades
        System.out.println("=== Executing Trades ===");
        boolean r1 = dao.executeTrade(1, "AAPL", 10, 150.0, "BUY");
        System.out.println("Buy 10 AAPL: " + r1);

        boolean r2 = dao.executeTrade(1, "AAPL", 5, 160.0, "BUY");
        System.out.println("Buy 5 AAPL: " + r2);

        boolean r3 = dao.executeTrade(1, "AAPL", 20, 155.0, "SELL");
        System.out.println("Sell 20 AAPL (should fail/rollback): " + r3);

        boolean r4 = dao.executeTrade(1, "AAPL", 3, 170.0, "SELL");
        System.out.println("Sell 3 AAPL: " + r4);

        // View portfolio positions
        System.out.println("\n=== Portfolio Positions ===");
        List<Position> positions = dao.getPortfolioPositions(1);
        for (Position pos : positions) {
            System.out.println(pos.getSymbol() + " | Shares: " + pos.getShares() +
                    " | Avg Price: " + pos.getAveragePrice() +
                    " | Current Price: " + pos.getCurrentPrice() +
                    " | P/L: " + pos.getProfitLoss());
        }

        // View user portfolios
        System.out.println("\n=== User Portfolios ===");
        dao.getUserPortfolios(1).forEach(p ->
                System.out.println("Portfolio: " + p.getName() + " (ID: " + p.getId() + ")"));

        // Portfolio diversification
        System.out.println("\n=== Portfolio Diversification ===");
        Map<String, Double> diversification = dao.getPortfolioDiversification(1);
        diversification.forEach((sector, percent) ->
                System.out.println(sector + ": " + String.format("%.2f", percent) + "%"));

        // Daily P/L report
        System.out.println("\n=== Daily P/L Report ===");
        dao.generateDailyPnLReport(1);

        // Batch price updates
        System.out.println("\n=== Batch Price Update ===");
        Map<String, Double> newPrices = new HashMap<>();
        newPrices.put("AAPL", 175.0);
        newPrices.put("TSLA", 720.0);
        boolean batchResult = dao.updateMultipleStockPrices(newPrices);
        System.out.println("Batch price update success? " + batchResult);

        // Show updated positions
        System.out.println("\n=== Updated Positions After Price Update ===");
        dao.getPortfolioPositions(1).forEach(pos ->
                System.out.println(pos.getSymbol() + " | Shares: " + pos.getShares() +
                        " | Avg Price: " + pos.getAveragePrice() +
                        " | Current Price: " + pos.getCurrentPrice() +
                        " | P/L: " + pos.getProfitLoss()));
    }
}
