# Personal Expenses Calculator (Java)

This project is a Java OOP financial tracker focused on private and simple expense tracking.

## Implemented Classes

- `User`
- `Transaction`
- `Category`
- `Budget`
- `Goal`
- `Notification`
- `ExpenseTrackerService`
- `DashboardSummary`
- `TransactionType` (enum)
- `FinancialTrackerUI` (frontend preview)

## Build

```bash
javac -d out $(find src -name "*.java")
```

## Run (Console)

```bash
java -cp out com.financialtracker.app.Main
```

## Run (Frontend Preview)

```bash
java -cp out com.financialtracker.app.Main --ui
```

You can also launch the UI class directly:

```bash
java -cp out com.financialtracker.app.FinancialTrackerUI
```

## What the Program Produces

- Dashboard totals (income, expenses, net)
- Budget status (limit, spent, remaining)
- Goal progress percentage
- Full transaction list with category labels
- Expense totals by category
- Notifications (budget alerts, app alerts)
- Frontend preview window with dashboard, tables, and notifications

## UML

See [docs/UML.md](docs/UML.md) for the class diagram that matches the implemented classes.

## Submission Checklist (Rubric)

- `Code`: all classes and service logic are implemented.
- `Compilation`: compile with the commands above.
- `Program Execution`: run `Main` with no errors.
- `Results`: output demonstrates required financial tracking behavior with test data.
- `Coding Style & UML`: consistent formatting, comments, and UML included.
- `GitHub`: push this folder to your repository and submit the repo URL.
- `Screenshots`: capture console output and frontend preview using test data.
