package com.thinkdevs.api.v1.services;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.token.jwt.encryption.EncryptionConfiguration;
import io.micronaut.security.token.jwt.signature.SignatureConfiguration;
import io.micronaut.security.token.jwt.validator.ExpirationJwtClaimsValidator;
import io.micronaut.security.token.jwt.validator.GenericJwtClaimsValidator;
import io.micronaut.security.token.jwt.validator.JwtAuthenticationFactory;
import io.micronaut.security.token.jwt.validator.JwtValidator;
import io.micronaut.security.token.validator.TokenValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotBlank;
import java.text.ParseException;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class ConfirmationCodeVerifierImpl implements ConfirmationCodeVerifier{

    private static final Logger LOG = LoggerFactory.getLogger(ConfirmationCodeVerifierImpl.class);
    private final JwtValidator jwtValidator;

    public ConfirmationCodeVerifierImpl( Collection<SignatureConfiguration> signatureConfigurations,
                                         Collection<EncryptionConfiguration> encryptionConfigurations,
                                         ExpirationJwtClaimsValidator expirationJwtClaimsValidator) {
        this.jwtValidator =JwtValidator.builder()
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
}
