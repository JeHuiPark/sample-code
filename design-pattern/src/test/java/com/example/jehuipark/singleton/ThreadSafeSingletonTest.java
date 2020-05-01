package com.example.jehuipark.singleton;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;


class ThreadSafeSingletonTest {

  private ExecutorService nonBlock = Executors.newWorkStealingPool();

  @BeforeEach
  void testContextClear() {
    ThreadSafeSingleton.contextClear();
  }

  @RepeatedTest(1000)
  void test1() {
    Set<ThreadSafeSingleton> result = IntStream.range(0, 10)
        .mapToObj(v -> nonBlock.submit(ThreadSafeSingleton::getInstance))
        .collect(toList())
        .stream()
        .map(blocking)
        .collect(toSet());

    assertEquals(1, result.size());
    assertEquals(1,ThreadSafeSingleton.getInstance().getInitializeCount());
  }

  private Function<Future<ThreadSafeSingleton>, ThreadSafeSingleton> blocking = future -> {
    try {
      return future.get();
    } catch (Exception e) {
      throw new AssertionError();
    }
  };

}
