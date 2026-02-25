package com.financialtracker.models;

import java.util.UUID;

public class Category {
    private final String categoryId;
    private String name;
    private double monthlyLimit;

    public Category(String name, double monthlyLimit) {
        this.categoryId = UUID.randomUUID().toString();
        this.name = name;
        this.monthlyLimit = monthlyLimit;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMonthlyLimit() {
        return monthlyLimit;
    }

    public void updateLimit(double monthlyLimit) {
        this.monthlyLimit = monthlyLimit;
    }
}
