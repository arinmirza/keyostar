package com.example.valonis.observability;

import com.example.valonis.config.ApplicationProperties;
import com.example.valonis.config.ApplicationProperties.LogLevel;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component("console")
public class ConsoleLogger implements Logger {

    private final String loggerName;
    private final LogLevel threshold;

    public ConsoleLogger(ApplicationProperties properties) {
        this.loggerName = "ConsoleLogger";
        this.threshold = LogLevel.valueOf(properties.observability().logLevel().toUpperCase());
    }

    private void log(ApplicationProperties.LogLevel level, String message) {
        if (threshold.compareTo(level) <= 0) {
            System.out.printf("%s [%s] [%s] %s%n", java.time.Instant.now(), loggerName, level, message);
        }
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
