package com.example.jehuipark.singleton;

import java.util.concurrent.atomic.AtomicInteger;

final class ThreadSafeSingleton {

  private static ThreadSafeSingleton instance;
  private static final AtomicInteger initialCount = new AtomicInteger(0);

  private ThreadSafeSingleton() {
    initialCount.incrementAndGet();
  }

  public static ThreadSafeSingleton getInstance() {
    if (instance == null) {
      safetyInitialize();
    }
    return instance;
  }

  private static final Object _lock = new Object();
  private static void safetyInitialize() {
    synchronized (_lock) {
      if (instance == null) {
        instance = new ThreadSafeSingleton();
      }
    }
  }

  public int getInitializeCount() {
    return initialCount.get();
  }

  // for test
  static void contextClear() {
    instance = null;
    initialCount.set(0);
  }
}
