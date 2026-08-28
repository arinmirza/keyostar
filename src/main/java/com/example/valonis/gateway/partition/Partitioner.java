package com.example.valonis.gateway.partition;

public interface Partitioner {
    int getStoreIndex(String key);
}
