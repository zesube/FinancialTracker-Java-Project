package com.financialtracker.app;

import com.financialtracker.models.Budget;
import com.financialtracker.models.Category;
import com.financialtracker.models.DashboardSummary;
import com.financialtracker.models.Goal;
import com.financialtracker.models.Notification;
import com.financialtracker.models.Transaction;
import com.financialtracker.models.TransactionType;
import com.financialtracker.models.User;
import com.financialtracker.services.ExpenseTrackerService;
import java.time.LocalDate;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        ExpenseTrackerService trackerService = new ExpenseTrackerService();

        User user = new User("Cadeau", "", "Simple and private");

        Budget budget = new Budget("MONTHLY", 2000.00);
        trackerService.setBudget(user, budget);

        Category housing = new Category("Housing", 1000.00);
        Category food = new Category("Food", 400.00);
        Category utilities = new Category("Utilities", 250.00);
        trackerService.addCategory(user, housing);
        trackerService.addCategory(user, food);
        trackerService.addCategory(user, utilities);

        Goal emergencyFund = new Goal("Emergency Fund", 1000.00, LocalDate.now().plusMonths(3));
        trackerService.addGoal(user, emergencyFund);

        // Seed with test data to prove behavior and output.
        trackerService.addTransaction(user,
                new Transaction(3200.00, LocalDate.now(), "Paycheck", TransactionType.INCOME, "income"));
        trackerService.addTransaction(user,
                new Transaction(1200.00, LocalDate.now(), "Rent", TransactionType.EXPENSE, housing.getCategoryId()));
        trackerService.addTransaction(user,
                new Transaction(180.00, LocalDate.now(), "Groceries", TransactionType.EXPENSE, food.getCategoryId()));
        trackerService.addTransaction(user,
                new Transaction(220.00, LocalDate.now(), "Electric + Internet", TransactionType.EXPENSE,
                        utilities.getCategoryId()));
        trackerService.addTransaction(user,
                new Transaction(500.00, LocalDate.now(), "Extra groceries + supplies", TransactionType.EXPENSE,
                        food.getCategoryId()));

        trackerService.contributeToGoal(user, emergencyFund, 400.00);
        user.addNotification(new Notification("Welcome! Your tracker is ready."));

        DashboardSummary summary = user.getDashboardSummary();
        Map<String, Double> byCategory = trackerService.getExpenseTotalsByCategory(user);

        System.out.println("=== Personal Expenses Calculator ===");
        System.out.println("User: " + user.getName());
        System.out.printf("Income: $%.2f%n", summary.getTotalIncome());
        System.out.printf("Expenses: $%.2f%n", summary.getTotalExpenses());
        System.out.printf("Net: $%.2f%n", summary.getNetAmount());
        System.out.printf("Budget Limit: $%.2f%n", budget.getTotalLimit());
        System.out.printf("Budget Spent: $%.2f%n", budget.getSpentAmount());
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
    }
}
