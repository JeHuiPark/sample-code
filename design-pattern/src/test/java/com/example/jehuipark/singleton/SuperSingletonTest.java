package com.example.jehuipark.singleton;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


class SuperSingletonTest {

  @Test
  void test1() {
    DefaultSingleton.register();
    AnotherSingleton.register();

    assertEquals(DefaultSingleton.class, SuperSingleton.getInstance().getClass());
    assertEquals(AnotherSingleton.class, SuperSingleton.lookup("anotherSingleton").getClass());

    assertEquals(SuperSingleton.getInstance(), SuperSingleton.getInstance());
    assertEquals(
        SuperSingleton.lookup("anotherSingleton"),
        SuperSingleton.lookup("anotherSingleton"));
  }


  private static class AnotherSingleton extends SuperSingleton {

    private static final AnotherSingleton instance = new AnotherSingleton();

    private AnotherSingleton() {
      register("anotherSingleton", this);
    }

    static void register() {
      // for class load
    }
  }

  private static class DefaultSingleton extends SuperSingleton {

    private static final DefaultSingleton instance = new DefaultSingleton();

    private DefaultSingleton() {
      register("default", this);
    }

    static void register() {
      // for class load
    }
  }
}
