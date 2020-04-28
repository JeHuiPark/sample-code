package com.example.demo.user;

import io.requery.Entity;
import io.requery.Generated;
import io.requery.Key;
import io.requery.Persistable;
import io.requery.Table;
import lombok.ToString;

@ToString
@Entity
@Table(name = "example_user")
class User implements Persistable {

  @Key
  @Generated
  int id;
  String firstName;
  String lastName;

  User() {
  }

  User(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }
}
