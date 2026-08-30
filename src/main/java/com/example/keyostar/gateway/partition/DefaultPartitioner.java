package com.example.keyostar.gateway.partition;

import com.example.keyostar.config.ApplicationProperties;
import com.example.keyostar.gateway.hash.HashFunction;
import com.example.keyostar.gateway.hash.HashFunctionRegistry;
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
