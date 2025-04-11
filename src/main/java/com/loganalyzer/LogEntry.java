package com.loganalyzer;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a single entry in a log file.
 * Contains: timestamp of the log entry, log level or severity, and the message of the log
 */
public class LogEntry {
   
    private LocalDateTime timestamp;
    private LogLevel level;
    private String message; 

    public LogEntry(LocalDateTime timestamp, LogLevel level, String message){
        this.timestamp = timestamp;
        this.level = level;
        this.message = message;
    }
    
    // Getters and setters
    public LocalDateTime getTimestamp(){
        return timestamp;
    }

    public LogLevel getLevel(){
        return level;
    }

    public String getMessage(){
        return message;
    }

    public void setTimestamp(LocalDateTime timestamp){
        this.timestamp = timestamp;
    }

    public void setLevel(LogLevel level){
        this.level = level;
    }

    public void setMessage(String message){
        this.message = message;
    }

    // Overrides for string representation and comparison
    @Override
    public String toString(){
        return "LogeEntry{" + "timestamp=" + timestamp + ", level=" + level + '\'' +
         ", message=" + message + '\'' + "}";
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()){
            // if log entry objectis null or not of the same class
            return false;
        }

        // Cast object to LogEntry class
        LogEntry logObj = (LogEntry) obj;

        return Objects.equals(timestamp, logObj.timestamp) && Objects.equals(level, logObj.level) 
        && Objects.equals(message, logObj.message);
    }

    // Needed for when overwritting .equals()
    @Override
    public int hashCode(){
        return Objects.hash(timestamp, level, message);
    }
}
