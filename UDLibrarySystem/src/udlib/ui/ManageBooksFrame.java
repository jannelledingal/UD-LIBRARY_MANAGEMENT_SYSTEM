package udlib.ui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.io.*;
import java.sql.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.nio.file.*;
import java.awt.event.*;
import java.util.*;
import udlib.db.DBConnection;

public class ManageBooksFrame extends JFrame {
    private DefaultTableModel model;
    private JTable table;
    private JTextField titleField, authorField, categoryField, shelfField, isbnField, searchField;
    private JLabel coverPreview;
    private String selectedCoverPath = "";
    private TableRowSorter<DefaultTableModel> sorter;
    private JComboBox<String> categoryFilter;

    public ManageBooksFrame() {
        setTitle("Manage Books - University of Davao Library System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(13,27,102));

        JPanel leftHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        leftHeader.setBackground(new Color(13,27,102));
        JLabel logo = new JLabel(new ImageIcon(getClass().getResource("/udlib/assets/ud_logo.png")));
        logo.setPreferredSize(new Dimension(60,60));
        leftHeader.add(logo);
        JLabel title = new JLabel("Manage Books");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Poppins", Font.BOLD, 24));
        leftHeader.add(title);
        headerPanel.add(leftHeader, BorderLayout.WEST);

        // 🔍 Search bar + Category dropdown
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        searchPanel.setBackground(new Color(13,27,102));

        JLabel searchLabel = new JLabel("Search: ");
        searchLabel.setForeground(Color.WHITE);
        searchLabel.setFont(new Font("Poppins", Font.PLAIN, 16));
        searchField = new JTextField(20);
        searchField.setFont(new Font("Poppins", Font.PLAIN, 14));

        JLabel filterLabel = new JLabel("Category: ");
        filterLabel.setForeground(Color.WHITE);
        filterLabel.setFont(new Font("Poppins", Font.PLAIN, 16));

        categoryFilter = new JComboBox<>();
        categoryFilter.setFont(new Font("Poppins", Font.PLAIN, 14));
        categoryFilter.addItem("All");
        loadCategories();

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(filterLabel);
        searchPanel.add(categoryFilter);

        headerPanel.add(searchPanel, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(227,242,253));

        // Table
        String[] cols = {"Title", "Author", "Category", "Shelf", "ISBN", "Availability", "Cover"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        table.setRowHeight(30);
        loadBooksFromDB();

        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        // 🔍 Search + Filter listeners
        searchField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) { applyFilters(); }
        });

        categoryFilter.addActionListener(e -> applyFilters());

        // Row click → autofill
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    int modelRow = table.convertRowIndexToModel(row);
                    titleField.setText(model.getValueAt(modelRow, 0).toString());
                    authorField.setText(model.getValueAt(modelRow, 1).toString());
                    categoryField.setText(model.getValueAt(modelRow, 2).toString());
                    shelfField.setText(model.getValueAt(modelRow, 3).toString());
                    isbnField.setText(model.getValueAt(modelRow, 4).toString());
                    selectedCoverPath = model.getValueAt(modelRow, 6).toString();
                    updateCoverPreview(selectedCoverPath);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Right panel (form)
        JPanel formPanel = new JPanel(new GridLayout(8,2,10,10));
        formPanel.setBackground(new Color(227,242,253));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        titleField = new JTextField();
        authorField = new JTextField();
        categoryField = new JTextField();
        shelfField = new JTextField();
        isbnField = new JTextField();

        coverPreview = new JLabel("No Cover", SwingConstants.CENTER);
        coverPreview.setPreferredSize(new Dimension(100,120));
        coverPreview.setBorder(BorderFactory.createLineBorder(new Color(13,27,102),2,true));

        JButton chooseCoverBtn = new JButton("Choose Cover");
        chooseCoverBtn.addActionListener(e -> chooseCover());

        formPanel.add(new JLabel("Title:")); formPanel.add(titleField);
        formPanel.add(new JLabel("Author:")); formPanel.add(authorField);
        formPanel.add(new JLabel("Category:")); formPanel.add(categoryField);
        formPanel.add(new JLabel("Shelf No.:")); formPanel.add(shelfField);
        formPanel.add(new JLabel("ISBN:")); formPanel.add(isbnField);
        formPanel.add(chooseCoverBtn); formPanel.add(coverPreview);

        JButton addBtn = createButton("➕ Add Book");
        JButton editBtn = createButton("✏️ Edit Book");
        JButton deleteBtn = createButton("🗑️ Delete Book");
        JButton backBtn = createButton("← Back");

        formPanel.add(addBtn);
        formPanel.add(editBtn);
        formPanel.add(deleteBtn);
        formPanel.add(backBtn);

