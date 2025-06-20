package Frontend;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.nio.file.Path;

public class AdvisorChatPanel extends ChatPanel {

    private final JPanel messageContainer;
    private final JScrollPane scrollPane;

    public AdvisorChatPanel(Path backgroundPath) {
        setLayout(new BorderLayout());
        loadBackground(backgroundPath);
        setupInputField();
        setupSendButton();

        messageContainer = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(18, 18, 28));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        messageContainer.setLayout(new BoxLayout(messageContainer, BoxLayout.Y_AXIS));
        messageContainer.setOpaque(false);

        scrollPane = new JScrollPane(messageContainer);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        scrollPane.getVerticalScrollBar().setUnitIncrement(18);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUI(new ModernScrollBarUI());

        styleInputField();
        styleSendButton();

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBackground(new Color(30, 30, 47));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        scrollPane.getViewport().addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustBubbleWidths();
            }
        });
    }

    private void styleInputField() {
        inputField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        inputField.setBackground(new Color(40, 40, 60));
        inputField.setForeground(Color.WHITE);
        inputField.setCaretColor(Color.WHITE);
        inputField.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
    }

    private void styleSendButton() {
        sendButton.setBackground(new Color(100, 180, 255));
        sendButton.setForeground(Color.WHITE);
        sendButton.setFocusPainted(false);
        sendButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        sendButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        sendButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void appendUserMessage(String msg) {
        addMessageBubble("Du", msg, new Color(0, 123, 255, 220), true);
    }

    @Override
    public void appendAnswerMessage(String headline, String answer) {
        addMessageBubble("Politikberater", headline + "\n" + answer, new Color(50, 50, 70, 220), false);
    }

    private void addMessageBubble(String sender, String message, Color bubbleColor, boolean rightAligned) {
        JPanel bubble = createMessageBubble(sender, message, bubbleColor);
        JPanel wrapper = new JPanel(new FlowLayout(rightAligned ? FlowLayout.RIGHT : FlowLayout.LEFT));
        wrapper.setOpaque(false);
        wrapper.add(bubble);
        messageContainer.add(wrapper);
        messageContainer.revalidate();
        messageContainer.repaint();
        adjustBubbleWidths();
        SwingUtilities.invokeLater(() -> {
            JScrollBar vertical = scrollPane.getVerticalScrollBar();
            vertical.setValue(vertical.getMaximum());
        });
    }

    private JPanel createMessageBubble(String sender, String message, Color bubbleColor) {
        JLabel senderLabel = new JLabel(sender);
        senderLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        senderLabel.setForeground(new Color(180, 180, 180));
        senderLabel.setBorder(new EmptyBorder(0, 10, 6, 10));

        JTextArea textArea = new JTextArea(message);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        textArea.setOpaque(false);
        textArea.setForeground(new Color(220, 220, 220));
        textArea.setBorder(new EmptyBorder(8, 12, 8, 12));

        JPanel bubble = new RoundedPanel(16, bubbleColor);
        bubble.setLayout(new BorderLayout());
        bubble.setBorder(new EmptyBorder(4, 4, 4, 4));
        bubble.add(senderLabel, BorderLayout.NORTH);
        bubble.add(textArea, BorderLayout.CENTER);
        bubble.setOpaque(false);
        textArea.setOpaque(false);
        textArea.setBackground(new Color(0, 0, 0, 0));
        return bubble;
    }

    private void adjustBubbleWidths() {
        int maxBubbleWidth = 400;
        for (Component wrapperComp : messageContainer.getComponents()) {
            if (!(wrapperComp instanceof JPanel wrapper)) continue;
            if (wrapper.getComponentCount() == 0) continue;
            Component bubbleComp = wrapper.getComponent(0);
            if (!(bubbleComp instanceof JPanel bubble)) continue;
            JTextArea textArea = null;
            for (Component c : bubble.getComponents()) {
                if (c instanceof JTextArea) {
                    textArea = (JTextArea) c;
                    break;
                }
            }
            if (textArea == null) continue;
            textArea.setSize(new Dimension(maxBubbleWidth, Short.MAX_VALUE));
            Dimension prefSize = textArea.getPreferredSize();
            textArea.setPreferredSize(new Dimension(maxBubbleWidth, prefSize.height));
            textArea.setMaximumSize(new Dimension(maxBubbleWidth, prefSize.height));
            textArea.revalidate();
            bubble.revalidate();
            wrapper.setPreferredSize(new Dimension(scrollPane.getViewport().getWidth(), wrapper.getPreferredSize().height));
            wrapper.setMaximumSize(new Dimension(scrollPane.getViewport().getWidth(), Integer.MAX_VALUE));
            wrapper.revalidate();
        }
        messageContainer.revalidate();
        messageContainer.repaint();
    }

    // Die Methode für die Krisenanzeige im Berater-Panel:
    @Override
    public void printKrise() {
        addMessageBubble("Politikberater", "Sie sollten sich um diese Krise kümmern! Wenden Sie sich bei Fragen gerne an mich!", Color.RED, false);
    }

    private static class RoundedPanel extends JPanel {
        private final int cornerRadius;
        private final Color backgroundColor;

        public RoundedPanel(int radius, Color bgColor) {
            super();
            cornerRadius = radius;
            backgroundColor = bgColor;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(0, 0, 0, 60));
            g2.fillRoundRect(4, 4, getWidth() - 8, getHeight() - 8, cornerRadius, cornerRadius);
            g2.setColor(backgroundColor);
            g2.fillRoundRect(0, 0, getWidth() - 8, getHeight() - 8, cornerRadius, cornerRadius);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    static class ModernScrollBarUI extends javax.swing.plaf.basic.BasicScrollBarUI {
        private final Dimension d = new Dimension();

        @Override
        protected JButton createDecreaseButton(int orientation) {
            return createZeroButton();
        }

        @Override
        protected JButton createIncreaseButton(int orientation) {
            return createZeroButton();
        }

        private JButton createZeroButton() {
            JButton jbutton = new JButton();
            jbutton.setPreferredSize(d);
            jbutton.setMinimumSize(d);
            jbutton.setMaximumSize(d);
            return jbutton;
        }

        @Override
        protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
            g.setColor(new Color(45, 45, 70));
            g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
        }

        @Override
        protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setPaint(new Color(100, 180, 255, 180));
            g2.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10);
            g2.dispose();
        }
    }

}
