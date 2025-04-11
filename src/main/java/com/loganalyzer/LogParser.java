package com.loganalyzer;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * Utility class for reading log files line by line and converting them to LogEntry
 */
public class LogParser {
    
    // Utility for executing tasks concurrently from the pool of threads
    private final ExecutorService executorService;

    public LogParser(){
        // CREATES a pool with 4 threads
        this.executorService = Executors.newFixedThreadPool(4);
    }

    /**
     * Open and parse a log file into a list of LogEntry objects
     */
    public List<LogEntry> parseFile(String filePath){
        // List of parsed Log entry objects
        List<LogEntry> entries = new ArrayList<>();
        // List of receipt-like objects that promise later results
        List<Future<List<LogEntry>>> promises = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String line;
            List<String> allLines = new ArrayList<>();

            while ((line = reader.readLine()) != null) { 
                allLines.add(line);
            }

            // Determine the size of each chunk to be read per thread (4 threads)
            int chunkSize = (int) Math.ceil(allLines.size() / 4.0);
            //Determine which lines is each thread reading
            for(int i = 0; i<4; i++){
                int start = i * chunkSize;
                int end = Math.min(start + chunkSize, allLines.size());

                // Creates sublist with each chunk
                List<String> chunk = allLines.subList(start, end);
                // Submit task to executor as a lambda
                Future<List<LogEntry>> promiseChunk = executorService.submit(() -> parseLines(chunk));
                promises.add(promiseChunk);
            }

            // After all tasks are completed, get the results of the promises
            for(Future<List<LogEntry>> p : promises){
                try {
                    // Add to the entries list
                    entries.addAll(p.get());
                } catch (InterruptedException | ExecutionException e) {
                    System.err.println("Error while processing chunk: " + e.getMessage());
                }
                
            }

        } catch (IOException e) {
            System.err.println("Cannot read file: " + e.getMessage());
        } finally{
            executorService.shutdown(); // Shut down the executor service since is not needed anymore
        }

        return entries;
    }

    /***
     * Gets the respective chunk of lines and call parseLine() on each other
     */
    public List<LogEntry> parseLines(List<String> lines){

        // Store the log entries for each chunk
        List<LogEntry> entries = new ArrayList<>();
        for(String l : lines){
            LogEntry e = parseLine(l);

            if(e != null){
                entries.add(e);
            }
        }

        return entries;
    }
    
    /***
     * Parse a single line, used with the reader in parseFile()
     */
    public LogEntry parseLine(String line){
        try {
            String[] components = line.split(" ", 3); //splits into the first 3 spaces, one per component
            if(components.length < 3) {
                return null;
            }

            LocalDateTime timestamp = LocalDateTime.parse(components[0]);
            LogLevel level = LogLevel.valueOf(components[1].toUpperCase());
            String message = components[2];

            return new LogEntry(timestamp, level, message);

        } catch (Exception e) {
            System.err.println("Line could not be read or was skipped: " + line);
            return null;
        }
    }
    
}
