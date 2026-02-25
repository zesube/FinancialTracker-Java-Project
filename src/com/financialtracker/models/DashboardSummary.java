package com.financialtracker.models;

public class DashboardSummary {
    private final double totalIncome;
    private final double totalExpenses;
    private final double netAmount;
    private final double remainingBudget;

    public DashboardSummary(double totalIncome, double totalExpenses, double netAmount, double remainingBudget) {
        this.totalIncome = totalIncome;
        this.totalExpenses = totalExpenses;
        this.netAmount = netAmount;
        this.remainingBudget = remainingBudget;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public double getTotalExpenses() {
        return totalExpenses;
    }

    public double getNetAmount() {
        return netAmount;
    }

    public double getRemainingBudget() {
        return remainingBudget;
    }
}
