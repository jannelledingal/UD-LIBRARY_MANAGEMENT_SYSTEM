package udlib.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.*;
import udlib.db.DBConnection;

public class BookDetailsFrame extends JFrame {

    public BookDetailsFrame(String title, String author, String category, String shelf, String isbn, String status) {
        setTitle("Book Details - University of Davao Library System");
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

        JLabel headerTitle = new JLabel("University of Davao Library System");
        headerTitle.setForeground(Color.WHITE);
        headerTitle.setFont(new Font("Poppins", Font.BOLD, 22));
        leftHeader.add(headerTitle);
        headerPanel.add(leftHeader, BorderLayout.WEST);

        add(headerPanel, BorderLayout.NORTH);

        // Main panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(227,242,253));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Cover
        JLabel coverLabel = new JLabel();
        coverLabel.setHorizontalAlignment(SwingConstants.CENTER);
        coverLabel.setVerticalAlignment(SwingConstants.CENTER);
        coverLabel.setBorder(BorderFactory.createLineBorder(new Color(13,27,102), 3, true));
        loadCoverImage(coverLabel, isbn);

        // Book info
        JLabel titleLabel = createLabel("Title: " + title);
        JLabel authorLabel = createLabel("Author: " + author);
        JLabel categoryLabel = createLabel("Category: " + category);
        JLabel shelfLabel = createLabel("Shelf Number: " + shelf);
        JLabel isbnLabel = createLabel("ISBN: " + isbn);
        JLabel statusLabel = createLabel("Availability: " + status);
        if (status.equalsIgnoreCase("Not Available")) statusLabel.setForeground(Color.RED);

        JLabel noteLabel = new JLabel("If not available, go to the Admin Desk to request borrowing.");
        noteLabel.setFont(new Font("Poppins", Font.ITALIC, 14));
        noteLabel.setForeground(new Color(70,70,70));

        // Buttons
        JButton requestBtn = new JButton("Request Book");
        JButton backBtn = new JButton("← Back");
        styleButton(requestBtn);
        styleButton(backBtn);

        backBtn.addActionListener(e -> { new SearchFrame(); dispose(); });
        requestBtn.addActionListener(e -> openRequestModal(title, isbn));

        // Layout
        gbc.gridx = 0; gbc.gridy = 0;
        mainPanel.add(coverLabel, gbc);

        JPanel infoPanel = new JPanel(new GridLayout(7,1,10,10));
        infoPanel.setBackground(new Color(227,242,253));
        infoPanel.add(titleLabel);
        infoPanel.add(authorLabel);
        infoPanel.add(categoryLabel);
        infoPanel.add(shelfLabel);
        infoPanel.add(isbnLabel);
        infoPanel.add(statusLabel);
        infoPanel.add(noteLabel);

        gbc.gridx = 1;
        mainPanel.add(infoPanel, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(227,242,253));
        buttonPanel.add(requestBtn);
        buttonPanel.add(backBtn);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        mainPanel.add(buttonPanel, gbc);

        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Poppins", Font.PLAIN, 16));
        label.setForeground(new Color(13,27,102));
        return label;
    }

    private void styleButton(JButton btn) {
        btn.setFont(new Font("Poppins", Font.BOLD, 15));
        btn.setForeground(new Color(13,27,102));
        btn.setBackground(Color.WHITE);
        btn.setBorder(BorderFactory.createLineBorder(new Color(13,27,102), 2, true));
        btn.setFocusPainted(false);
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(new Color(227,242,253)); }
            public void mouseExited(MouseEvent e) { btn.setBackground(Color.WHITE); }
        });
    }

    private void loadCoverImage(JLabel label, String isbn) {
        try {
            File coverFile = new File("src/udlib/assets/covers/" + isbn + ".jpg");
            if (!coverFile.exists()) coverFile = new File("src/udlib/assets/covers/no_cover.png");
            ImageIcon icon = new ImageIcon(new ImageIcon(coverFile.getAbsolutePath()).getImage()
                    .getScaledInstance(250, 350, Image.SCALE_SMOOTH));
            label.setIcon(icon);
        } catch (Exception e) {
            label.setText("No cover image");
        }
    }

    // 📘 Small modal for book request input
    private void openRequestModal(String bookTitle, String isbn) {
        JDialog dialog = new JDialog(this, "Book Request", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridLayout(5,1,10,10));
        dialog.getContentPane().setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel("UD Member Name:");
        nameLabel.setFont(new Font("Poppins", Font.PLAIN, 14));
        JTextField nameField = new JTextField();

        JLabel idLabel = new JLabel("UD Member ID:");
        idLabel.setFont(new Font("Poppins", Font.PLAIN, 14));
        JTextField idField = new JTextField();

        JButton submitBtn = new JButton("Submit Request");
        styleButton(submitBtn);

        submitBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String id = idField.getText().trim();

            if (name.isEmpty() || id.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please enter both name and ID!");
                return;
            }

            saveBookRequest(name, id, bookTitle, isbn);
            dialog.dispose();
            JOptionPane.showMessageDialog(this, "📚 Book request successfully sent!");
        });

        dialog.add(nameLabel);
        dialog.add(nameField);
        dialog.add(idLabel);
        dialog.add(idField);
        dialog.add(submitBtn);

        dialog.setVisible(true);
    }

    // ✅ Save to MySQL table
    private void saveBookRequest(String name, String id, String title, String isbn) {
        try (Connection conn = DBConnection.connect()) {
            String sql = "INSERT INTO book_requests (ud_name, ud_id, book_title, book_isbn, status) VALUES (?, ?, ?, ?, 'Pending')";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, id);
            ps.setString(3, title);
            ps.setString(4, isbn);
            ps.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving request: " + e.getMessage());
        }
    }
}
