package com.example.jehuipark.abstract_factory.server.mac;

import com.example.jehuipark.abstract_factory.server.Commander;
import com.example.jehuipark.abstract_factory.server.ModuleA;

class MacModuleA implements ModuleA {

  @Override
  public void function() {
    System.out.println("mac function");
  }

  @Override
  public Commander getCommander() {
    return new MacCommander();
  }
}
