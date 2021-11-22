package com.thinkdevs.api.v1.data;

import io.micronaut.core.annotation.NonNull;

public interface SubscriberCountService {
    @NonNull
    Integer countConfirmedSubscribers();
}
