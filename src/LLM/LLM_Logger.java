package LLM;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class LLM_Logger {

    public static final String ENGINE_LOG_PATH = "src/temp/engine_log.txt";
    public static final String ADVISOR_LOG_PATH = "src/temp/advisor_log.txt";

    private static boolean engineLogInitialized = false;
    private static boolean advisorLogInitialized = false;

    /**
     * Schreibt einen Eintrag in das Log.
     * Beim ersten Aufruf wird die Datei neu erstellt (überschrieben),
     * danach wird sie ergänzt.
     */




    public void logEngine(String entry) {
        writeToFile(ENGINE_LOG_PATH, entry, engineLogInitialized);
        engineLogInitialized = true;
    }


    public void logAdvisor(String entry) {
        writeToFile(ADVISOR_LOG_PATH, entry, advisorLogInitialized);
        advisorLogInitialized = true;
    }

    /**
     * Schreibt den Eintrag in eine Datei.
     * Wenn alreadyInitialized false ist, wird die Datei überschrieben.
     * Ansonsten wird angehängt.
     */
    private static void writeToFile(String path, String entry, boolean alreadyInitialized) {

        try (FileWriter writer = new FileWriter(path, alreadyInitialized)) {
            writer.write("Time: " + LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute() +" "+ entry + System.lineSeparator());
        } catch (IOException e) {
            System.err.println("Fehler beim Schreiben in " + path + ": " + e.getMessage());
        }
    }

}
