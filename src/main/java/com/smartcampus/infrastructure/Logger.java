package com.smartcampus.infrastructure;

import java.time.LocalDateTime;

/**
 * Singleton logger shared by the whole application.
 */
public final class Logger {
    private static final Logger INSTANCE = new Logger();

    private Logger() {
    }

    public static Logger getInstance() {
        return INSTANCE;
    }

    public void log(String message) {
        System.out.println("[Logger Singleton | " + LocalDateTime.now() + "] " + message);
    }
}
