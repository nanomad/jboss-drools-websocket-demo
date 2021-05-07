package com.redhat.consulting.jboss_ws_demo.model;

public class LogMessage {
    public enum Level {
        DEBUG,
        INFO,
        WARNING,
        ERROR
    }

    private final Level level;
    private final String message;

    public LogMessage(Level level, String message) {
        this.level = level;
        this.message = message;
    }

    public Level getLevel() {
        return level;
    }

    public String getMessage() {
        return message;
    }
}
