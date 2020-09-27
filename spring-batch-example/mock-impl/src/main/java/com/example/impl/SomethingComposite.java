package com.example.impl;

import com.example.api.SomethingAPI;
import com.example.api.SomethingStatus;
import com.example.api.SomethingStatusService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class SomethingComposite implements SomethingAPI, SomethingStatusService {

  private final SomethingStatusEntityRepository repository;

  @Modifying
  @Transactional
  @Override
  public void function1(long id) {
    var entity = repository.findById(id).orElseThrow();
    entity.setStatus1(LocalDateTime.now().toString());
  }

  @Modifying
  @Transactional
  @Override
  public void function2(long id) {
    var entity = repository.findById(id).orElseThrow();
    entity.setStatus2(LocalDateTime.now().toString());
  }

  @Transactional(readOnly = true)
  @Override
  public SomethingStatus getSomethingStatus(long id) {
    var entity = repository.findById(id).orElseThrow();
    return new InternalSomethingStatus(entity);
  }

  private static class InternalSomethingStatus implements SomethingStatus {

    private final long id;

    private final String status1;

    private final String status2;

    InternalSomethingStatus(SomethingStatusEntity entity) {
      this.id = entity.getId();
      this.status1 = entity.getStatus1();
      this.status2 = entity.getStatus2();
    }

    @Override
    public long getId() {
      return id;
    }

    @Override
    public String getStatus1() {
      return status1;
    }

    @Override
    public String getStatus2() {
      return status2;
    }
  }
}
