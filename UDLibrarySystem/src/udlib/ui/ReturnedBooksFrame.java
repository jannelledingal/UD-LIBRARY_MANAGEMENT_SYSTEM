package udlib.ui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import udlib.db.DBConnection;

public class ReturnedBooksFrame extends JFrame {
    private DefaultTableModel borrowedModel, overdueModel;
    private JTable borrowedTable, overdueTable;
    private JLabel timerLabel;
    private LocalTime startTime;

    public ReturnedBooksFrame() {
        setTitle("Returned Books - University of Davao Library System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(13,27,102));

        JPanel leftHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        leftHeader.setBackground(new Color(13,27,102));
        JLabel logo = new JLabel(new ImageIcon(getClass().getResource("/udlib/assets/ud_logo.png")));
        logo.setPreferredSize(new Dimension(60,60));
        leftHeader.add(logo);

        JLabel title = new JLabel("Returned Books Panel");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Poppins", Font.BOLD, 24));
        leftHeader.add(title);
        headerPanel.add(leftHeader, BorderLayout.WEST);

        add(headerPanel, BorderLayout.NORTH);

        // Main panel
        JTabbedPane tabbedPane = new JTabbedPane();

        String[] borrowedCols = {"ID", "UD Name", "UD ID", "Book Title", "ISBN", "Borrow Date", "Due Date", "Status"};
        borrowedModel = new DefaultTableModel(borrowedCols, 0);
        borrowedTable = new JTable(borrowedModel);
        borrowedTable.setRowHeight(30);
        loadBorrowedBooks();

        String[] overdueCols = {"UD Name", "Book Title", "ISBN", "Due Date", "Days Late", "Penalty"};
        overdueModel = new DefaultTableModel(overdueCols, 0);
        overdueTable = new JTable(overdueModel);
        overdueTable.setRowHeight(30);
        loadOverdueBooks();

        tabbedPane.addTab("Borrowed Books", new JScrollPane(borrowedTable));
        tabbedPane.addTab("Overdue Books", new JScrollPane(overdueTable));

        add(tabbedPane, BorderLayout.CENTER);

        // Footer with buttons + timer
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(Color.WHITE);
        footerPanel.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, new Color(13,27,102)));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton markReturnedBtn = createButton("📦 Mark as Returned");
        JButton backBtn = createButton("← Back");

        markReturnedBtn.addActionListener(e -> markAsReturned());
        backBtn.addActionListener(e -> { new AdminDashboardFrame("Admin").setVisible(true); dispose(); });

        buttonPanel.add(markReturnedBtn);
        buttonPanel.add(backBtn);

        footerPanel.add(buttonPanel, BorderLayout.WEST);

        // Timer
        timerLabel = new JLabel("System Running Time: 00:00:00");
        timerLabel.setFont(new Font("Poppins", Font.PLAIN, 16));
        timerLabel.setForeground(new Color(13,27,102));
        JPanel timerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        timerPanel.setOpaque(false);
        timerPanel.add(timerLabel);
        footerPanel.add(timerPanel, BorderLayout.EAST);

        add(footerPanel, BorderLayout.SOUTH);

        // Start timer
        startTime = LocalTime.now();
        startTimer();

        setVisible(true);
    }

    private void startTimer() {
        Timer timer = new Timer(1000, e -> {
            Duration elapsed = Duration.between(startTime, LocalTime.now());
            long hours = elapsed.toHours();
            long minutes = elapsed.toMinutesPart();
            long seconds = elapsed.toSecondsPart();
            timerLabel.setText(String.format("System Running Time: %02d:%02d:%02d", hours, minutes, seconds));
        });
        timer.start();
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Poppins", Font.BOLD, 14));
        btn.setForeground(new Color(13,27,102));
        btn.setBackground(Color.WHITE);
        btn.setBorder(BorderFactory.createLineBorder(new Color(13,27,102),2,true));
        btn.setFocusPainted(false);
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(new Color(227,242,253)); }
            public void mouseExited(MouseEvent e) { btn.setBackground(Color.WHITE); }
        });
        return btn;
    }

    // === Database methods remain the same ===
    private void loadBorrowedBooks() { /* same as your version */ }
    private void loadOverdueBooks() { /* same as your version */ }
    private void markAsReturned() { /* same as your version */ }
}
