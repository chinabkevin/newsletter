package com.thinkdevs.api.v1.services;

import com.thinkdevs.Subscriber;
import io.micronaut.core.annotation.NonNull;

import javax.validation.constraints.NotNull;

public interface SubscriberSaveService {
    void save(@NonNull @NotNull Subscriber subscribe);
}
