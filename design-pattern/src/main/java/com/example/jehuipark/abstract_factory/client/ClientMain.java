package com.example.jehuipark.abstract_factory.client;

import com.example.jehuipark.abstract_factory.server.ModuleFactory;

public class ClientMain {

  public static void main(String[] args) {
    ModuleFactory moduleFactory = ModuleFactory.getFactory();
    ClientProgram clientProgram = new ClientProgram(moduleFactory);

    clientProgram.macro();
  }
}
