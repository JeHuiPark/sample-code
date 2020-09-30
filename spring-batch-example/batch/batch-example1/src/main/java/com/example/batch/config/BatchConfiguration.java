package com.example.batch.config;

import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

  private JobBuilderFactory jobBuilderFactory;

  private StepBuilderFactory stepBuilderFactory;

  @Autowired
  void setJobBuilderFactory(JobBuilderFactory jobBuilderFactory) {
    this.jobBuilderFactory = jobBuilderFactory;
  }

  @Autowired
  void setStepBuilderFactory(StepBuilderFactory stepBuilderFactory) {
    this.stepBuilderFactory = stepBuilderFactory;
  }

  @Bean
  public Job job(ItemReaderImpl itemReader) {
    return this.jobBuilderFactory.get("uuid-generate-job")
        .start(step(null))
        .listener(new JobExecutionListener() {

          @Override
          public void beforeJob(JobExecution jobExecution) {
            itemReader.cleanup();
          }

          @Override
          public void afterJob(JobExecution jobExecution) {
          }
        })
        .build();
  }

  @Bean
  public Step step(ItemReader<String> itemReader) {
    return this.stepBuilderFactory
        .get("uuid-generate-job-step")
        .<String, String>chunk(100)
        .reader(itemReader)
        .processor(new ItemTransformer())
        .writer(System.out::println)
        .listener(new ChunkListenerImpl())
        .build();
  }

  @Bean
  ItemReaderImpl itemReader() {
    return new ItemReaderImpl();
  }

  static class ItemReaderImpl implements ItemReader<String> {

    private volatile int count;
    private static final int STREAM_SIZE = 1_000;

    @Override
    public String read() {
      if (getAndIncrement() < STREAM_SIZE) {
        return UUID.randomUUID().toString();
      }
      return null;
    }

    synchronized int getAndIncrement() {
      return count++;
    }

    synchronized void cleanup() {
      count = 0;
    }
  }

  static class ItemTransformer implements ItemProcessor<String, String> {

    @Override
    public String process(String item) {
      return item.substring(0, 8);
    }
  }

  static class ChunkListenerImpl implements ChunkListener {

    @Override
    public void beforeChunk(ChunkContext context) {
    }

    @Override
    public void afterChunk(ChunkContext context) {
      log.info(context.getStepContext().getStepExecution().getSummary());
    }

    @Override
    public void afterChunkError(ChunkContext context) {
      log.error("chunk error");
    }
  }
}
