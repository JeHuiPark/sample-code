package com.example.jehuipark.abstract_factory.server;

import com.example.jehuipark.abstract_factory.server.mac.MacFactory;
import com.example.jehuipark.abstract_factory.server.window.WindowFactory;

public abstract class ModuleFactory {

  public static ModuleFactory getFactory() {
    return LazyFactoryLoader.INSTANCE;
  }

  public abstract ModuleA createModule();

  private static class LazyFactoryLoader {

    private final static ModuleFactory INSTANCE;

    static {
      if (System.getProperty("os.name").toLowerCase().contains("win")) {
        INSTANCE = new WindowFactory();
      } else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
        INSTANCE = new MacFactory();
      } else {
        throw new UnsupportedOperationException("unsupported operation");
      }
    }
  }
}
