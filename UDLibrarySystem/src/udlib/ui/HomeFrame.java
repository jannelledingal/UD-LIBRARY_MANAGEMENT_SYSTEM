package udlib.ui;

import javax.swing.*;
import java.awt.*;

public class HomeFrame extends JFrame {
    public HomeFrame() {
        setTitle("University of Davao Library System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        headerPanel.setBackground(new Color(13,27,102));

        JLabel logo = new JLabel(new ImageIcon(getClass().getResource("/udlib/assets/ud_logo.png")));
        logo.setPreferredSize(new Dimension(60,60));
        headerPanel.add(logo);

        JLabel title = new JLabel("University of Davao Library System");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Poppins", Font.BOLD, 24));
        headerPanel.add(title);
        add(headerPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(2,1,20,20));
        centerPanel.setBackground(new Color(227,242,253));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(200,500,200,500));

        JButton searchBtn = createButton("🔍 Search Books");
        JButton adminBtn = createButton("🔑 Admin Login");

        searchBtn.addActionListener(e -> { new SearchFrame(); dispose(); });
        adminBtn.addActionListener(e -> { new AdminLoginFrame(); dispose(); });

        centerPanel.add(searchBtn);
        centerPanel.add(adminBtn);
        add(centerPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Poppins", Font.BOLD, 20));
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

    public static void main(String[] args) { SwingUtilities.invokeLater(HomeFrame::new); }
}
