package com.example.keyostar.observability;

import com.example.keyostar.config.ApplicationProperties;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupLogger implements ApplicationRunner {

    private final ApplicationProperties properties;
    private final Logger logger;

    public StartupLogger(ApplicationProperties properties, Logger logger) {
        this.properties = properties;
        this.logger = logger;
    }

    @Override
    public void run(@NonNull ApplicationArguments args) {
        logger.info("Keyostar instance started in [%s] mode.".formatted(properties.instance().mode()));
        logger.info("For sanity check, this executable is compiled from the source code with magic string [5].");
        logger.info("The applications properties are configured as follows: %s".formatted(properties.toString()));
    }
}
