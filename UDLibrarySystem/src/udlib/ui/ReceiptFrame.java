package udlib.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.PrinterException;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Text-only receipt popup (no external libs). Saves text files into /receipts.
 */
public class ReceiptFrame extends JFrame {
    private JTextArea receiptArea;
    private JButton btnPrint, btnSave, btnClose;
    private String memberName;

    public ReceiptFrame(String memberId, String memberName, String bookName, String isbn, String borrowDate, String dueDate) {
        this.memberName = memberName;

        setTitle("Borrow Receipt - University of Davao Library System");
        setSize(640, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(8, 8));

        receiptArea = new JTextArea();
        receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        receiptArea.setEditable(false);
        receiptArea.setMargin(new Insets(10,10,10,10));
        JScrollPane scrollPane = new JScrollPane(receiptArea);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnPrint = new JButton("🖨 Print");
        btnSave = new JButton("💾 Save");
        btnClose = new JButton("❌ Close");
        buttonPanel.add(btnPrint);
        buttonPanel.add(btnSave);
        buttonPanel.add(btnClose);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        generateReceipt(memberId, memberName, bookName, isbn, dueDate);

        btnPrint.addActionListener(e -> {
            try {
                boolean ok = receiptArea.print();
                if (ok) {
                    JOptionPane.showMessageDialog(this, "Receipt sent to printer.");
                }
            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(this, "Printing failed: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnSave.addActionListener(e -> saveReceiptToFile());

        btnClose.addActionListener(e -> dispose());
    }

    private void generateReceipt(String memberId, String memberName, String bookName, String isbn, String dueDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateTime = sdf.format(new Date());

        StringBuilder sb = new StringBuilder();
        sb.append("=========================================\n");
        sb.append("  University of Davao Library System\n");
        sb.append("=========================================\n");
        sb.append(" Date/Time : ").append(dateTime).append("\n");
        sb.append(" Member ID : ").append(memberId).append("\n");
        sb.append(" Member    : ").append(memberName).append("\n");
        sb.append("-----------------------------------------\n");
        sb.append(" Book Name : ").append(bookName).append("\n");
        sb.append(" ISBN      : ").append(isbn).append("\n");
        sb.append(" Due Date  : ").append(dueDate).append("\n");
        sb.append("=========================================\n");
        sb.append("   Thank you for using our library!\n");
        sb.append("=========================================\n");

        receiptArea.setText(sb.toString());
        receiptArea.setCaretPosition(0);
    }

    private void saveReceiptToFile() {
        try {
            File dir = new File("receipts");
            if (!dir.exists()) dir.mkdirs();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String timestamp = sdf.format(new Date());
            String safeName = (memberName == null || memberName.trim().isEmpty()) ? "member" : memberName.replaceAll("[^a-zA-Z0-9_\\-]", "_");
            String fileName = "Receipt_" + safeName + "_" + timestamp + ".txt";
            File file = new File(dir, fileName);

            try (BufferedWriter w = new BufferedWriter(new FileWriter(file))) {
                w.write(receiptArea.getText());
            }

            JOptionPane.showMessageDialog(this, "Receipt saved:\n" + file.getAbsolutePath());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving receipt: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
