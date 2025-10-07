package udlib.ui;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import udlib.db.DBConnection;

public class ReportsFrame extends JFrame {

    private JLabel borrowedCountLabel, returnedCountLabel, activeCountLabel, penaltyLabel;
    private int borrowedCount, returnedCount, activeCount;

    public ReportsFrame() {
        setTitle("Reports - University of Davao Library System");
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

        JLabel title = new JLabel("Reports and Analytics");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Poppins", Font.BOLD, 24));
        leftHeader.add(title);
        headerPanel.add(leftHeader, BorderLayout.WEST);

        add(headerPanel, BorderLayout.NORTH);

        // Stats Grid
        JPanel mainPanel = new JPanel(new GridLayout(2,2,40,40));
        mainPanel.setBackground(new Color(227,242,253));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50,150,50,150));

        borrowedCountLabel = createStatCard("📚 Total Borrowed Books", "0");
        returnedCountLabel = createStatCard("📗 Total Returned Books", "0");
        activeCountLabel = createStatCard("🕐 Active Borrowings", "0");
        penaltyLabel = createStatCard("💸 Total Penalties", "₱0.00");

        mainPanel.add(borrowedCountLabel);
        mainPanel.add(returnedCountLabel);
        mainPanel.add(activeCountLabel);
        mainPanel.add(penaltyLabel);

        add(mainPanel, BorderLayout.CENTER);

        // Chart Panel
        ChartPanel chartPanel = new ChartPanel();
        chartPanel.setPreferredSize(new Dimension(400, 250));
        add(chartPanel, BorderLayout.SOUTH);

        // Footer
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        footer.setBackground(Color.WHITE);
        JButton backBtn = new JButton("← Back");
        backBtn.setFont(new Font("Poppins", Font.BOLD, 16));
        backBtn.setForeground(new Color(13,27,102));
        backBtn.setBackground(Color.WHITE);
        backBtn.setBorder(BorderFactory.createLineBorder(new Color(13,27,102),2,true));
        backBtn.addActionListener(e -> { new AdminDashboardFrame("Admin"); dispose(); });
        footer.add(backBtn);
        add(footer, BorderLayout.PAGE_END);

        // Load data
        updateReportData(chartPanel);

        setVisible(true);
    }

    private JLabel createStatCard(String title, String value) {
        JLabel label = new JLabel("<html><center>" + title + "<br><br><span style='font-size:40px;font-weight:bold;'>" + value + "</span></center></html>");
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        label.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(13,27,102),3,true),
            BorderFactory.createEmptyBorder(30,20,30,20)
        ));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Poppins", Font.PLAIN, 16));
        return label;
    }

    private void updateReportData(ChartPanel chartPanel) {
        double totalPenalty = 0;

        try (Connection conn = DBConnection.connect()) {
            Statement st = conn.createStatement();

            ResultSet rs1 = st.executeQuery("SELECT COUNT(*) AS cnt FROM borrowed_books");
            if (rs1.next()) borrowedCount = rs1.getInt("cnt");

            ResultSet rs2 = st.executeQuery("SELECT COUNT(*) AS cnt FROM returned_books");
            if (rs2.next()) returnedCount = rs2.getInt("cnt");

            ResultSet rs3 = st.executeQuery("SELECT COUNT(*) AS cnt FROM borrowed_books WHERE status='Borrowed'");
            if (rs3.next()) activeCount = rs3.getInt("cnt");

            ResultSet rs4 = st.executeQuery("SELECT SUM(penalty) AS total FROM returned_books");
            if (rs4.next()) totalPenalty = rs4.getDouble("total");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading report data: " + e.getMessage());
        }

        borrowedCountLabel.setText(formatCard("📚 Total Borrowed Books", borrowedCount + ""));
        returnedCountLabel.setText(formatCard("📗 Total Returned Books", returnedCount + ""));
        activeCountLabel.setText(formatCard("🕐 Active Borrowings", activeCount + ""));
        penaltyLabel.setText(formatCard("💸 Total Penalties", "₱" + String.format("%.2f", totalPenalty)));

        chartPanel.repaint();
    }

    private String formatCard(String title, String value) {
        return "<html><center>" + title + "<br><br><span style='font-size:40px;font-weight:bold;'>" + value + "</span></center></html>";
    }

    // 🎨 Inner Chart Panel Class
    private class ChartPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackground(Color.WHITE);

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight() - 40;
            int barWidth = 120;
            int baseY = height - 50;

            // Compute max for scaling
            int maxVal = Math.max(Math.max(borrowedCount, returnedCount), activeCount);
            maxVal = Math.max(maxVal, 1); // avoid divide by zero

            // Borrowed bar
            int borrowedBarHeight = (int) ((borrowedCount / (double) maxVal) * (height - 100));
            g2.setColor(new Color(25,118,210));
            g2.fillRoundRect(width/6 - barWidth/2, baseY - borrowedBarHeight, barWidth, borrowedBarHeight, 10,10);
            drawLabel(g2, "Borrowed", width/6 - 45, baseY + 30, borrowedCount);

            // Returned bar
            int returnedBarHeight = (int) ((returnedCount / (double) maxVal) * (height - 100));
            g2.setColor(new Color(56,142,60));
            g2.fillRoundRect(width/2 - barWidth/2, baseY - returnedBarHeight, barWidth, returnedBarHeight, 10,10);
            drawLabel(g2, "Returned", width/2 - 45, baseY + 30, returnedCount);

            // Active bar
            int activeBarHeight = (int) ((activeCount / (double) maxVal) * (height - 100));
            g2.setColor(new Color(255,167,38));
            g2.fillRoundRect((5*width)/6 - barWidth/2, baseY - activeBarHeight, barWidth, activeBarHeight, 10,10);
            drawLabel(g2, "Active", (5*width)/6 - 30, baseY + 30, activeCount);

            // Title
            g2.setColor(new Color(13,27,102));
            g2.setFont(new Font("Poppins", Font.BOLD, 18));
            g2.drawString("Borrowing Statistics Overview", width/2 - 140, 30);
        }

        private void drawLabel(Graphics2D g2, String text, int x, int y, int value) {
            g2.setColor(new Color(13,27,102));
            g2.setFont(new Font("Poppins", Font.PLAIN, 14));
            g2.drawString(text + " (" + value + ")", x, y);
        }
    }
}
