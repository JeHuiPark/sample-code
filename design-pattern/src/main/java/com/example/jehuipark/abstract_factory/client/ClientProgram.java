package com.example.jehuipark.abstract_factory.client;

import com.example.jehuipark.abstract_factory.server.ModuleA;
import com.example.jehuipark.abstract_factory.server.ModuleFactory;

class ClientProgram {

  private final ModuleFactory moduleFactory;

  ClientProgram(ModuleFactory moduleFactory) {
    this.moduleFactory = moduleFactory;
  }

  void macro() {
    ModuleA moduleA = moduleFactory.createModule();
    moduleA.function();

    moduleA
        .getCommander()
        .execute();
  }
}
