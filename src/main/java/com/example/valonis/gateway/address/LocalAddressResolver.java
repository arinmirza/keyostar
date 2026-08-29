package com.example.valonis.gateway.address;

import com.example.valonis.config.ApplicationProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
@ConditionalOnProperty(name = "valonis.gateway.addressing", havingValue = "localhost")
public class LocalAddressResolver implements AddressResolver {

    private final ApplicationProperties properties;

    public LocalAddressResolver(ApplicationProperties properties) {
        this.properties = properties;
    }

    @Override
    public URI resolve(int storeIndex) {
        int port = properties.localhost().storeBasePort() + storeIndex;
        return URI.create("http://localhost:%d".formatted(port));
    }

}
