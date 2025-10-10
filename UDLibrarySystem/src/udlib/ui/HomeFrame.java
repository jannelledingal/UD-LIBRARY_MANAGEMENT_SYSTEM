package udlib.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.Duration;
import java.time.LocalTime;

public class HomeFrame extends JFrame {
    private JLabel timerLabel;
    private LocalTime startTime;

    public HomeFrame() {
        setTitle("University of Davao Library System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        // HEADER
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

        // CENTER BUTTONS
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

        // FOOTER (System Running Time)
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        footerPanel.setBackground(new Color(13,27,102));

        timerLabel = new JLabel("System Running Time: 00:00:00");
        timerLabel.setForeground(Color.WHITE);
        timerLabel.setFont(new Font("Poppins", Font.PLAIN, 16));
        footerPanel.add(timerLabel);
        add(footerPanel, BorderLayout.SOUTH);

        // Start tracking time
        startTime = LocalTime.now();
        startTimer();

        setVisible(true);
    }

    //TIMER FUNCTION
    private void startTimer() {
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Duration elapsed = Duration.between(startTime, LocalTime.now());
                long hours = elapsed.toHours();
                long minutes = elapsed.toMinutesPart();
                long seconds = elapsed.toSecondsPart();
                timerLabel.setText(String.format("System Running Time: %02d:%02d:%02d", hours, minutes, seconds));
            }
        });
        timer.start();
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HomeFrame::new);
    }
}
