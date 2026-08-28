package com.example.valonis.gateway.partition;

import com.example.valonis.config.ApplicationProperties;
import com.example.valonis.gateway.hash.HashFunction;
import com.example.valonis.gateway.hash.HashFunctionRegistry;
import org.springframework.stereotype.Component;

@Component
public class DefaultPartitioner implements Partitioner {
    private final HashFunctionRegistry registry;
    private final ApplicationProperties properties;

    public DefaultPartitioner(HashFunctionRegistry registry, ApplicationProperties properties) {
        this.registry = registry;
        this.properties = properties;
    }

    public int getStoreIndex(String key) {
        HashFunction hasher = registry.get(properties.gateway().hashFunction());
        int shardCount = this.properties.store().count();
        return Math.floorMod(hasher.hash(key), shardCount);
    }
}
