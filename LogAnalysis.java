
import java.io.*;
import java.util.*;
import java.util.logging.Logger;
import java.io.BufferedReader;

public class LogAnalysis {

    private Queue<String> logQueue = new Queue<>();
    private Stack<String> errorStack = new Stack<>();
    private LinkedList<String> recentErrors = new LinkedList<>();

    private int infoCount = 0;
    private int warnCount = 0;
    private int errorCount = 0;
    private int memoryWarningCount = 0;

    private final int MazRecentErrors = 100;

    public void readFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String logLine;
            while ((logLine = br.readLine()) != null) {
                // Assume log entries follow the format: [timestamp] logLevel message
                String[] parts = logLine.split(" ", 3);
                if (parts.length < 3) continue; // skip malformed entries

                String logTime = parts[0] + " " + parts[1];
                String logLevel = parts[2].substring(0, parts[2].indexOf(" "));
                String logMessage = parts[2].substring(parts[2].indexOf(" ") + 1);

                LogEntry logEntry = new LogEntry(logTime, logLevel, logMessage);
                logQueue.enqueue(logLine); // Enqueue the log entry
            }
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    public static void main(String[] args) {

        // Display analysis
        LogAnalysis logAnalysis = new LogAnalysis();
        logAnalysis.readFile("/Users/corinnedekshenieks/Downloads/log-data.csv");
        logAnalysis.processLogEntries();
        System.out.println("Log Level Counts:");
        System.out.println("INFO: " + logAnalysis.infoCount);
        System.out.println("WARN: " + logAnalysis.warnCount);
        System.out.println("ERROR: " + logAnalysis.errorCount);
        System.out.println("\nMemory Warnings: " + logAnalysis.memoryWarningCount);

        System.out.println("\nLast 100 Errors:");
        for (String error : logAnalysis.recentErrors) {
            System.out.println(error);
        }
        

    }
    public void processLogEntries() {
        while (!logQueue.isEmpty()) {
            String LogEntry = logQueue.dequeue(); // Dequeue the entry
            // Increment counters based on log level
            if(LogEntry.contains("INFO")) {
                infoCount++;
            }
            else if(LogEntry.contains("WARN")) {
                warnCount++;
                if (LogEntry.contains("Memory")) {
                    memoryWarningCount++;
                }
            }
            else if(LogEntry.contains("ERROR")) {
                errorCount++;
                errorStack.push(LogEntry); // Push to stack
                // Store the last 100 errors
                if (recentErrors.size() == 100) {
                    recentErrors.remove(0); // Keep only 100 errors
                }
                recentErrors.add(LogEntry);
            }

        }
    }
}


class LogEntry {
    String logTime;
    String logLevel;
    String logMessage;

    public LogEntry(String logTime, String logLevel, String logMessage) {
        this.logTime = logTime;
        this.logLevel = logLevel;
        this.logMessage = logMessage;
    }

    @Override
    public String toString() {
        return "[" + logTime + "] " + logLevel + " " + logMessage;
    }
}
