package com.example.valonis.gateway.partition;

import com.example.valonis.config.ApplicationProperties;
import com.example.valonis.gateway.hash.HashFunction;
import com.example.valonis.gateway.hash.HashFunctionRegistry;
import org.springframework.stereotype.Component;

@Component
public class DefaultPartitioner implements Partitioner {
    private final ApplicationProperties properties;
    private final HashFunction hasher;

    public DefaultPartitioner(ApplicationProperties properties, HashFunctionRegistry registry) {
        this.hasher = registry.get(properties.gateway().hashFunction());
        this.properties = properties;
    }

    public int getStoreIndex(String key) {
        int storeCount = this.properties.gateway().storeCount();
        return Math.floorMod(hasher.hash(key), storeCount);
    }
}
