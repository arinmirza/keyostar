package com.example.kvstore.logging;

public interface ILogger {
    void trace(String message);
    void debug(String message);
    void info(String message);
    void warn(String message);
    void error(String message);
}
