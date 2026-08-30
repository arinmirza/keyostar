package com.example.keyostar.gateway.hash;

import org.springframework.stereotype.Component;

@Component("java")
public class JavaHasher implements HashFunction {

    @Override
    public int hash(String key) {
        return key.hashCode();
    }
}
