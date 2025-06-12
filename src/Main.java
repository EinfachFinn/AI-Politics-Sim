
import Frontend.ChatUI;
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

        gameController.setupNewPlayer();
        // gameController.startGameLoop(); // <-- Blockiert vermutlich den Thread!
        Player_stats player_stats = new Player_stats();
        gameController.setPlayer(player_stats);

        SwingUtilities.invokeLater(() -> {
            ChatUI ui = new ChatUI();
            ui.setGameController(gameController);
            gameController.setUI(ui);
            ui.setPlayerStats(player_stats);
            ui.setVisible(true);
        });
    }
}





