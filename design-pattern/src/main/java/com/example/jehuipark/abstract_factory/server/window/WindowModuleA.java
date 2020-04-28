package com.example.jehuipark.abstract_factory.server.window;

import com.example.jehuipark.abstract_factory.server.Commander;
import com.example.jehuipark.abstract_factory.server.ModuleA;

class WindowModuleA implements ModuleA {

  @Override
  public void function() {
    System.out.println("window function");
  }

  @Override
  public Commander getCommander() {
    return new WindowCommander();
  }
}
