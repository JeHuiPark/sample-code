package com.example.demo.user;

import java.util.Collection;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final CacheManager cacheManager;

  public User getUserById(long id) {
    return userRepository.findById(id)
        .orElseThrow(IllegalArgumentException::new);
  }

  public Collection<User> getAllUserByIds(Collection<Long> ids) {
    return ids.stream().map(this::getUserById).collect(Collectors.toList());
  }

  @PostConstruct
  void warmup() {
    for (int i = 0; i < 100_000; i++) {
      userRepository.save(new User("test_user:" + i));
    }
    var cache = cacheManager.getCache("users");
    userRepository.findAll().forEach(e -> cache.put(e.getId(), e));
  }
}
