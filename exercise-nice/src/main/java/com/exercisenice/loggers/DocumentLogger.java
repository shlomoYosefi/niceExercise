package com.exercisenice.loggers;

import java.io.IOException;
import java.util.logging.*;


public abstract class DocumentLogger {

    public static Logger logger;

    static {
        try {
            logger = getLogger();
        } catch (IOException e) {
            logger = Logger.getLogger("documentLogger");
        }
    }

    public static Logger getLogger() throws IOException {
        Logger logger = Logger.getLogger("documentLogger");
        ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(Level.INFO);
        logger.addHandler(ch);
        try {
            FileHandler fh = new FileHandler("loggerDocument.log");
            fh.setLevel(Level.WARNING);
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "logger document not working", e);
        }
        return logger;
    }
}
