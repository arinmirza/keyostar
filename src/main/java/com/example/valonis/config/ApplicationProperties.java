package com.example.valonis.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "valonis")
public record ApplicationProperties(
    InstanceProperties instance,
    GatewayProperties gateway,
    LocalhostProperties localhost,
    DockerProperties docker,
    KubernetesProperties kubernetes,
    ObservabilityProperties observability) {

    public record InstanceProperties(InstanceMode mode) {}

    public record GatewayProperties(
        String addressing,
        String hashFunction,
        int storeCount
    ) {}

    public record LocalhostProperties(
            int storeBasePort
    ) {}

    public record DockerProperties(
            String storeHostTemplate,
            int storePort) {}

    public record KubernetesProperties(
            String storeStatefulSetName,
            String storeServiceName,
            int storePort
    ) {}

    public record ObservabilityProperties(
        String logger,
        String logLevel
    ) {}

    public enum InstanceMode { GATEWAY, STORE }

    public enum LogLevel {
        TRACE,
        DEBUG,
        INFO,
        WARN,
        ERROR,
    }

}

