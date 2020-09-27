package com.example.impl;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan
@EnableJpaRepositories
@EntityScan
@Configuration
public class SomethingAutoConfiguration {

  SomethingAutoConfiguration() {
    System.out.println("SomethingAutoConfiguration initialize");
  }
}
