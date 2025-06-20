package Frontend;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import javax.imageio.ImageIO;

public abstract class ChatPanel extends JPanel {
    protected JTextPane chatArea;
    protected JTextField inputField;
    protected JButton sendButton;
    protected BufferedImage backgroundImg;

    public JTextPane getChatArea() { return chatArea; }
    public JTextField getInputField() { return inputField; }
    public JButton getSendButton() { return sendButton; }
    public void clearInput() { inputField.setText(""); }

    protected void setupChatArea() {
        chatArea = new JTextPane() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.95f));
                g2.setColor(getBackground());
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        chatArea.setOpaque(false);
        chatArea.setBackground(new Color(30, 30, 47, 200));
        chatArea.setEditable(false);
        chatArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    }

    protected void setupInputField() {
        inputField = new JTextField();
        inputField.setBackground(new Color(40, 40, 60));
        inputField.setForeground(Color.WHITE);
        inputField.setCaretColor(Color.WHITE);
        inputField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        inputField.setBorder(new EmptyBorder(8, 12, 8, 12));
    }

    protected void setupSendButton() {
        sendButton = new JButton("Senden");
        sendButton.setBackground(new Color(79, 195, 247));
        sendButton.setForeground(Color.WHITE);
        sendButton.setFocusPainted(false);
        sendButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        sendButton.setBorder(new EmptyBorder(8, 20, 8, 20));
        sendButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        sendButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                sendButton.setBackground(new Color(69, 175, 227));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                sendButton.setBackground(new Color(79, 195, 247));
            }
        });
    }

    public void appendUserMessage(String msg) {
        appendStyledText("Du: " + msg + "\n", new Color(79, 195, 247), false, false);
    }

    public void appendAnswerMessage(String headline, String answer) {
        appendStyledText(headline + "\n", Color.WHITE, true, true);
        appendStyledText(answer + "\n\n", Color.LIGHT_GRAY, false, false);
    }

    public void appendStyledText(String text, Color color, boolean bold, boolean headline) {
        StyledDocument doc = chatArea.getStyledDocument();
        SimpleAttributeSet style = new SimpleAttributeSet();
        StyleConstants.setForeground(style, color);
        StyleConstants.setBold(style, bold);
        StyleConstants.setFontFamily(style, "Segoe UI");
        StyleConstants.setLineSpacing(style, 0.2f);

        if (headline) {
            StyleConstants.setFontSize(style, 16);
            StyleConstants.setAlignment(style, StyleConstants.ALIGN_CENTER);
        } else {
            StyleConstants.setFontSize(style, 14);
            StyleConstants.setAlignment(style, StyleConstants.ALIGN_LEFT);
        }

        try {
            int len = doc.getLength();
            doc.insertString(len, text, style);
            doc.setParagraphAttributes(len, text.length(), style, false);
            chatArea.setCaretPosition(doc.getLength());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public void printKrise() {
        StyledDocument doc = chatArea.getStyledDocument();
        SimpleAttributeSet style = new SimpleAttributeSet();
        StyleConstants.setForeground(style, Color.RED);
        StyleConstants.setBold(style, true);
        StyleConstants.setFontSize(style, 32);
        StyleConstants.setFontFamily(style, "Segoe UI");
        StyleConstants.setAlignment(style, StyleConstants.ALIGN_CENTER);

        try {
            int len = doc.getLength();
            doc.insertString(len, "KRISE\n\n", style);
            doc.setParagraphAttributes(len, "KRISE\n\n".length(), style, false);
            chatArea.setCaretPosition(doc.getLength());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    protected void loadBackground(Path path) {
        try {
            backgroundImg = ImageIO.read(path.toFile());
        } catch (IOException e) {
            e.printStackTrace();
            backgroundImg = null;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImg != null) {
            int panelWidth = getWidth();
            int panelHeight = getHeight();
            int imgWidth = backgroundImg.getWidth();
            int imgHeight = backgroundImg.getHeight();

            double scale = Math.max((double) panelWidth / imgWidth, (double) panelHeight / imgHeight);
            int drawWidth = (int) (imgWidth * scale);
            int drawHeight = (int) (imgHeight * scale);
            int x = (panelWidth - drawWidth) / 2;
            int y = (panelHeight - drawHeight) / 2;

            g.drawImage(backgroundImg, x, y, drawWidth, drawHeight, this);
        }
    }
}