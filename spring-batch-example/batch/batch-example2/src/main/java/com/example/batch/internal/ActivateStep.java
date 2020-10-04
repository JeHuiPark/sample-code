package com.example.batch.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@JobScope
@Component
public class ActivateStep implements ItemWriter<ScheduleDocument> {

  private final MongoTemplate mongoTemplate;
  private final Iterable<ActivateStepRunner> activateSteps;

  ActivateStep(MongoTemplate mongoTemplate, Collection<ActivateStepRunner> activateSteps) {
    this.mongoTemplate = mongoTemplate;
    this.activateSteps = new ArrayList<>(activateSteps);
  }

  @Override
  public void write(List<? extends ScheduleDocument> items) {
    var entry = items.get(0);
    if (entry != null) {
      log.info("entry object = {}", entry);
    }
    for (ScheduleDocument each : items) {
      var activateStep = lookup(each.getMethod());
      if (activateStep == null) {
        log.warn("lookup fail, try key is {}", each.getMethod());
        continue;
      }
      activateStep.run(each);
    }
  }

  private ActivateStepRunner lookup(String key) {
    for (ActivateStepRunner activateStep : activateSteps) {
      if (key.toUpperCase().equals(activateStep.lookupKey().toUpperCase())) {
        return activateStep;
      }
    }
    return null;
  }
}
