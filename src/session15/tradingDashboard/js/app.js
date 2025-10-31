import Storage from './storage.js';
import { fetchStockPrice } from './api.js';

class TradingDashboard {
  constructor() {
    this.watchlist = Storage.load('watchlist') || [];
    this.$container = document.getElementById('stock-container');
    this.$empty = document.getElementById('empty');
    this.renderWatchlist();
    this.bindEvents();
  }

  bindEvents() {
    // Add new stock
    document.getElementById('add-stock-form').addEventListener('submit', e => {
      e.preventDefault();
      const symbol = e.target.symbol.value.trim();
      if (symbol) this.addToWatchlist(symbol);
      e.target.reset();
    });

    // Refresh all
    document.getElementById('refresh-all').addEventListener('click', () => this.refreshAll());

    // Clear all
    document.getElementById('clear-watchlist').addEventListener('click', () => {
      if (confirm('Clear your entire watchlist?')) {
        this.watchlist = [];
        Storage.clear('watchlist');
        this.renderWatchlist();
      }
    });

    // Card buttons (refresh/remove)
    this.$container.addEventListener('click', e => {
      const btn = e.target.closest('button[data-action]');
      if (!btn) return;
      const symbol = btn.dataset.symbol;
      if (btn.dataset.action === 'refresh') this.refreshOne(symbol);
      else if (btn.dataset.action === 'remove') this.removeFromWatchlist(symbol);
    });
  }

  async addToWatchlist(symbol) {
    try {
      const data = await fetchStockPrice(symbol);
      if (this.watchlist.some(s => s.symbol === data.symbol)) {
        alert(`${data.symbol} already in watchlist.`);
        return;
      }
      this.watchlist.push(data);
      Storage.save('watchlist', this.watchlist);
      this.renderWatchlist();
    } catch (err) {
      alert(`Failed to add ${symbol}: ${err.message}`);
    }
  }

  async refreshOne(symbol) {
    try {
      const data = await fetchStockPrice(symbol);
      this.watchlist = this.watchlist.map(s => s.symbol === symbol ? data : s);
      Storage.save('watchlist', this.watchlist);
      this.renderWatchlist();
    } catch (err) {
      alert(`Failed to refresh ${symbol}: ${err.message}`);
    }
  }

  removeFromWatchlist(symbol) {
    this.watchlist = this.watchlist.filter(s => s.symbol !== symbol);
    Storage.save('watchlist', this.watchlist);
    this.renderWatchlist();
  }

  async refreshAll() {
    const updated = [];
    for (const s of this.watchlist) {
      try {
        const data = await fetchStockPrice(s.symbol);
        updated.push(data);
      } catch {
        updated.push(s);
      }
    }
    this.watchlist = updated;
    Storage.save('watchlist', this.watchlist);
    this.renderWatchlist();
  }

  renderWatchlist() {
    if (!this.watchlist.length) {
      this.$container.innerHTML = '';
      this.$empty.style.display = 'block';
      return;
    }

    this.$empty.style.display = 'none';
    this.$container.innerHTML = this.watchlist.map(s => this.cardHtml(s)).join('');
  }

  cardHtml(stock) {
    const changeClass = stock.change >= 0 ? 'positive' : 'negative';
    return `
      <div class="stock-card" data-symbol="${stock.symbol}">
        <div>
          <h3>${stock.symbol}</h3>
          <div class="meta">
            <span class="price">$${stock.price.toFixed(2)}</span>
            <span class="change ${changeClass}">
              ${stock.change >= 0 ? '+' : ''}${stock.change.toFixed(2)}
            </span>
          </div>
        </div>
        <div class="card-actions">
          <button data-action="refresh" data-symbol="${stock.symbol}">Refresh</button>
          <button data-action="remove" data-symbol="${stock.symbol}">Remove</button>
        </div>
      </div>
    `;
  }
}

document.addEventListener('DOMContentLoaded', () => {
  new TradingDashboard();
});
