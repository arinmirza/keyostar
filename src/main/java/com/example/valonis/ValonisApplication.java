package com.example.valonis;

import com.example.valonis.config.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties.class)
public class ValonisApplication {

    public static void main(String[] args) {
        SpringApplication.run(ValonisApplication.class, args);
    }

}
