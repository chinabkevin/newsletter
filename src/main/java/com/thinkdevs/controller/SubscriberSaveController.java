package com.thinkdevs.controller;

import com.thinkdevs.Subscriber;
import com.thinkdevs.services.SubscriberSaveService;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Status;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static com.thinkdevs.api.v1.Api.SUBSCRIBER_PATH;
import static com.thinkdevs.api.v1.Api.V1_PATH;

@Controller(V1_PATH)
public class SubscriberSaveController {

    private final SubscriberSaveService subscriberSaveService;

    public SubscriberSaveController(SubscriberSaveService subscriberSaveService) {
        this.subscriberSaveService = subscriberSaveService;
    }

    @ExecuteOn(TaskExecutors.IO)
    @Post(SUBSCRIBER_PATH)
    @Status(HttpStatus.CREATED)
    void save(@NonNull @NotNull @Valid Subscriber subscribe){
        subscriberSaveService.save(subscribe);

    }
}
