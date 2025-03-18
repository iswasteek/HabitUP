package com.fsf.habitup.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class LogService {
    private final Environment env;

    public LogService(Environment env) {
        this.env = env;
    }

    public Path getLogFilePath() {
        String logFilePath = env.getProperty("logging.file.name", "logs/habitUP.log"); // Default path
        return Paths.get(logFilePath).toAbsolutePath();
    }

    public void printLogFilePath() {
        System.out.println("Log file path: " + getLogFilePath());
    }

    public List<String> readLogs() {
        Path logFile = getLogFilePath();
        try {
            if (Files.exists(logFile)) {
                return Files.readAllLines(logFile);
            } else {
                System.out.println("Log file does not exist.");
                return List.of("No logs found.");
            }
        } catch (IOException e) {
            System.err.println("Error reading log file: " + e.getMessage());
            return List.of("Error reading logs.");
        }
    }

}
