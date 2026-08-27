package com.example.kvstore.logging;

public class PlainLogger implements ILogger {

    private final String loggerName;

    public enum LogLevel {
        TRACE,
        DEBUG,
        INFO,
        WARN,
        ERROR,
    }

    public PlainLogger() {
        this.loggerName = "PlainLogger";
    }

    public PlainLogger(String loggerName) {
        this.loggerName = loggerName;
    }

    private void log(LogLevel level, String message) {
        System.out.printf("%s [%s] [%s] %s%n", java.time.Instant.now(), loggerName, level, message);
    }

    public void trace(String message) {
        this.log(LogLevel.TRACE, message);
    }

    public void debug(String message) {
        this.log(LogLevel.DEBUG, message);
    }

    public void info(String message) {
        this.log(LogLevel.INFO, message);
    }

    public void warn(String message) {
        this.log(LogLevel.WARN, message);
    }

    public void error(String message) {
        this.log(LogLevel.ERROR, message);
    }

}
