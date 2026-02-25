package com.financialtracker.services;

import com.financialtracker.models.Budget;
import com.financialtracker.models.Category;
import com.financialtracker.models.Goal;
import com.financialtracker.models.Notification;
import com.financialtracker.models.Transaction;
import com.financialtracker.models.User;
import java.util.LinkedHashMap;
import java.util.Map;

public class ExpenseTrackerService {
    public void addTransaction(User user, Transaction transaction) {
        user.addTransaction(transaction);
        checkBudgetAlerts(user);
    }

    public void setBudget(User user, Budget budget) {
        user.setBudget(budget);
    }

    public void addGoal(User user, Goal goal) {
        user.createGoal(goal);
    }

    public void addCategory(User user, Category category) {
        user.addCategory(category);
    }

    public void contributeToGoal(User user, Goal goal, double amount) {
        goal.addContribution(amount);
        if (goal.isCompleted()) {
            user.addNotification(new Notification("Goal completed: " + goal.getName()));
        }
    }

    public Map<String, Double> getExpenseTotalsByCategory(User user) {
        Map<String, Double> totals = new LinkedHashMap<>();
        for (Transaction transaction : user.getTransactions()) {
            if (!transaction.isExpense()) {
                continue;
            }
            String categoryName = user.getCategoryNameById(transaction.getCategoryId());
            totals.put(categoryName, totals.getOrDefault(categoryName, 0.0) + transaction.getAmount());
        }
        return totals;
    }

    private void checkBudgetAlerts(User user) {
        Budget budget = user.getBudget();
        if (budget == null) {
            return;
        }

        double spent = budget.getSpentAmount();
        double limit = budget.getTotalLimit();
        if (limit <= 0) {
            return;
        }

        double percentUsed = spent / limit;
        if (percentUsed >= 1.0) {
            user.addNotification(new Notification("You are over budget."));
        } else if (percentUsed >= 0.9) {
            user.addNotification(new Notification("You have used 90% of your budget."));
        }
    }
}
