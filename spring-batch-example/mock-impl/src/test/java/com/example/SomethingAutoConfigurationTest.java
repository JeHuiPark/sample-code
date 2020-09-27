package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test-init")
@SpringBootTest
class SomethingAutoConfigurationTest {

  @Autowired
  JdbcTemplate jdbcTemplate;

  @Test
  @DisplayName("context load test")
  void test2() {
    var resultMap = jdbcTemplate.queryForMap("select count(*) AS CNT from something");
    assertEquals(10_000L, resultMap.get("CNT"));
  }
}
