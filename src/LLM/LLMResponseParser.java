package LLM;

import Player.Player_stats;
import org.json.JSONObject;

public class LLMResponseParser {

    public static void parseAndApplyEngineResponse(String jsonString, Player_stats player, LLM_Logger logger) {
        try {
            // Den JSON-String in ein JSONObject umwandeln
            JSONObject response = new JSONObject(jsonString);

            // Die content-Zeichenkette extrahieren
            JSONObject choice = response.getJSONArray("choices").getJSONObject(0);
            JSONObject message = choice.getJSONObject("message");
            String content = message.getString("content");

            // JSON-String im content bereinigen
            int startIndex = content.indexOf("{");
            int endIndex = content.lastIndexOf("}");
            if (startIndex == -1 || endIndex == -1) {
                throw new Exception("Ungültiger JSON-Inhalt in content");
            }

            String jsonContent = content.substring(startIndex, endIndex + 1);
            JSONObject contentJson = new JSONObject(jsonContent);

            // Titel und Kommentar ausgeben
            String title = contentJson.getString("title");
            String commentary = contentJson.getString("commentary");
            System.out.println("Titel: " + title);
            System.out.println("Kommentar: " + commentary);

            logger.logEngine(commentary);

            // newStats auslesen
            JSONObject newStats = contentJson.getJSONObject("newStats");

            // Werte ins Player_stats-Objekt übertragen
            player.setPopularity(newStats.getInt("popularity"));
            player.setTrustParliament(newStats.getInt("trustParliament"));
            player.setMediaApproval(newStats.getInt("mediaApproval"));
            player.setCoalitionStability(newStats.getInt("coalitionStability"));
            player.setHealth(newStats.getInt("health"));
            player.setStressLevel(newStats.getInt("stressLevel"));
            player.setYearsInOffice(newStats.getInt("yearsInOffice"));
            player.setPassedLaws(newStats.getInt("passedLaws"));
            player.setCrisesSurvived(newStats.getInt("crisesSurvived"));

            System.out.print(player.toString());

        } catch (Exception e) {
            System.err.println("Fehler beim Verarbeiten: " + e.getMessage());
        }
    }

    public static String parseAndApplyAdvisorResponse(String jsonString, LLM_Logger logger) {
        try {
            JSONObject response = new JSONObject(jsonString);
            JSONObject choice = response.getJSONArray("choices").getJSONObject(0);
            JSONObject message = choice.getJSONObject("message");
            String content = message.getString("content");
            logger.logAdvisor(content);
            System.out.println("Antwort: " + content);
            return content.trim(); // Optional: Whitespace entfernen

        } catch (Exception e) {
            System.err.println("Fehler beim Verarbeiten (Advisor): " + e.getMessage());
            return null;
        }
    }

}
