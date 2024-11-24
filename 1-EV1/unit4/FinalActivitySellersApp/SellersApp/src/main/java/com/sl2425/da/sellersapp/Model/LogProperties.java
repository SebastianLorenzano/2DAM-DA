package com.sl2425.da.sellersapp.Model;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.logging.FileHandler;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.Formatter;
import java.util.Date;

public class LogProperties {

    // Set up the root logger to ensure all logs are captured
    public static Logger logger = LogManager.getLogManager().getLogger(""); // Root logger
    static {
        try {
            Logger rootLogger = LogManager.getLogManager().getLogger("");
            for (var handler : rootLogger.getHandlers())
            {
                rootLogger.removeHandler(handler);
            }

            // Set up a custom FileHandler to capture all logs
            String logFile = "logs/[" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + "] log.txt";
            FileHandler fileHandler = new FileHandler(logFile, true); // Makes it so it doesn't override
            fileHandler.setFormatter(new CustomFormatter());
            fileHandler.setLevel(Level.ALL);
            logger.addHandler(fileHandler);

            //logger.setLevel(Level.ALL);
            // With this, you log a lot of the Hibernate's internal logs, which is not necessary
             // and just makes the log file bigger

            logger.setUseParentHandlers(false); // Disable default console output

        } catch (IOException e) {
            logger.severe("Failed to initialize logger: " + e.getMessage());
        }
    }

    // Custom formatter to format log entries
    private static class CustomFormatter extends Formatter {
        private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        @Override
        public String format(LogRecord record) {
            StringBuilder builder = new StringBuilder();
            builder.append("[")
                    .append(dateFormat.format(new Date(record.getMillis())))
                    .append("] ")
                    .append(record.getLevel()).append(": ")
                    .append(formatMessage(record))
                    .append("\n");
            return builder.toString();
        }
    }
}
