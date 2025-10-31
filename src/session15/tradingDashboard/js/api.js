function randomPrice(base = 100, volatility = 0.03) {
  const change = (Math.random() - 0.5) * volatility;
  return parseFloat((base * (1 + change)).toFixed(2));
}

export async function fetchStockPrice(symbol) {
  symbol = String(symbol || '').toUpperCase();

  if (!symbol.match(/^[A-Z\.]{1,6}$/)) {
    throw new Error('Invalid symbol');
  }

  await new Promise(r => setTimeout(r, 200 + Math.random() * 400));

  if (Math.random() < 0.06) {
    throw new Error('Network error (simulated)');
  }

  const seed = symbol.split('').reduce((s, c) => s + c.charCodeAt(0), 0);
  const base = 80 + (seed % 60);
  const price = randomPrice(base, 0.06);
  const change = parseFloat(((Math.random() - 0.5) * 2).toFixed(2));

  return { symbol, price, change };
}
