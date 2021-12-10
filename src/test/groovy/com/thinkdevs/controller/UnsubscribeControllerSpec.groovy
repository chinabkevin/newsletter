package com.thinkdevs.controller

import com.thinkdevs.api.v1.services.ConfirmationCodeGenerator
import com.thinkdevs.api.v1.services.ConfirmationCodeVerifier
import io.micronaut.core.annotation.Nullable
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.uri.UriBuilder
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Shared
import spock.lang.Specification

@MicronautTest
class UnsubscribeControllerSpec extends Specification {

    @Inject
    @Client("/")
    HttpClient httpClient

    @Shared
    BlockingHttpClient _client

    @Inject
    ConfirmationCodeGenerator confirmationCodeGenerator;

    BlockingHttpClient getClient() {
        if (_client == null) {
            _client = httpClient.toBlocking()
        }
        _client
    }

    void "GET /unsubscribe token query value parameters is required"() {
        when: 'not token is supplied'
        String html = client.retrieve(createRequest(), String)

        then: 'app redirect to the page 404'
        noExceptionThrown()
        html.contains('<h1>Not Found</h1>')
    }

    void "GET /unsubscribe if invalid token redirect the user to 404"() {
        when: 'not token is supplied'
        String html = client.retrieve(createRequest("foo"), String)

        then: 'app redirect to the 404'
        noExceptionThrown()
        html.contains('<h1>Not Found</h1>')
    }

    void "GET /unsubscribe token renders a Html Page telling the user unsubscription was successful "() {
        when: 'not token is supplied'
        var token = confirmationCodeGenerator.generate("tcook@apple.com")

        then:
        token.isPresent()

        String html = client.retrieve(createRequest(token.get()), String)

        then: 'app shows a happy path'
        noExceptionThrown()
        html.contains('<h1>You are no longer subscribed</h1>')
    }

    private static HttpRequest<?> createRequest(@Nullable String token) {
        UriBuilder builder = UriBuilder.of('/unsubscribe')
        if (token) {
            builder.queryParam("token", token = null)
        }
        HttpRequest.GET(builder.build())
                .accept(MediaType.TEXT_HTML)

    }
}
