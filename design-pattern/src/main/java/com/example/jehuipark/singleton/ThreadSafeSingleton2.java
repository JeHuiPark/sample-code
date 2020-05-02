package com.example.jehuipark.singleton;

final class ThreadSafeSingleton2 {

  public static ThreadSafeSingleton2 getInstance() {
    return Inner.INSTANCE;
  }

  private static final class Inner {
    static final ThreadSafeSingleton2 INSTANCE = new ThreadSafeSingleton2();
  }
}
