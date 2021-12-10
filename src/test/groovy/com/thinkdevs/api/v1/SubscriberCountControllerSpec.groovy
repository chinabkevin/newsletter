package com.thinkdevs.api.v1


import com.thinkdevs.data.SubscriberDataRepository
import com.thinkdevs.data.SubscriberEntity
import com.thinkdevs.model.SubscriptionStatus
import com.thinkdevs.services.IdGenerator
import io.micronaut.http.HttpRequest
import io.micronaut.http.MediaType
import io.micronaut.http.client.BlockingHttpClient
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest(transactional = false)
class SubscriberCountControllerSpec extends Specification {

    @Inject
    @Client
    HttpClient httpClient

    @Inject
    SubscriberDataRepository dataRepository

    @Inject
    IdGenerator idGenerator

    void "Get /api/v1/subscriber/count returns the number of confirmed subscribers"() {
        given:
        BlockingHttpClient client = httpClient.toBlocking()

        when:
        HttpRequest<?> request = HttpRequest.GET('/api/v1/subscriber/count')
                .accept(MediaType.TEXT_PLAIN)
        Integer result = client.retrieve(request, Integer)

        then:
        noExceptionThrown()
        0 == result

        when:
        String id = idGenerator.generate().get()
        dataRepository.save(new SubscriberEntity(id,
                "tcook@apple.com",
                "Tim cook",
                SubscriptionStatus.ACTIVE))
        result = client.retrieve(request, Integer)

        then:
        noExceptionThrown()
        1 == result


        when:
        String federighId = idGenerator.generate().get()
        dataRepository.save(new SubscriberEntity(federighId,
                "cfederigh@apple.com",
                "Craig federighId",
                SubscriptionStatus.CANCELED))
        result = client.retrieve(request, Integer)

        then:
        noExceptionThrown()
        1 == result


        cleanup:
        dataRepository.deleteById(id)
        dataRepository.deleteById(federighId)

    }

}
