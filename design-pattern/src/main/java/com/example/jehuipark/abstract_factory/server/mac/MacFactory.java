package com.example.jehuipark.abstract_factory.server.mac;

import com.example.jehuipark.abstract_factory.server.ModuleFactory;
import com.example.jehuipark.abstract_factory.server.ModuleA;

public class MacFactory extends ModuleFactory {

  public MacFactory() {
    System.out.println("factory constructor for MAC");
  }

  @Override
  public ModuleA createModule() {
    return new MacModuleA();
  }
}
