package com.example.batch.internal;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.stereotype.Component;

@JobScope
@Component
@Slf4j
class JobParameterProviderImpl implements JobParameterProvider {

  private LocalDateTime parameter;

  public LocalDateTime getParameter() throws IllegalStateException {
    if (parameter == null) {
      throw new IllegalStateException("required parameter");
    }
    return parameter;
  }

  @BeforeStep
  public void beforeStep(StepExecution stepExecution) {
    log.info(stepExecution.getSummary());
    var jobArg = stepExecution.getJobParameters().getString(PARAMETER_KEY);
    try {
      parameter = LocalDateTime.parse(jobArg);
      parameter = parameter.truncatedTo(ChronoUnit.MINUTES);
    } catch (Exception e) {
      log.error("illegal job parameter = jobArg");
    }
  }

  @AfterStep
  public void afterStep(StepExecution stepExecution) {
    parameter = null;
  }
}
