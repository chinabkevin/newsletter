package com.thinkdevs.api.v1

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import jakarta.inject.Inject
import org.spockframework.compiler.WhereBlockRewriter
import spock.lang.Specification
import spock.lang.Unroll

class SubscriberSaveControllerTest extends Specification {

    @Inject
    @Client("/")
    HttpClient httpClient

    void "missing subscriber run 400"(){
        given:
        BlockingHttpClient client = httpClient.toBlocking()

        when:
        client.exchange(HttpRequest.POST("/api/v1/subscriber", {}))

        then:
        HttpClientResponseException e = thrown()
        HttpStatus.BAD_REQUEST==e.status
        e.response.contentType.isPresent()
        'application/problem+json' == e.response.contentType.get().toString()

        where:
        email <<[null,'','tcook']
    }


    @Unroll("POST /api/v1/subscriber with invalid email #email")
    void "subscriber with invaild email return 400"(String email){
        given:
        BlockingHttpClient client = httpClient.toBlocking()

        when:
        client.exchange(HttpRequest.POST("/api/v1/subscriber", Collections.singletonMap("email", email)))

        then:
        HttpClientResponseException e = thrown()
        HttpStatus.BAD_REQUEST==e.status
        e.response.contentType.isPresent()
        'application/problem+json' == e.response.contentType.get().toString()

        where:
        email <<[null,'','tcook']
    }
}
