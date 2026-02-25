package com.financialtracker.models;

import java.time.LocalDate;
import java.util.UUID;

public class Transaction {
    private final String transactionId;
    private double amount;
    private LocalDate date;
    private String description;
    private TransactionType type;
    private String categoryId;

    public Transaction(
            double amount, LocalDate date, String description, TransactionType type, String categoryId) {
        this.transactionId = UUID.randomUUID().toString();
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.type = type;
        this.categoryId = categoryId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public boolean isExpense() {
        return type == TransactionType.EXPENSE;
    }

    public boolean isIncome() {
        return type == TransactionType.INCOME;
    }
}
