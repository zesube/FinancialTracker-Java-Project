package com.financialtracker.models;

import java.time.LocalDate;
import java.util.UUID;

public class Goal {
    private final String goalId;
    private String name;
    private double targetAmount;
    private double currentAmount;
    private LocalDate deadline;

    public Goal(String name, double targetAmount, LocalDate deadline) {
        this.goalId = UUID.randomUUID().toString();
        this.name = name;
        this.targetAmount = targetAmount;
        this.currentAmount = 0.0;
        this.deadline = deadline;
    }

    public String getGoalId() {
        return goalId;
    }

    public String getName() {
        return name;
    }

    public double getTargetAmount() {
        return targetAmount;
    }

    public double getCurrentAmount() {
        return currentAmount;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void addContribution(double amount) {
        this.currentAmount += amount;
    }

    public double getProgressPercent() {
        if (targetAmount <= 0) {
            return 0.0;
        }
        return (currentAmount / targetAmount) * 100.0;
    }

    public boolean isCompleted() {
        return currentAmount >= targetAmount;
    }
}
