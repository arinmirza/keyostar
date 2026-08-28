package com.example.valonis.gateway.address;

import com.example.valonis.config.ApplicationProperties;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component("sequential-ports")
public class SequentialPortAddressResolver implements AddressResolver {

    private final ApplicationProperties properties;

    public SequentialPortAddressResolver(ApplicationProperties properties) {
        this.properties = properties;
    }

    @Override
    public URI resolve(int storeIndex) {
        String url = properties.store().baseUrl();
        int port = properties.store().basePort() + storeIndex;
        return URI.create("%s:%d".formatted(url, port));
    }

}
