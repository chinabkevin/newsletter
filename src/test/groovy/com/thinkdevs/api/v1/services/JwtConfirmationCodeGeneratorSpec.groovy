package com.thinkdevs.api.v1.services

import com.nimbusds.jwt.JWT
import com.nimbusds.jwt.JWTParser
import com.nimbusds.jwt.SignedJWT
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest(startApplication = false)
class JwtConfirmationCodeGeneratorSpec extends Specification {
    @Inject
    JwtConfirmationCodeGenerator jwtConfirmationCodeGenerator

    @Inject
    ConfirmationCodeVerifier codeVerifier;

    void "JwtConfirmationCodeGenerator generates a JWT with the user email in the claims"(){
        when:
        Optional<String> jwtString = jwtConfirmationCodeGenerator.generate("tcook@apple.com")

        then:
        jwtString.isPresent()

        JWT jwt = JWTParser.parse(jwtString.get())
        jwt instanceof SignedJWT
        "tcook@apple.com" ==((SignedJWT) jwt).getJWTClaimsSet().getClaim("email")

        then:
        codeVerifier.verify(jwtString.get())

    }
}
