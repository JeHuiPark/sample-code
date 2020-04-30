package com.example.jehuipark.prototype_pattern;

class Client {

  private final Prototype prototype;

  Client(Prototype prototype) {
    this.prototype = prototype;
  }

  void operation(String configuration) {
    Prototype prototype = this.prototype.clone();
    prototype.configuration(configuration);
    prototype.operation();
  }
}
