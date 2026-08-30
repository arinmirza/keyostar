package com.example.keyostar;

import com.example.keyostar.config.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties.class)
public class KeyostarApplication {

    public static void main(String[] args) {
        SpringApplication.run(KeyostarApplication.class, args);
    }

}
