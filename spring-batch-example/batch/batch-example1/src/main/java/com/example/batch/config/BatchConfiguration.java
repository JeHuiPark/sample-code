package com.example.batch.config;

import java.util.UUID;
import java.util.concurrent.atomic.LongAdder;
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
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

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
    return jobBuilderFactory.get("uuid-generate-job")
        .listener(new JobExecutionListener() {

          @Override
          public void beforeJob(JobExecution jobExecution) {
            itemReader.cleanup();
          }

          @Override
          public void afterJob(JobExecution jobExecution) {
          }
        })
        .start(step(null))
        .next(customStep())
        .build();
  }

  @Bean
  public Step step(ItemReader<String> itemReader) {
    return stepBuilderFactory
        .get("uuid-generate-job-step")
        .<String, String>chunk(100)
        .reader(itemReader)
        .processor(new ItemTransformer())
        .writer(itemWriter())
        .listener(new ChunkListenerImpl())
        .taskExecutor(asyncTaskExecutor()) // 스텝 병렬화
        .build();
  }

  @Bean
  public Step customStep() {
    LongAdder counter = new LongAdder();
    return stepBuilderFactory
        .get("step2")
        .tasklet(
            (contribution, chunkContext) -> {
              counter.increment();
              if (counter.sum() == 100) {
                counter.reset();
                return RepeatStatus.FINISHED;
              }
              return RepeatStatus.CONTINUABLE;
            })
        .stream(new ItemStream() {
          @Override
          public void open(ExecutionContext executionContext) throws ItemStreamException {
            log.info("step2 stream open, counter = {}", counter.sum());
          }

          @Override
          public void update(ExecutionContext executionContext) throws ItemStreamException {
            log.info("step2 stream update, counter = {}", counter.sum());
          }

          @Override
          public void close() throws ItemStreamException {
            log.info("step2 stream close, counter = {}", counter.sum());
          }
        })
        .build();
  }

  @Bean
  ItemReaderImpl itemReader() {
    return new ItemReaderImpl();
  }

  @Bean
  ItemWriter<String> itemWriter() {
    return e -> log.info("{}", e);
  }

  @Bean
  public TaskExecutor asyncTaskExecutor(){
    return new SimpleAsyncTaskExecutor("spring_batch");
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
