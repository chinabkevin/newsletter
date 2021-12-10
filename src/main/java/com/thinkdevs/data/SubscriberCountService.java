package com.thinkdevs.data;

import io.micronaut.core.annotation.NonNull;

public interface SubscriberCountService {
    @NonNull
    Integer countSubscribers();
}
