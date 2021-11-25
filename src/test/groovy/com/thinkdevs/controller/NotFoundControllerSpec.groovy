package com.thinkdevs.controller

import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification


@MicronautTest
class NotFoundControllerSpec extends Specification {
    @Inject
    @Client("/")
    HttpClient httpClient


    void "Get /404 renders an Html page with Not Found title"(){
        given:
        BlockingHttpClient client = httpClient.toBlocking()
        HttpRequest<?> request =HttpRequest.GET('/notfound').accept(MediaType.TEXT_HTML)

        when:
        HttpResponse<String> response = client.exchange(request, String)

        then:
        noExceptionThrown()
        HttpStatus.OK ==response.status()
        response.getBody().isPresent()
        response.getBody().get().contains('<h1>Not Found</h1>')
    }
}
