package com.example.keyostar.gateway.partition;

public interface Partitioner {
    int getStoreIndex(String key);
}
