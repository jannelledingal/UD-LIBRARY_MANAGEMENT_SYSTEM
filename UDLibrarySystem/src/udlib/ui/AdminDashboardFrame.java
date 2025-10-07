package udlib.ui;

import javax.swing.*;
import java.awt.*;

public class AdminDashboardFrame extends JFrame {

    public AdminDashboardFrame(String adminName) {
        setTitle("Admin Dashboard - University of Davao Library System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(230, 240, 255)); // light blue-gray bg

        // ===== HEADER =====
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(10, 30, 90));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        // Left side logo + title
        JPanel leftHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        leftHeader.setOpaque(false);
        JLabel logo = new JLabel(new ImageIcon(getClass().getResource("/udlib/assets/ud_logo.png")));
        logo.setPreferredSize(new Dimension(70, 70));
        leftHeader.add(logo);

        JLabel headerTitle = new JLabel("University of Davao Library System");
        headerTitle.setForeground(Color.WHITE);
        headerTitle.setFont(new Font("Poppins", Font.BOLD, 26));
        leftHeader.add(headerTitle);
        headerPanel.add(leftHeader, BorderLayout.WEST);

        // Right side welcome
        JLabel adminLabel = new JLabel("Welcome, " + adminName);
        adminLabel.setForeground(Color.WHITE);
        adminLabel.setFont(new Font("Poppins", Font.PLAIN, 20));
        JPanel rightHeader = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 5));
        rightHeader.setOpaque(false);
        rightHeader.add(adminLabel);
        headerPanel.add(rightHeader, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        // ===== MAIN BUTTONS PANEL =====
        JPanel mainPanel = new JPanel(new GridLayout(2, 2, 50, 50));
        mainPanel.setBackground(new Color(230, 240, 255));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(120, 220, 120, 220));

        JButton manageBooksBtn = createModernButton("📚 Manage Books");
        JButton bookRequestsBtn = createModernButton("📩 Book Requests");
        JButton returnedBooksBtn = createModernButton("📗 Returned Books");
        JButton reportsBtn = createModernButton("📊 Reports");

        // Frame navigation
        manageBooksBtn.addActionListener(e -> {
            new ManageBooksFrame().setVisible(true);
            dispose();
        });
        bookRequestsBtn.addActionListener(e -> {
            new BookRequestsFrame().setVisible(true);
            dispose();
        });
        returnedBooksBtn.addActionListener(e -> {
            new ReturnedBooksFrame().setVisible(true);
            dispose();
        });
        reportsBtn.addActionListener(e -> {
            new ReportsFrame().setVisible(true);
            dispose();
        });

        mainPanel.add(manageBooksBtn);
        mainPanel.add(bookRequestsBtn);
        mainPanel.add(returnedBooksBtn);
        mainPanel.add(reportsBtn);
        add(mainPanel, BorderLayout.CENTER);

        // ===== FOOTER =====
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        footerPanel.setBackground(Color.WHITE);
        footerPanel.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, new Color(10, 30, 90)));

        JButton logoutBtn = new JButton("← Logout");
        logoutBtn.setFocusPainted(false);
        logoutBtn.setFont(new Font("Poppins", Font.BOLD, 16));
        logoutBtn.setBackground(new Color(10, 30, 90));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setPreferredSize(new Dimension(160, 45));
        logoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutBtn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(10, 30, 90), 2),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        logoutBtn.addActionListener(e -> {
            new AdminLoginFrame().setVisible(true);
            dispose();
        });
        footerPanel.add(logoutBtn);
        add(footerPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // ===== CUSTOM STYLED BUTTON CREATOR =====
    private JButton createModernButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setFont(new Font("Poppins", Font.BOLD, 20));
        button.setBackground(new Color(21, 101, 192));
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(260, 120));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(10, 30, 90), 2, true),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Subtle hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(25, 118, 210));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(21, 101, 192));
            }
        });

        // Shadow effect
        button.setOpaque(true);
        return button;
    }
}
