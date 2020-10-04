package com.example.batch.internal;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

@Slf4j
@Configuration
class JobFactory {

  private JobBuilderFactory jobBuilderFactory;

  private StepBuilderFactory stepBuilderFactory;

  private JobParameterProvider jobParameterProvider;

  private ActivateStep activateStep;

  @Autowired
  void setJobBuilderFactory(JobBuilderFactory jobBuilderFactory) {
    this.jobBuilderFactory = jobBuilderFactory;
  }

  @Autowired
  void setStepBuilderFactory(StepBuilderFactory stepBuilderFactory) {
    this.stepBuilderFactory = stepBuilderFactory;
  }

  @Autowired
  void setJobParameterProvider(JobParameterProvider jobParameterProvider) {
    this.jobParameterProvider = jobParameterProvider;
  }

  @Autowired
  void setActivateStep(ActivateStep activateStep) {
    this.activateStep = activateStep;
  }

  @JobScope
  @Bean("function1Reader")
  MongoItemReader<ScheduleDocument> function1Reader(MongoTemplate mongoTemplate) {
    var query = Query.query(
        Criteria
            .where("begin").lte(jobParameterProvider.getParameter())
            .and("end").gt(jobParameterProvider.getParameter())
            .and("status").is("reservation")
            .and("method").is("function1"));
    return new MongoItemReaderBuilder<ScheduleDocument>()
        .template(mongoTemplate)
        .query(query)
        .pageSize(1000)
        .targetType(ScheduleDocument.class)
        .saveState(false)
        .sorts(Map.of())
        .build();
  }

  @Bean
  Job function1Job(@Qualifier("activeJob1Manager") JobStepManager jobStepManager) {
    return jobBuilderFactory.get("function1-job")
        .start(function1JobStep(null))
        .next(activeStepFinalize(jobStepManager))
        .build();
  }

  private Step activeStepFinalize(JobStepManager jobStepManager) {
    return stepBuilderFactory.get("finalize-step")
        .tasklet(FinalizeStepTask.create(jobStepManager))
        .build();
  }

  @Bean("function1-job-step")
  Step function1JobStep(@Qualifier("function1Reader") ItemReader<ScheduleDocument> function1Reader) {
    return stepBuilderFactory.get("function1-job-step")
        .<ScheduleDocument, ScheduleDocument>chunk(1000)
        .reader(function1Reader)
        .writer(activateStep)
        .listener(jobParameterProvider)
        .build();
  }

  @JobScope
  @Bean("function2Reader")
  MongoItemReader<ScheduleDocument> function2Reader(MongoTemplate mongoTemplate) {
    var query = Query.query(
        Criteria
            .where("begin").lte(jobParameterProvider.getParameter())
            .and("end").gt(jobParameterProvider.getParameter())
            .and("status").is("reservation")
            .and("method").is("function2"));

    return new MongoItemReaderBuilder<ScheduleDocument>()
        .template(mongoTemplate)
        .query(query)
        .pageSize(1000)
        .targetType(ScheduleDocument.class)
        .saveState(false)
        .sorts(Map.of())
        .build();
  }

  @Bean
  Job function2Job(@Qualifier("activeJob2Manager") JobStepManager jobStepManager) {
    return jobBuilderFactory.get("function2-job")
        .start(function2JobStep(null))
        .next(activeStepFinalize(jobStepManager))
        .build();
  }

  @Bean("function2-job-step")
  Step function2JobStep(@Qualifier("function2Reader") ItemReader<ScheduleDocument> function2Reader) {
    return stepBuilderFactory
        .get("function2-job-step")
        .<ScheduleDocument, ScheduleDocument>chunk(1000)
        .reader(function2Reader)
        .writer(activateStep)
        .listener(jobParameterProvider)
        .build();
  }
}
