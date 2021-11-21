package com.thinkdevs.api.v1.data;

import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

@JdbcRepository(dialect = Dialect.H2)
public interface SubscriberDataRepository extends CrudRepository<SubscriberEntity, String> {
}
