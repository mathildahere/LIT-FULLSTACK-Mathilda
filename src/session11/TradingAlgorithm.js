// Trading algorithm simulator boilerplate
// Requirements:
// 1. Start with $10,000 initial capital
// 2. Simulate 30 days of trading
// 3. Buy when price drops 2% below average
// 4. Sell when price rises 3% above average
// 5. Track daily portfolio value
// 6. Calculate final profit/loss

function simulateTradingAlgorithm() {
    const initialCapital = 10000;
    let capital = initialCapital;
    let shares = 0;
    let averagePrice = 0;
    let portfolioValues = [];

    // Generate or fetch prices for 30 days
    let prices = generateDailyPrices(30);

    console.log("=== Trading Simulation Start ===");

    for (let day = 0; day < prices.length; day++) {
        const price = prices[day];

        // Hitung average dari harga sebelumnya
        if (day > 0) {
            averagePrice = prices.slice(0, day).reduce((a, b) => a + b, 0) / day;
        }

        // BUY condition
        if (shouldBuy(price, averagePrice)) {
            const sharesToBuy = Math.floor(capital / price);
            if (sharesToBuy > 0) {
                capital -= sharesToBuy * price;
                shares += sharesToBuy;
                console.log(`Day ${day + 1}: BUY ${sharesToBuy} shares @ $${price.toFixed(2)}`);
            }
        }

        // SELL condition
        else if (shouldSell(price, averagePrice) && shares > 0) {
            const revenue = shares * price;
            capital += revenue;
            console.log(`Day ${day + 1}: SELL ${shares} shares @ $${price.toFixed(2)}`);
            shares = 0;
        }

        // Track daily portfolio value
        portfolioValues.push(calculatePortfolioValue(capital, shares, price));
    }

    // Calculate and log final profit/loss
    const finalValue = calculatePortfolioValue(capital, shares, prices[prices.length - 1]);
    console.log("\n=== Final Report ===");
    console.log("Final portfolio value:", `$${finalValue.toFixed(2)}`);
    console.log("Profit/Loss:", `$${(finalValue - initialCapital).toFixed(2)}`);
    console.log("=============================");

    return portfolioValues;
}

// === Helper functions ===

// Generate an array of simulated prices for n days
function generateDailyPrices(days) {
    let prices = [100]; // mulai dari 100
    for (let i = 1; i < days; i++) {
        let prev = prices[i - 1];
        let change = (Math.random() - 0.5) * 4; // naik/turun sekitar -2% s.d. +2%
        let newPrice = prev + prev * (change / 100);
        prices.push(parseFloat(newPrice.toFixed(2)));
    }
    return prices;
}

// Decide whether to buy (returns true if should buy)
function shouldBuy(price, averagePrice) {
    return averagePrice > 0 && price < averagePrice * 0.98;
}

// Decide whether to sell (returns true if should sell)
function shouldSell(price, averagePrice) {
    return averagePrice > 0 && price > averagePrice * 1.03;
}

// Calculate current portfolio value
function calculatePortfolioValue(capital, shares, price) {
    return capital + shares * price;
}

// === Uncomment to run simulation ===
simulateTradingAlgorithm();
