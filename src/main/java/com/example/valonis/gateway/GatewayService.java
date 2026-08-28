package com.example.valonis.gateway;

import com.example.valonis.gateway.address.AddressResolver;
import com.example.valonis.gateway.partition.DefaultPartitioner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.function.Function;

@Service
@ConditionalOnProperty(name = "valonis.instance.mode", havingValue = "GATEWAY")
public class GatewayService {

    private final DefaultPartitioner partitioner;
    private final AddressResolver addressResolver;
    private final RestClient restClient;

    public GatewayService(DefaultPartitioner partitioner, AddressResolver addressResolver) {
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
