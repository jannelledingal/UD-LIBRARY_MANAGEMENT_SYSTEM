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

        // Borrowed Books Table
        String[] borrowedCols = {"ID", "UD Name", "UD ID", "Book Title", "ISBN", "Borrow Date", "Due Date", "Status"};
        borrowedModel = new DefaultTableModel(borrowedCols, 0);
        borrowedTable = new JTable(borrowedModel);
        borrowedTable.setRowHeight(30);
        loadBorrowedBooks();

        // Overdue Books Table
        String[] overdueCols = {"UD Name", "Book Title", "ISBN", "Due Date", "Days Late", "Penalty"};
        overdueModel = new DefaultTableModel(overdueCols, 0);
        overdueTable = new JTable(overdueModel);
        overdueTable.setRowHeight(30);
        loadOverdueBooks();

        tabbedPane.addTab("Borrowed Books", new JScrollPane(borrowedTable));
        tabbedPane.addTab("Overdue Books", new JScrollPane(overdueTable));

        add(tabbedPane, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton markReturnedBtn = createButton("📦 Mark as Returned");
        JButton backBtn = createButton("← Back");

        markReturnedBtn.addActionListener(e -> markAsReturned());
        backBtn.addActionListener(e -> { new AdminDashboardFrame("Admin"); dispose(); });

        buttonPanel.add(markReturnedBtn);
        buttonPanel.add(backBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
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

    private void loadBorrowedBooks() {
        borrowedModel.setRowCount(0);
        try (Connection conn = DBConnection.connect()) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM borrowed_books WHERE status='Borrowed'");
            while (rs.next()) {
                borrowedModel.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("ud_name"),
                    rs.getString("ud_id"),
                    rs.getString("book_title"),
                    rs.getString("book_isbn"),
                    rs.getString("borrow_date"),
                    rs.getString("due_date"),
                    rs.getString("status")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading borrowed books: " + e.getMessage());
        }
    }

    private void loadOverdueBooks() {
        overdueModel.setRowCount(0);
        try (Connection conn = DBConnection.connect()) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT ud_name, book_title, book_isbn, due_date FROM borrowed_books WHERE status='Borrowed'");
            while (rs.next()) {
                LocalDate dueDate = LocalDate.parse(rs.getString("due_date").substring(0,10));
                LocalDate today = LocalDate.now();
                if (today.isAfter(dueDate)) {
                    long daysLate = ChronoUnit.DAYS.between(dueDate, today);
                    double penalty = daysLate * 25.0;
                    overdueModel.addRow(new Object[]{
                        rs.getString("ud_name"),
                        rs.getString("book_title"),
                        rs.getString("book_isbn"),
                        rs.getString("due_date"),
                        daysLate,
                        "₱" + penalty
                    });
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading overdue books: " + e.getMessage());
        }
    }

    private void markAsReturned() {
        int row = borrowedTable.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Select a borrowed book to mark as returned."); return; }

        int id = Integer.parseInt(borrowedModel.getValueAt(row, 0).toString());
        String name = borrowedModel.getValueAt(row, 1).toString();
        String udid = borrowedModel.getValueAt(row, 2).toString();
        String title = borrowedModel.getValueAt(row, 3).toString();
        String isbn = borrowedModel.getValueAt(row, 4).toString();
        String dueDateStr = borrowedModel.getValueAt(row, 6).toString();

        try (Connection conn = DBConnection.connect()) {
            LocalDate dueDate = LocalDate.parse(dueDateStr.substring(0,10));
            LocalDate today = LocalDate.now();
            long daysLate = ChronoUnit.DAYS.between(dueDate, today);
            double penalty = daysLate > 0 ? daysLate * 25.0 : 0.0;

            // Move to returned_books
            PreparedStatement ps1 = conn.prepareStatement(
                "INSERT INTO returned_books (ud_name, ud_id, book_title, book_isbn, penalty) VALUES (?, ?, ?, ?, ?)");
            ps1.setString(1, name);
            ps1.setString(2, udid);
            ps1.setString(3, title);
            ps1.setString(4, isbn);
            ps1.setDouble(5, penalty);
            ps1.executeUpdate();

            // Update borrowed_books
            PreparedStatement ps2 = conn.prepareStatement("UPDATE borrowed_books SET status='Returned' WHERE id=?");
            ps2.setInt(1, id);
            ps2.executeUpdate();

            // Update book availability
            PreparedStatement ps3 = conn.prepareStatement("UPDATE books SET availability='Available' WHERE isbn=?");
            ps3.setString(1, isbn);
            ps3.executeUpdate();

            JOptionPane.showMessageDialog(this,
                "📗 Book marked as returned!\nPenalty: ₱" + penalty);
            loadBorrowedBooks();
            loadOverdueBooks();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error marking returned: " + e.getMessage());
        }
    }
}