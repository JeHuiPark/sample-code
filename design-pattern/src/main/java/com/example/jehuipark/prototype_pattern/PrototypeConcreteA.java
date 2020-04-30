package com.example.jehuipark.prototype_pattern;

class PrototypeConcreteA implements Prototype {

  private String configuration = "default_configuration";

  @Override
  public void configuration(String configuration) {
    this.configuration = configuration;
  }

  @Override
  public void operation() {
    System.out.println("PrototypeConcreteA configuration = " + configuration);
  }

  @Override
  public Prototype clone() {
    try {
      return (Prototype) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError();
    }
  }
}
