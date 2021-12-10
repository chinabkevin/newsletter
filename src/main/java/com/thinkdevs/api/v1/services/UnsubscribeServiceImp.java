package com.thinkdevs.api.v1.services;

import io.micronaut.core.annotation.NonNull;
import jakarta.inject.Singleton;

import javax.validation.constraints.NotBlank;

@Singleton
public class UnsubscribeServiceImp implements UnsubscribeService{
    @Override
    public void unsubscribe(@NonNull @NotBlank String email) {

    }
}
