package com.thinkdevs;


import io.micronaut.core.annotation.Introspected;

@Introspected
public class Subscriber {
    private final String email;
    private final String name;

    public Subscriber(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
