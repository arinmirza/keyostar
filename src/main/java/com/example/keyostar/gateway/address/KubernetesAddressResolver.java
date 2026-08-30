package com.example.keyostar.gateway.address;

import com.example.keyostar.config.ApplicationProperties;
import com.example.keyostar.observability.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
@ConditionalOnProperty(name = "keyostar.gateway.addressing", havingValue = "kubernetes")
public class KubernetesAddressResolver implements AddressResolver {

    private final ApplicationProperties properties;
    private final Logger logger;

    public KubernetesAddressResolver(ApplicationProperties properties, Logger logger) {
        this.properties = properties;
        this.logger = logger;
    }

    @Override
    public URI resolve(int storeIndex) {
        URI uri = URI.create(
                "http://%s-%d.%s:%d".formatted(
                        properties.kubernetes().storeStatefulSetName(),
                        storeIndex,
                        properties.kubernetes().storeServiceName(),
                        properties.kubernetes().storePort()
                )
        );
        logger.trace("Resolved store with idx=%d to address %s".formatted(storeIndex, uri));
        return uri;
    }
}
