package com.example.keyostar.store;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

import static com.example.keyostar.config.StoreConstraints.MAX_KEY_LENGTH;
import static com.example.keyostar.config.StoreConstraints.MAX_VALUE_LENGTH;

@RestController
@RequestMapping("/store")
@ConditionalOnProperty(name = "keyostar.instance.mode", havingValue = "STORE")
@Validated
public class KeyValueController {

    private final KeyValueStore store;

    public KeyValueController(KeyValueStore store) {
        this.store = store;
    }

    @PutMapping("/key/{key}")
    public ResponseEntity<Void> put(
            @PathVariable @NotBlank @Size(max=MAX_KEY_LENGTH, message="{store.key.too-long}") String key,
            @RequestBody @NotNull @Size(max=MAX_VALUE_LENGTH, message="{store.value.too-long}") String value) {
        store.put(key, value);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/key/{key}")
    public ResponseEntity<String> get(
            @PathVariable @NotBlank @Size(max=MAX_KEY_LENGTH, message="{store.key.too-long}") String key) {
        final Optional<String> value = store.get(key);
        return value.isPresent()
                ? ResponseEntity.of(value)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/key/{key}")
    public ResponseEntity<Void> delete(
            @PathVariable @NotBlank @Size(max=MAX_KEY_LENGTH, message="{store.key.too-long}") String key) {
        final Optional<String> removed = store.delete(key);
        return removed.isPresent()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, String>> stats() {
        return ResponseEntity.of(Optional.ofNullable(store.stats()));
    }


}
