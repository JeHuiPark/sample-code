package com.example.demo.config;

import com.example.demo.user.Models;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.requery.Persistable;
import io.requery.cache.WeakEntityCache;
import io.requery.jackson.EntityMapper;
import io.requery.sql.ConfigurationBuilder;
import io.requery.sql.EntityDataStore;
import io.requery.sql.SchemaModifier;
import io.requery.sql.TableCreationMode;
import java.util.concurrent.Executors;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RequeryConfig {

  @Bean
  public ObjectMapper objectMapper(@Autowired EntityDataStore<?> entityDataStore) {
    return new EntityMapper(Models.DEFAULT, entityDataStore);
  }

  @Bean
  public EntityDataStore<Persistable> provideDataStore(@Autowired DataSource dataSource) {
    io.requery.sql.Configuration configuration = new ConfigurationBuilder(dataSource, Models.DEFAULT)
        .useDefaultLogging()
        .setEntityCache(new WeakEntityCache())
        .setWriteExecutor(Executors.newWorkStealingPool(16))
        .build();

    SchemaModifier tables = new SchemaModifier(configuration);
    tables.createTables(TableCreationMode.CREATE_NOT_EXISTS);
    return new EntityDataStore<>(configuration);
  }
}
