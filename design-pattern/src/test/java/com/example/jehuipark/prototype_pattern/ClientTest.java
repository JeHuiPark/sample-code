package com.example.jehuipark.prototype_pattern;

import org.junit.jupiter.api.Test;

class ClientTest {

  private Client clientA = new Client(new PrototypeConcreteA());
  private Client clientB = new Client(new PrototypeConcreteB());

  @Test
  void test1() {
    clientA.operation("a");
    clientB.operation("b");
  }

}
