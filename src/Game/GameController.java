package Game;

import Frontend.ChatUI;
import LLM.LLMClient;
import LLM.LLMResponseParser;
import LLM.LLM_Logger;
import Player.Player_stats;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class GameController {



    public GameController() {
        this.player = new Player_stats();
        this.logger = new LLM_Logger();
        logger.logAdvisor("Das ist das log, des politikberaters. Alle Gespräche sind hier drin: ");
        logger.logAdvisor("Es ist der erste Tag im Kanzleramt");
        logger.logEngine("Das ist das log, des Games. Alle Entscheidungen und Folgen sind hier drin: ");
        logger.logEngine("Es ist der erste Tag im Kanzleramt");
    }

    public void setPlayer(Player_stats player) {this.player = player;}
    private Player_stats player;
    private LLM_Logger logger;

    private ChatUI ui;
    public void setUI(ChatUI ui) {this.ui = ui;}



    public void setupNewPlayer() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Willkommen! Wie heißt dein Politiker? ");
        String name = scanner.nextLine();
        player.setName(name);

        //System.out.println("Wie Schwierigkeitsmultiplikator. 0,5 = Schwer, 1= Mittel, 1.5 = Leicht");
        float difficulty = 1;
        player.setYearsInOffice(0);
        player.setPopularity(Math.round(50 * difficulty));
        player.setTrustParliament(Math.round(50 * difficulty));
        player.setMediaApproval(Math.round(50 * difficulty));
        player.setCoalitionStability(Math.round(50 * difficulty));
        player.setHealth(Math.round(50 * difficulty));
        player.setStressLevel(Math.round(50 * 1/difficulty));

        System.out.println("Wie soll deine Partei heißen?");
        String partyName = scanner.nextLine();
        player.setOwnPartyName(partyName);

        System.out.println("Welche ideologie verfolgst du?");
        String ideology = scanner.nextLine();
        player.setPoliticalIdeology(ideology);

        System.out.println("Mit wem koalierst du?");
        String coalitionParties = scanner.nextLine();
        player.setCoalitionParties(coalitionParties);

        player.setPassedLaws(0);
        player.setCrisesSurvived(0);

        System.out.print("Wie alt bist du? ");
        int age = scanner.nextInt();
        player.setAge(age);


        System.out.println("Hallo " + name + ", du bist jetzt Kanzler der Partei " + partyName + " ! Herzlichen Glückwunsch!");
        System.out.println("Hier sind deine Startwerte: ");
        System.out.println(player.toString());


    }

    public void startGameLoop() {
        // logger.logEngine("Erster Tag als neu gewählter Kanzler");
        // logger.logAdvisor("Erster Tag als neu gewählter Kanzler");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Willst du eine Entscheidung (1) treffen oder mit deinem Politikberater (2) sprechen?");
            int menu = scanner.nextInt();
            if (menu == 1){decision();}
            else if (menu == 2){advisor();}
        }
    }

    public void decision()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nDeine Entscheidung: ");
        String input = scanner.nextLine();
        sendDecision(input);
    }

    public void advisor() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("\nDeine Frage: ");

        String input = scanner.nextLine();
        sendAdvisor(input);
    }

    public void sendAdvisor(String adviceMessage) {
        logger.logAdvisor(adviceMessage);

        // Cursor auf Warten setzen
        ui.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        new Thread(() -> {
            try {
                String jsonResponse = LLMClient.callLLM_Advisor(adviceMessage, player);
                String message = LLMResponseParser.parseAndApplyAdvisorResponse(jsonResponse, logger, ui);
            } catch (Exception e) {
                System.out.println("Fehler beim Verarbeiten: " + e.getMessage());
                e.printStackTrace();
            } finally {
                // Cursor zurücksetzen – wichtig: im Swing-Thread
                SwingUtilities.invokeLater(() -> ui.setCursor(Cursor.getDefaultCursor()));
            }
        }).start();
    }

    public void sendDecision(String decisionMessage) {
        boolean neueKrise = Math.random() < 0.5;
        logger.logEngine(decisionMessage);

        if (neueKrise) {
            System.out.println("NEUE KRISE");
            ui.printKriseAll();
        }

        ui.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        new Thread(() -> {
            try {
                String jsonResponse = LLMClient.callLLM_Engine(decisionMessage, player, neueKrise);
                String message = LLMResponseParser.parseAndApplyEngineResponse(jsonResponse, player, logger, ui);
            } catch (Exception e) {
                System.out.println("Fehler beim Verarbeiten: " + e.getMessage());
                e.printStackTrace();
            } finally {
                SwingUtilities.invokeLater(() -> ui.setCursor(Cursor.getDefaultCursor()));
            }
        }).start();
    }


}