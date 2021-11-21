package com.thinkdevs.api.v1

import com.thinkdevs.Subscriber
import com.thinkdevs.api.v1.services.SubscriberSaveService
import io.micronaut.context.annotation.Property
import io.micronaut.context.annotation.Replaces
import io.micronaut.context.annotation.Requires
import io.micronaut.core.annotation.NonNull
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import jakarta.inject.Inject
import spock.lang.Specification
import spock.lang.Unroll

import javax.validation.constraints.NotNull

@Property(name ="spec.name", value = "SubscriberSaveServiceSpec")
class SubscriberSaveControllerSpec extends Specification {

    @Inject
    @Client("/")
    HttpClient httpClient

    @Inject
    SubscriberSaveService subscriberSaveService

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


    void "for happy path a POST request to /api/v1/subscriber delegates to SubscriberSaveService"(){
        given:
        BlockingHttpClient client = httpClient.toBlocking()

        when:
        client.exchange(HttpRequest.POST("/api/v1/subscriber", Collections.singletonMap("email", 'tcook@apple.com')))

        then:
        noExceptionThrown()
        subscriberSaveService instanceof  SubscriberSaveServiceReplacement

       1== ((SubscriberSaveServiceReplacement) subscriberSaveService).invocations


    }


    @Requires(property ="spec.name", value = "SubscriberSaveServiceSpec")
    @Replaces(SubscriberSaveService)
    @Singleton
    static class SubscriberSaveServiceReplacement implements SubscriberSaveService{

        int invocations
        @Override
        void save(@NonNull @NotNull Subscriber subscribe) {

        }
    }
}
