package com.example.batch.config;

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
class ScheduleConfiguration {

  private JobLauncher jobLauncher;

  private Job job;

  @Autowired
  void setJobLauncher(JobLauncher jobLauncher) {
    this.jobLauncher = jobLauncher;
  }

  @Autowired
  void setJob(Job job) {
    this.job = job;
  }

  @Scheduled(cron = "0 * * ? * *")
  void trigger() throws JobParametersInvalidException, JobExecutionAlreadyRunningException,
      JobRestartException, JobInstanceAlreadyCompleteException {

    var jobParameter = new JobParametersBuilder()
        .addString("run.id", LocalDateTime.now().toString(), true)
        .toJobParameters();

    jobLauncher.run(job, jobParameter);
  }
}
