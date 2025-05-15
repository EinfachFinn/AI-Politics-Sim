package Game;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Startchecker {
    public static void main(String[] args) {
        Path promptPath = Paths.get("src/LLM/API_KEY.txt");
        if(Files.notExists(promptPath)) {
            System.out.println("No file found");
            System.exit(1);
        }
        else
        {
            System.out.println("File found");
        }
    }
}
