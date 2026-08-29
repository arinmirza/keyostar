package com.example.valonis.gateway.address;

import com.example.valonis.config.ApplicationProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
@ConditionalOnProperty(name = "valonis.gateway.addressing", havingValue = "docker")
public class DockerAddressResolver implements AddressResolver {

    private final ApplicationProperties properties;

    public DockerAddressResolver(ApplicationProperties properties) {
        this.properties = properties;
    }

    @Override
    public URI resolve(int storeIndex) {
        String host = properties.docker().storeHostTemplate().formatted(storeIndex);
        int port = properties.docker().storePort();
        return URI.create("http://%s:%d".formatted(host, port));
    }
}