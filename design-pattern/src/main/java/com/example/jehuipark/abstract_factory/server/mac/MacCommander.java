package com.example.jehuipark.abstract_factory.server.mac;

import com.example.jehuipark.abstract_factory.server.Commander;

class MacCommander implements Commander {

  @Override
  public void execute() {
    System.out.println("mac specific command");
  }
}
