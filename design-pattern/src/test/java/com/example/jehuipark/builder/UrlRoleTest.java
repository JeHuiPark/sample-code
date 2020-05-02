package com.example.jehuipark.builder;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


class UrlRoleTest {

  @Test
  void test1() {
    System.out.println(
        UrlRole
            .url("/a")
            .hasAnyRole("example1", "example2")
            .build()
    );

    System.out.println(
        UrlRole
            .url("/b", Method.DELETE)
            .hasAnyRole("example1")
            .build()
    );
  }

}
