package udlib.ui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import udlib.db.DBConnection;

public class SearchFrame extends JFrame {
    private JTextField searchField;
    private JComboBox<String> categoryFilter;
    private DefaultTableModel model;
    private JTable table;
    private TableRowSorter<DefaultTableModel> sorter;

    public SearchFrame() {
        setTitle("Search Books - University of Davao Library System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        // ===== HEADER =====
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(13, 27, 102));

        JPanel leftHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        leftHeader.setBackground(new Color(13, 27, 102));

        JLabel logo = new JLabel(new ImageIcon(getClass().getResource("/udlib/assets/ud_logo.png")));
        logo.setPreferredSize(new Dimension(60, 60));
        leftHeader.add(logo);

        JLabel title = new JLabel("University of Davao Library System");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Poppins", Font.BOLD, 22));
        leftHeader.add(title);

        headerPanel.add(leftHeader, BorderLayout.WEST);

        // 🔍 Search + Category filter
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        searchPanel.setBackground(new Color(13, 27, 102));

        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setForeground(Color.WHITE);
        searchLabel.setFont(new Font("Poppins", Font.PLAIN, 16));

        searchField = new JTextField(20);
        searchField.setFont(new Font("Poppins", Font.PLAIN, 14));

        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setForeground(Color.WHITE);
        categoryLabel.setFont(new Font("Poppins", Font.PLAIN, 16));

        categoryFilter = new JComboBox<>();
        categoryFilter.setFont(new Font("Poppins", Font.PLAIN, 14));
        categoryFilter.addItem("All");
        loadCategories();

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(categoryLabel);
        searchPanel.add(categoryFilter);
        headerPanel.add(searchPanel, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        // ===== TABLE AREA =====
        String[] cols = {"Title", "Author", "Category", "Shelf Number", "ISBN", "Availability"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        table.setRowHeight(30);
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        loadBooksFromDB();

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // 🔍 Search & Filter listeners
        searchField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) { applyFilters(); }
        });

        categoryFilter.addActionListener(e -> applyFilters());

        // Row click → Book Details
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    String title = table.getValueAt(row, 0).toString();
                    String author = table.getValueAt(row, 1).toString();
                    String category = table.getValueAt(row, 2).toString();
                    String shelf = table.getValueAt(row, 3).toString();
                    String isbn = table.getValueAt(row, 4).toString();
                    String status = table.getValueAt(row, 5).toString();
                    new BookDetailsFrame(title, author, category, shelf, isbn, status);
                    dispose();
                }
            }
        });

        // ===== FOOTER WITH RUNNING TIME =====
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(Color.WHITE);
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 30));

        JButton backBtn = new JButton("← Back to Home");
        backBtn.setFont(new Font("Poppins", Font.BOLD, 14));
        backBtn.setBackground(Color.WHITE);
        backBtn.setForeground(new Color(13, 27, 102));
        backBtn.setBorder(BorderFactory.createLineBorder(new Color(13, 27, 102), 2, true));
        backBtn.addActionListener(e -> { new HomeFrame(); dispose(); });

        // Running Time Label
        JLabel timeLabel = new JLabel("⏱ Running Time: 00:00:00");
        timeLabel.setFont(new Font("Poppins", Font.PLAIN, 16));
        timeLabel.setForeground(new Color(13, 27, 102));
        timeLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        footerPanel.add(backBtn, BorderLayout.WEST);
        footerPanel.add(timeLabel, BorderLayout.EAST);
        add(footerPanel, BorderLayout.SOUTH);

        // Start timer
        long startTime = System.currentTimeMillis();
        new javax.swing.Timer(1000, e -> {
            long elapsed = (System.currentTimeMillis() - startTime) / 1000;
            long hours = elapsed / 3600;
            long minutes = (elapsed % 3600) / 60;
            long seconds = elapsed % 60;
            timeLabel.setText(String.format("⏱ Running Time: %02d:%02d:%02d", hours, minutes, seconds));
        }).start();

        setVisible(true);
    }

    // ===== FILTERING =====
    private void applyFilters() {
        String searchText = searchField.getText().trim().toLowerCase();
        String categoryText = categoryFilter.getSelectedItem().toString();

        sorter.setRowFilter(new RowFilter<DefaultTableModel, Integer>() {
            public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                String title = entry.getStringValue(0).toLowerCase();
                String author = entry.getStringValue(1).toLowerCase();
                String category = entry.getStringValue(2).toLowerCase();
                String isbn = entry.getStringValue(4).toLowerCase();

                boolean matchesSearch = searchText.isEmpty() ||
                        title.contains(searchText) || author.contains(searchText) ||
                        category.contains(searchText) || isbn.contains(searchText);

                boolean matchesCategory = categoryText.equals("All") || category.equalsIgnoreCase(categoryText);

                return matchesSearch && matchesCategory;
            }
        });
    }

    // ===== LOAD CATEGORIES =====
    private void loadCategories() {
        try (Connection conn = DBConnection.connect()) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT DISTINCT category FROM books ORDER BY category");
            while (rs.next()) {
                categoryFilter.addItem(rs.getString("category"));
            }
        } catch (Exception e) {
            System.out.println("Error loading categories: " + e.getMessage());
        }
    }

    // ===== LOAD BOOKS =====
    private void loadBooksFromDB() {
        model.setRowCount(0);
        try (Connection conn = DBConnection.connect()) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT title, author, category, shelf_number, isbn, availability FROM books");
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("category"),
                    rs.getString("shelf_number"),
                    rs.getString("isbn"),
                    rs.getString("availability")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading books: " + e.getMessage());
        }
    }
}
