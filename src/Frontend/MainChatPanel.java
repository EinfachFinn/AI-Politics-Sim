
package Frontend;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;

public class MainChatPanel extends ChatPanel {
    public MainChatPanel(Path backgroundPath) {
        setLayout(new BorderLayout());
        loadBackground(backgroundPath);

        setupChatArea();
        JScrollPane scrollPane = new JScrollPane(chatArea);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        scrollPane.getVerticalScrollBar().setUI(new AdvisorChatPanel.ModernScrollBarUI());

        setupInputField();
        setupSendButton();

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBackground(new Color(30, 30, 47));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);
    }
}