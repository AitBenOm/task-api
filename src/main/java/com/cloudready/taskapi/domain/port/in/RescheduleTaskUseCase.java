package com.cloudready.taskapi.domain.port.in;

import com.cloudready.taskapi.application.service.TaskResult;

import java.time.Instant;

public interface RescheduleTaskUseCase {
    record RescheduleTaskCommand(String taskId, String dueDate){}
    TaskResult Reschedule(RescheduleTaskCommand command);
}
