package com.example.valonis.store;

import com.example.valonis.observability.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
@ConditionalOnProperty(name = "valonis.instance.mode", havingValue = "STORE")
public class KeyValueStore {

    private final Logger logger;

    public KeyValueStore(Logger logger) {
        this.logger = logger;
    }

    private final ConcurrentHashMap<String, String> data = new ConcurrentHashMap<>();

    public void put(String key, String value) {
        logger.trace("A new key was put: %s".formatted(key));
        data.put(key, value);
    }

    public Optional<String> get(String key) {
        return Optional.ofNullable(data.get(key));
    }

    public Optional<String> delete(String key) {
        logger.trace("A key was deleted: %s".formatted(key));
        return Optional.ofNullable(data.remove(key));
    }

    public Map<String, String> stats() {
        return Map.of("size", String.valueOf(data.size()));
    }

}
