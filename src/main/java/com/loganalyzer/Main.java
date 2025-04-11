package com.loganalyzer;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        // Check for the existance of the log path in args
        if (args.length != 1){
            System.err.println("Log file not found");
            System.exit(1);
        }

        // Parsing the log file into a list of entries
        String filePath = args[0];
        LogParser logParser = new LogParser();
        List<LogEntry> logEntries = logParser.parseFile(filePath);

        // Validate content
        if(logEntries.isEmpty()){
            System.err.println("No entries found in the file");
            return;
        }

        // Process the log entries
        LogAnalyzer logAnalyzer = new LogAnalyzer();
        logAnalyzer.processEntries(logEntries);

        // Print the results
        logAnalyzer.printStats();

    }

}
