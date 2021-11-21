package com.thinkdevs.api.v1.services;

import com.thinkdevs.Subscriber;
import io.micronaut.core.annotation.NonNull;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface SubscriberSaveService {
    @NonNull
    Optional<String> save(@NonNull @NotNull @Valid Subscriber subscribe);
}
