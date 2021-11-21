package com.thinkdevs.api.v1;

import com.thinkdevs.Subscriber;
import com.thinkdevs.api.v1.services.SubscriberSaveService;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Status;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Controller("api/v1")
public class SubscriberSaveController {

    private final SubscriberSaveService subscriberSaveService;

    public SubscriberSaveController(SubscriberSaveService subscriberSaveService) {
        this.subscriberSaveService = subscriberSaveService;
    }


    @Post("subscriber")
    @Status(HttpStatus.CREATED)
    void save(@NonNull @NotNull @Valid Subscriber subscribe){
        subscriberSaveService.save(subscribe);

    }
}
