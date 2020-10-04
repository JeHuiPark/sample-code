package com.example.batch.internal;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import com.example.api.SomethingAPI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

interface ActivateStepRunner extends ActiveJobContext {

  String lookupKey();

  void run(ScheduleDocument scheduleDocument);

  @Slf4j
  @RequiredArgsConstructor
  abstract class AbstractRunner implements ActivateStepRunner, JobStepManager {

    private static final String MARK_FIELD = "mark";
    private static final String MARK_VALUE = "active-mark";

    protected final SomethingAPI somethingAPI;
    private final MongoTemplate mongoTemplate;

    @Override
    public void run(ScheduleDocument scheduleDocument) {
      exec(scheduleDocument.getTarget());
      mark(scheduleDocument);
    }

    abstract protected void exec(long targetId);

    private void mark(ScheduleDocument scheduleDocument) {
      mongoTemplate.updateFirst(
          query(where("_id").is(scheduleDocument.getMongoId())),
          new Update().set(MARK_FIELD, MARK_VALUE),
          ScheduleDocument.class);
    }

    @Override
    public void release() {
      var result = mongoTemplate.updateMulti(
          query(where(MARK_FIELD).is(MARK_VALUE).and("method").is(lookupKey())),
          new Update()
              .unset(MARK_FIELD)
              .set("status","ing"),
          ScheduleDocument.class
      );
      log.info("release summary = {} / {}", result.getModifiedCount(), result.getMatchedCount());
    }
  }

  @Slf4j
  @Component("activeJob1Manager")
  class Function1Runner extends AbstractRunner {

    public Function1Runner(SomethingAPI somethingAPI, MongoTemplate mongoTemplate) {
      super(somethingAPI, mongoTemplate);
    }

    @Override
    public String lookupKey() {
      return "function1";
    }

    @Override
    protected void exec(long targetId) {
      somethingAPI.function1(targetId);
    }
  }

  @Slf4j
  @Component("activeJob2Manager")
  class Function2Runner extends AbstractRunner {

    public Function2Runner(SomethingAPI somethingAPI, MongoTemplate mongoTemplate) {
      super(somethingAPI, mongoTemplate);
    }

    @Override
    public String lookupKey() {
      return "function2";
    }

    @Override
    protected void exec(long targetId) {
      somethingAPI.function2(targetId);
    }
  }
}
