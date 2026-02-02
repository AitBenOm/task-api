package com.cloudready.taskapi.domain.port.in;

import com.cloudready.taskapi.application.service.TaskResult;
import com.cloudready.taskapi.domain.model.TaskStatus;

public interface ChangeStatusUseCase {
    record ChangeStatusCommand(String taskId, String to){}
    TaskResult changeTaskStatus(ChangeStatusCommand command);
}
