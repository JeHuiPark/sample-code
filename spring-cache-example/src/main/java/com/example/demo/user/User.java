package com.example.demo.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;

@Getter
public class User {

  @Id
  long id;

  @Setter
  String name;

  @PersistenceConstructor
  User(long id, String name) {
    this.id = id;
    this.name = name;
  }

  public User(String name) {
    this.name = name;
  }
}
