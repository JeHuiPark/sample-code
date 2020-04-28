package com.example.demo.user;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @see UserRepository
 */
@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
@DisplayName("UserRepository")
class UserRepositoryTest {

  @Autowired
  UserRepository userRepository;

  @Test
  @DisplayName("testname")
  void test1() {
    List<Integer> list = new ArrayList<>(10000);

    for (int i = 0; i < 10000; i++) {
      list.add(i);
    }

    log.info("start");
    list
        .parallelStream()
        .forEach(e -> userRepository.save("박", "제희"));
    log.info("end");
  }
}
