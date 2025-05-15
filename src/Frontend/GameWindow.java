package Frontend;

import javax.swing.*;
import java.awt.*;

public class GameWindow {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameWindow::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("PolitikSimulatorTest");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //frame.setUndecorated(true);



        // Panel für den Hintergrund
        JPanel backgroundPanel = startBackgroundImage();
        backgroundPanel.setLayout(new BorderLayout()); // Mit BorderLayout für einfachere Positionierung

        // Overlay Panel für UI-Komponenten
        JPanel overlayPanel = new JPanel(null);  // null Layout, um genaue Positionen festzulegen
        overlayPanel.setOpaque(false);  // Transparentes Overlay, damit Hintergrund sichtbar bleibt

        // Eingabefeld und Sendebutton in der Mitte unten
        JPanel inputPanel = createInputPanel();
        inputPanel.setBounds((frame.getSize().width/2), 200, 400, 80); // zentriert unten
        overlayPanel.add(inputPanel);

        // Status-Button unten links
        JButton statusButton = createIconButton("Status", "/Frontend/status.png", 140, 80);
        statusButton.setBounds(30, 340, 180, 180); // Position links unten
        overlayPanel.add(statusButton);

        // Advisor-Button unten rechts
        JButton advisorButton = createIconButton("Berater", "/Frontend/advisor.png", 140, 80);
        advisorButton.setBounds(630, 530, 180, 180); // Position rechts unten
        overlayPanel.add(advisorButton);

        // Hintergrund und Overlay zusammenfügen
        backgroundPanel.add(overlayPanel, BorderLayout.CENTER);

        // Content-Pane setzen und Frame sichtbar machen
        frame.setContentPane(backgroundPanel);
        frame.setVisible(true);
    }

    private static JPanel createInputPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setSize(400, 80);

        JTextArea inputArea = new JTextArea(3, 30);
        inputArea.setLineWrap(true);
        inputArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(inputArea);

        JButton sendButton = new JButton("Absenden");
        sendButton.setPreferredSize(new Dimension(100, 80));
        sendButton.addActionListener(e -> {
            String inputText = inputArea.getText();
            System.out.println("Eingabe: " + inputText);
            inputArea.setText("");
        });

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(sendButton, BorderLayout.EAST);

        return panel;
    }

    private static JPanel startBackgroundImage() {
        var url = GameWindow.class.getResource("/Frontend/background.png");

        if (url != null) {
            System.out.println("✅ Bild gefunden unter: " + url);
            Image image = new ImageIcon(url).getImage();

            return new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    // Hintergrundbild wird skaliert, um das Panel auszufüllen
                    g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                }
            };
        } else {
            System.err.println("❌ Bild nicht gefunden!");
            return new JPanel();
        }
    }

    private static JButton createIconButton(String text, String iconPath, int width, int height) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(width, height));

        var iconURL = GameWindow.class.getResource(iconPath);
        if (iconURL != null) {
            ImageIcon icon = new ImageIcon(iconURL);
            Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(scaledImage));  // Skaliertes Icon setzen
            button.setHorizontalTextPosition(SwingConstants.CENTER);
            button.setVerticalTextPosition(SwingConstants.BOTTOM);
        } else {
            System.err.println("❌ Icon nicht gefunden: " + iconPath);
        }
        return button;
    }
}
