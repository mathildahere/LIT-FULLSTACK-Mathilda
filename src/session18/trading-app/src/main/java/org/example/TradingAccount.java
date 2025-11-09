package org.example;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "trading_accounts",
        indexes = {
                @Index(name = "idx_account_name", columnList = "account_name"),
                @Index(name = "idx_created_date", columnList = "created_at")
        })
public class TradingAccount {

    // Primary Key with Auto-generation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Basic columns with constraints
    @Column(name = "account_name", nullable = false, length = 100, unique = true)
    private String accountName;

    @Column(name = "account_type", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)  // Store as string, not ordinal
    private AccountType accountType;

    @Column(name = "balance", nullable = false, precision = 15, scale = 2)
    private BigDecimal balance;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    // Temporal fields
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Lifecycle callbacks
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // One-to-Many relationship
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Trade> trades = new ArrayList<>();

    // Many-to-One relationship
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id", nullable = true)
    private Portfolio portfolio;

    // Constructors
    public TradingAccount() {
        // Required by Hibernate
    }

    public TradingAccount(String accountName, AccountType accountType, BigDecimal balance) {
        this.accountName = accountName;
        this.accountType = accountType;
        this.balance = balance;
        this.isActive = true;
    }

    // Helper methods for bidirectional relationships
    public void addTrade(Trade trade) {
        trades.add(trade);
        trade.setAccount(this);
    }

    public void removeTrade(Trade trade) {
        trades.remove(trade);
        trade.setAccount(null);
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAccountName() { return accountName; }
    public void setAccountName(String accountName) { this.accountName = accountName; }

    public AccountType getAccountType() { return accountType; }
    public void setAccountType(AccountType accountType) { this.accountType = accountType; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public List<Trade> getTrades() { return trades; }
    public void setTrades(List<Trade> trades) { this.trades = trades; }

    public Portfolio getPortfolio() { return portfolio; }
    public void setPortfolio(Portfolio portfolio) { this.portfolio = portfolio; }

    @Override
    public String toString() {
        return String.format("TradingAccount[id=%d, name='%s', type=%s, balance=%.2f, trades=%d]",
                id, accountName, accountType, balance, trades.size());
    }
}


