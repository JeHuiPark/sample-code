package com.example.jehuipark.abstract_factory.server.window;

import com.example.jehuipark.abstract_factory.server.Commander;

class WindowCommander implements Commander {

  @Override
  public void execute() {
    System.out.println("window specific command");
  }
}
