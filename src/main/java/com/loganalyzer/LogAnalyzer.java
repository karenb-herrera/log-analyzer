package com.loganalyzer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Analyze the logs entries 
 */
public class LogAnalyzer {
    
    // Tracks total number of logs and log levels
    private int totalLogs;
    private Map<LogLevel, Integer> levelCount;

    // Tracks totalnumber of 'success' and 'failure' messages
    private int successCount;
    private int failureCount;

    public LogAnalyzer(){
        this.totalLogs = 0;
        this.levelCount = new HashMap<>();
        this.successCount = 0;
        this.failureCount = 0;
    }

    /** 
     * Processes entries and gets stat info
     * @param logEntries List of log entries to analyze
     */
    public void processEntries(List<LogEntry> logEntries){

        for (LogEntry entry : logEntries){
            // Increment how many log lines have been read
            totalLogs++;

            // Increment respective level counter
            LogLevel level = entry.getLevel();
            levelCount.put(level, levelCount.getOrDefault(level, 0) + 1);

            // Check if message contains the word success or failed
            String status = entry.getMessage().toLowerCase();
            if (status.contains("success") || status.contains("pass")){
                successCount++;
            }
            if(status.contains("failure") || status.contains("fail") || status.contains("failed")){
                failureCount++;
            }
        }

    }

    // Getters
    public int getTotalLogs(){
        return totalLogs;
    }

    public Map<LogLevel, Integer> getLevelCount(){
        return levelCount;
    }

    // Print the results of the analytics
    public void printStats(){
        System.out.println("==== Log Analysis Summary ====");
        System.err.println("Total logs: " + totalLogs);

        System.out.println("---- Log Levels ----");
        for (Map.Entry<LogLevel, Integer> entry : levelCount.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        System.out.println("---- Keyword Stats ----");
        System.err.println("Success messages: " + successCount);
        System.err.println("Failure messages: " + failureCount);

    }
}
