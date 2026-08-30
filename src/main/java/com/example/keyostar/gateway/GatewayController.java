package com.example.keyostar.gateway;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.example.keyostar.config.StoreConstraints.MAX_KEY_LENGTH;
import static com.example.keyostar.config.StoreConstraints.MAX_VALUE_LENGTH;

@RestController
@RequestMapping("/gateway")
@ConditionalOnProperty(name = "keyostar.instance.mode", havingValue = "GATEWAY")
@Validated
public class GatewayController {

    private final GatewayService service;

    public GatewayController(GatewayService service) {
        this.service = service;
    }

    @PutMapping("/key/{key}")
    public ResponseEntity<Void> put(
            @PathVariable @NotBlank @Size(max=MAX_KEY_LENGTH, message="{store.key.too-long}") String key,
            @RequestBody @NotNull @Size(max=MAX_VALUE_LENGTH, message="{store.value.too-long}") String value) {
        return service.put(key, value);
    }

    @GetMapping("/key/{key}")
    public ResponseEntity<String> get(
            @PathVariable @NotBlank @Size(max=MAX_KEY_LENGTH, message="{store.key.too-long}") String key) {
        return service.get(key);
    }

    @DeleteMapping("/key/{key}")
    public ResponseEntity<Void> delete(
            @PathVariable @NotBlank @Size(max=MAX_KEY_LENGTH, message="{store.key.too-long}") String key) {
        return service.delete(key);
    }

    @GetMapping("/stats")
    public ResponseEntity<List<Map<String, String>>> stats() {
        return service.stats();
    }

}