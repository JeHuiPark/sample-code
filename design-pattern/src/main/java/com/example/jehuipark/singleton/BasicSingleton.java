package com.example.jehuipark.singleton;

final class BasicSingleton {
  private static BasicSingleton instance;

  private BasicSingleton() {};

  // thread non safe
  public static BasicSingleton getInstance() {
    if (instance == null) {
      instance = new BasicSingleton();
    }
    return instance;
  }
}
