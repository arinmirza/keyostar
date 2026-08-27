package com.example.kvstore.logging;

import org.springframework.stereotype.Component;

@Component
public class SpringManagedPlainLogger extends PlainLogger implements ILogger {

    public SpringManagedPlainLogger() {
        super("SpringManagedLogger");
    }
}
