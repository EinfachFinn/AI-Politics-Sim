package Frontend;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import javax.imageio.ImageIO;

public class ChatPanel extends JPanel {
    private JTextPane chatArea;
    private JTextField inputField;
    private JButton sendButton;
    private BufferedImage backgroundImg;
    private final boolean isMiniPanel;

    public JTextPane getChatArea() {return chatArea;}


    public ChatPanel(Path imagepath, Boolean isMiniPanel) {
        setLayout(new BorderLayout());
            this.isMiniPanel = isMiniPanel;


            chatArea = new JTextPane() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f)); // 70% sichtbar
                g2.setColor(getBackground());
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        chatArea.setOpaque(false); // wichtig für Transparenz5
        chatArea.setBackground(new Color(255, 255, 255, 220)); // halbtransparentes Weiß
        chatArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(chatArea);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        JPanel chatWrapper = new JPanel(new BorderLayout());
        chatWrapper.setOpaque(false); // damit Hintergrund durchscheint

        JPanel leftSpacer = new JPanel();
        leftSpacer.setPreferredSize(new Dimension(50, 0)); // ca. 10% bei 600px
        leftSpacer.setOpaque(false);

        JPanel rightSpacer = new JPanel();
        rightSpacer.setPreferredSize(new Dimension(50,0));
        rightSpacer.setOpaque(false);

        chatWrapper.add(leftSpacer, BorderLayout.WEST);
        chatWrapper.add(scrollPane, BorderLayout.CENTER);
        chatWrapper.add(rightSpacer, BorderLayout.EAST);



        inputField = new JTextField();
        sendButton = new JButton("Send");

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        add(chatWrapper, BorderLayout.CENTER);

        add(inputPanel, BorderLayout.SOUTH);

        // Bild laden
        try {
            backgroundImg = ImageIO.read(imagepath.toFile());
            if(backgroundImg == null) {
                System.out.println("Background image not found");
            }
        } catch (IOException e) {
            e.printStackTrace();
            backgroundImg = null;
        }
    }





    public JTextField getInputField() {
        return inputField;
    }

    public JButton getSendButton() {
        return sendButton;
    }

    public void clearInput() {
        inputField.setText("");
    }

    public void appendUserMessage(String msg) {
        appendStyledText("Du: " + msg + "\n", new Color(0, 128, 255), false, false);
    }

    public void appendAnswerMessage(String headline, String answer) {
        appendStyledText(headline + "\n", Color.BLACK, true, true);
        appendStyledText(answer + "\n\n", Color.DARK_GRAY, false, false);
    }

    public void appendStyledText(String text, Color color, boolean bold, boolean headline) {
        StyledDocument doc = chatArea.getStyledDocument();
        SimpleAttributeSet style = new SimpleAttributeSet();
        StyleConstants.setForeground(style, color);
        StyleConstants.setBold(style, bold);
        if (headline) {
            StyleConstants.setFontSize(style, 16);
            StyleConstants.setAlignment(style, StyleConstants.ALIGN_CENTER);
        } else {
            StyleConstants.setFontSize(style, 13);
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






    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImg != null) {
            int panelWidth = getWidth();
            int panelHeight = getHeight();
            int imgWidth = backgroundImg.getWidth();
            int imgHeight = backgroundImg.getHeight();

            double scale;
            int drawWidth, drawHeight;

            if (isMiniPanel) {
                // Mini-Panel: Skaliere zur Breite, begrenze Höhe
                scale = (double) panelWidth / imgWidth;
                drawHeight = (int) (imgHeight * scale);

                // Falls Höhe überschritten wird, neu skalieren
                if (drawHeight > panelHeight) {
                    scale = (double) panelHeight / imgHeight;
                }
            } else {
                // Main-Panel: Cover-Verhalten (vorherige Logik)
                scale = Math.max(
                        (double) panelWidth / imgWidth,
                        (double) panelHeight / imgHeight
                );
            }

            drawWidth = (int) (imgWidth * scale);
            drawHeight = (int) (imgHeight * scale);

            // Positionierung
            int x = isMiniPanel ? panelWidth - drawWidth : (panelWidth - drawWidth)/2;
            int y = isMiniPanel ? panelHeight - drawHeight : (panelHeight - drawHeight)/2;

            g.drawImage(backgroundImg, x, y, drawWidth, drawHeight, this);
        }
    }

}
