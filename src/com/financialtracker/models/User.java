package com.financialtracker.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class User {
    private final String userId;
    private String name;
    private String email;
    private String preferences;
    private Budget budget;
    private final List<Transaction> transactions;
    private final List<Goal> goals;
    private final List<Notification> notifications;
    private final List<Category> categories;

    public User(String name, String email, String preferences) {
        this.userId = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.preferences = preferences;
        this.transactions = new ArrayList<>();
        this.goals = new ArrayList<>();
        this.notifications = new ArrayList<>();
        this.categories = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPreferences() {
        return preferences;
    }

    public Budget getBudget() {
        return budget;
    }

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    public List<Goal> getGoals() {
        return Collections.unmodifiableList(goals);
    }

    public List<Notification> getNotifications() {
        return Collections.unmodifiableList(notifications);
    }

    public List<Category> getCategories() {
        return Collections.unmodifiableList(categories);
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        if (budget != null && transaction.isExpense()) {
            budget.addSpending(transaction.getAmount());
        }
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    public void createGoal(Goal goal) {
        goals.add(goal);
    }

    public void addNotification(Notification notification) {
        notifications.add(notification);
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

    public String getCategoryNameById(String categoryId) {
        for (Category category : categories) {
            if (category.getCategoryId().equals(categoryId)) {
                return category.getName();
            }
        }
        return "Uncategorized";
    }

    public DashboardSummary getDashboardSummary() {
        double income = 0.0;
        double expenses = 0.0;

        // Consolidate totals for the dashboard card values.
        for (Transaction transaction : transactions) {
            if (transaction.isIncome()) {
                income += transaction.getAmount();
            } else if (transaction.isExpense()) {
                expenses += transaction.getAmount();
            }
        }

        double net = income - expenses;
        double remaining = budget == null ? 0.0 : budget.remainingAmount();
        return new DashboardSummary(income, expenses, net, remaining);
    }
}
