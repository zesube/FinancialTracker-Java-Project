package com.financialtracker.app;

import com.financialtracker.models.Budget;
import com.financialtracker.models.DashboardSummary;
import com.financialtracker.models.Goal;
import com.financialtracker.models.Notification;
import com.financialtracker.models.Transaction;
import com.financialtracker.models.User;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        SampleDataFactory.SampleData sample = SampleDataFactory.create();
        User user = sample.getUser();
        Goal emergencyFund = sample.getGoal();

        if (args.length > 0 && "--ui".equalsIgnoreCase(args[0])) {
            FinancialTrackerUI.launch(user, emergencyFund, sample.getService());
            return;
        }

        DashboardSummary summary = user.getDashboardSummary();
        Map<String, Double> byCategory = sample.getService().getExpenseTotalsByCategory(user);

        System.out.println("=== Personal Expenses Calculator ===");
        System.out.println("User: " + user.getName());
        System.out.printf("Income: $%.2f%n", summary.getTotalIncome());
        System.out.printf("Expenses: $%.2f%n", summary.getTotalExpenses());
        System.out.printf("Net: $%.2f%n", summary.getNetAmount());

        Budget budget = user.getBudget();
        if (budget != null) {
            System.out.printf("Budget Limit: $%.2f%n", budget.getTotalLimit());
            System.out.printf("Budget Spent: $%.2f%n", budget.getSpentAmount());
        }

        System.out.printf("Remaining Budget: $%.2f%n", summary.getRemainingBudget());
        System.out.printf("Goal Progress (%s): %.2f%%%n", emergencyFund.getName(), emergencyFund.getProgressPercent());
        System.out.println();

        System.out.println("Transactions:");
        for (Transaction transaction : user.getTransactions()) {
            String categoryName = user.getCategoryNameById(transaction.getCategoryId());
            System.out.printf("- %s | %s | %s | $%.2f | %s%n",
                    transaction.getDate(),
                    transaction.getType(),
                    categoryName,
                    transaction.getAmount(),
                    transaction.getDescription());
        }
        System.out.println();

        System.out.println("Expense Totals by Category:");
        for (Map.Entry<String, Double> entry : byCategory.entrySet()) {
            System.out.printf("- %s: $%.2f%n", entry.getKey(), entry.getValue());
        }
        System.out.println();

        System.out.println("Notifications:");
        for (Notification notification : user.getNotifications()) {
            System.out.println("- " + notification.getMessage());
        }

        System.out.println();
        System.out.println("Tip: Run with --ui to launch the frontend preview window.");
    }
}
