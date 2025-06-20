/*
 * Refined version of your ChatUI application for a modern and sleek look.
 * Enhancements:
 * - Improved scroll bar
 * - Responsive message bubbles with max width
 * - Antialiased rendering for smooth visuals
 * - Consistent styling across components
 */

package Frontend;

import Game.GameController;
import Player.Player_stats;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.nio.file.Paths;

public class ChatUI extends JFrame {

    private final MainChatPanel mainChatPanel;
    private final AdvisorChatPanel miniChatPanel;

    private GameController gameController;
    private Player_stats playerStats;

    private JProgressBar trustBar, mediaBar, coalitionBar, healthBar, stressBar, yearsInOfficeBar;

    public ChatUI() {
        setTitle("Politik-Simulator 2025");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 720);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {}

        mainChatPanel = new MainChatPanel(Paths.get("src/background.png"));
        miniChatPanel = new AdvisorChatPanel(Paths.get("src/advisorbackground.png"));

        JTabbedPane rightTabs = new JTabbedPane();
        rightTabs.setUI(new ModernTabbedPaneUI());
        rightTabs.setBackground(new Color(30, 30, 47));
        rightTabs.setForeground(Color.BLACK);
        UIManager.put("TabbedPane.selected", new Color(50, 50, 70));
        UIManager.put("TabbedPane.contentAreaColor", new Color(30, 30, 47));

        rightTabs.addTab("Umfragewerte", createProgressPanel());
        rightTabs.addTab("Berater", miniChatPanel);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mainChatPanel, rightTabs);
        splitPane.setDividerLocation(850);
        splitPane.setDividerSize(10);
        splitPane.setBackground(new Color(45, 45, 60));

        add(splitPane, BorderLayout.CENTER);

        mainChatPanel.getSendButton().addActionListener(e -> {
            String text = mainChatPanel.getInputField().getText().trim();
            if (!text.isEmpty()) sendMainChat(text);
        });

        miniChatPanel.getSendButton().addActionListener(e -> {
            String text = miniChatPanel.getInputField().getText().trim();
            if (!text.isEmpty()) sendMiniChat(text);
        });
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setPlayerStats(Player_stats playerStats) {
        this.playerStats = playerStats;
    }

    public void sendMainChat(String msg) {
        mainChatPanel.appendUserMessage(msg);
        if (gameController != null) gameController.sendDecision(msg);
        mainChatPanel.clearInput();
    }

    public void printMainAnswer(String headline, String answer) {
        printValues();
        mainChatPanel.appendAnswerMessage(headline, answer);
    }

    public void sendMiniChat(String msg) {
        miniChatPanel.appendUserMessage(msg);
        if (gameController != null) gameController.sendAdvisor(msg);
        miniChatPanel.clearInput();
    }

    public void printMiniAnswer(String headline, String answer) {
        miniChatPanel.appendAnswerMessage(headline, answer);
    }

    public void printKriseAll() {
        mainChatPanel.printKrise();
        miniChatPanel.printKrise();
    }

    private JPanel createProgressPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(25, 25, 35));  // Dunkler
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        trustBar = createStyledBar("Vertrauen im Parlament", new Color(79, 195, 247));
        mediaBar = createStyledBar("Medienakzeptanz", new Color(255, 202, 40));
        coalitionBar = createStyledBar("Koalitionsstabilität", new Color(129, 199, 132));
        healthBar = createStyledBar("Gesundheit", new Color(239, 83, 80));
        stressBar = createStyledBar("Stresslevel", new Color(66, 165, 245));
        yearsInOfficeBar = createStyledBar("Amtsjahre", new Color(171, 71, 188));
        yearsInOfficeBar.setMaximum(4);

        // Abstand zwischen den ProgressBars
        int spacing = 15;

        panel.add(trustBar);
        panel.add(Box.createRigidArea(new Dimension(0, spacing)));
        panel.add(mediaBar);
        panel.add(Box.createRigidArea(new Dimension(0, spacing)));
        panel.add(coalitionBar);
        panel.add(Box.createRigidArea(new Dimension(0, spacing)));
        panel.add(healthBar);
        panel.add(Box.createRigidArea(new Dimension(0, spacing)));
        panel.add(stressBar);
        panel.add(Box.createRigidArea(new Dimension(0, spacing)));
        panel.add(yearsInOfficeBar);

        return panel;
    }


    private JProgressBar createStyledBar(String title, Color color) {
        JProgressBar bar = new JProgressBar(0, 100);
        bar.setString(title);
        bar.setStringPainted(true);
        bar.setForeground(color);
        bar.setBackground(new Color(40, 40, 60));      // dunkler Hintergrund
        bar.setBorder(BorderFactory.createLineBorder(new Color(70, 70, 90)));  // dezenter Rand
        bar.setFont(new Font("Segoe UI", Font.BOLD, 16));   // größerer, fetter Text
        bar.setPreferredSize(new Dimension(200, 30));        // weniger hoch, breiter
        return bar;
    }


    private void printValues() {
        if (playerStats != null) {
            trustBar.setValue(playerStats.getTrustParliament());
            mediaBar.setValue(playerStats.getMediaApproval());
            coalitionBar.setValue(playerStats.getCoalitionStability());
            healthBar.setValue(playerStats.getHealth());
            stressBar.setValue(playerStats.getStressLevel());
            yearsInOfficeBar.setValue(playerStats.getYearsInOffice());
        }
    }

    // Optional: Add custom tab styling here
    private static class ModernTabbedPaneUI extends javax.swing.plaf.basic.BasicTabbedPaneUI {
        @Override
        protected void installDefaults() {
            super.installDefaults();
            tabAreaInsets.left = 10;
        }

        @Override
        protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
            g.setColor(isSelected ? new Color(70, 130, 180) : new Color(45, 45, 70));
            g.fillRoundRect(x + 4, y + 2, w - 8, h - 4, 12, 12);
        }

        @Override
        protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
            // No border
        }
    }
}
