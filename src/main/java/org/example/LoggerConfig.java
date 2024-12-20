package org.example;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.Level;

public class LoggerConfig {
    public LoggerConfig() {
    }
    public static Logger createLogger(Class<?> className) throws IOException {
        Handler fileHandler = new FileHandler("logger.log", true);
        ((Handler)fileHandler).setFormatter(new SimpleFormatter());
        Logger logger = Logger.getLogger(className.getName());
        logger.addHandler(fileHandler);
        logger.setLevel(Level.FINE);
        logger.setUseParentHandlers(false);
        return logger;
    }
}
