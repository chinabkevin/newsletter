package com.thinkdevs.services;

import io.micronaut.core.annotation.NonNull;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

public interface ConfirmationCodeVerifier {
    /**
     *
     * @param token
     * @return the email address associated with the thoken ior empty could not be validated
     */
    @NonNull
    Optional<String> verify(@NonNull @NotBlank String token);
}
