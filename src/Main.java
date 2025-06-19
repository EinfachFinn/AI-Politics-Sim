
import Frontend.ChatUI;
import Frontend.SetupGUI;
import Game.GameController;
import Player.Player_stats;

import javax.swing.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        startGame();
    }

    private static void startGame() {
        GameController gameController = new GameController();
        Player_stats player = new Player_stats();

        SwingUtilities.invokeLater(() -> {
            new SetupGUI(player, () -> {
                // Dieser Code wird ausgeführt, nachdem Setup abgeschlossen ist
                SwingUtilities.invokeLater(() -> {
                    ChatUI ui = new ChatUI();
                    ui.setGameController(gameController);
                    gameController.setUI(ui);
                    ui.setPlayerStats(player);
                    ui.setVisible(true);
                });
            });
        });

        gameController.setPlayer(player);
    }
}




