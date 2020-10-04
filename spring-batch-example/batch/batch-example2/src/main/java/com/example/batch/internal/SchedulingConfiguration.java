package com.example.batch.internal;

import static com.example.batch.internal.JobParameterProvider.PARAMETER_KEY;

import java.time.LocalDateTime;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@Configuration
class SchedulingConfiguration {

  private JobLauncher jobLauncher;

  private Job function1Job;

  private Job function2Job;

  @Autowired
  void setJobLauncher(JobLauncher jobLauncher) {
    this.jobLauncher = jobLauncher;
  }

  @Autowired
  void setFunction1Job(Job function1Job) {
    this.function1Job = function1Job;
  }

  @Autowired
  void setFunction2Job(Job function2Job) {
    this.function2Job = function2Job;
  }

  @Scheduled(cron = "0 * * ? * *")
  void job1Trigger() throws JobParametersInvalidException, JobExecutionAlreadyRunningException,
      JobRestartException, JobInstanceAlreadyCompleteException {

    var jobParameter = new JobParametersBuilder()
        .addString(PARAMETER_KEY, LocalDateTime.now().toString(), true)
        .toJobParameters();

    jobLauncher.run(function1Job, jobParameter);
  }

  @Scheduled(cron = "0 * * ? * *")
  void job2Trigger() throws JobParametersInvalidException, JobExecutionAlreadyRunningException,
      JobRestartException, JobInstanceAlreadyCompleteException {

    var jobParameter = new JobParametersBuilder()
        .addString(PARAMETER_KEY, LocalDateTime.now().toString(), true)
        .toJobParameters();

    jobLauncher.run(function2Job, jobParameter);
  }
}
