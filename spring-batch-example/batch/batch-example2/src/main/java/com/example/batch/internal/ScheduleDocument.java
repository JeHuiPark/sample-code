package com.example.batch.internal;

import static org.springframework.data.mongodb.core.mapping.FieldType.OBJECT_ID;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@ToString
@Document(collection = "schedule")
public class ScheduleDocument {

  @MongoId(targetType = OBJECT_ID)
  private String mongoId;

  private long target;

  private String status;

  private String method;

  private LocalDateTime begin;

  private LocalDateTime end;

  private String audit;
}
