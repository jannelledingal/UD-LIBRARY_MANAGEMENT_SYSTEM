package udlib.ui;

import javax.swing.*;
import java.awt.*;

public class ModernDialog extends JDialog {

    public ModernDialog(JFrame parent, String title, String message, boolean confirmMode) {
        super(parent, title, true);
        setSize(400, 220);
        setLocationRelativeTo(parent);
        setUndecorated(true);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createLineBorder(new Color(13, 27, 102), 3, true));
        panel.setBackground(Color.WHITE);

        JLabel msgLabel = new JLabel("<html><div style='text-align:center;'>" + message + "</div></html>", SwingConstants.CENTER);
        msgLabel.setFont(new Font("Poppins", Font.PLAIN, 16));
        msgLabel.setForeground(new Color(13, 27, 102));

        JButton okBtn = new JButton(confirmMode ? "Yes" : "OK");
        JButton cancelBtn = confirmMode ? new JButton("No") : null;

        okBtn.setFont(new Font("Poppins", Font.BOLD, 14));
        okBtn.setBackground(new Color(21, 101, 192));
        okBtn.setForeground(Color.WHITE);
        okBtn.setFocusPainted(false);

        if (cancelBtn != null) {
            cancelBtn.setFont(new Font("Poppins", Font.BOLD, 14));
            cancelBtn.setBackground(Color.WHITE);
            cancelBtn.setForeground(new Color(21, 101, 192));
            cancelBtn.setFocusPainted(false);
            cancelBtn.setBorder(BorderFactory.createLineBorder(new Color(21, 101, 192), 2, true));
        }

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        if (confirmMode) {
            buttonPanel.add(okBtn);
            buttonPanel.add(cancelBtn);
        } else {
            buttonPanel.add(okBtn);
        }

        panel.add(msgLabel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        add(panel);

        okBtn.addActionListener(e -> {
            setVisible(false);
            dispose();
            result = true;
        });

        if (cancelBtn != null)
            cancelBtn.addActionListener(e -> {
                setVisible(false);
                dispose();
                result = false;
            });
    }

    private boolean result = false;
    public boolean getResult() { return result; }

    public static void showMessage(JFrame parent, String message) {
        ModernDialog dialog = new ModernDialog(parent, "Notification", message, false);
        dialog.setVisible(true);
    }

    public static boolean showConfirm(JFrame parent, String message) {
        ModernDialog dialog = new ModernDialog(parent, "Confirmation", message, true);
        dialog.setVisible(true);
        return dialog.getResult();
    }
}
