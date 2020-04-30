package com.example.jehuipark.prototype_pattern;

public interface Prototype extends Cloneable {

  void configuration(String configuration);

  void operation();

  Prototype clone();
}
