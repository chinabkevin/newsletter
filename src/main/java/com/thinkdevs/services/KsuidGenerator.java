package com.thinkdevs.services;

import com.amirkhawaja.Ksuid;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

@Singleton
public class KsuidGenerator implements IdGenerator{

    private static final Logger LOG = LoggerFactory.getLogger(KsuidGenerator.class);
    @Override
    public Optional<String> generate() {

        try {
            return Optional.of(new Ksuid().generate());
        } catch (IOException e) {
            if (LOG.isErrorEnabled()){
                LOG.info("IO exception");
            }
        }
        return Optional.empty();
    }
}
