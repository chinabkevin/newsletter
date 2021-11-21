package com.thinkdevs.api.v1.services;

import io.micronaut.core.annotation.NonNull;

import java.util.Optional;

@FunctionalInterface
public interface IdGenerator {

    @NonNull
    Optional<String> generate();
}
