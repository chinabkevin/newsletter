package com.thinkdevs.api.v1;

import com.thinkdevs.api.v1.services.ConfirmationCodeVerifier;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;


import static com.thinkdevs.api.v1.Api.SUBSCRIBER_PATH;
import static com.thinkdevs.api.v1.Api.V1_PATH;

@Controller(V1_PATH)
public class SubscriberConfirmationController {

    private final ConfirmationCodeVerifier confirmationCodeVerifier;

    public SubscriberConfirmationController(ConfirmationCodeVerifier confirmationCodeVerifier) {
        this.confirmationCodeVerifier = confirmationCodeVerifier;
    }

    @ExecuteOn(TaskExecutors.IO)
    @Get(SUBSCRIBER_PATH)
    HttpStatus confirm(@QueryValue String token){
       return confirmationCodeVerifier.verify(token).isPresent() ? HttpStatus.OK : HttpStatus.UNPROCESSABLE_ENTITY;

    }

}
