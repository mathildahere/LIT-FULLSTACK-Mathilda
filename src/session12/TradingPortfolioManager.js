// ================================================
// Trading Portfolio Manager
// ================================================
// Requirements:
// 1. Add stocks to portfolio
// 2. Remove stocks from portfolio
// 3. Calculate total portfolio value
// 4. Calculate profit/loss for each stock
// 5. Calculate overall portfolio performance
// 6. Generate portfolio report
// ================================================

class PortfolioManager {
  constructor() {
    this.stocks = [];
    this.initialInvestment = 0;
  }

  // Add a stock
  addStock(symbol, shares, buyPrice, currentPrice) {
    if (shares <= 0 || buyPrice <= 0) {
      console.log("Invalid input: shares and prices must be positive numbers.");
      return;
    }
    this.stocks.push({ symbol, shares, buyPrice, currentPrice });
    this.initialInvestment += shares * buyPrice;
  }

  // Remove a stock
  removeStock(symbol) {
    const index = this.stocks.findIndex(stock => stock.symbol === symbol);
    if (index !== -1) {
      console.log(`Removing ${symbol} from portfolio...`);
      this.stocks.splice(index, 1);
    } else {
      console.log(`Stock ${symbol} not found in portfolio.`);
    }
  }

  // Calculate total portfolio value
  calculateTotalValue() {
    return this.stocks.reduce((sum, s) => sum + s.shares * s.currentPrice, 0);
  }

  // Calculate profit/loss for each stock
  calculatePnL(stock) {
    const invested = stock.shares * stock.buyPrice;
    const valueNow = stock.shares * stock.currentPrice;
    const pnl = valueNow - invested;
    const ret = (pnl / invested) * 100;
    return { invested, valueNow, pnl, ret };
  }

  // Calculate overall portfolio performance
  calculatePerformance() {
    let totalInvested = 0;
    let totalValue = 0;

    for (const s of this.stocks) {
      const stats = this.calculatePnL(s);
      totalInvested += stats.invested;
      totalValue += stats.valueNow;
    }

    const totalPnL = totalValue - totalInvested;
    const totalReturn = (totalPnL / totalInvested) * 100;

    return {
      totalInvested,
      totalValue,
      totalPnL,
      totalReturn
    };
  }

  generateReport() {
    const summary = this.calculatePerformance();

    let report = "\nPORTFOLIO REPORT\n";
    report += "=====================================================\n";
    report += "Symbol | Shares | Buy | Current | P/L ($) | Return (%)\n";
    report += "-----------------------------------------------------\n";

    for (const s of this.stocks) {
      const { pnl, ret } = this.calculatePnL(s);
      report += `${s.symbol.padEnd(6)} | ${String(s.shares).padEnd(6)} | $${s.buyPrice
        .toFixed(2)
        .padStart(7)} | $${s.currentPrice
        .toFixed(2)
        .padStart(7)} | $${pnl.toFixed(2).padStart(8)} | ${ret
        .toFixed(2)
        .padStart(7)}%\n`;
    }

    report += "-----------------------------------------------------\n";
    report += `Total Invested : $${summary.totalInvested.toFixed(2)}\n`;
    report += `Current Value  : $${summary.totalValue.toFixed(2)}\n`;
    report += `Total P/L      : $${summary.totalPnL.toFixed(2)}\n`;
    report += `Performance    : ${summary.totalReturn.toFixed(2)}%\n`;
    report += "=====================================================\n";

    return report;
  }
}

let portfolio = new PortfolioManager();

portfolio.addStock("AAPL", 25, 140, 176.35);
portfolio.addStock("GOOGL", 12, 120.3, 165.1);
portfolio.addStock("MSFT", 10, 310.5, 405.12);
portfolio.addStock("TSLA", 8, 220, 255.75);
portfolio.addStock("BBCA", 100, 9800, 10400);

console.log(portfolio.generateReport());

portfolio.removeStock("TSLA");

console.log(portfolio.generateReport());
