package mdc.example;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

class Example {

  private static final Logger logger = LoggerFactory.getLogger("logger.name");

  public static void main(String[] args) {
    MockApplication mockApplication = new MockApplication();

    for (int i = 0; i < 4; i++) {
      mockApplication.request();
    }

    mockApplication.shutdown();
  }

  static class MockApplication {

    final ExecutorService executorService = Executors.newFixedThreadPool(4,
        Executors.privilegedThreadFactory());

    void request() {
      executorService.submit(() -> {
        MDC.put("trace-id", UUID.randomUUID().toString().substring(0,8));
        doSomeThing();
        MDC.clear();
      });
    }

    void shutdown() {
      executorService.shutdown();
    }

    private void doSomeThing() {
      logger.debug("Hello World");
      logger.info("Hello World");
    }

  }
}
