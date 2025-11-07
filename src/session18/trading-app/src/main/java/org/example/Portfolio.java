package org.example;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "portfolios")
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "target_return", precision = 5, scale = 2)
    private BigDecimal targetReturn;

    @Enumerated(EnumType.STRING)
    @Column(name = "risk_level", length = 20)
    private RiskLevel riskLevel;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // One-to-Many: One portfolio has many positions
    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Position> positions = new ArrayList<>();

    // One-to-Many: One portfolio has many accounts
    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
    private List<TradingAccount> accounts = new ArrayList<>();

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

    // Constructors
    public Portfolio() {}

    public Portfolio(String name, RiskLevel riskLevel) {
        this.name = name;
        this.riskLevel = riskLevel;
        this.isActive = true;
    }

    // Helper methods for bidirectional relationships
    public void addPosition(Position position) {
        positions.add(position);
        position.setPortfolio(this);
    }

    public void removePosition(Position position) {
        positions.remove(position);
        position.setPortfolio(null);
    }

    public void addAccount(TradingAccount account) {
        accounts.add(account);
        account.setPortfolio(this);
    }

    public void removeAccount(TradingAccount account) {
        accounts.remove(account);
        account.setPortfolio(null);
    }

    // Business logic methods
    public BigDecimal getTotalValue() {
        return positions.stream()
                .map(Position::getCurrentValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotalProfitLoss() {
        return positions.stream()
                .map(Position::getProfitLoss)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Double getOverallReturn() {
        BigDecimal totalCost = positions.stream()
                .map(Position::getTotalCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalCost.compareTo(BigDecimal.ZERO) > 0) {
            return getTotalProfitLoss()
                    .divide(totalCost, 4, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal(100))
                    .doubleValue();
        }
        return 0.0;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getTargetReturn() { return targetReturn; }
    public void setTargetReturn(BigDecimal targetReturn) { this.targetReturn = targetReturn; }

    public RiskLevel getRiskLevel() { return riskLevel; }
    public void setRiskLevel(RiskLevel riskLevel) { this.riskLevel = riskLevel; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public List<Position> getPositions() { return positions; }
    public void setPositions(List<Position> positions) { this.positions = positions; }

    public List<TradingAccount> getAccounts() { return accounts; }
    public void setAccounts(List<TradingAccount> accounts) { this.accounts = accounts; }

    @Override
    public String toString() {
        return String.format(
                "Portfolio[id=%d, name='%s', positions=%d, totalValue=%.2f]",
                id, name, positions.size(), getTotalValue()
        );
    }
}
