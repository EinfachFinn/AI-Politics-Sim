package Frontend;

import Player.Player_stats;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SetupGUI extends JFrame {

    private JTextField partyNameField;
    private JTextField ideologyField;
    private JTextField coalitionField;
    private JTextField ageField;
    private JLabel statusLabel;

    private final Player_stats player;
    private final Runnable onSuccess;

    // Neuer Konstruktor mit Callback
    public SetupGUI(Player_stats player, Runnable onSuccess) {
        this.player = player;
        this.onSuccess = onSuccess;


        setTitle("Parteigründung");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        // Hauptpanel mit BoxLayout (vertikal)
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        mainPanel.setBackground(new Color(30, 30, 30)); // dunkler Hintergrund

        // Eingabefelder + Labels
        partyNameField = createInputField(mainPanel, "Wie soll deine Partei heißen?");
        ideologyField = createInputField(mainPanel, "Welche Ideologie verfolgst du?");
        coalitionField = createInputField(mainPanel, "Mit wem koalierst du?");
        ageField = createInputField(mainPanel, "Wie alt bist du?");

        // Button
        JButton submitButton = new JButton("Bestätigen");
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.setBackground(new Color(60, 120, 200));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        submitButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        submitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(submitButton);

        // Status Label
        statusLabel = new JLabel("Setup your Character");
        statusLabel.setForeground(Color.LIGHT_GRAY);
        statusLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(statusLabel);

        // Aktion
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveData();
                return;
            }
        });

        add(mainPanel);
        setVisible(true);
    }

    private JTextField createInputField(JPanel panel, String labelText) {
        JLabel label = new JLabel(labelText);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField textField = new JTextField();
        textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setBackground(new Color(50, 50, 50));
        textField.setForeground(Color.WHITE);
        textField.setCaretColor(Color.WHITE);
        textField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        panel.add(label);
        panel.add(Box.createVerticalStrut(5));
        panel.add(textField);
        panel.add(Box.createVerticalStrut(15));

        return textField;
    }

    private void saveData() {
        try {
            String partyName = partyNameField.getText();
            String ideology = ideologyField.getText();
            String coalition = coalitionField.getText();
            int age = Integer.parseInt(ageField.getText());

            player.setOwnPartyName(partyName);
            player.setPoliticalIdeology(ideology);
            player.setCoalitionParties(coalition);
            player.setAge(age);
            player.setPassedLaws(0);
            player.setCrisesSurvived(0);

            statusLabel.setText("Daten erfolgreich gespeichert!");
            dispose();
            if (onSuccess != null) {
                onSuccess.run();
            }

        } catch (NumberFormatException ex) {
            statusLabel.setText("⚠ Bitte gib ein gültiges Alter ein.");
        }
    }


}
