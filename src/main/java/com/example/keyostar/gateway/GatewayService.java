package com.example.keyostar.gateway;

import com.example.keyostar.config.ApplicationProperties;
import com.example.keyostar.gateway.address.AddressResolver;
import com.example.keyostar.gateway.partition.DefaultPartitioner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.IntStream;

@Service
@ConditionalOnProperty(name = "keyostar.instance.mode", havingValue = "GATEWAY")
public class GatewayService {

    private final ApplicationProperties properties;
    private final DefaultPartitioner partitioner;
    private final AddressResolver addressResolver;
    private final RestClient restClient;

    public GatewayService(ApplicationProperties properties,
                          DefaultPartitioner partitioner,
                          AddressResolver addressResolver) {
        this.properties = properties;
        this.partitioner = partitioner;
        this.addressResolver = addressResolver;
        this.restClient = RestClient.builder().build();
    }

    public ResponseEntity<String> get(String key) {
        var builder = getStoreUriBuilder(key);
        return restClient
                .get()
                .uri(builder)
                .retrieve()
                .toEntity(String.class);
    }

    public ResponseEntity<Void> put(String key, String value) {
        var builder = getStoreUriBuilder(key);
        return restClient
                .put()
                .uri(builder)
                .body(value)
                .retrieve()
                .toBodilessEntity();
    }

    public ResponseEntity<Void> delete(String key) {
        var builder = getStoreUriBuilder(key);
        return restClient
                .delete()
                .uri(builder)
                .retrieve()
                .toBodilessEntity();
    }

    public ResponseEntity<List<Map<String, String>>> stats() {
        int storeCount = properties.gateway().storeCount();

        List<CompletableFuture<Map<String, String>>> futures =
                IntStream.range(0, storeCount)
                        .mapToObj(i -> CompletableFuture.supplyAsync(
                                () -> restClient
                                        .get()
                                        .uri(addressResolver.resolve(i) + "/stats/")
                                        .retrieve()
                                        .body(new ParameterizedTypeReference<Map<String, String>>() {})
                        ).exceptionally(exception -> Map.of())
                        ).toList();

        CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new)).join();

        List<Map<String, String>> results = futures.stream().map(CompletableFuture::join).toList();
        return ResponseEntity.ok(results);
    }

    private URI resolveStore(String key) {
        int storeIndex = partitioner.getStoreIndex(key);
        return addressResolver.resolve(storeIndex);
    }

    private Function<UriBuilder, URI> getStoreUriBuilder(String key) {
        URI storeAddress = resolveStore(key);
        return (uriBuilder) ->  uriBuilder
                .scheme(storeAddress.getScheme())
                .host(storeAddress.getHost())
                .port(storeAddress.getPort())
                .path("store/{key}")
                .build(key);
    }
}
