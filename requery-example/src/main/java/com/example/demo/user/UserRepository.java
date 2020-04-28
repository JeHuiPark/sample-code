package com.example.demo.user;

import io.requery.Persistable;
import io.requery.query.Result;
import io.requery.sql.EntityDataStore;
import org.springframework.stereotype.Repository;

@Repository
class UserRepository {

  private final EntityDataStore<Persistable> entityDataStore;

  public UserRepository(EntityDataStore<Persistable> entityDataStore) {
    this.entityDataStore = entityDataStore;
  }

  User findOne() {
    Result<User> query = entityDataStore.select(User.class)
        .limit(1)
        .get();

    return query.first();
  }

  User save(String a, String b) {
    UserEntity userEntity = new UserEntity(a, b);
    return entityDataStore.insert(userEntity);
  }
}
