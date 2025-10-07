package udlib.ui;

import javax.swing.*;
import java.awt.*;

public class AdminLoginFrame extends JFrame {
    private JTextField userField;
    private JPasswordField passField;

    public AdminLoginFrame() {
        setTitle("Admin Login - University of Davao Library System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        // 🖥️ Screen-based scaling
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screen.width;
        int height = screen.height;

        int baseFont = width / 80;   // scales text (bigger screen → bigger font)
        int titleFont = baseFont + 12;
        int labelFont = baseFont + 4;
        int fieldFont = baseFont + 4;
        int buttonFont = baseFont + 6;

        int padding = width / 6;     // spacing depends on screen width
        int fieldHeight = height / 18;
        int buttonHeight = height / 14;

        // Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        headerPanel.setBackground(new Color(13, 27, 102));

        JLabel logo = new JLabel(new ImageIcon(getClass().getResource("/udlib/assets/ud_logo.png")));
        logo.setPreferredSize(new Dimension(height / 10, height / 10)); // responsive logo
        headerPanel.add(logo);

        JLabel title = new JLabel("Admin Login");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Poppins", Font.BOLD, titleFont));
        headerPanel.add(title);

        add(headerPanel, BorderLayout.NORTH);

        // Form
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 25, 25));
        formPanel.setBackground(new Color(227, 242, 253));
        formPanel.setBorder(BorderFactory.createEmptyBorder(height / 6, padding, height / 6, padding));

        JLabel userLbl = new JLabel("Username:");
        JLabel passLbl = new JLabel("Password:");
        userLbl.setFont(new Font("Poppins", Font.PLAIN, labelFont));
        passLbl.setFont(new Font("Poppins", Font.PLAIN, labelFont));

        userField = new JTextField();
        passField = new JPasswordField();
        userField.setFont(new Font("Poppins", Font.PLAIN, fieldFont));
        passField.setFont(new Font("Poppins", Font.PLAIN, fieldFont));

        Dimension fieldSize = new Dimension(width / 4, fieldHeight);
        userField.setPreferredSize(fieldSize);
        passField.setPreferredSize(fieldSize);

        JButton loginBtn = createButton("Login", buttonFont, buttonHeight);
        JButton backBtn = createButton("← Back", buttonFont, buttonHeight);

        loginBtn.addActionListener(e -> validateLogin());
        backBtn.addActionListener(e -> {
            new HomeFrame();
            dispose();
        });

        formPanel.add(userLbl);
        formPanel.add(userField);
        formPanel.add(passLbl);
        formPanel.add(passField);
        formPanel.add(backBtn);
        formPanel.add(loginBtn);

        add(formPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void validateLogin() {
        String user = userField.getText().trim().toLowerCase();
        String pass = new String(passField.getPassword()).trim();

        if ((user.equals("jannelle") || user.equals("alfert") || user.equals("mark") ||
             user.equals("onez") || user.equals("hannah") || user.equals("axel")) && pass.equals("12345")) {
            new AdminDashboardFrame(user);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JButton createButton(String text, int fontSize, int height) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Poppins", Font.BOLD, fontSize));
        btn.setPreferredSize(new Dimension(200, height));
        btn.setBackground(Color.WHITE);
        btn.setForeground(new Color(13, 27, 102));
        btn.setBorder(BorderFactory.createLineBorder(new Color(13, 27, 102), 3, true));
        btn.setFocusPainted(false);

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(227, 242, 253));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(Color.WHITE);
            }
        });

        return btn;
    }
}
