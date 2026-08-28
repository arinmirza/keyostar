package com.example.valonis.gateway.hash;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class HashFunctionRegistry {
    // Note: Spring will automatically inject all beans implementing HashFunction interface
    private final Map<String, HashFunction> hashFunctions;

    public HashFunctionRegistry(Map<String, HashFunction> hashFunctions) {
        this.hashFunctions = hashFunctions;
    }

    public HashFunction get(String fnName) {
        HashFunction fn = hashFunctions.get(fnName);

        if (fn == null) {
            throw new IllegalArgumentException("Unknown hash function: " + fnName);
        }

        return fn;
    }
}
