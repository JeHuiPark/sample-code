package com.example.jehuipark.singleton;

import java.util.HashMap;
import java.util.Map;

abstract class SuperSingleton {

  private static final InstanceRegistry INSTANCE_REGISTRY = new InstanceRegistry();

  protected SuperSingleton() {}

  protected static void register(String name, SuperSingleton singleton) {
    INSTANCE_REGISTRY.addRegistry(name, singleton);
  }

  public static SuperSingleton getInstance() {
    SuperSingleton lookupInstance = defaultLookup();
    if (lookupInstance == null) {
      throw new IllegalStateException();
    }
    return lookupInstance;
  }

  private static SuperSingleton defaultLookup() {
    return lookup("default");
  }

  public static SuperSingleton lookup(String name) {
    return INSTANCE_REGISTRY.lookup(name);
  }

  private static class InstanceRegistry {
    private static final Map<String, SuperSingleton> REGISTRY = new HashMap<>();

    void addRegistry(String name, SuperSingleton instance) {
      if (REGISTRY.get(name) != null) {
        throw new IllegalStateException();
      }
      REGISTRY.put(name, instance);
    }

    SuperSingleton lookup(String name) {
      return REGISTRY.get(name);
    }
  }
}
