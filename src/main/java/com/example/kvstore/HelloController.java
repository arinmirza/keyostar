package com.example.kvstore;

import com.example.kvstore.logging.PlainLogger;
import com.example.kvstore.logging.SpringManagedPlainLogger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private String clientName = "world";
    private final PlainLogger plainLogger = new PlainLogger();
    private final SpringManagedPlainLogger managedLogger;

    public HelloController(SpringManagedPlainLogger injectedLogger) {
        this.managedLogger = injectedLogger;
        this.plainLogger.debug("HelloController object was created.");
        this.managedLogger.debug("HelloController object was created.");
    }

    @GetMapping("/hello")
    public String hello() {
        plainLogger.info("Someone said hello.");
        managedLogger.info("Someone said hello.");
        return "Hello " + clientName + "!";
    }

    @PostMapping("/hello")
    public void setClientName(@RequestBody String clientName) {
        this.clientName = clientName;
        plainLogger.info("Someone changed the client name.");
        managedLogger.info("Someone changed the client name.");
    }
}
