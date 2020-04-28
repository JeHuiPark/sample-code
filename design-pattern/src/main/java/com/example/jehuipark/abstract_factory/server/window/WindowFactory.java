package com.example.jehuipark.abstract_factory.server.window;

import com.example.jehuipark.abstract_factory.server.ModuleFactory;
import com.example.jehuipark.abstract_factory.server.ModuleA;

public class WindowFactory extends ModuleFactory {

  public WindowFactory() {
    System.out.println("factory constructor for WINDOW");
  }

  @Override
  public ModuleA createModule() {
    return new WindowModuleA();
  }
}
