package com.sl2425.da.sellersapp.Model;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.logging.FileHandler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.Formatter;
import java.util.Date;
public class LogProperties
{
    public static Logger logger = Logger.getLogger("SellersApp");
    static
    {
        try
        {
            FileHandler fileHandler = new FileHandler("app.log", true); // 'true' for appending
            fileHandler.setFormatter(new CustomFormatter());
            logger.addHandler(fileHandler);
            logger.setLevel(java.util.logging.Level.ALL);
            logger.setUseParentHandlers(true); //False to disable console output
        }
        catch (IOException e)
        {
            logger.severe("Failed to initialize logger: " + e.getMessage());
        }
    }


    private static class CustomFormatter extends Formatter
    {
        private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        @Override
        public String format(LogRecord record)
        {
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



