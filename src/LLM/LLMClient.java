package LLM;

import Player.Player_stats;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LLMClient {

    private static final String API_URL = "https://api.together.xyz/v1/chat/completions";
    private static String API_KEY;
    private static final String LLM_MODEL = "meta-llama/Llama-3.3-70B-Instruct-Turbo-Free";

    public LLMClient() {
        Path keyPath = Paths.get("src/LLM/API_KEY.txt");
        if (Files.notExists(keyPath)) {
            System.out.println("API KEY FILE nicht gefunden.");
            System.exit(0);
        }
        try {
            API_KEY = Files.readString(keyPath, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }





    public static String callLLM_Engine(String userInput, Player_stats player, boolean neueKrise) throws IOException, InterruptedException {
        Path promptPath = Paths.get("src/Game/engineprompt.txt");
        if (Files.notExists(promptPath)) {
            System.out.println("Systemprompt nicht gefunden.");
            System.exit(0);
        }
        String systemPrompt = Files.readString(promptPath, StandardCharsets.UTF_8);


        Path logPath = Paths.get("src/engine_log.txt");
        if (Files.notExists(logPath)) {
            System.out.println("Log nicht gefunden.");
            System.exit(0);
        }
        String engineLogs = Files.readString(logPath, StandardCharsets.UTF_8);



        String playerJson = convertPlayerStatsToJsonObject(player).toString();

        JSONObject userMessage = new JSONObject();
        userMessage.put("entscheidung", userInput);
        userMessage.put("spielerStats", new JSONObject(playerJson));
        userMessage.put("neueKrise", neueKrise);

        JSONObject json = new JSONObject();
        json.put("model", LLM_MODEL);

        JSONArray messages = new JSONArray();
        messages.put(new JSONObject().put("role", "system").put("content", systemPrompt += engineLogs));
        messages.put(new JSONObject().put("role", "user").put("content", userMessage.toString()));

        json.put("messages", messages);
        json.put("stream", false);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Authorization", "Bearer " + API_KEY)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Fehler: " + response.statusCode() + " - " + response.body());
        }

        //System.out.println(response.body());
        return response.body();
    }


    public static String callLLM_Advisor(String userInput, Player_stats player) throws IOException, InterruptedException {
        Path promptPath = Paths.get("src/Game/advisorprompt.txt");

        if (Files.notExists(promptPath)) {
            System.out.println("Systemprompt nicht gefunden.");
            System.exit(0);
        }

        Path engineLogPath = Paths.get("src/engine_log.txt");
        if (Files.notExists(engineLogPath)) {
            System.out.println("Log nicht gefunden.");
            System.exit(0);
        }
        String engineLogs = Files.readString(engineLogPath, StandardCharsets.UTF_8);

        Path advisorLogPath = Paths.get("src/advisor_log.txt");
        if (Files.notExists(advisorLogPath)) {
            System.out.println("Log nicht gefunden.");
            System.exit(0);
        }
        String advisorLogs = Files.readString(advisorLogPath, StandardCharsets.UTF_8);

        String advisorPrompt = Files.readString(promptPath, StandardCharsets.UTF_8);
        String playerJson = convertPlayerStatsToJsonObject(player).toString();

        JSONObject userMessage = new JSONObject();
        userMessage.put("entscheidung", userInput);
        userMessage.put("spielerStats", new JSONObject(playerJson));

        JSONObject json = new JSONObject();
        json.put("model", LLM_MODEL);

        JSONArray messages = new JSONArray();
        messages.put(new JSONObject().put("role", "system").put("content", advisorPrompt += engineLogs += advisorLogs));
        messages.put(new JSONObject().put("role", "user").put("content", userMessage.toString()));

        json.put("messages", messages);
        json.put("stream", false);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Authorization", "Bearer " + API_KEY)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("Fehler: " + response.statusCode() + " - " + response.body());
        }

        return response.body();
    }












    private static JSONObject convertPlayerStatsToJsonObject(Player_stats p) {
        JSONObject json = new JSONObject();
        json.put("name", p.getName());
        json.put("age", p.getAge());
        json.put("yearsInOffice", p.getYearsInOffice());
        json.put("popularity", p.getPopularity());
        json.put("trustParliament", p.getTrustParliament());
        json.put("mediaApproval", p.getMediaApproval());
        json.put("coalitionStability", p.getCoalitionStability());
        json.put("health", p.getHealth());
        json.put("stressLevel", p.getStressLevel());
        json.put("ownPartyName", p.getOwnPartyName());
        json.put("politicalIdeology", p.getPoliticalIdeology());
        json.put("coalitionParties", p.getCoalitionParties());
        json.put("passedLaws", p.getPassedLaws());
        json.put("crisesSurvived", p.getCrisesSurvived());
        return json;
    }
}