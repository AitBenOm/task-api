package com.cloudready.taskapi.domain.port.in;

import com.cloudready.taskapi.application.service.TaskResult;

import java.time.Instant;

public interface UpdateTaskDetailUseCase {
    record UpdateTaskCommand(String taskId, String title, String desc) {
    }

    TaskResult updateTaskCommand(UpdateTaskCommand command);
}
