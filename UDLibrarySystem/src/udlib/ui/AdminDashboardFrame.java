package udlib.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdminDashboardFrame extends JFrame {

    public AdminDashboardFrame(String adminName) {
        setTitle("Admin Dashboard - University of Davao Library System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(13, 27, 102));

        JPanel leftHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        leftHeader.setBackground(new Color(13, 27, 102));
        JLabel logo = new JLabel(new ImageIcon(getClass().getResource("/udlib/assets/ud_logo.png")));
        logo.setPreferredSize(new Dimension(60, 60));
        leftHeader.add(logo);

        JLabel headerTitle = new JLabel("University of Davao Library System");
        headerTitle.setForeground(Color.WHITE);
        headerTitle.setFont(new Font("Poppins", Font.BOLD, 22));
        leftHeader.add(headerTitle);

        headerPanel.add(leftHeader, BorderLayout.WEST);

        JLabel adminLabel = new JLabel("Welcome, " + adminName);
        adminLabel.setForeground(Color.WHITE);
        adminLabel.setFont(new Font("Poppins", Font.PLAIN, 18));
        JPanel rightHeader = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        rightHeader.setBackground(new Color(13, 27, 102));
        rightHeader.add(adminLabel);
        headerPanel.add(rightHeader, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // Main body
        JPanel mainPanel = new JPanel(new GridLayout(2, 2, 40, 40));
        mainPanel.setBackground(new Color(227, 242, 253));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(100, 150, 100, 150));

        JButton manageBooksBtn = createButton("📚 Manage Books");
        JButton bookRequestsBtn = createButton("📩 Book Requests");
        JButton returnedBooksBtn = createButton("📗 Returned Books");
        JButton reportsBtn = createButton("📊 Reports");

        manageBooksBtn.addActionListener(e -> {
            new ManageBooksFrame();
            dispose();
        });

        bookRequestsBtn.addActionListener(e -> {
            new BookRequestsFrame();
            dispose();
        });

        returnedBooksBtn.addActionListener(e -> {
            new ReturnedBooksFrame();
            dispose();
        });

        reportsBtn.addActionListener(e -> {
            new ReportsFrame();
            dispose();
        });

        mainPanel.add(manageBooksBtn);
        mainPanel.add(bookRequestsBtn);
        mainPanel.add(returnedBooksBtn);
        mainPanel.add(reportsBtn);

        add(mainPanel, BorderLayout.CENTER);

        // Footer
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        footerPanel.setBackground(Color.WHITE);

        JButton logoutBtn = createButton("← Logout");
        logoutBtn.addActionListener(e -> {
            new AdminLoginFrame();
            dispose();
        });

        footerPanel.add(logoutBtn);
        add(footerPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Poppins", Font.BOLD, 20));
        btn.setForeground(new Color(13, 27, 102));
        btn.setBackground(Color.WHITE);
        btn.setBorder(BorderFactory.createLineBorder(new Color(13, 27, 102), 3, true));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(227, 242, 253));
            }

            public void mouseExited(MouseEvent e) {
                btn.setBackground(Color.WHITE);
            }
        });
        return btn;
    }
}
