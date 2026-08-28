package com.example.valonis.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "valonis")
public record ApplicationProperties(
    InstanceProperties instance,
    GatewayProperties gateway,
    StoreProperties store) {

    public record InstanceProperties(InstanceMode mode) {}

    public record GatewayProperties(
        String baseUrl,
        int basePort,
        int count,
        String hashFunction,
        String addressResolver
    ) {}

    public record StoreProperties(
        String baseUrl,
        int basePort,
        int count
    ) {}

    public enum InstanceMode { GATEWAY, SHARD }

}

