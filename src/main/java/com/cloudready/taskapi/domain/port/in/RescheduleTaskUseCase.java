package com.cloudready.taskapi.domain.port.in;

import com.cloudready.taskapi.application.service.TaskResult;

public interface RescheduleTaskUseCase {
    record RescheduleTaskCommand(String taskId, String dueDate){}
    TaskResult reschedule(RescheduleTaskCommand command);
}
