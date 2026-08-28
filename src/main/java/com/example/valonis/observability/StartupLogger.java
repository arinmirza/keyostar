package com.example.valonis.observability;

import com.example.valonis.config.ApplicationProperties;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupLogger implements ApplicationRunner {

    private final ApplicationProperties properties;

    public StartupLogger(ApplicationProperties properties) {
        this.properties = properties;
    }

    @Override
    public void run (@NonNull ApplicationArguments args) {
        System.out.printf("Valonis instance started in %s mode.%n", properties.instance().mode());
    }
}
