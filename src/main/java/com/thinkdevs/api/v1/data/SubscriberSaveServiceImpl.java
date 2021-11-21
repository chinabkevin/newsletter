package com.thinkdevs.api.v1.data;

import com.thinkdevs.Subscriber;
import com.thinkdevs.api.v1.services.IdGenerator;
import com.thinkdevs.api.v1.services.SubscriberSaveService;
import io.micronaut.core.annotation.NonNull;
import jakarta.inject.Singleton;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Singleton
public class SubscriberSaveServiceImpl implements SubscriberSaveService {
    private final IdGenerator idGenerator;
    private final SubscriberDataRepository subscriberDataRepository;

    public SubscriberSaveServiceImpl(IdGenerator idGenerator, SubscriberDataRepository subscriberDataRepository) {
        this.idGenerator = idGenerator;
        this.subscriberDataRepository = subscriberDataRepository;
    }

    @Override
    @NonNull
    public Optional<String> save(@NonNull @NotNull @Valid Subscriber subscribe) {
       return idGenerator.generate().map(id->{
            SubscriberEntity entity = new SubscriberEntity(id, subscribe.getEmail(), subscribe.getName());
            subscriberDataRepository.save(entity);
            return id;
        });

    }
}
