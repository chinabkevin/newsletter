package com.thinkdevs.api.v1.services

import com.thinkdevs.services.ConfirmationCodeGenerator
import io.micronaut.context.BeanContext
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest(startApplication = false)
class ConfirmationCodeGeneratorSpect extends Specification {

    @Inject
    BeanContext beanContext

    void "bean of type ConfirmationCodeGenerator exists"(){
        expect:
        beanContext.containsBean(ConfirmationCodeGenerator)

    }
}
