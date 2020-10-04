package com.example.impl;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

@DynamicUpdate
@Data
@Table(name = "something")
@Entity
class SomethingStatusEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String status1;

  private String status2;

  void setStatus2(String status2) {
    this.status2 = status2;
  }

  void setStatus1(String status1) {
    this.status1 = status1;
  }
}
