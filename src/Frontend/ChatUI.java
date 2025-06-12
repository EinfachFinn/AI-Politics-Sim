package Frontend;

import Game.GameController;
import Player.Player_stats;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ChatUI extends JFrame {

    private ChatPanel mainChatPanel;
    private ChatPanel miniChatPanel;

    private GameController gameController;
    private Player_stats playerStats;

    // Fortschrittsbalken
    private JProgressBar trustBar;
    private JProgressBar mediaBar;
    private JProgressBar coalitionBar;
    private JProgressBar healthBar;
    private JProgressBar stressBar;
    private JProgressBar yearsInOfficeBar;

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setPlayerStats(Player_stats playerStats) {
        this.playerStats = playerStats;
    }

    public ChatUI() {
        setTitle("Chat Anwendung");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

// In ChatUI-Konstruktor:
        mainChatPanel = new ChatPanel(Paths.get("src/background.png"), false);
        miniChatPanel = new ChatPanel(Paths.get("src/advisorbackground.png"), true);


        JTabbedPane rightTabs = new JTabbedPane();
        rightTabs.addTab("Aufgaben", createProgressPanel());
        rightTabs.addTab("Mini-Chat", miniChatPanel);

        JSplitPane splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                mainChatPanel,
                rightTabs
        );
        splitPane.setDividerLocation(900);

        add(splitPane);

        mainChatPanel.getSendButton().addActionListener(e -> {
            String text = mainChatPanel.getInputField().getText().trim();
            if (!text.isEmpty()) {
                sendMainChat(text);
            }
        });

        miniChatPanel.getSendButton().addActionListener(e -> {
            String text = miniChatPanel.getInputField().getText().trim();
            if (!text.isEmpty()) {
                sendMiniChat(text);
            }
        });
    }

    // Methoden für Chat
    public void sendMainChat(String msg) {
        mainChatPanel.appendUserMessage(msg);
        if (gameController != null) {
            gameController.sendDecision(msg);
        }
        mainChatPanel.clearInput();
    }

    public void printMainAnswer(String headline, String answer) {
        printValues();
        mainChatPanel.appendAnswerMessage(headline, answer);
    }

    public void sendMiniChat(String msg) {
        miniChatPanel.appendUserMessage(msg);
        if (gameController != null) {
            gameController.sendAdvisor(msg);
        }
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
        panel.setLayout(new GridLayout(6, 1, 10, 10));

        trustBar = new JProgressBar(0, 100);
        trustBar.setStringPainted(true);
        trustBar.setString("Trust in Parliament");
        panel.add(trustBar);

        mediaBar = new JProgressBar(0, 100);
        mediaBar.setStringPainted(true);
        mediaBar.setString("Media Approval");
        panel.add(mediaBar);

        coalitionBar = new JProgressBar(0, 100);
        coalitionBar.setStringPainted(true);
        coalitionBar.setString("Coalition Stability");
        panel.add(coalitionBar);

        healthBar = new JProgressBar(0, 100);
        healthBar.setStringPainted(true);
        healthBar.setString("Health");
        panel.add(healthBar);

        stressBar = new JProgressBar(0, 100);
        stressBar.setStringPainted(true);
        stressBar.setString("Stress Level");
        panel.add(stressBar);

        yearsInOfficeBar = new JProgressBar(0, 4);
        yearsInOfficeBar.setStringPainted(true);
        yearsInOfficeBar.setString("Years In Office");
        panel.add(yearsInOfficeBar);

        return panel;
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
}
