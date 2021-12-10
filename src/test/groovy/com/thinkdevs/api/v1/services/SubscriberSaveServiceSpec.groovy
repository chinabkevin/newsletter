package com.thinkdevs.api.v1.services

import com.thinkdevs.Subscriber
import com.thinkdevs.services.SubscriberSaveService
import io.micronaut.context.annotation.Replaces
import io.micronaut.context.annotation.Requires
import io.micronaut.core.annotation.NonNull
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification
import spock.lang.Unroll

import javax.validation.ConstraintViolationException
import javax.validation.Valid
import javax.validation.constraints.NotNull

@MicronautTest(startApplication = false)
class SubscriberSaveServiceSpec extends Specification {

    @Inject
    SubscriberSaveService subscriberSaveService

    void "SubscriberSaveService#save cannot be null"(){
        when :
        subscriberSaveService.save()

        then:
        thrown(ConstraintViolationException)
    }


    @Unroll("#email is not a valid email for  subcribe with a valid email address SubscriberSaveService::save")
    void "SubscriberSaveService::save parameter must contain subcribe with a valid email"(String email){
        when :
        Subscriber subscriber = new Subscriber(email, null)
        subscriberSaveService.save(subscriber)

        then:
        thrown(ConstraintViolationException)

        where:
        email << ['',null,'tcook']
    }

    @Requires(property = "spec.name", value = "SubscriberSaveServiceSpec")
    @Replaces(SubscriberSaveService)
    @Singleton
    static class SubscriberSaveServiceReplacement implements SubscriberSaveService {

        @Override
        @NonNull
        Optional<String> save(@NonNull @NotNull @Valid Subscriber subscriber) {
            Optional.empty()
        }
    }
}
