package com.exercisenice.loggers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.util.logging.*;


public abstract class DocumentLogger {

    public static Logger logger = Logger.getLogger("documentLogger");

    @Bean
    public static Logger getLogger() throws IOException {

        ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(Level.SEVERE);
        logger.addHandler(ch);

        try {
            FileHandler fh = new FileHandler("loggerDocument.log");
            fh.setLevel(Level.INFO);
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "logger not working", e);
        }
        return logger;
    }
}
