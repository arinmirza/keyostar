package com.example.kvstore.store;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class KeyValueStore {

    private final ConcurrentHashMap<String, String> data = new ConcurrentHashMap<>();

    public void put(String key, String value) {
        data.put(key, value);
    }

    public Optional<String> get(String key) {
        return Optional.ofNullable(data.get(key));
    }

    public Optional<String> delete(String key) {
        return Optional.ofNullable(data.remove(key));
    }

}
