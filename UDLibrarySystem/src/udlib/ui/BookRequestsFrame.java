package udlib.ui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import udlib.db.DBConnection;

public class BookRequestsFrame extends JFrame {
    private DefaultTableModel model;
    private JTable table;
    private JLabel timerLabel;
    private LocalTime startTime;

    public BookRequestsFrame() {
        setTitle("Book Requests - University of Davao Library System");
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

        JLabel title = new JLabel("Book Requests Panel");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Poppins", Font.BOLD, 24));
        leftHeader.add(title);
        headerPanel.add(leftHeader, BorderLayout.WEST);

        add(headerPanel, BorderLayout.NORTH);

        // Table
        String[] cols = {"Request ID", "UD Member Name", "UD ID", "Book Title", "ISBN", "Request Date", "Status"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        table.setRowHeight(30);
        loadRequestsFromDB();

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons + Timer
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(Color.WHITE);
        footerPanel.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, new Color(13,27,102)));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton approveBtn = createButton("✅ Approve Request");
        JButton rejectBtn = createButton("❌ Reject Request");
        JButton backBtn = createButton("← Back");

        approveBtn.addActionListener(e -> approveRequest());
        rejectBtn.addActionListener(e -> rejectRequest());
        backBtn.addActionListener(e -> { new AdminDashboardFrame("Admin").setVisible(true); dispose(); });

        buttonPanel.add(approveBtn);
        buttonPanel.add(rejectBtn);
        buttonPanel.add(backBtn);

        footerPanel.add(buttonPanel, BorderLayout.WEST);

        // Timer (Right side)
        timerLabel = new JLabel("System Running Time: 00:00:00");
        timerLabel.setFont(new Font("Poppins", Font.PLAIN, 16));
        timerLabel.setForeground(new Color(13,27,102));
        JPanel timerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        timerPanel.setOpaque(false);
        timerPanel.add(timerLabel);
        footerPanel.add(timerPanel, BorderLayout.EAST);

        add(footerPanel, BorderLayout.SOUTH);

        // Start Timer
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

    private void loadRequestsFromDB() {
        model.setRowCount(0);
        try (Connection conn = DBConnection.connect()) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT id, ud_name, ud_id, book_title, book_isbn, request_date, status FROM book_requests ORDER BY request_date DESC");
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("ud_name"),
                    rs.getString("ud_id"),
                    rs.getString("book_title"),
                    rs.getString("book_isbn"),
                    rs.getString("request_date"),
                    rs.getString("status")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading requests: " + e.getMessage());
        }
    }

    private void approveRequest() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a request to approve.");
            return;
        }

        int id = Integer.parseInt(model.getValueAt(row, 0).toString());
        String name = model.getValueAt(row, 1).toString();
        String udid = model.getValueAt(row, 2).toString();
        String book = model.getValueAt(row, 3).toString();
        String isbn = model.getValueAt(row, 4).toString();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime due = now.plusDays(3); // 3 Days Due date for Penalty
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try (Connection conn = DBConnection.connect()) {
            conn.setAutoCommit(false);

            PreparedStatement ps1 = conn.prepareStatement("UPDATE book_requests SET status='Approved' WHERE id=?");
            ps1.setInt(1, id);
            ps1.executeUpdate();

            PreparedStatement ps2 = conn.prepareStatement(
                "INSERT INTO borrowed_books (ud_name, ud_id, book_title, book_isbn, borrow_date, due_date, status) VALUES (?, ?, ?, ?, ?, ?, 'Borrowed')");
            ps2.setString(1, name);
            ps2.setString(2, udid);
            ps2.setString(3, book);
            ps2.setString(4, isbn);
            ps2.setString(5, now.format(formatter));
            ps2.setString(6, due.format(formatter));
            ps2.executeUpdate();

            PreparedStatement ps3 = conn.prepareStatement("UPDATE books SET availability='Not Available' WHERE isbn=?");
            ps3.setString(1, isbn);
            ps3.executeUpdate();

            conn.commit();
            JOptionPane.showMessageDialog(this, "✅ Request approved successfully!\nBorrow period until: " +
                    due.format(DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm")));

            new ReceiptFrame(name, udid, book, isbn, now.format(formatter), due.format(formatter)).setVisible(true);
            loadRequestsFromDB();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error approving request: " + e.getMessage());
        }
    }

    private void rejectRequest() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a request to reject.");
            return;
        }

        int id = Integer.parseInt(model.getValueAt(row, 0).toString());
        try (Connection conn = DBConnection.connect()) {
            PreparedStatement ps = conn.prepareStatement("UPDATE book_requests SET status='Rejected' WHERE id=?");
            ps.setInt(1, id);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "❌ Request rejected.");
            loadRequestsFromDB();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error rejecting request: " + e.getMessage());
        }
    }
}