        addBtn.addActionListener(e -> addBook());
        editBtn.addActionListener(e -> editBook());
        deleteBtn.addActionListener(e -> deleteBook());
        backBtn.addActionListener(e -> { new AdminDashboardFrame("Admin"); dispose(); });

        mainPanel.add(formPanel, BorderLayout.EAST);
        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Poppins", Font.BOLD, 14));
        btn.setBackground(Color.WHITE);
        btn.setForeground(new Color(13,27,102));
        btn.setBorder(BorderFactory.createLineBorder(new Color(13,27,102),2,true));
        btn.setFocusPainted(false);
        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { btn.setBackground(new Color(227,242,253)); }
            @Override public void mouseExited(MouseEvent e) { btn.setBackground(Color.WHITE); }
        });
        return btn;
    }

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

                boolean matchesCategory = categoryText.equals("All") || category.equals(categoryText.toLowerCase());

                return matchesSearch && matchesCategory;
            }
        });
    }

    private void chooseCover() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "jpeg"));
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            selectedCoverPath = file.getAbsolutePath();
            updateCoverPreview(selectedCoverPath);
        }
    }

    private void updateCoverPreview(String path) {
        try {
            File file = new File(path);
            if (!file.exists()) file = new File("src/udlib/assets/covers/no_cover.png");
            ImageIcon icon = new ImageIcon(new ImageIcon(file.getAbsolutePath()).getImage().getScaledInstance(100,120,Image.SCALE_SMOOTH));
            coverPreview.setIcon(icon);
            coverPreview.setText("");
        } catch (Exception e) {
            coverPreview.setIcon(null);
            coverPreview.setText("No Cover");
        }
    }

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

    private void addBook() {
        if (isbnField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill out all fields including ISBN!");
            return;
        }

        try (Connection conn = DBConnection.connect()) {
            String newCoverPath = copyCoverToAssets(isbnField.getText().trim());
            String sql = "INSERT INTO books (title, author, category, shelf_number, isbn, cover_path, availability) VALUES (?, ?, ?, ?, ?, ?, 'Available')";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, titleField.getText());
            ps.setString(2, authorField.getText());
            ps.setString(3, categoryField.getText());
            ps.setString(4, shelfField.getText());
            ps.setString(5, isbnField.getText());
            ps.setString(6, newCoverPath);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "✅ Book added successfully!");
            loadBooksFromDB();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding book: " + e.getMessage());
        }
    }

    private void editBook() {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Select a book to edit."); return; }
        String isbn = model.getValueAt(table.convertRowIndexToModel(row), 4).toString();
        try (Connection conn = DBConnection.connect()) {
            String newCoverPath = copyCoverToAssets(isbn);
            String sql = "UPDATE books SET title=?, author=?, category=?, shelf_number=?, cover_path=? WHERE isbn=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, titleField.getText());
            ps.setString(2, authorField.getText());
            ps.setString(3, categoryField.getText());
            ps.setString(4, shelfField.getText());
            ps.setString(5, newCoverPath);
            ps.setString(6, isbn);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "✅ Book updated successfully!");
            loadBooksFromDB();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error updating book: " + e.getMessage());
        }
    }

    private void deleteBook() {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Select a book to delete."); return; }
        String isbn = model.getValueAt(table.convertRowIndexToModel(row), 4).toString();
        try (Connection conn = DBConnection.connect()) {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM books WHERE isbn=?");
            ps.setString(1, isbn);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "🗑️ Book deleted successfully!");
            loadBooksFromDB();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error deleting book: " + e.getMessage());
        }
    }

    private void loadBooksFromDB() {
        model.setRowCount(0);
        try (Connection conn = DBConnection.connect()) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT title, author, category, shelf_number, isbn, availability, cover_path FROM books");
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("category"),
                    rs.getString("shelf_number"),
                    rs.getString("isbn"),
                    rs.getString("availability"),
                    rs.getString("cover_path")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading books: " + e.getMessage());
        }
        categoryFilter.removeAllItems();
        categoryFilter.addItem("All");
        loadCategories();
    }

    private String copyCoverToAssets(String isbn) {
        try {
            if (selectedCoverPath.isEmpty()) return "src/udlib/assets/covers/no_cover.png";
            Path source = Paths.get(selectedCoverPath);
            Path targetDir = Paths.get("src/udlib/assets/covers/");
            if (!Files.exists(targetDir)) Files.createDirectories(targetDir);
            Path target = targetDir.resolve(isbn + ".jpg");
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
            return target.toString();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error copying cover: " + e.getMessage());
            return "src/udlib/assets/covers/no_cover.png";
        }
    }
}
