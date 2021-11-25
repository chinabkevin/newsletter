package com.thinkdevs.api.v1.services;

import com.nimbusds.jwt.JWT;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.security.token.generator.TokenGenerator;
import io.micronaut.security.token.jwt.encryption.EncryptionConfiguration;
import io.micronaut.security.token.jwt.generator.claims.JwtClaims;
import io.micronaut.security.token.jwt.signature.SignatureConfiguration;
import io.micronaut.security.token.jwt.validator.ExpirationJwtClaimsValidator;
import io.micronaut.security.token.jwt.validator.JwtValidator;
import jakarta.inject.Singleton;

import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Singleton
public class JwtConfirmationCodeGenerator implements ConfirmationCodeGenerator, ConfirmationCodeVerifier {

    public static final String EMAIL_CLAIM = "email";
    private final TokenGenerator tokenGenerator;
    private final JwtValidator jwtValidator;

    public JwtConfirmationCodeGenerator(TokenGenerator tokenGenerator,
                                        Collection<SignatureConfiguration> signatureConfigurations,
                                        Collection<EncryptionConfiguration> encryptionConfigurations,
                                        ExpirationJwtClaimsValidator expirationJwtClaimsValidator) {
        this.tokenGenerator = tokenGenerator;
        this.jwtValidator = JwtValidator.builder()
                .withSignatures(signatureConfigurations)
                .withEncryptions(encryptionConfigurations)
                .withClaimValidators(Collections.singleton(expirationJwtClaimsValidator))
                .build();

    }

    @Override
    public boolean verify(@NonNull @NotBlank String token) {
        Optional<JWT> validate = jwtValidator.validate(token, null);
        return validate.isPresent();
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
