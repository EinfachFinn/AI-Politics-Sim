package Frontend;

import javax.swing.*;
import java.awt.*;

class BackgroundPanel extends JPanel {
    private final Image backgroundImage;

    public BackgroundPanel(Image image) {
        this.backgroundImage = image;
        setLayout(new BorderLayout()); // ⬅️ wichtig: damit deine UI-Komponenten normal layoutet werden
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
