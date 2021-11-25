package com.thinkdevs.api.v1.services;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.security.token.generator.TokenGenerator;
import io.micronaut.security.token.jwt.generator.claims.JwtClaims;
import jakarta.inject.Singleton;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Singleton
public class JwtConfirmationCodeGenerator implements ConfirmationCodeGenerator{

    public static final String EMAIL_CLAIM = "email";
    private final TokenGenerator tokenGenerator;

    public JwtConfirmationCodeGenerator(TokenGenerator tokenGenerator) {
        this.tokenGenerator = tokenGenerator;
    }


    @Override
    @NonNull
    public Optional<String> generate(@NonNull String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaims.EXPIRATION_TIME, Date.from(Instant.now().plus(3600, ChronoUnit.SECONDS))); //move 3600 in teh configurate
        claims.put(EMAIL_CLAIM, email);
        return tokenGenerator.generateToken(claims);
    }
}
