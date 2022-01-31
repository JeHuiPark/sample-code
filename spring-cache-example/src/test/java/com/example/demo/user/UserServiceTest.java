package com.example.demo.user;

import static java.util.stream.Collectors.toList;

import java.util.stream.LongStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
class UserServiceTest {

  @Autowired
  UserService userService;

  @Autowired
  UserRepository userRepository;

  @Test
  void test1() {
    for (int i = 1; i <= 100_000; i++) {
      userService.getUserById(i);
    }
  }

  @Test
  void test2() {
    userService.getAllUserByIds(
        LongStream.range(1, 100_000)
            .boxed()
            .collect(toList())
    );
  }

  @Test
  void test3() {
    userRepository.findAllById(
        LongStream.range(1, 100_000)
            .boxed()
            .collect(toList())
    );
  }
}
