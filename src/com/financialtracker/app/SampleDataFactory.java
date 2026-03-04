package com.financialtracker.app;

import com.financialtracker.models.Budget;
import com.financialtracker.models.Category;
import com.financialtracker.models.Goal;
import com.financialtracker.models.Notification;
import com.financialtracker.models.Transaction;
import com.financialtracker.models.TransactionType;
import com.financialtracker.models.User;
import com.financialtracker.services.ExpenseTrackerService;
import java.time.LocalDate;

public final class SampleDataFactory {
    private SampleDataFactory() {
    }

    public static SampleData create() {
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

        trackerService.addTransaction(
                user, new Transaction(3200.00, LocalDate.now(), "Paycheck", TransactionType.INCOME, "income"));
        trackerService.addTransaction(
                user, new Transaction(1200.00, LocalDate.now(), "Rent", TransactionType.EXPENSE, housing.getCategoryId()));
        trackerService.addTransaction(
                user, new Transaction(180.00, LocalDate.now(), "Groceries", TransactionType.EXPENSE, food.getCategoryId()));
        trackerService.addTransaction(
                user, new Transaction(220.00, LocalDate.now(), "Electric + Internet", TransactionType.EXPENSE,
                        utilities.getCategoryId()));
        trackerService.addTransaction(
                user, new Transaction(500.00, LocalDate.now(), "Extra groceries + supplies", TransactionType.EXPENSE,
                        food.getCategoryId()));

        trackerService.contributeToGoal(user, emergencyFund, 400.00);
        user.addNotification(new Notification("Welcome! Your tracker is ready."));

        return new SampleData(user, emergencyFund, trackerService);
    }

    public static final class SampleData {
        private final User user;
        private final Goal goal;
        private final ExpenseTrackerService service;

        public SampleData(User user, Goal goal, ExpenseTrackerService service) {
            this.user = user;
            this.goal = goal;
            this.service = service;
        }

        public User getUser() {
            return user;
        }

        public Goal getGoal() {
            return goal;
        }

        public ExpenseTrackerService getService() {
            return service;
        }
    }
}
