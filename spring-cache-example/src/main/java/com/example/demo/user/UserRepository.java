package com.example.demo.user;

import java.util.Optional;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;

@CacheConfig(cacheNames = "users")
interface UserRepository extends CrudRepository<User, Long> {

//  @CachePut(key = "#result.id")
  @Override
  <S extends User> S save(S user);

  @Cacheable
  @Override
  Optional<User> findById(Long id);
}
