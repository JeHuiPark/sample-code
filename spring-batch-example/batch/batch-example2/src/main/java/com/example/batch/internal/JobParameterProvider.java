package com.example.batch.internal;

import java.time.LocalDateTime;

public interface JobParameterProvider {
  String PARAMETER_KEY = "run.id";

  LocalDateTime getParameter();
}
