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

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        headerPanel.setBackground(new Color(13,27,102));
        JLabel logo = new JLabel(new ImageIcon(getClass().getResource("/udlib/assets/ud_logo.png")));
        logo.setPreferredSize(new Dimension(60,60));
        headerPanel.add(logo);
        JLabel title = new JLabel("Admin Login");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Poppins", Font.BOLD, 24));
        headerPanel.add(title);
        add(headerPanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(3,2,15,15));
        formPanel.setBackground(new Color(227,242,253));
        formPanel.setBorder(BorderFactory.createEmptyBorder(250,600,250,600));
        JLabel userLbl = new JLabel("Username:");
        JLabel passLbl = new JLabel("Password:");
        userField = new JTextField();
        passField = new JPasswordField();
        JButton loginBtn = createButton("Login");
        JButton backBtn = createButton("← Back");
        loginBtn.addActionListener(e -> validateLogin());
        backBtn.addActionListener(e -> { new HomeFrame(); dispose(); });
        formPanel.add(userLbl); formPanel.add(userField);
        formPanel.add(passLbl); formPanel.add(passField);
        formPanel.add(backBtn); formPanel.add(loginBtn);
        add(formPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void validateLogin() {
        String user = userField.getText().trim().toLowerCase();
        String pass = new String(passField.getPassword()).trim();
        if ((user.equals("jannelle")||user.equals("alfert")||user.equals("mark")||user.equals("onez")||user.equals("hannah")||user.equals("axel")) && pass.equals("12345")) {
            new AdminDashboardFrame(user);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Poppins", Font.BOLD, 18));
        btn.setBackground(Color.WHITE);
        btn.setForeground(new Color(13,27,102));
        btn.setBorder(BorderFactory.createLineBorder(new Color(13,27,102),2,true));
        btn.setFocusPainted(false);
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent e) { btn.setBackground(new Color(227,242,253)); }
            @Override public void mouseExited(java.awt.event.MouseEvent e) { btn.setBackground(Color.WHITE); }
        });
        return btn;
    }
}
