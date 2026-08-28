package com.example.valonis.gateway;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.example.valonis.config.StoreConstraints.MAX_KEY_LENGTH;
import static com.example.valonis.config.StoreConstraints.MAX_VALUE_LENGTH;

@RestController
@RequestMapping("/gateway")
@ConditionalOnProperty(name = "valonis.instance.mode", havingValue = "GATEWAY")
@Validated
public class GatewayController {

    private final GatewayService service;

    public GatewayController(GatewayService service) {
        this.service = service;
    }

    @PutMapping("/{key}")
    public ResponseEntity<Void> put(
            @PathVariable @NotBlank @Size(max=MAX_KEY_LENGTH, message="{store.key.too-long}") String key,
            @RequestBody @NotNull @Size(max=MAX_VALUE_LENGTH, message="{store.value.too-long}") String value) {
        return service.put(key, value);
    }

    @GetMapping("/{key}")
    public ResponseEntity<String> get(
            @PathVariable @NotBlank @Size(max=MAX_KEY_LENGTH, message="{store.key.too-long}") String key) {
        return service.get(key);
    }

    @DeleteMapping("/{key}")
    public ResponseEntity<Void> delete(
            @PathVariable @NotBlank @Size(max=MAX_KEY_LENGTH, message="{store.key.too-long}") String key) {
        return service.delete(key);
    }

}