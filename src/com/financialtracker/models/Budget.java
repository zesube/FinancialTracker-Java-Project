package com.financialtracker.models;

import java.util.UUID;

public class Budget {
    private final String budgetId;
    private String period;
    private double totalLimit;
    private double spentAmount;

    public Budget(String period, double totalLimit) {
        this.budgetId = UUID.randomUUID().toString();
        this.period = period;
        this.totalLimit = totalLimit;
        this.spentAmount = 0.0;
    }

    public String getBudgetId() {
        return budgetId;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public double getTotalLimit() {
        return totalLimit;
    }

    public void setTotalLimit(double totalLimit) {
        this.totalLimit = totalLimit;
    }

    public double getSpentAmount() {
        return spentAmount;
    }

    public void addSpending(double amount) {
        this.spentAmount += amount;
    }

    public double remainingAmount() {
        return totalLimit - spentAmount;
    }

    public boolean isOverBudget() {
        return spentAmount > totalLimit;
    }
}
