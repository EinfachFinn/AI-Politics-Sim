import Game.GameController;
import Player.Player_stats;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {


        System.out.println("devmode (1) or normal mode (2)");
        Scanner sc = new Scanner(System.in);
        int devMode = sc.nextInt();
        if(devMode == 1) {
            devStart();
        }
        else if(devMode == 2)
        {

            GameController gameController = new GameController();
            gameController.setupNewPlayer();
            gameController.startGameLoop();

        }


    }

    public static void devStart() {
        System.out.println("Dev Mode!");
        GameController gameController = new GameController();
        Player_stats player = new Player_stats();

        player.setName("Thorben");
        float difficulty = 1;
        player.setYearsInOffice(0);
        player.setPopularity(Math.round(50 * difficulty));
        player.setTrustParliament(Math.round(50 * difficulty));
        player.setMediaApproval(Math.round(50 * difficulty));
        player.setCoalitionStability(Math.round(50 * difficulty));
        player.setHealth(Math.round(50 * difficulty));
        player.setStressLevel(Math.round(50 * 1/difficulty));


        player.setOwnPartyName("Partei der Ziele");
        player.setPoliticalIdeology("Fortschritt");
        player.setCoalitionParties("CDU, SPD");

        player.setPassedLaws(0);
        player.setCrisesSurvived(0);
        player.setAge(22);



        System.out.println("Hallo " + player.getName() + ", du bist jetzt Kanzler der Partei " + player.getOwnPartyName() + " ! Herzlichen Glückwunsch!");
        System.out.println("Hier sind deine Startwerte: ");
        System.out.println(player.toString());

        gameController.setPlayer(player);
        gameController.startGameLoop();
    }
}
