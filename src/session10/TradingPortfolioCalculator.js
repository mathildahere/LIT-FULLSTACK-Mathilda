// Trading Portfolio Calculator

let portfolio = [
  { symbol: "AAPL", shares: 100, buyPrice: 140.00, currentPrice: 150.25 },
  { symbol: "GOOGL", shares: 50, buyPrice: 2800.00, currentPrice: 2850.00 },
  { symbol: "MSFT", shares: 200, buyPrice: 300.00, currentPrice: 310.50 },
  { symbol: "BBCA", shares: 500, buyPrice: 9500.00, currentPrice: 10200.00 },
  { symbol: "TLKM", shares: 300, buyPrice: 3900.00, currentPrice: 3850.00 }
];

let totalValue = 0;
let totalCost = 0;

console.log("PORTFOLIO REPORT");
console.log("================\n");

for (let stock of portfolio) {
  let cost = stock.shares * stock.buyPrice;
  let value = stock.shares * stock.currentPrice;
  let profitLoss = value - cost;
  let percentChange = (profitLoss / cost) * 100;

  totalValue += value;
  totalCost += cost;

  console.log(`Stock: ${stock.symbol}`);
  console.log(`  Shares: ${stock.shares}`);
  console.log(`  Buy Price: $${stock.buyPrice.toFixed(2)}`);
  console.log(`  Current Price: $${stock.currentPrice.toFixed(2)}`);
  console.log(`  Value: $${value.toFixed(2)}`);
  console.log(`  Profit/Loss: $${profitLoss.toFixed(2)} (${percentChange.toFixed(2)}%)\n`);
}

let totalProfitLoss = totalValue - totalCost;
let totalPercentChange = (totalProfitLoss / totalCost) * 100;

console.log("================================");
console.log(`Total Portfolio Value: $${totalValue.toFixed(2)}`);
console.log(`Total Profit/Loss: $${totalProfitLoss.toFixed(2)} (${totalPercentChange.toFixed(2)}%)`);
console.log("================================");
