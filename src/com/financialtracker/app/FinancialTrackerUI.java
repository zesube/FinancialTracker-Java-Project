package com.financialtracker.app;

import com.financialtracker.models.DashboardSummary;
import com.financialtracker.models.Goal;
import com.financialtracker.models.Notification;
import com.financialtracker.models.Transaction;
import com.financialtracker.models.User;
import com.financialtracker.services.ExpenseTrackerService;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class FinancialTrackerUI {
    private static final NumberFormat CURRENCY = NumberFormat.getCurrencyInstance(Locale.US);

    public static void main(String[] args) {
        SampleDataFactory.SampleData sample = SampleDataFactory.create();
        launch(sample.getUser(), sample.getGoal(), sample.getService());
    }

    public static void launch(User user, Goal goal, ExpenseTrackerService service) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Personal Expenses Calculator - Preview");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1050, 700);
            frame.setLocationRelativeTo(null);

            JPanel root = new JPanel(new BorderLayout(12, 12));
            root.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
            root.setBackground(new Color(246, 248, 252));

            root.add(buildHeader(user, goal), BorderLayout.NORTH);
            root.add(buildBody(user, service), BorderLayout.CENTER);

            frame.setContentPane(root);
            frame.setVisible(true);
        });
    }

    private static JPanel buildHeader(User user, Goal goal) {
        DashboardSummary summary = user.getDashboardSummary();

        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 233)),
                BorderFactory.createEmptyBorder(12, 14, 12, 14)));

        JLabel title = new JLabel("Personal Expenses Dashboard");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setForeground(new Color(28, 39, 57));

        JLabel subtitle = new JLabel("User: " + user.getName() + " | Goal: " + goal.getName());
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitle.setForeground(new Color(82, 95, 127));

        JLabel metrics = new JLabel(
                "Income: " + CURRENCY.format(summary.getTotalIncome())
                        + "   Expenses: " + CURRENCY.format(summary.getTotalExpenses())
                        + "   Net: " + CURRENCY.format(summary.getNetAmount())
                        + "   Remaining Budget: " + CURRENCY.format(summary.getRemainingBudget())
                        + "   Goal Progress: " + String.format("%.2f%%", goal.getProgressPercent()));
        metrics.setFont(new Font("SansSerif", Font.BOLD, 13));
        metrics.setForeground(new Color(31, 41, 55));

        header.add(title);
        header.add(Box.createVerticalStrut(4));
        header.add(subtitle);
        header.add(Box.createVerticalStrut(8));
        header.add(metrics);
        return header;
    }

    private static JSplitPane buildBody(User user, ExpenseTrackerService service) {
        JTable transactionsTable = buildTransactionsTable(user);
        JTable categoryTable = buildCategoryTable(user, service);
        JList<String> notificationsList = buildNotificationsList(user);

        JPanel left = new JPanel(new BorderLayout(10, 10));
        left.setBackground(Color.WHITE);
        left.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 233)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        JLabel txLabel = new JLabel("Transactions");
        txLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        left.add(txLabel, BorderLayout.NORTH);
        left.add(new JScrollPane(transactionsTable), BorderLayout.CENTER);

        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        right.setBackground(new Color(246, 248, 252));

        JPanel categoryCard = new JPanel(new BorderLayout(8, 8));
        categoryCard.setBackground(Color.WHITE);
        categoryCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 233)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        JLabel catLabel = new JLabel("Category Totals");
        catLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        categoryCard.add(catLabel, BorderLayout.NORTH);
        categoryCard.add(new JScrollPane(categoryTable), BorderLayout.CENTER);
        categoryCard.setPreferredSize(new Dimension(320, 280));

        JPanel notificationsCard = new JPanel(new BorderLayout(8, 8));
        notificationsCard.setBackground(Color.WHITE);
        notificationsCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 233)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        JLabel nLabel = new JLabel("Notifications");
        nLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        notificationsCard.add(nLabel, BorderLayout.NORTH);
        notificationsCard.add(new JScrollPane(notificationsList), BorderLayout.CENTER);
        notificationsCard.setPreferredSize(new Dimension(320, 280));

        right.add(categoryCard);
        right.add(Box.createVerticalStrut(12));
        right.add(notificationsCard);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, right);
        splitPane.setDividerLocation(680);
        splitPane.setResizeWeight(0.70);
        splitPane.setBorder(BorderFactory.createEmptyBorder());
        return splitPane;
    }

    private static JTable buildTransactionsTable(User user) {
        DefaultTableModel model = new DefaultTableModel(
                new Object[] {"Date", "Type", "Category", "Amount", "Description"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Transaction transaction : user.getTransactions()) {
            model.addRow(new Object[] {
                    transaction.getDate(),
                    transaction.getType(),
                    user.getCategoryNameById(transaction.getCategoryId()),
                    CURRENCY.format(transaction.getAmount()),
                    transaction.getDescription()
            });
        }

        JTable table = new JTable(model);
        table.setRowHeight(24);
        table.setFillsViewportHeight(true);
        return table;
    }

    private static JTable buildCategoryTable(User user, ExpenseTrackerService service) {
        DefaultTableModel model = new DefaultTableModel(new Object[] {"Category", "Total"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        Map<String, Double> totals = service.getExpenseTotalsByCategory(user);
        for (Map.Entry<String, Double> entry : totals.entrySet()) {
            model.addRow(new Object[] {entry.getKey(), CURRENCY.format(entry.getValue())});
        }

        JTable table = new JTable(model);
        table.setRowHeight(24);
        table.setFillsViewportHeight(true);
        return table;
    }

    private static JList<String> buildNotificationsList(User user) {
        String[] notifications = new String[user.getNotifications().size()];
        for (int i = 0; i < user.getNotifications().size(); i++) {
            Notification notification = user.getNotifications().get(i);
            notifications[i] = notification.getCreatedAt().toLocalTime().withNano(0) + " - " + notification.getMessage();
        }
        return new JList<>(notifications);
    }
}
