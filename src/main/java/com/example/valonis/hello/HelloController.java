package com.example.valonis.hello;

import com.example.valonis.observability.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private String clientName = "world";
    private final Logger logger;

    public HelloController(Logger logger) {
        this.logger = logger;
        this.logger.debug("HelloController object was created.");
    }

    @GetMapping("/hello")
    public String hello() {
        logger.info("Someone said hello.");
        return "Hello " + clientName + "!";
    }

    @PostMapping("/hello")
    public void setClientName(@RequestBody String clientName) {
        this.clientName = clientName;
        logger.info("Someone changed the client name.");
    }
}
