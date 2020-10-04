package com.example.batch.internal;

import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

interface FinalizeStepTask extends Tasklet, ActiveJobContext {

  static FinalizeStepTask create(JobStepManager jobStepManager) {
    return ((contribution, chunkContext) -> {
      jobStepManager.release();
      return RepeatStatus.FINISHED;
    });
  }
}
