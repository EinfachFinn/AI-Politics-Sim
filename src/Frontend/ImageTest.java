package Frontend;

import javax.swing.*;
import java.awt.*;

public class ImageTest {
    public static void main(String[] args) {
        var url = ImageTest.class.getResource("/Frontend/background.png");

        if (url != null) {
            System.out.println("✅ Bild gefunden unter: " + url);

            // 🖼️ Fenster mit vollflächig gestrecktem Bild anzeigen
            JFrame frame = new JFrame("Bildvorschau – gestreckt");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);

            Image image = new ImageIcon(url).getImage();

            JPanel panel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                }
            };

            frame.setContentPane(panel);
            frame.setVisible(true);
        } else {
            System.out.println("❌ Bild NICHT gefunden. Bitte Pfad prüfen.");
        }
    }
}
