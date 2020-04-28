package com.example.jehuipark;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@SuppressWarnings("InnerClassMayBeStatic")
@DisplayName("테스트")
class JunitExample {

  @DisplayName("nested")
  @Nested
  class Context_Sample {

    @Test
    @DisplayName("example")
    void test1() {
    }
  }
}
